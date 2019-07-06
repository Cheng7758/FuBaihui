package com.example.zhanghao.woaisiji.utils;

import android.content.Context;

/**
 * Created by admin on 2016/9/21.
 */
public class DpTransPx {

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
