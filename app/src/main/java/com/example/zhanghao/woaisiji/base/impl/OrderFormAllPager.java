package com.example.zhanghao.woaisiji.base.impl;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/9/19.
 */
public class OrderFormAllPager extends BasePager {


    public OrderFormAllPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        OrderFormDetailAllPager orderFormDetailAllPager = new OrderFormDetailAllPager(mActivity,type,100,100);
//        Log.d("activity_type",""+type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(orderFormDetailAllPager.mRootView);
        orderFormDetailAllPager.initData();

    }



}
