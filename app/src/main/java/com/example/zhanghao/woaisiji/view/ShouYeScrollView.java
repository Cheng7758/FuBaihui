package com.example.zhanghao.woaisiji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.zhanghao.woaisiji.refresh.Pullable;


/**
 * Created by zhanghao on 2016/8/1.
 */
public class ShouYeScrollView extends ScrollView implements Pullable {
    public ShouYeScrollView(Context context)
    {
        super(context);
    }

    public ShouYeScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ShouYeScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown()
    {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp()
    {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }
}
