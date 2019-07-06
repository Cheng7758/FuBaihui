package com.example.zhanghao.woaisiji.base.lovebuy;

import android.app.Activity;
import android.util.Log;

import com.example.zhanghao.woaisiji.base.BasePager;

import java.util.List;

/**
 * Created by admin on 2016/11/4.
 */
public class LoveBuyTwelvePager extends BasePager {
    public LoveBuyTwelvePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        ILoveBuyDetailPager iLoveBuyDetailPager = new ILoveBuyDetailPager(mActivity,3);
        flOrderFormList.removeAllViews();
        flOrderFormList.addView(iLoveBuyDetailPager.mRootView);
        iLoveBuyDetailPager.initData();
        iLoveBuyDetailPager.setOnSendDataListener(new ILoveBuyDetailPager.OnSendDataListener() {

            @Override
            public void sendData(List<String> data) {
                if (listener != null){
                    listener.sendData(data);
                }
            }
        });
    }
}
