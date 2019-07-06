package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.lovebuy.LoveBuyElevenPager;
import com.example.zhanghao.woaisiji.base.lovebuy.LoveBuyTenPager;
import com.example.zhanghao.woaisiji.base.lovebuy.LoveBuyTwelvePager;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/4.
 */
public class ILoveShopActivity extends Activity {

    private String[] pagerTitle = {
            "10:00",
            "11:00",
            "12:00",
    };
    //    private TabPageIndicator mIndicatorSociallyTitle;
    private ViewPager vpSociallyPager;
    private List<BasePager> mBasePagerLists;
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;

    private RelativeLayout.LayoutParams params;


    private RadioGroup rgOrderFormIndicator;
    private RadioButton rbOrderFormIndicatorItem01;
    private RadioButton rbOrderFormIndicatorItem02;
    private RadioButton rbOrderFormIndicatorItem03;

    private ImageView ivRedRectangle;

    private int screenWidth;
    public int positionType = 0;


    private String currentTime;
    private String tenTime;
    private String elevenTime;
    private String twelveTime;
    private String thirteenTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilove_shop);

        initView();

        // 初始化ViewPager布局
        initViewPager();

//        initTitle();
    }

    private void initTitle() {

        if (Integer.parseInt(currentTime) < Integer.parseInt(tenTime)){
            rbOrderFormIndicatorItem01.setText(pagerTitle[0]+"\n"+"即将开抢");
            rbOrderFormIndicatorItem02.setText(pagerTitle[1]+"\n"+"即将开抢");
            rbOrderFormIndicatorItem03.setText(pagerTitle[2]+"\n"+"即将开抢");
        }else if (Integer.parseInt(currentTime) < Integer.parseInt(elevenTime)){
            rbOrderFormIndicatorItem01.setText(pagerTitle[0]+"\n"+"抢购进行中");
            rbOrderFormIndicatorItem02.setText(pagerTitle[1]+"\n"+"即将开抢");
            rbOrderFormIndicatorItem03.setText(pagerTitle[2]+"\n"+"即将开抢");
        }else if (Integer.parseInt(currentTime) < Integer.parseInt(twelveTime)){
            rbOrderFormIndicatorItem01.setText(pagerTitle[0]+"\n"+"抢购已结束");
            rbOrderFormIndicatorItem02.setText(pagerTitle[1]+"\n"+"抢购进行中");
            rbOrderFormIndicatorItem03.setText(pagerTitle[2]+"\n"+"即将开抢");
        }else if (Integer.parseInt(currentTime) < Integer.parseInt(thirteenTime)){
            rbOrderFormIndicatorItem01.setText(pagerTitle[0]+"\n"+"抢购已结束");
            rbOrderFormIndicatorItem02.setText(pagerTitle[1]+"\n"+"抢购已结束");
            rbOrderFormIndicatorItem03.setText(pagerTitle[2]+"\n"+"抢购进行中");
        }else{
            rbOrderFormIndicatorItem01.setText(pagerTitle[0]+"\n"+"抢购已结束");
            rbOrderFormIndicatorItem02.setText(pagerTitle[1]+"\n"+"抢购已结束");
            rbOrderFormIndicatorItem03.setText(pagerTitle[2]+"\n"+"抢购已结束");
        }


    }


    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("爱抢购");


        rgOrderFormIndicator = (RadioGroup) findViewById(R.id.rg_order_form_indicator);
        rbOrderFormIndicatorItem01 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item01);
        rbOrderFormIndicatorItem02 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item02);
        rbOrderFormIndicatorItem03 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item03);

        ivRedRectangle = (ImageView) findViewById(R.id.iv_red_rectangle);

//        mIndicatorSociallyTitle = (TabPageIndicator) findViewById(R.id.indicator_socially_title);
        vpSociallyPager = (ViewPager) findViewById(R.id.vp_socially_pager);
    }

    private void initViewPager() {
        mBasePagerLists = new ArrayList<>();

        mBasePagerLists.add(new LoveBuyTenPager(ILoveShopActivity.this));
        mBasePagerLists.add(new LoveBuyElevenPager(ILoveShopActivity.this));
        mBasePagerLists.add(new LoveBuyTwelvePager(ILoveShopActivity.this));

        vpSociallyPager.setAdapter(new LoveDriverSocialAdapter());
//        mIndicatorSociallyTitle.setViewPager(vpSociallyPager);


        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        params = (RelativeLayout.LayoutParams) ivRedRectangle.getLayoutParams();
        params.width = screenWidth / mBasePagerLists.size();

        rgOrderFormIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                rbOrderFormIndicatorItem01.setSelected(false);
                rbOrderFormIndicatorItem02.setSelected(false);
                rbOrderFormIndicatorItem03.setSelected(false);
                switch (checked) {
                    case R.id.rb_order_form_indicator_item01:
                        vpSociallyPager.setCurrentItem(0);
                        positionType = 0;
                        rbOrderFormIndicatorItem01.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item02:
                        vpSociallyPager.setCurrentItem(1);
                        positionType = 1;
                        rbOrderFormIndicatorItem02.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item03:
                        vpSociallyPager.setCurrentItem(2);
                        rbOrderFormIndicatorItem03.setChecked(true);
                        positionType = 2;
                        break;
                }
                params.leftMargin = params.width * positionType;
                ivRedRectangle.setLayoutParams(params);
            }
        });


        vpSociallyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mBasePagerLists.get(position);
//                pager.initData();


                for (int select = 0; select < mBasePagerLists.size(); select++) {
                    rgOrderFormIndicator.getChildAt(select).setSelected(false);
                }

                rgOrderFormIndicator.getChildAt(position).setSelected(true);
                rbOrderFormIndicatorItem01.setChecked(false);
                rbOrderFormIndicatorItem02.setChecked(false);
                rbOrderFormIndicatorItem03.setChecked(false);
                switch (rgOrderFormIndicator.getChildAt(position).getId()) {
                    case R.id.rb_order_form_indicator_item01:
                        rbOrderFormIndicatorItem01.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item02:
                        rbOrderFormIndicatorItem02.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item03:
                        rbOrderFormIndicatorItem03.setChecked(true);
                        break;

                }


//                BasePager pager = mBasePagerLists.get(position);
                positionType = position;


                pager.initData();
                pager.setOnSendDataListener(new BasePager.OnSendDataListener() {
                    @Override
                    public void sendData(List<String> data) {
                        currentTime = data.get(0);
                        tenTime = data.get(1);
                        elevenTime = data.get(2);
                        twelveTime = data.get(3);
                        thirteenTime = data.get(4);
//                        Log.d("dddddd", TimeUtils.times(data.get(0)));
//                        Log.d("dddddd",TimeUtils.times(data.get(1)));
//                        Log.d("dddddd",TimeUtils.times(data.get(2)));
//                        Log.d("dddddd",TimeUtils.times(data.get(3)));
//                        Log.d("dddddd",TimeUtils.times(data.get(4)));
                        initTitle();
                    }
                });

                // 更新短横的距离
                int leftMargin = ((screenWidth / mBasePagerLists.size()) * position);// 计算短横的当前的左边距
                params.leftMargin = leftMargin;

                ivRedRectangle.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBasePagerLists.get(0).initData();
        mBasePagerLists.get(0).setOnSendDataListener(new BasePager.OnSendDataListener() {
            @Override
            public void sendData(List<String> data) {
                currentTime = data.get(0);
                tenTime = data.get(1);
                elevenTime = data.get(2);
                twelveTime = data.get(3);
                thirteenTime = data.get(4);
//                Log.d("dddddd", TimeUtils.times(data.get(0)));
//                Log.d("dddddd",TimeUtils.times(data.get(1)));
//                Log.d("dddddd",TimeUtils.times(data.get(2)));
//                Log.d("dddddd",TimeUtils.times(data.get(3)));
//                Log.d("dddddd",TimeUtils.times(data.get(4)));
                initTitle();
            }
        });
        rbOrderFormIndicatorItem01.setChecked(true);


    }


    class LoveDriverSocialAdapter extends PagerAdapter {

       /* @Override
        public CharSequence getPageTitle(int position) {
            String title = pagerTitle[position];
            return title;
        }*/

        @Override
        public int getCount() {
            return mBasePagerLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mBasePagerLists.get(position);
            View view = pager.mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
