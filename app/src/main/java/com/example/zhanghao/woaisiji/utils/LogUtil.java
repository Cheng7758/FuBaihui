package com.example.zhanghao.woaisiji.utils;

import android.util.Log;
/**
 * 简单的log工具类
 * @author GraysonGao
 *
 */
public class LogUtil {

    private static final String TAG = "我爱死机";

    public static void logi(String msg) {
        Log.i(TAG, msg);
    }
    
    public static void logi(String tag, String msg) {
        Log.i(tag, msg);
    }
}
