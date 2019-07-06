package com.example.zhanghao.woaisiji.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP 工具类
 */
public class SharedPrefUtil {
    private static SharedPreferences mSp;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);//两参数，第一个参数是文件名
        }
        return mSp;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value) {//保存参数
        getSharedPreferences(context).edit().putString(key, value).commit();//editor为载体
                                                                            // editor.put.commit
    }
    public static String getString(Context context, String key, String defValue) {//取参数
        return getSharedPreferences(context).getString(key, defValue);             //get就行
    }

    public static void remove(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).commit();
    }

    public static void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }
}
