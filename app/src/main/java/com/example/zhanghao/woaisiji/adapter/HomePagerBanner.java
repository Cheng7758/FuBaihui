package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.AdvertisementWebViewActivity;
import com.example.zhanghao.woaisiji.bean.GlobalSlideShow;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class HomePagerBanner extends PagerAdapter {
    private DisplayImageOptions options;
    private List<GlobalSlideShow> mIvIds;
    private Context context;
    public HomePagerBanner(Context context, List<GlobalSlideShow> ivIds) {
       this.context=context;
        mIvIds = ivIds;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.weixianshi)
                .showImageForEmptyUri(R.drawable.weixianshi)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mIvIds==null?0:mIvIds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width =  ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height =   ViewPager.LayoutParams.MATCH_PARENT;

        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Log.d("picture",ServerAddress.SERVER_ROOT + mIvIds.get(position).getPicture());
        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+mIvIds.get(position).getPicture(),imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AdvertisementWebViewActivity.class);
                intent.putExtra("content",mIvIds.get(position).getTzurl());
                context. startActivity(intent);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}