package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/10/29.
 */
public class FBHStoreGoodPager extends BasePager {

    private String id;
    private int type;
    public FBHStoreGoodPager(Activity activity, String id, int type) {
        super(activity);
        this.id = id;
        this.type = type;
        initData();

    }

    @Override
    public void initData() {
        FBHStoreGoodDetailPager fbhStoreGoodDetailPager = new FBHStoreGoodDetailPager(mActivity,id,type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(fbhStoreGoodDetailPager.mRootView);
        fbhStoreGoodDetailPager.initData();
    }
}
