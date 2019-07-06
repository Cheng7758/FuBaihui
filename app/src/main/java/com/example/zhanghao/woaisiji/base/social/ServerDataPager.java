package com.example.zhanghao.woaisiji.base.social;

import android.app.Activity;

import com.example.zhanghao.woaisiji.base.BasePager;

/**
 * Created by admin on 2016/9/26.
 */
public class ServerDataPager extends BasePager {


    private int type;
    private FateDetailPager fateDetailPager;
    private NewMemberDetailPager memberDetailPager;

    public ServerDataPager(Activity activity, int type) {
        super(activity);
        this.type = type;
    }

    @Override
    public void initData() {
        flOrderFormList.removeAllViews();
        switch (type) {
            case TYPE_FATE:
                fateDetailPager = new FateDetailPager(mActivity);

                flOrderFormList.addView(fateDetailPager.mRootView);
                fateDetailPager.initData();
                break;
            case TYPE_NEW_MEM:
                memberDetailPager = new NewMemberDetailPager(mActivity,TYPE_NEW_MEM);
                flOrderFormList.addView(memberDetailPager.mRootView);
                memberDetailPager.initData();
                break;
            case TYPE_FEMALE_MEM:
                memberDetailPager = new NewMemberDetailPager(mActivity,TYPE_FEMALE_MEM);
                flOrderFormList.addView(memberDetailPager.mRootView);
                memberDetailPager.initData();
                break;
            case TYPE_MALE_MEM:
                memberDetailPager = new NewMemberDetailPager(mActivity,TYPE_MALE_MEM);
                flOrderFormList.addView(memberDetailPager.mRootView);
                memberDetailPager.initData();
                break;
        }

    }


}
