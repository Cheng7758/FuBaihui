package com.example.zhanghao.woaisiji.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.home.BabyEvaluationActivity;
import com.example.zhanghao.woaisiji.adapter.ImageAdapter;
import com.example.zhanghao.woaisiji.bean.fbh.FBHBusinessDetails;
import com.example.zhanghao.woaisiji.view.AmountView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhanghao on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class WenZiDetails2 extends Fragment implements ViewPager.OnPageChangeListener {
    //储存请求地址
    public static final String ID = "id";
    private FBHBusinessDetails detailsBean;
    //图片返回值
    private String cover = "";
    private ImageAdapter imageAdapter;
    private ViewPager vp_product_detail2_banner_carousel;
    private LinearLayout ll_product_detail2_banner_carousel_dots;
    private RelativeLayout baby_evaluation;
    //商品名称
    private TextView tv_product_detail2_product_title, tv_product_detail2_product_price,
            tv_product_detail2_product_number, tv_product_detail2_product_introduction;
    private AmountView et_product_detail2_product_amount;
    private WebView tv_product_detail2_product_detail_content;
    private ImageView[] imageViews;
    private ImageView img;
    private Context mContext;
    private int mType;
    private SendDataActivityListener listener;

    @SuppressLint("ValidFragment")
    public WenZiDetails2(Context context, FBHBusinessDetails detailsBean, int pType) {
        this.detailsBean = detailsBean;
        this.mContext = context;
        mType = pType;
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
        //简介
        tv_product_detail2_product_introduction = (TextView) view.findViewById(R.id.tv_product_detail2_product_introduction);
        //详细内容
        tv_product_detail2_product_detail_content = (WebView) view.findViewById(R.id.tv_product_detail2_product_detail_content);
        //宝贝评价
        baby_evaluation = (RelativeLayout) view.findViewById(R.id.baby_evaluation);
        setValue();
        return view;
    }

    private void setValue() {
        //以下都是设置参数
        if (mType == 0) {
            tv_product_detail2_product_price.setText(detailsBean.getPrice());
        } else if (mType == 1) {
            tv_product_detail2_product_price.setText(detailsBean.getSilver());
        }
        tv_product_detail2_product_title.setText(detailsBean.getTitle());
        tv_product_detail2_product_number.setText(detailsBean.getNumber());
        tv_product_detail2_product_introduction.setText(detailsBean.getDescription());
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
        Log.e("------id", detailsBean.getId());
        //宝贝评价
        baby_evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BabyEvaluationActivity.class);
                intent.putExtra("id", detailsBean.getId());
                startActivity(intent);
            }
        });

        String id = detailsBean.getId();
        tv_product_detail2_product_detail_content.loadUrl("http://wasj.zhangtongdongli.com/Admin/Public/impublic/id/"
                + id + "/type/2");
        WebSettings settings = tv_product_detail2_product_detail_content.getSettings();
        settings.setJavaScriptEnabled(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        /*settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setDefaultFixedFontSize(15);*/
        /*tv_product_detail2_product_detail_content.loadData(getHtmlData(detailsBean.getContent()),
                "text/html; charset=UTF-8", null);*/
        tv_product_detail2_product_detail_content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
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

   /* private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }*/

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
