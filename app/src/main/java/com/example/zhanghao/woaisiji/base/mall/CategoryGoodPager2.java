package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/10/29.
 */
public class CategoryGoodPager2 extends BasePager {

    private int id;
    private int type;
    public CategoryGoodPager2(Activity activity, int id, int type) {
        super(activity);
        this.id = id;
        this.type = type;
        initData();

    }

    @Override
    public void initData() {
        CategoryGoodDetailPager2 categoryGoodDetailPager = new CategoryGoodDetailPager2(mActivity,id,type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(categoryGoodDetailPager.mRootView);
        categoryGoodDetailPager.initData();
    }
}
