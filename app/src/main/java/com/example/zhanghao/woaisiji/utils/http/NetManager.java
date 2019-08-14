package com.example.zhanghao.woaisiji.utils.http;

import com.example.zhanghao.woaisiji.httpurl.Myserver;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Cheng on 2019/4/1.
 */

//网络管理器
public class NetManager {
    private static volatile NetManager sNetManager;

    private NetManager() {}

    public static NetManager getNetManager() {
        if (sNetManager == null) {  //考虑效率问题
            synchronized (NetManager.class){    //同步
                if (sNetManager == null) {  //考虑多个线程问题
                    sNetManager = new NetManager();
                }
            }
        }
        return sNetManager;
    }

    public Myserver getMyService(String url) {
        Myserver service = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Myserver.class);
        return service;
    }

}
