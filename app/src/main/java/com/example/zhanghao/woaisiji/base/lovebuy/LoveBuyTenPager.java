package com.example.zhanghao.woaisiji.base.lovebuy;

import android.app.Activity;
import android.util.Log;

import com.example.zhanghao.woaisiji.base.BasePager;

import java.util.List;

/**
 * Created by admin on 2016/11/4.
 */
public class LoveBuyTenPager extends BasePager {
    public LoveBuyTenPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        ILoveBuyDetailPager iLoveBuyDetailPager = new ILoveBuyDetailPager(mActivity,1);
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

   /* private OnSendDataListener listener;
    public void setOnSendDataListener(OnSendDataListener listener){
        this.listener = listener;
    }
    // 回调接口
    public interface OnSendDataListener{
        public void sendData(List<String> data);
    }*/
}
