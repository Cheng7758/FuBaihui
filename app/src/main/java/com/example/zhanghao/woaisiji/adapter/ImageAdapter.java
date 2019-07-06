package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.graphics.Bitmap;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/21.
 */
public class ImageAdapter extends PagerAdapter {
    private final DisplayImageOptions options;
    private List<String> mUrls;

    private Context mActivity;

    public ImageAdapter( List<String> mUrls, Context mActivity) {
        this.mUrls = mUrls;
        this.mActivity = mActivity;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.weixianshi)
                .showImageForEmptyUri(R.drawable.weixianshi)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        if(mUrls ==null) {
            return 0;
        }
        return mUrls.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mActivity);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width =  ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height =   ViewPager.LayoutParams.MATCH_PARENT;

        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(0,3,0,0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+mUrls.get(position),imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
