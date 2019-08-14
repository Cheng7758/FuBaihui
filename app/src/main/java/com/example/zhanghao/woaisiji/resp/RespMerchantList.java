package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.merchant.MerchantInfoDetailBean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespMerchantList extends RespBase{

    public List<MerchantInfoDetailBean> data;

    public List<MerchantInfoDetailBean> getData() {
        return data;
    }
    public void setData(List<MerchantInfoDetailBean> data) {
        this.data = data;
    }
}
