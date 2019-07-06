package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.social.ServerDataPager;
import com.example.zhanghao.woaisiji.base.social.SearchPager;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.view.DialogLoveDriver;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 *  搜索会员
 */
public class LoveDriverSociallyActivity extends Activity {
    private ImageView ivSocialBack;
    private ImageView ivSocialMore;
    private TabPageIndicator mIndicatorSociallyTitle;
    private ViewPager vpSociallyPager;

    private List<BasePager> mBasePagerLists;
//    private String[] pagerTitle = {"缘分","新人","女会员","男会员","搜索"};
    private String[] pagerTitle = {"搜索"};

    private final static int TYPE_FATE = 0;
    private final static int TYPE_NEW_MEM = 1;
    private final static int TYPE_FEMALE_MEM = 2;
    private final static int TYPE_MALE_MEM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_driver_social);

        initView();
        initViewPager();

        ivSocialBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSocialMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出对话框
                DialogLoveDriver dialogLoveDriver = new DialogLoveDriver(LoveDriverSociallyActivity.this);
                dialogLoveDriver.show();
            }
        });
    }

    private void initView() {
        ivSocialBack = (ImageView) findViewById(R.id.iv_social_back);//返回按钮
        ivSocialMore = (ImageView) findViewById(R.id.iv_social_more);
        mIndicatorSociallyTitle = (TabPageIndicator) findViewById(R.id.indicator_socially_title);//tab
        vpSociallyPager = (ViewPager) findViewById(R.id.vp_socially_pager);//viewpager
    }

    private void initViewPager() {
        mBasePagerLists = new ArrayList<>();

        // 添加viewpager， 前四个页面和搜索页面
//        mBasePagerLists.add(new ServerDataPager(LoveDriverSociallyActivity.this, TYPE_FATE));
//        mBasePagerLists.add(new ServerDataPager(LoveDriverSociallyActivity.this,TYPE_NEW_MEM));
//        mBasePagerLists.add(new ServerDataPager(LoveDriverSociallyActivity.this,TYPE_FEMALE_MEM));
//        mBasePagerLists.add(new ServerDataPager(LoveDriverSociallyActivity.this,TYPE_MALE_MEM));
        mBasePagerLists.add(new SearchPager(LoveDriverSociallyActivity.this));//搜索页面

        vpSociallyPager.setAdapter(new LoveDriverSocialAdapter());//view的setadapter
        mIndicatorSociallyTitle.setViewPager(vpSociallyPager);//tab与viewpager的绑定

        vpSociallyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mBasePagerLists.get(position);
                pager.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBasePagerLists.get(0).initData();
    }

    class LoveDriverSocialAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            String title = pagerTitle[position];
            return title;
        }

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
