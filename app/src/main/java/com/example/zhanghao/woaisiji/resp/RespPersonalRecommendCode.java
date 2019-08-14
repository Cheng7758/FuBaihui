package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.PersonalRecommendCodeBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalRecommendCode extends RespBase{

    private PersonalRecommendCodeBean data;

    public PersonalRecommendCodeBean getData() {
        return data;
    }

    public void setData(PersonalRecommendCodeBean data) {
        this.data = data;
    }

}
