package com.example.zhanghao.woaisiji.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.zhanghao.woaisiji.R;

import java.util.List;

/**
 * Created by admin on 2016/9/19.
 */
public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public FrameLayout flOrderFormList;
    public static int type = 1;
    public final static int TYPE_FATE = 0;
    public final static int TYPE_NEW_MEM = 1;
    public final static int TYPE_FEMALE_MEM = 2;
    public final static int TYPE_MALE_MEM = 3;


    //

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_pager_yw,null);
        flOrderFormList = (FrameLayout) view.findViewById(R.id.fl_order_form_list);
        return view;
    }
    // 初始化数据
    public void initData(){}


    public OnSendDataListener listener;
    public void setOnSendDataListener(OnSendDataListener listener){
        this.listener = listener;
    }
    // 回调接口
    public interface OnSendDataListener{
        public void sendData(List<String> data);
    }


}
