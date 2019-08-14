package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalCoupon extends RespBase{

    private List<PersonalCouponBean> data;

    public List<PersonalCouponBean> getData() {
        return data;
    }

    public void setData(List<PersonalCouponBean> data) {
        this.data = data;
    }


}
