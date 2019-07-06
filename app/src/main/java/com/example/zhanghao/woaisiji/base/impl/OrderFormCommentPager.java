package com.example.zhanghao.woaisiji.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/9/19.
 */
public class OrderFormCommentPager extends BasePager {
    public OrderFormCommentPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("order_form","评论的pager执行了");
        // 要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("待评论");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        flOrderFormList.addView(view);
    }
}
