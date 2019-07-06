package com.example.zhanghao.woaisiji.base.social;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/9/26.
 */
public class SearchPager extends BasePager {
    public SearchPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        SearchDetailPager searchDetailPager = new SearchDetailPager(mActivity);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(searchDetailPager.mRootView);
    }
}
