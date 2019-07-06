package com.example.zhanghao.woaisiji.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zhanghao.woaisiji.WoAiSiJiApp;

/**
 *      工具类一般都封装成单例的
 */

public class SpUtils {
    public static final String IS_NEW_INVITE = "is_new_invite";

    private static SharedPreferences sp;
    private static SpUtils instance = new SpUtils();

    private SpUtils(){}

    public static SpUtils getInstance(){
        if (sp == null){
            sp = WoAiSiJiApp.getGlobalApplication()
                    .getSharedPreferences("im", Context.MODE_PRIVATE);//第一个是创建的文件名称
        }

        return instance;
    }

    //保存
    public void save(String key, Object value){
        if (value instanceof String){
            sp.edit().putString(key, (String) value).commit();
        }else if (value instanceof Boolean){
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }else if(value instanceof Integer){
            sp.edit().putInt(key, (Integer) value).commit();
        }
    }

    //获取数据的方法
    public String getString(String key, String defValue){
        return sp.getString(key, defValue);
    }

    //获取boolean数据
    public boolean getBoolean(String key, boolean defValue){
        return sp.getBoolean(key, defValue);
    }

    //获取int数据
    public int getInt(String key, int defValue){
        return sp.getInt(key, defValue);
    }
}
