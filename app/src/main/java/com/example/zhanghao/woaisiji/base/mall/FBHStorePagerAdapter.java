package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhanghao.woaisiji.base.BasePager;

import java.util.List;

public class FBHStorePagerAdapter extends PagerAdapter {
    private Activity mActivity;
    private List<BasePager> mBasePagerLists;

    public FBHStorePagerAdapter(Activity activity, List<BasePager> mBasePagerLists){
        mActivity = activity;
        this.mBasePagerLists = mBasePagerLists;
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
