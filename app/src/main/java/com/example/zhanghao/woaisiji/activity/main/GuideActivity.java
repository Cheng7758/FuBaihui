package com.example.zhanghao.woaisiji.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.Constants;
import com.example.zhanghao.woaisiji.utils.SharedPrefUtil;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager mVp;
    private int[] mImgRes=new int[]{R.drawable.shouye_1, R.drawable.shouye_2,
            R.drawable.shouye_3, R.drawable.shouye_4};//滑动的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        initView();
        initData();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp_guide);
    }
    //初始化数据
    private void initData() {
        mVp.setAdapter(new GuideAdapter());
        mVp.setOnPageChangeListener(this);
    }

    class GuideAdapter extends PagerAdapter {
        //返回的数量
        @Override
        public int getCount() {
            return mImgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        // 加载一个view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(mImgRes[position]);

            container.addView(imageView);
            return imageView;
        }

        //移除一个view
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    // 页面滚动状态发生变化时
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    // 页面滚动时
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    // 页面选中时,长度是1234，位置是0123
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        if(position==mImgRes.length-1){
            // 修改SP
            SharedPrefUtil.putBoolean(getApplicationContext(), Constants.KEY_IS_FIRST_RUN, false);
            // 进主页面
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }
}
