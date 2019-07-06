package com.example.zhanghao.woaisiji.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by admin on 2016/9/20.
 */
public abstract class BasePagerDetail {
    //
    public Activity mActivity;
    public View mRootView;

    public BasePagerDetail(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    // 初始化布局，必须子类实现
    public abstract View initView();

    // 初始化数据
    public void initData(){}

}
