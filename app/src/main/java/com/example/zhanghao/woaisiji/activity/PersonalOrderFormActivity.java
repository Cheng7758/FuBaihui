package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.impl.OrderFormAllPager;
import com.example.zhanghao.woaisiji.base.impl.OrderFormPayPager;
import com.example.zhanghao.woaisiji.base.impl.OrderFormReceivePager;
import com.example.zhanghao.woaisiji.base.impl.OrderFormSendPager;
import com.example.zhanghao.woaisiji.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/19.
 */
public class PersonalOrderFormActivity extends Activity implements View.OnClickListener {
    private ViewPager mViewPagerOrderForm;
    private static List<BasePager> mBasePagerLists;

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;

    public Button btnOrderFormDrivemall;
    public Button btnOrderFormConvertMall;
    public Button btnOrderFormGoldMall;
    private LinearLayout llPersonalOrderForm;

    public int positionType = 0;
    public int type = 1;
    private RadioGroup rgOrderFormIndicator;
    private RadioButton rbOrderFormIndicatorItem01;
    private RadioButton rbOrderFormIndicatorItem02;
    private RadioButton rbOrderFormIndicatorItem03;
    private RadioButton rbOrderFormIndicatorItem04;
    private ImageView ivRedRectangle;

    private int screenWidth;
    private RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_order_form);

        initView();
        // 响应点击事件
        responseClickListener();
        type =1;
        positionType = 0;
        initData();
    }


    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        llPersonalOrderForm = (LinearLayout) findViewById(R.id.ll_personal_order_form);
        btnOrderFormDrivemall = (Button) findViewById(R.id.btn_siji);
        btnOrderFormConvertMall = (Button) findViewById(R.id.btn_yinbi);
        btnOrderFormGoldMall = (Button) findViewById(R.id.btn_jinbi);

        ivRedRectangle = (ImageView) findViewById(R.id.iv_red_rectangle);

        rgOrderFormIndicator = (RadioGroup) findViewById(R.id.rg_order_form_indicator);
        rbOrderFormIndicatorItem01 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item01);
        rbOrderFormIndicatorItem02 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item02);
        rbOrderFormIndicatorItem03 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item03);
        rbOrderFormIndicatorItem04 = (RadioButton) findViewById(R.id.rb_order_form_indicator_item04);

        mViewPagerOrderForm = (ViewPager) findViewById(R.id.vp_order_form);
        btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
        btnOrderFormDrivemall.setOnClickListener(this);
        btnOrderFormConvertMall.setOnClickListener(this);
        btnOrderFormGoldMall.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BasePager pager = new BasePager(this);
        btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnOrderFormConvertMall.setBackgroundColor(Color.parseColor("#FF5000"));
        btnOrderFormGoldMall.setBackgroundColor(Color.parseColor("#FF5000"));
       // llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_default);
        pager.type =1;
        type = pager.type;
        positionType = 0;
        initData();


    }

    @Override
    protected void onStart() {
        BasePager pager = new BasePager(this);
        btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnOrderFormConvertMall.setBackgroundColor(Color.parseColor("#FF5000"));
        btnOrderFormGoldMall.setBackgroundColor(Color.parseColor("#FF5000"));
        pager.type =1;
        type = pager.type;
        positionType = 0;
        initData();
        super.onStart();
    }

    public void initData() {
//        mBasePagerLists.clear();
        mBasePagerLists = new ArrayList<>();
        positionType = 0;
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;
        params = (RelativeLayout.LayoutParams) ivRedRectangle.getLayoutParams();
        rbOrderFormIndicatorItem04.setVisibility(View.GONE);

        // 添加五个标签页
        mBasePagerLists.add(new OrderFormAllPager(PersonalOrderFormActivity.this));
        mBasePagerLists.add(new OrderFormSendPager(PersonalOrderFormActivity.this));
        mBasePagerLists.add(new OrderFormReceivePager(PersonalOrderFormActivity.this));
        if (type == 1) {
//            Log.d("width", "" + params.width);
            rbOrderFormIndicatorItem04.setVisibility(View.VISIBLE);
            mBasePagerLists.add(new OrderFormPayPager(PersonalOrderFormActivity.this));
        }
        params.width = screenWidth / mBasePagerLists.size();

//        if (type == 1){
        mViewPagerOrderForm.setAdapter(new OrderFormAdapter());
        params.leftMargin = 0;
       /* if (positionType >=mBasePagerLists.size()){
            mBasePagerLists.get(positionType-1).initData();
            params.leftMargin = params.width*(positionType-1);
        }else{*/
            mBasePagerLists.get(positionType).initData();
//        }
        ivRedRectangle.setLayoutParams(params);
        rbOrderFormIndicatorItem01.setChecked(true);

//        mIndicatorAuthentic.setViewPager(mViewPagerOrderForm);
        rgOrderFormIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                rbOrderFormIndicatorItem01.setSelected(false);
                rbOrderFormIndicatorItem02.setSelected(false);
                rbOrderFormIndicatorItem03.setSelected(false);
                rbOrderFormIndicatorItem04.setSelected(false);
                switch (checked) {
                    case R.id.rb_order_form_indicator_item01:
                        mViewPagerOrderForm.setCurrentItem(0);
                        positionType = 0;
                        rbOrderFormIndicatorItem01.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item02:
                        mViewPagerOrderForm.setCurrentItem(1);
                        positionType = 1;
                        rbOrderFormIndicatorItem02.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item03:
                        mViewPagerOrderForm.setCurrentItem(2);
                        rbOrderFormIndicatorItem03.setChecked(true);
                            positionType = 2;

                        break;
                    case R.id.rb_order_form_indicator_item04:
                        mViewPagerOrderForm.setCurrentItem(3);
                        rbOrderFormIndicatorItem04.setChecked(true);
                            positionType = 3;
                        break;
                }
                params.leftMargin = params.width*positionType;
                ivRedRectangle.setLayoutParams(params);
            }
        });

        mViewPagerOrderForm.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int select = 0; select < mBasePagerLists.size(); select++) {
                    rgOrderFormIndicator.getChildAt(select).setSelected(false);
                }

                rgOrderFormIndicator.getChildAt(position).setSelected(true);
                rbOrderFormIndicatorItem01.setChecked(false);
                rbOrderFormIndicatorItem02.setChecked(false);
                rbOrderFormIndicatorItem03.setChecked(false);
                rbOrderFormIndicatorItem04.setChecked(false);
                switch (rgOrderFormIndicator.getChildAt(position).getId()){
                    case R.id.rb_order_form_indicator_item01:
                        rbOrderFormIndicatorItem01.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item02:
                        rbOrderFormIndicatorItem02.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item03:
                        rbOrderFormIndicatorItem03.setChecked(true);
                        break;
                    case R.id.rb_order_form_indicator_item04:
                        rbOrderFormIndicatorItem04.setChecked(true);
                        break;
                }



                BasePager pager = mBasePagerLists.get(position);
                positionType = position;


                pager.initData();

                // 更新短横的距离
                int leftMargin =  ((screenWidth /mBasePagerLists.size() )* position);// 计算短横的当前的左边距
                params.leftMargin = leftMargin;

                ivRedRectangle.setLayoutParams(params);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        BasePager pager = new BasePager(this);
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.btn_siji:
               // llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_default);
                btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btnOrderFormConvertMall.setBackgroundColor(Color.parseColor("#FF5000"));
                btnOrderFormGoldMall.setBackgroundColor(Color.parseColor("#FF5000"));
                pager.type = 1;
                type = pager.type;
                initData();
                break;
            case R.id.btn_yinbi:
               // llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_selected);
                btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FF5000"));
                btnOrderFormConvertMall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btnOrderFormGoldMall.setBackgroundColor(Color.parseColor("#FF5000"));
                pager.type = 2;
                type = pager.type;
                initData();
                break;
            case R.id.btn_jinbi:
                // llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_selected);
                btnOrderFormDrivemall.setBackgroundColor(Color.parseColor("#FF5000"));
                btnOrderFormConvertMall.setBackgroundColor(Color.parseColor("#FF5000"));
                btnOrderFormGoldMall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pager.type = 3;
                type = pager.type;
                initData();
                break;
        }
    }


    class OrderFormAdapter extends PagerAdapter {

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
