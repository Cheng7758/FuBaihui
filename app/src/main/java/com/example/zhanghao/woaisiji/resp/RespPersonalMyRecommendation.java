package com.example.zhanghao.woaisiji.resp;

import android.text.TextUtils;

import com.example.zhanghao.woaisiji.bean.my.PersonalMyRecommendationBean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalMyRecommendation extends RespBase{

    private List<PersonalMyRecommendationBean> data;

    public List<PersonalMyRecommendationBean> getData() {
        return data;
    }

    public void setData(List<PersonalMyRecommendationBean> data) {
        this.data = data;
    }

}
