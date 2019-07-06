package com.example.zhanghao.woaisiji.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.zhanghao.woaisiji.activity.CommentActivity;
import com.example.zhanghao.woaisiji.adapter.ImageAdapter;
import com.example.zhanghao.woaisiji.bean.ProductPictureBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespFBHCommodityDetails;
import com.example.zhanghao.woaisiji.view.AmountView;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghao on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class WenZiDetails2 extends Fragment implements ViewPager.OnPageChangeListener {
    //储存请求地址
    public static final String ID = "id";
    private RespFBHCommodityDetails.FBHBusinessDetails detailsBean;
    //图片返回值
    private String cover = "";
    private ImageAdapter imageAdapter;
    private ViewPager vp_product_detail2_banner_carousel;
    private LinearLayout ll_product_detail2_banner_carousel_dots;
    //商品名称
    private TextView tv_product_detail2_product_title,tv_product_detail2_product_price,
            tv_product_detail2_product_number;
    private AmountView et_product_detail2_product_amount;
    private ImageView[] imageViews;
    private ImageView img;
    private Context mContext;
    private SendDataActivityListener listener;

    @SuppressLint("ValidFragment")
    public WenZiDetails2(Context context, RespFBHCommodityDetails.FBHBusinessDetails detailsBean) {
        this.detailsBean = detailsBean;
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_product_detail2_info, null);
        ll_product_detail2_banner_carousel_dots = (LinearLayout) view.findViewById(R.id.ll_product_detail2_banner_carousel_dots);
        //viewpager轮播图
        vp_product_detail2_banner_carousel = (ViewPager) view.findViewById(R.id.vp_product_detail2_banner_carousel);
        //商品名称
        tv_product_detail2_product_title = (TextView) view.findViewById(R.id.tv_product_detail2_product_title);
        //商品价格
        tv_product_detail2_product_price = (TextView) view.findViewById(R.id.tv_product_detail2_product_price);
        //库存
        tv_product_detail2_product_number = (TextView) view.findViewById(R.id.tv_product_detail2_product_number);
        //数量加减
        et_product_detail2_product_amount = (AmountView) view.findViewById(R.id.et_product_detail2_product_amount);

        setValue();

        return view;
    }

    private void setValue() {
        //以下都是设置参数
        tv_product_detail2_product_price.setText(detailsBean.getPrice());
        tv_product_detail2_product_title.setText(detailsBean.getTitle());
        tv_product_detail2_product_number.setText(detailsBean.getNumber());
        int kucun = Integer.parseInt(detailsBean.getNumber());
        et_product_detail2_product_amount.setGoods_storage(kucun);
        et_product_detail2_product_amount.setListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                String num = String.valueOf(amount);
                if (listener != null) {
                    listener.sendData(num);
                }
            }
        });

        imageViews = new ImageView[detailsBean.getImages().size()];
        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 0);

        for (int i = 0; i < detailsBean.getImages().size(); i++) {
            img = new ImageView(getActivity());
            imageViews[i] = img;
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.dot_focus);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.dot_normal);
            }
            img.setLayoutParams(layoutParams);
            ll_product_detail2_banner_carousel_dots.addView(imageViews[i]);
        }
        imageAdapter = new ImageAdapter(detailsBean.getImages(), getActivity());
        vp_product_detail2_banner_carousel.setAdapter(imageAdapter);
        vp_product_detail2_banner_carousel.setOnPageChangeListener(WenZiDetails2.this);

    }

    public void setSendDataActivity(SendDataActivityListener listener) {
        this.listener = listener;
    }

    public interface SendDataActivityListener {
        void sendData(String data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < imageViews.length; i++) {
            if (position == i) {
                imageViews[position].setBackgroundResource(R.drawable.dot_focus);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.dot_normal);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
}
