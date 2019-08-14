package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.OrderPreviewActivity;
import com.example.zhanghao.woaisiji.activity.PaymentMainActivity;
import com.example.zhanghao.woaisiji.activity.my.CheckBogisticsActivity;
import com.example.zhanghao.woaisiji.activity.my.MyEvaluateActivity;
import com.example.zhanghao.woaisiji.activity.my.MyOrderDetailActivity;
import com.example.zhanghao.woaisiji.bean.CancellationOrder;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.resp.RespAddOrderSuccess;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cheng on 2019/7/1.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderBean.DataBean> mList;
    private int mIndex;
    private LayoutInflater mInflater;
    private String uid = WoAiSiJiApp.getUid();  //用户UID
    private String token = WoAiSiJiApp.token;   //token

    public MyOrderAdapter(Context pContext, List<OrderBean.DataBean> pBeanList, int pIndex) {
        mContext = pContext;
        mList = pBeanList;
        mIndex = pIndex;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_order_show, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderBean.DataBean bean = mList.get(position);
        holder.store_name.setText(bean.getStore_name());    //商家名称
        holder.status_m.setText(bean.getStatus_m());    //付款状态
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + bean.getGoods_img())
                .into(holder.goods_img);
        holder.goods_name.setText(bean.getGoods_name());    //商品名
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + bean.getSymbol()).into(holder.symbol);
        holder.goods_price.setText(bean.getGoods_price());  //单价
        holder.goods_num.setText("×" + bean.getGoods_num());    //数量
        holder.pay_price.setText("共" + bean.getGoods_num() + "件商品，合计：￥" + bean.getPay_price()
                //需支付
                + "(含运费￥0.00)");

        if (bean.getStatus_o() == -1 || bean.getStatus_o() == -2) {
            holder.cancel.setVisibility(View.GONE); //取消订单
            holder.confirmation.setVisibility(View.GONE);//确认收货
        } else {
            holder.cancel.setVisibility(View.VISIBLE);//取消订单
            holder.confirmation.setVisibility(View.VISIBLE);//确认收货
        }

        if (bean.getStatus_o() == 0) {
            holder.cancel.setVisibility(View.VISIBLE);  //取消订单
            holder.payment.setVisibility(View.VISIBLE);//付款
            holder.refund.setVisibility(View.GONE);  //申请退款
            holder.check_the_logistics.setVisibility(View.GONE); //查看物流
            holder.confirmation.setVisibility(View.GONE);    //确认收货
            holder.evaluate.setVisibility(View.GONE); //评价
        } else if (bean.getStatus_o() == 1) {
            holder.refund.setVisibility(View.VISIBLE);  //申请退款
            holder.cancel.setVisibility(View.GONE);  //取消订单
            holder.payment.setVisibility(View.GONE);//付款
            holder.check_the_logistics.setVisibility(View.GONE); //查看物流
            holder.confirmation.setVisibility(View.GONE);    //确认收货
            holder.evaluate.setVisibility(View.GONE); //评价
        } else if (bean.getStatus_o() == 2) {
            holder.check_the_logistics.setVisibility(View.VISIBLE); //查看物流
            holder.confirmation.setVisibility(View.VISIBLE);    //确认收货
            holder.refund.setVisibility(View.GONE);  //申请退款
            holder.cancel.setVisibility(View.GONE);  //取消订单
            holder.payment.setVisibility(View.GONE);//付款
            holder.evaluate.setVisibility(View.GONE); //评价
        } else if (bean.getStatus_o() == 3) {
            holder.check_the_logistics.setVisibility(View.VISIBLE); //查看物流
            holder.evaluate.setVisibility(View.VISIBLE); //评价
            holder.confirmation.setVisibility(View.GONE);    //确认收货
            holder.refund.setVisibility(View.GONE);  //申请退款
            holder.cancel.setVisibility(View.GONE);  //取消订单
            holder.payment.setVisibility(View.GONE);//付款
        } else if (bean.getStatus_o() == 4) {

        }
        //取消订单
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("------id", bean.getId());
                quxiaoOrder(bean);  //取消订单
                mList.remove(position);
                holder.status_m.setText(bean.getStatus_m());    //改变订单状态
                notifyDataSetChanged();
            }
        });
        //查看物流
        holder.check_the_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看物流
                Log.e("------id", bean.getId());
                Intent intent = new Intent(mContext, CheckBogisticsActivity.class);
                intent.putExtra("id", bean.getWuliu());
                intent.putExtra("nu", bean.getWuliunum());
                mContext.startActivity(intent);
            }
        });
        //付款
        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到付款页面
//                submitOrder(bean.getGoods_price());
                Intent intent = new Intent(mContext, PaymentMainActivity.class);
                intent.putExtra("price", bean.getGoods_price());
                intent.putExtra("orderID",bean.getId());
                intent.putExtra("orderNumber",bean.getOrdernum());
//                intent.putExtra("pay_type2",bean.getPay_type()+"");
                intent.putExtra("merge","0");
                Log.i("qlb", "订单付款按钮Pay_type()-->" + bean.getPay_type());
                mContext.startActivity(intent);
            }
        });
        //申请退款
        holder.refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("------id", bean.getId());
                refund(bean);
            }
        });
        //确认收货
        holder.confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("------id", bean.getId());
                confirmReceipt(bean);
            }
        });
        //订单详情
        holder.relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("------id", bean.getId());
                Intent intent = new Intent(mContext, MyOrderDetailActivity.class);
                intent.putExtra("id", bean.getId());
                mContext.startActivity(intent);
            }
        });

        //　评价
        holder.evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyEvaluateActivity.class);
                intent.putExtra("bean", bean);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView store_name;
        private TextView status_m;
        private ImageView goods_img;
        private TextView goods_name;
        private ImageView symbol;
        private TextView goods_price;
        private TextView goods_num;
        private TextView pay_price;
        private RelativeLayout relativeLayout2;
        private TextView cancel, check_the_logistics, refund, payment, evaluate, confirmation;

        public ViewHolder(View itemView) {
            super(itemView);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            status_m = (TextView) itemView.findViewById(R.id.status_m);
            goods_img = (ImageView) itemView.findViewById(R.id.goods_img);
            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            symbol = (ImageView) itemView.findViewById(R.id.symbol);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            goods_num = (TextView) itemView.findViewById(R.id.goods_num);
            pay_price = (TextView) itemView.findViewById(R.id.pay_price);
            relativeLayout2 = (RelativeLayout) itemView.findViewById(R.id.relativeLayout2);

            cancel = (TextView) itemView.findViewById(R.id.cancel);
            check_the_logistics = (TextView) itemView.findViewById(R.id.check_the_logistics);
            refund = (TextView) itemView.findViewById(R.id.refund);
            payment = (TextView) itemView.findViewById(R.id.payment);
            evaluate = (TextView) itemView.findViewById(R.id.evaluate);
            confirmation = (TextView) itemView.findViewById(R.id.confirmation);
        }
    }

    //取消订单
    private void quxiaoOrder(OrderBean.DataBean pBean) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        params.put("id", pBean.getId());
        NetManager.getNetManager().getMyService(Myserver.url)
                .getCancellationOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancellationOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CancellationOrder value) {
                        if (value == null) {
                            return;
                        } else {
                            Toast.makeText(mContext, "" + value.getMsg(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //申请退款
    private void refund(OrderBean.DataBean pBean) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        params.put("id", pBean.getId());
        NetManager.getNetManager().getMyService(Myserver.url)
                .getRefund(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancellationOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CancellationOrder value) {
                        if (value == null) {
                            return;
                        } else {
                            Toast.makeText(mContext, "" + value.getMsg(), Toast.LENGTH_SHORT)
                                    .show();
                            mList.remove(value);
                            notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //确认收货
    private void confirmReceipt(OrderBean.DataBean pBean) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        params.put("id", pBean.getId());
        NetManager.getNetManager().getMyService(Myserver.url)
                .getConfirmReceipt(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancellationOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CancellationOrder value) {
                        if (value == null) {
                            return;
                        } else {
                            Toast.makeText(mContext, "" + value.getMsg(), Toast.LENGTH_SHORT)
                                    .show();
                            mList.remove(value);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 提交订单
     * @param pGoods_price
     */
    private strictfp void submitOrder(final String pGoods_price) {
        StringRequest oderListDataUrl = new StringRequest(Request.Method.POST,
                ServerAddress.URL_ORDER_PREVIEW_SUBMIT_ODER_LIST_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                Log.d("requestParams", "onSuccess: " + response);
                RespAddOrderSuccess respAddOrderSuccess = gson.fromJson(response, RespAddOrderSuccess.class);
                Log.i("qlb-response-list_data>","参数："+response.toString());
                if (respAddOrderSuccess.getCode() == 200) {
                    Intent intent = new Intent(mContext, PaymentMainActivity.class);
                    intent.putExtra("price",pGoods_price);
                    intent.putExtra("orderID",respAddOrderSuccess.getId().getId());
                    intent.putExtra("orderNumber",respAddOrderSuccess.getId().getOrdernum());
                    intent.putExtra("merge","1");
                    mContext.startActivity(intent);
                    return;
                }
                if (TextUtils.isEmpty(respAddOrderSuccess.getMsg()))
                    Toast.makeText(mContext, "订单生成失败!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext, respAddOrderSuccess.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());//
                params.put("token", WoAiSiJiApp.token);//1
                /*params.put("pay_type", isSilver ? "4" : "0");//1
                params.put("beizhu", "");//1

                params.put("plcid", addressBean.getId());//1
                String ins = "";
                for (String i : cardIdList) {
                    ins+=i+",";
                }
                params.put("cart_id[]", ins);//1
                String ins2 = "";
                for (String i : couponIdList) {
                    ins2+=i+",";
                }
                params.put("coupon", ins2);//1*/
//                Log.i("qlb-response-list_data>","返回："+params);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(oderListDataUrl);
    }
}
