package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.pay.PayOrderBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPayOrder extends RespBase{

    private PayOrderBean data;

    public PayOrderBean getData() {
        return data;
    }

    public void setData(PayOrderBean data) {
        this.data = data;
    }
}
