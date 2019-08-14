package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.merchant.MerchantInfoDetailBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespMerchantDetail extends RespBase{

    public MerchantInfoDetailBean data;

    public MerchantInfoDetailBean getData() {
        return data;
    }
    public void setData(MerchantInfoDetailBean data) {
        this.data = data;
    }
}
