package com.example.zhanghao.woaisiji.base.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.OrdersubmissionActivity;
import com.example.zhanghao.woaisiji.activity.PersonalOrderFormDetailActivity;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.OrderFormBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.utils.DpTransPx;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/20.
 */
public class OrderFormDetailAllPager extends BasePagerDetail implements PullToRefreshLayout.OnRefreshListener {


    private static final int TYPE_ORDER = 1;
    private static final int TYPE_CANCEL_ORDER = 2;
    private BaseListView lvOrderFormAllList;
    //    private ListView lvOrderFormAllList;
    private OrderFormBean orderFromBean;

    private PullToRefreshLayout refreshView;

    private List<OrderFormBean.OrderFormAll> orderFormAllList;
    // 后台返回的数据是从-1开始的
    private String[] goodsStatus = {"已过期", "待发货", "已发货", "已退货", "已取消", ""};
    private String[] goodsAlipay = {"待付款", "已付款"};
    private int type = 1;
    private int pager = 1;
    private int status = 0;
    private int alipay = 0;
    private AlterResultBean resultBean;
    private OrderFormAllAdapter orderFormAllAdapter;
    private boolean isFirst = true;

    public OrderFormDetailAllPager(Activity activity, int type, int alipay, int status) {
        super(activity);
        this.type = type;
        this.status = status;
        this.alipay = alipay;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_order_form_all, null);
        lvOrderFormAllList = (BaseListView) view.findViewById(R.id.lv_order_form_all_list);
//        lvOrderFormAllList = (ListView) view.findViewById(R.id.lv_order_form_all_list);
        refreshView = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void initData() {

        // 获取服务器数据
        orderFormAllList = new ArrayList<>();
        orderFormAllAdapter = new OrderFormAllAdapter();
        getDataFromServer();
    }

    private void getDataFromServer() {
        StringRequest orderFormRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ORDER_FORM_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_ORDER);
                if (orderFromBean.code == 200) {
                    if (orderFromBean.list != null) {
                        for (int i = 0; i < orderFromBean.list.size(); i++) {
                            if (orderFromBean.list.get(i).data != null) {
                                orderFormAllList.add(orderFromBean.list.get(i));
                            }

                        }
//                        isFirst = true;
                        if (isFirst) {
                            isFirst = false;
                            lvOrderFormAllList.setAdapter(orderFormAllAdapter);
                        } else {
                            orderFormAllAdapter.notifyDataSetChanged();
                        }


                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
//                Log.d("activity_type_detail", "" + type);
                params.put("type", String.valueOf(type));
                params.put("p", String.valueOf(pager));
                if (status + alipay < 3) {
                    params.put("status", String.valueOf(status));
                    if (type == 1) {
                        params.put("alipay", String.valueOf(alipay));
                    }
                }

                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(orderFormRequest);
    }

    // 取消订单
    private void cancelOrder(final String id) {
        StringRequest cancelOrderRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_CANCEL_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_CANCEL_ORDER);
                if (resultBean.code == 200) {
                    Toast.makeText(mActivity, resultBean.msg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("type", String.valueOf(type));
                params.put("id", id);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(cancelOrderRequest);
    }

    private void transServerData(String response, int types) {
        Gson gson = new Gson();
        switch (types) {
            case TYPE_ORDER:
                orderFromBean = gson.fromJson(response, OrderFormBean.class);
                break;
            case TYPE_CANCEL_ORDER:
                resultBean = gson.fromJson(response, AlterResultBean.class);
                break;
        }

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pager = 1;
                getDataFromServer();
                // 千万别忘了告诉控件刷新完毕了哦！
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pager = pager + 1;
                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    /* */
    public class OrderFormAllAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderFormAllList.size();
        }

        @Override
        public OrderFormBean.OrderFormAll getItem(int i) {
            return orderFormAllList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.pager_order_form_all_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.lvOrderFormItems = (ExpandableListView) view.findViewById(R.id.lv_order_form_items);
//                viewHolder.lvOrderFormItems = (ListView) view.findViewById(R.id.lv_order_form_items);
                viewHolder.llOrderFormSetHeight = (LinearLayout) view.findViewById(R.id.ll_order_form_set_height);

                viewHolder.tvOrderFormTotal = (TextView) view.findViewById(R.id.tv_order_form_total);
                viewHolder.btnOrderFormConfirmAccept = (Button) view.findViewById(R.id.btn_order_form_confirm_accept);
                viewHolder.btnOrderFormCancelOrder = (Button) view.findViewById(R.id.btn_order_form_cancel_order);
                viewHolder.btnOrderFormViewDetail = (Button) view.findViewById(R.id.btn_order_form_view_detail);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final OrderFormBean.OrderFormAll item = getItem(i);

           /* dataList = new ArrayList<>();
            for (int j = 0; j < item.data.size(); j++) {
                dataList.add(item.data.get(j));
            }*/

            if ("3".equals(item.status)) {
                viewHolder.btnOrderFormCancelOrder.setVisibility(View.GONE);
            }
            if (type == 1) {
                String orderTotal = "共" + item.data.size() + "件商品   " + "合计:￥" + item.scores + "元";
                viewHolder.tvOrderFormTotal.setText(orderTotal);
            } else if (type == 2) {
                String orderTotal = "共" + item.data.size() + "件商品   " + "合计:￥" + item.scores + "银币";
                viewHolder.tvOrderFormTotal.setText(orderTotal);

            } else {
                String orderTotal = "共" + item.data.size() + "件商品   " + "合计:￥" + item.scores + "金币";
                viewHolder.tvOrderFormTotal.setText(orderTotal);

            }


            // 已付款的商品没有取消订单和付款项
            if ("1".equals(item.alipay)) {
                viewHolder.btnOrderFormCancelOrder.setVisibility(View.GONE);
                viewHolder.btnOrderFormConfirmAccept.setVisibility(View.GONE);
            } else {
                viewHolder.btnOrderFormCancelOrder.setVisibility(View.VISIBLE);
                viewHolder.btnOrderFormConfirmAccept.setVisibility(View.VISIBLE);
            }
            viewHolder.btnOrderFormCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 取消订单
                    cancelOrder(item.id);
                    viewHolder.btnOrderFormCancelOrder.setVisibility(View.GONE);
                }
            });
            viewHolder.btnOrderFormConfirmAccept.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, OrdersubmissionActivity.class);
                    intent.addFlags(1);
                    intent.putExtra("ordernum", item.ordernum);

                    mActivity.startActivity(intent);
//                    Toast.makeText(mActivity, "确认付款", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.btnOrderFormViewDetail.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    String[] orderId = new String[item.data.size()];
                    String[] goodsNum = new String[item.data.size()];
                    String[] goodsId = new String[item.data.size()];
                    String[] goodsCel = new String[item.data.size()];
                    String[] goodTitle = new String[item.data.size()];
                    String[] goodPrice = new String[item.data.size()];
                    String[] goodPic = new String[item.data.size()];
                    for (int i = 0; i < item.data.size(); i++) {
                        orderId[i] = item.data.get(0).id;
                        goodsId[i] = item.data.get(i).goods_id;
                        goodsNum[i] = item.data.get(i).goods_num;
                        goodsCel[i] = item.data.get(i).goods_celnum;
                        goodTitle[i] = item.data.get(i).goods_name;
                        goodPrice[i] = item.data.get(i).goods_price;
                        goodPic[i] = item.data.get(i).cover;
                    }
                    // 查看详情
                    Intent intent = new Intent(mActivity, PersonalOrderFormDetailActivity.class);

                    intent.putExtra("order_id", item.id);
                    intent.putExtra("ordernum", item.ordernum);
                    intent.putExtra("time", item.time);
                    intent.putExtra("nickname", item.nickname);
                    intent.putExtra("mobile", item.mobile);
                    intent.putExtra("place", item.place);
                    intent.putExtra("good_id", goodsId);
                    intent.putExtra("good_title", goodTitle);
                    intent.putExtra("good_price", goodPrice);
                    intent.putExtra("good_num", goodsNum);
                    intent.putExtra("good_cel", goodsCel);
                    intent.putExtra("good_pic", goodPic);
                    intent.putExtra("orderId", orderId);
                    intent.addFlags(type);
                    mActivity.startActivity(intent);
                }
            });

            ViewGroup.LayoutParams params = viewHolder.lvOrderFormItems.getLayoutParams();
            int size = item.data.size();


            params.height = DpTransPx.Dp2Px(mActivity, 90) * size + DpTransPx.Dp2Px(mActivity, 40);
            viewHolder.lvOrderFormItems.setLayoutParams(params);

            viewHolder.lvOrderFormItems.setAdapter(new OrderFormExpandListView(item));
            viewHolder.lvOrderFormItems.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    return true;
                }
            });
            viewHolder.lvOrderFormItems.expandGroup(0);
            viewHolder.lvOrderFormItems.setGroupIndicator(null);

            return view;
        }
    }


    class OrderFormExpandListView extends BaseExpandableListAdapter {

        private DisplayImageOptions options;
        private OrderFormBean.OrderFormAll orderFormAll;

        public OrderFormExpandListView(OrderFormBean.OrderFormAll orderFormAll) {
            this.orderFormAll = orderFormAll;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int i) {
            return orderFormAll.data.size();
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderTitle viewHolderTitle;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.order_form_items_lists_title, null);
                viewHolderTitle = new ViewHolderTitle();
                viewHolderTitle.tvOrderFormOrderNum = (TextView) view.findViewById(R.id.tv_order_form_source);
                viewHolderTitle.tvOrderFormStatus = (TextView) view.findViewById(R.id.tv_order_form_status);
                viewHolderTitle.tvOrderFormAlipay = (TextView) view.findViewById(R.id.tv_order_form_alipay);
//                viewHolderTitle.rlOrderFormItemTitle = (RelativeLayout) view.findViewById(R.id.rl_order_form_item_title);

                view.setTag(viewHolderTitle);
            } else {
                viewHolderTitle = (ViewHolderTitle) view.getTag();
            }
            if (Integer.parseInt(orderFormAll.status) < goodsStatus.length) {
                viewHolderTitle.tvOrderFormStatus.setText(goodsStatus[Integer.parseInt(orderFormAll.status) + 1]);
            } else {
                viewHolderTitle.tvOrderFormStatus.setText(orderFormAll.status);
            }

            viewHolderTitle.tvOrderFormOrderNum.setText("订单号:" + orderFormAll.ordernum);
            viewHolderTitle.tvOrderFormAlipay.setText(goodsAlipay[Integer.parseInt(orderFormAll.alipay)]);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, DpTransPx.Dp2Px(mActivity, 36));
            view.setLayoutParams(lp);
            /*ViewGroup.LayoutParams params = viewHolderTitle.rlOrderFormItemTitle.getLayoutParams();
            params.height = ;
            viewHolderTitle.rlOrderFormItemTitle.setLayoutParams(params);*/
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderItem viewHolderItem;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.order_form_items_lists, null);
                viewHolderItem = new ViewHolderItem();
                viewHolderItem.llOrderFormEnterDetail = (LinearLayout) view.findViewById(R.id.ll_order_form_enter_detail);
                viewHolderItem.ivOrderFormIcon = (ImageView) view.findViewById(R.id.iv_order_form_icon);
                viewHolderItem.tvOrderFormTitle = (TextView) view.findViewById(R.id.tv_order_form_title);
                viewHolderItem.tvOrderFormGolds = (TextView) view.findViewById(R.id.tv_order_form_golds);
                viewHolderItem.tvOrderFormSilvers = (TextView) view.findViewById(R.id.tv_order_form_silvers);
                viewHolderItem.tvOrderFormOriginalPrice = (TextView) view.findViewById(R.id.tv_order_form_original_price);
                viewHolderItem.tvOrderFormCurrent = (TextView) view.findViewById(R.id.tv_order_form_current_price);
                viewHolderItem.tvOrderFormGoodsNum = (TextView) view.findViewById(R.id.tv_order_form_goods_num);

                view.setTag(viewHolderItem);
            } else {
                viewHolderItem = (ViewHolderItem) view.getTag();
            }
            final OrderFormBean.OrderFormData item = orderFormAll.data.get(i1);
            viewHolderItem.tvOrderFormTitle.setText(item.goods_name);
            if (type == 2) {
                viewHolderItem.tvOrderFormGolds.setVisibility(View.GONE);
                viewHolderItem.tvOrderFormSilvers.setVisibility(View.GONE);
            } else {
                viewHolderItem.tvOrderFormGolds.setVisibility(View.VISIBLE);
                viewHolderItem.tvOrderFormSilvers.setVisibility(View.VISIBLE);
                viewHolderItem.tvOrderFormGolds.setText("金币:" + item.goods_f_sorts);
                viewHolderItem.tvOrderFormSilvers.setText("银币:" + item.goods_f_silver);
            }

            viewHolderItem.tvOrderFormOriginalPrice.setText(item.goods_price);
            viewHolderItem.tvOrderFormCurrent.setText(item.goods_maxchit);
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.cover, viewHolderItem.ivOrderFormIcon, options, null);

            viewHolderItem.llOrderFormEnterDetail.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    Intent intent;
//                    if (type == 1) {
                    intent = new Intent(mActivity, ProductDetailActivity2.class);
//                    } else {
//                        intent = new Intent(mActivity, ExchangeProductdetail.class);
//                    }

                    intent.putExtra("id", item.goods_id);
                    intent.putExtra("type", type);
                    mActivity.startActivity(intent);

//                    Toast.makeText(mActivity, "订单ID：" + item.goods_id, Toast.LENGTH_SHORT).show();
                }
            });

            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, DpTransPx.Dp2Px(mActivity, 90));
            view.setLayoutParams(lp);

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }


    static class ViewHolderTitle {
        public TextView tvOrderFormOrderNum;
        public TextView tvOrderFormStatus;
        public TextView tvOrderFormAlipay;

    }

    static class ViewHolderItem {
        public TextView tvOrderFormTitle;
        public TextView tvOrderFormGolds;
        public TextView tvOrderFormSilvers;
        public TextView tvOrderFormOriginalPrice;
        public TextView tvOrderFormCurrent;
        public TextView tvOrderFormGoodsNum;
        public ImageView ivOrderFormIcon;
        public LinearLayout llOrderFormEnterDetail;
    }

    static class ViewHolder {
        public LinearLayout llOrderFormSetHeight;
        public ExpandableListView lvOrderFormItems;
//        public ListView lvOrderFormItems;


        public TextView tvOrderFormTotal;

        public Button btnOrderFormConfirmAccept;
        public Button btnOrderFormCancelOrder;
        public Button btnOrderFormViewDetail;

    }
}
