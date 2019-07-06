package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/10/29.
 */
public class CategoryGoodPager extends BasePager {

    private int id;
    private int type;
    public CategoryGoodPager(Activity activity,int id,int type) {
        super(activity);
        this.id = id;
        this.type = type;
        initData();

    }

    @Override
    public void initData() {
        CategoryGoodDetailPager categoryGoodDetailPager = new CategoryGoodDetailPager(mActivity,id,type);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(categoryGoodDetailPager.mRootView);
        categoryGoodDetailPager.initData();
    }
}
