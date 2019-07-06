package com.example.zhanghao.woaisiji.add;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zzz on 2016/11/22.
 */
public class ImageViewAdapter extends PagerAdapter {
    private List<ImageView> mImageViewList;
    private Activity mActivity;

    public ImageViewAdapter(Activity mActivity, List<ImageView> mImageViewList){
        this.mActivity = mActivity;
        this.mImageViewList = mImageViewList;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
