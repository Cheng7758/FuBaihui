package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.PersonalInfoBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespGetPersonalInfo extends RespBase{

    private PersonalInfoBean data;

    public PersonalInfoBean getData() {
        return data;
    }

    public void setData(PersonalInfoBean data) {
        this.data = data;
    }

}
