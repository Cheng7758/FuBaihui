package com.example.zhanghao.woaisiji.base.impl;

import android.app.Activity;
import android.util.Log;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/9/19.
 */
public class OrderFormSendPager extends BasePager {
    public OrderFormSendPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        OrderFormDetailAllPager orderFormDetailAllPager = new OrderFormDetailAllPager(mActivity,type,1,0);
        Log.d("activity_type",""+type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(orderFormDetailAllPager.mRootView);
        orderFormDetailAllPager.initData();
    }
}
