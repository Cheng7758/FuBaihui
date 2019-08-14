package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.FBHStoreActivity;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.adapter.viewholder.CuponHolder;
import com.example.zhanghao.woaisiji.adapter.viewholder.FBHStoreDataDetailHolder;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.CouponBean;
import com.example.zhanghao.woaisiji.resp.ExchangeBean;
import com.example.zhanghao.woaisiji.resp.RespAddOrder;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 商家优惠券
 */
public class CouponAdapter extends BaseQuickAdapter<CouponBean.DataBean,
        CuponHolder> {

    private Activity context;


    public CouponAdapter(Activity activity, List<CouponBean.DataBean> data) {
        super(R.layout.coupon_detail, data);
        this.context = activity;
    }

    @Override
    protected void convert(CuponHolder helper, CouponBean.DataBean item) {
        initContentView(helper, item);
    }

    private void initContentView(final CuponHolder viewHolder, final CouponBean.DataBean item) {
        viewHolder.name.setText(item.getName());
        viewHolder.manjian.setText("满" + item.getMoney_condition() + "减" + item.getMoney());

        viewHolder.integral.setText(item.getSilver() + "银积分");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcouponListServer(item.getId());
            }
        });
    }

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //声明这个接口变量
    private OnItemClickListener itemClick;

    //提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        itemClick = itemClickListener;
    }

    //优惠券 - 商家
    private void getcouponListServer(String id) {
        RequestParams entity = new RequestParams(ServerAddress.URL_MY_PERSONAL_INFO_EXCHANGE_COUPON);
        entity.addBodyParameter("uid", WoAiSiJiApp.getUid());
        entity.addBodyParameter("id", id);
        entity.addBodyParameter("token", WoAiSiJiApp.token);

        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (TextUtils.isEmpty(result))
                    return;
                Gson gson = new Gson();
                RespAddOrder respAddOrder = gson.fromJson(result, RespAddOrder.class);
                if (respAddOrder.getCode() == 200) {

                    Toast.makeText(mContext, respAddOrder.getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    if (respAddOrder.getMsg().contains("token") || respAddOrder.getMsg().contains("uid")) {
                        Toast.makeText(mContext, "兑换失败,登录信息已过期,请重新登录", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "兑换失败," + respAddOrder.getMsg(), Toast.LENGTH_LONG).show();
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


}
