package com.example.zhanghao.woaisiji.activity.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.fragment.my.MyOrderFragment;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView my_order_back;
    private TabLayout tabLayout;
    private ViewPager pager;
    String[] tabTitle = new String[]{"全部", "待付款", "待发货", "待收货", "评价"};
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initData();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyOrderFragment instance = MyOrderFragment.getInstance(i);
            fragmentList.add(instance);
        }
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });

        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序tabZhihuMain.addTab(tabZhihuMain.newTab().setText(tabTitle[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle[2]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle[3]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle[4]));
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setText(tabTitle[0]);
        tabLayout.getTabAt(1).setText(tabTitle[1]);
        tabLayout.getTabAt(2).setText(tabTitle[2]);
        tabLayout.getTabAt(3).setText(tabTitle[3]);
        tabLayout.getTabAt(4).setText(tabTitle[4]);
    }

    private void initView() {
        my_order_back = (ImageView) findViewById(R.id.my_order_back);
        tabLayout = (TabLayout) findViewById(R.id.my_order_tab);
        pager = (ViewPager) findViewById(R.id.my_order_pager);
        my_order_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
