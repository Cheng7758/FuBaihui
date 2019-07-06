package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.OrderPreviewBean;
import com.example.zhanghao.woaisiji.bean.ProductPictureBean;
import com.example.zhanghao.woaisiji.bean.SliverMallBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 */
public class OrderPreviewActivity extends AppCompatActivity {
    /**
     * TitleBar
     */
    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;

    private TextView people;
    private TextView phone;
    private TextView address;
    private TextView express;
    private EditText explain;
    private TextView shoppings;
    private TextView total;
    private TextView mortgage;
    private TextView totalSubmit;
    private Button submit;
    private ListView shoppingSettlement;
    private Intent intent;
    private ImageView selectaddress;
    private String name;
    private String shouji;
    private String dizhi;
    private String addressId;
    private int[] goodsid;
    private int[] goodsnum;
    //    private int[] goodsCel;
    private int[] price;
    private int[] f_sorts;
    private int[] f_silver;
    private int[] max;
    private String[] goodsName;
    private int[] carRmb;
    private String score;
    private int type;
    private OrderPreviewBean orderPreviewBean;
    private LinearLayout llSelectContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);

        intent = getIntent();
        goodsid = intent.getIntArrayExtra("id");
        goodsnum = intent.getIntArrayExtra("num");
        type = intent.getIntExtra("type", 1);

        intView();
        initData();
        obtainDataFromServer();
    }


    private void intView() {
        //切换商城名字
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("确认订单");
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        //收货人
        people = (TextView) findViewById(R.id.people);
        //收货人电话
        phone = (TextView) findViewById(R.id.tv_phone);
        //收货地址
        address = (TextView) findViewById(R.id.tv_address);
        //快递费
        express = (TextView) findViewById(R.id.tv_express);
        //买家留言
        explain = (EditText) findViewById(R.id.et_Explain);
        //一共有几件商品
        shoppings = (TextView) findViewById(R.id.num);
        //合计价格
        total = (TextView) findViewById(R.id.Total);
        //金币抵押
        mortgage = (TextView) findViewById(R.id.tv_mortgage);
        //底部结算价格
        totalSubmit = (TextView) findViewById(R.id.TotalSubmit);
        //结算按钮
        submit = (Button) findViewById(R.id.Submit);
        //商品条目
        shoppingSettlement = (ListView) findViewById(R.id.lv_shoppingSettlement);
        //选择地址
        llSelectContact = (LinearLayout) findViewById(R.id.ll_select_contact);

        selectaddress = (ImageView) findViewById(R.id.imageView2);

    }

    private void initData() {
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {//后退按钮
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llSelectContact.setOnClickListener(new View.OnClickListener() {//收货人和收货地址，跳转到管理收货地址
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(OrderPreviewActivity.this,
                        PersonalHarvestAddressActivity.class).putExtra("is_click", "click"), 1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
//            Editable beizhu=explain.getText();

            @Override
            public void onClick(View view) {//提交订单
                if (type == 3) {
                    // 金币商城确认支付
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderPreviewActivity.this);
                    builder.setTitle("确认付款");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goldMallPayment();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();

                } else if (type == 4) {
                    // 银币商城确认支付
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderPreviewActivity.this);
                    builder.setTitle("确认付款");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sliverMallPayment();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();

                } else {
                    if (addressId == null) {
                        Toast.makeText(OrderPreviewActivity.this, "请选择收货地址!", Toast.LENGTH_SHORT).show();
                    } else {
                        //跳转到提交订单界面
                        Intent intent = new Intent(OrderPreviewActivity.this, OrdersubmissionActivity.class);
                        String beizhu = String.valueOf(explain.getText());
                        intent.addFlags(0);
                        intent.putExtra("goods_num", goodsnum);
                        intent.putExtra("goods_price", price);
                        intent.putExtra("goods_f_sorts", f_sorts);
                        intent.putExtra("goods_f_silver", f_silver);
                        intent.putExtra("goods_max", max);
                        intent.putExtra("goods_name", goodsName);
                        intent.putExtra("goods_id", goodsid);
                        intent.putExtra("car_rmb", carRmb);
                        intent.putExtra("plcid", addressId);
                        intent.putExtra("beuzhu", beizhu);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void goldMallPayment() {
        RequestParams requestParams = new RequestParams(ServerAddress.URL_ORDER_GOLD_MALL);
        if (goodsnum != null) {
            for (int i : goodsnum) {
                requestParams.addBodyParameter("goods_num[]", "" + i);//1
            }
        }
        if (price != null) {
            for (int i : price) {
                requestParams.addBodyParameter("goods_price[]", "" + i);//1
            }
        }
        if (f_sorts != null) {
            for (int i : f_sorts) {
                requestParams.addBodyParameter("goods_f_sorts[]", "" + i);//1
            }
        }
        if (f_silver != null) {
            for (int i : f_silver) {
                requestParams.addBodyParameter("goods_f_silver[]", "" + i);//1
            }
        }
        if (max != null) {
            for (int i : max) {
                requestParams.addBodyParameter("goods_max[]", "" + i);//1
            }
        }
        if (goodsName != null) {
            for (String i : goodsName) {
                requestParams.addBodyParameter("goods_name[]", "" + i);//1
            }
        }
        if (goodsid != null) {
            for (int i : goodsid) {
                requestParams.addBodyParameter("goods_id[]", "" + i);//1
            }
        }

        requestParams.addBodyParameter("user_id", (WoAiSiJiApp.getUid()));//1

        requestParams.addBodyParameter("plcid", addressId);//1
        requestParams.addBodyParameter("beuzhu", String.valueOf(explain.getText()));//1
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final SliverMallBean sliverMallBean = gson.fromJson(result, SliverMallBean.class);

//                if (sliverMallBean.getCode() == 200){

                String odernum = String.valueOf(sliverMallBean.getOrdernum());
                Double good_price = Double.valueOf(orderPreviewBean.getRmb());
                Intent intent = new Intent(OrderPreviewActivity.this, PaySuccessActivity.class);
                intent.putExtra("order_num", odernum);
                intent.putExtra("good_price", good_price);
                startActivity(intent);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void sliverMallPayment() {
        RequestParams requestParams = new RequestParams(ServerAddress.URL_ORDER_SLIVER_MALL);
        if (goodsnum != null) {
            for (int i : goodsnum) {
                requestParams.addBodyParameter("goods_num[]", "" + i);//1
            }
        }
        if (price != null) {
            for (int i : price) {
                requestParams.addBodyParameter("goods_price[]", "" + i);//1
            }
        }
        if (f_sorts != null) {
            for (int i : f_sorts) {
                requestParams.addBodyParameter("goods_f_sorts[]", "" + i);//1
            }
        }
        if (f_silver != null) {
            for (int i : f_silver) {
                requestParams.addBodyParameter("goods_f_silver[]", "" + i);//1
            }
        }
        if (max != null) {
            for (int i : max) {
                requestParams.addBodyParameter("goods_max[]", "" + i);//1
            }
        }
        if (goodsName != null) {
            for (String i : goodsName) {
                requestParams.addBodyParameter("goods_name[]", "" + i);//1
            }
        }
        if (goodsid != null) {
            for (int i : goodsid) {
                requestParams.addBodyParameter("goods_id[]", "" + i);//1
            }
        }

        requestParams.addBodyParameter("user_id", (WoAiSiJiApp.getUid()));//1
        requestParams.addBodyParameter("plcid", addressId);//1
        requestParams.addBodyParameter("beuzhu", String.valueOf(explain.getText()));//1
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final SliverMallBean sliverMallBean = gson.fromJson(result, SliverMallBean.class);

//                if (sliverMallBean.getCode() == 200){
                String odernum = String.valueOf(sliverMallBean.getOrdernum());
                Double good_price = Double.valueOf(orderPreviewBean.getRmb());
                Intent intent = new Intent(OrderPreviewActivity.this, PaySuccessActivity.class);
                intent.putExtra("order_num", odernum);
                intent.putExtra("good_price", good_price);
                startActivity(intent);
//                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            people.setText(data.getExtras().getString("nickname"));
            phone.setText(data.getExtras().getString("mobile"));
            address.setText(data.getExtras().getString("place"));
            addressId = data.getExtras().getString("addressId");
        }

    }

    private void obtainDataFromServer() {
        //0.3s
        String url = null;
        if (type == 3) {
            url = ServerAddress.URL_GOLDORDERPREVIEW;
        } else if (type == 4) {
            url = ServerAddress.URL_DUIHUANORDERPREVIEW;
        } else {
            url = ServerAddress.URL_ZHENGPINORDERPREVIEW;
        }
        RequestParams entity = new RequestParams(url);
        entity.addBodyParameter("user_id", (WoAiSiJiApp.getUid()));

        for (int i : goodsnum) {
            entity.addBodyParameter("goods_number[]", "" + i);//1
        }
        for (int i : goodsid) {
            entity.addBodyParameter("goods_id[]", "" + i);//1
        }

        //现在是2个了
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                orderPreviewBean = gson.fromJson(result, OrderPreviewBean.class);
                if (orderPreviewBean.getCode() == 200) {
                    if (type == 3) {
                        tv_title_bar_view_centre_title.setText("金积分商城");
                        submit.setText("立即付款");
                        mortgage.setText("当前账户可用" + orderPreviewBean.getInfo().getScore() + "金币");
                        total.setText(orderPreviewBean.getRmb() + "金币");
                        totalSubmit.setText(orderPreviewBean.getRmb() + "金币");
                    } else if (type == 4) {
                        tv_title_bar_view_centre_title.setText("银积分商城");
                        submit.setText("立即付款");
                        mortgage.setText("当前账户可用" + orderPreviewBean.getInfo().getSilver() + "银币");
                        total.setText(orderPreviewBean.getRmb() + "银币");
                        totalSubmit.setText(orderPreviewBean.getRmb() + "银币");
                    } else {
                        mortgage.setText("可用" + orderPreviewBean.getLdrmb() + "金币抵￥" + orderPreviewBean.getLdrmb());
                        total.setText("合计:￥" + Double.valueOf(orderPreviewBean.getRmb()));
                        totalSubmit.setText("合计:￥" + Double.valueOf(orderPreviewBean.getRmb()));
                        tv_title_bar_view_centre_title.setText("正品商城");
                    }
                    people.setText(orderPreviewBean.getPlclist().get(0).getNew_nickname());
                    phone.setText(orderPreviewBean.getPlclist().get(0).getNew_mobile());
                    address.setText(orderPreviewBean.getPlclist().get(0).getNew_place());


                    int count = 0;
                    for (int i = 0; i < goodsnum.length; i++) {
                        count = count + goodsnum[i];
                    }
                    shoppings.setText("共" + String.valueOf(count) + "件商品");
                    shoppingSettlement.setAdapter(new OrderPreviewAdapter(orderPreviewBean.getList()));
                    //地址id
                    addressId = orderPreviewBean.getPlclist().get(0).getId();
                    int max = 0;
                    for (int i = 0; i < orderPreviewBean.getList().size(); i++) {
                        if (Integer.valueOf(orderPreviewBean.getList().get(i).getCarrmb()) > max) {
                            max = Integer.valueOf(orderPreviewBean.getList().get(i).getCarrmb());
                        }
                    }
                    if (max == 0) {
                        express.setText("快递  免运费");
                    } else {
                        express.setText(max);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }

    class OrderPreviewAdapter extends BaseAdapter {
        private final DisplayImageOptions options;
        private List<OrderPreviewBean.ListBean> data;
        private final int[] goodsnum;
        private int type;

        public OrderPreviewAdapter(List<OrderPreviewBean.ListBean> list) {
            this.data = list;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.weixianshi)
                    .showImageForEmptyUri(R.drawable.weixianshi)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            this.goodsnum = intent.getIntArrayExtra("num");
            this.type = intent.getIntExtra("type", 1);
            carRmb = new int[data.size()];
            price = new int[data.size()];
            f_sorts = new int[data.size()];
            f_silver = new int[data.size()];
            max = new int[data.size()];
            goodsName = new String[data.size()];
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public OrderPreviewBean.ListBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {//
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(OrderPreviewActivity.this).
                        inflate(R.layout.itme_orderpreview, null);
                viewHolder.price = (TextView) convertView.findViewById(R.id.price);
                viewHolder.Stock = (TextView) convertView.findViewById(R.id.Stock);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.picture = (ImageView) convertView.findViewById(R.id.picture);
                viewHolder.num = (TextView) convertView.findViewById(R.id.num);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            OrderPreviewBean.ListBean item = getItem(position);

            viewHolder.Stock.setText("库存 " + item.getNumber() + "件");
            viewHolder.title.setText(item.getTitle());
            viewHolder.num.setText("×" + goodsnum[position]);
            carRmb[position] = Integer.parseInt(item.getCarrmb());
            price[position] = Integer.parseInt(item.getPrice());
            f_sorts[position] = Integer.parseInt(item.getF_sorts());
            f_silver[position] = Integer.parseInt(item.getF_silver());
            max[position] = Integer.parseInt(item.getMaxchit());
            goodsName[position] = data.get(position).getTitle();

            if (type == 4) {
                viewHolder.price.setText("银币" + item.getPrice());
            } else {
                viewHolder.price.setText("￥" + Double.valueOf(item.getPrice()));
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PRODUCTPICTURE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    ProductPictureBean productPicture = gson.fromJson(response, ProductPictureBean.class);
                    if (productPicture.getCode() == 200) {

                        final String url = ServerAddress.SERVER_ROOT + productPicture.getList().get(0);
                        ImageLoader.getInstance().displayImage(url, viewHolder.picture, options, null);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap();
                    map.put("cover", data.get(position).getCover());
                    return map;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(stringRequest);
            return convertView;
        }
    }

    static class ViewHolder {
        //价格
        public TextView price;
        //库存
        public TextView Stock;
        //内容
        public TextView title;
        //商品图片
        public ImageView picture;
        //当前商品数量
        public TextView num;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (Activity activity : PublicActivityList.activityList) {
                activity.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
