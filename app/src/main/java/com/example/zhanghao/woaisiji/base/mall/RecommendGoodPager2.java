package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/10/29.
 */
public class RecommendGoodPager2 extends BasePager {
    private int type = 1;

    public RecommendGoodPager2(Activity activity, int type) {
        super(activity);
        this.type = type;
    }

    @Override
    public void initData() {
        RecommendGoodDetailPager2 recommendGoodDetailPager = new RecommendGoodDetailPager2(mActivity,type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(recommendGoodDetailPager.mRootView);
        recommendGoodDetailPager.initData();
    }
}
