package com.example.zhanghao.woaisiji.bean.pay;

public class RechargeGoldIntegralWXResponseData {
    private String notify_url ;
    private RechargeGoldIntegralWXAppResponse app_response;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public RechargeGoldIntegralWXAppResponse getApp_response() {
        return app_response;
    }

    public void setApp_response(RechargeGoldIntegralWXAppResponse app_response) {
        this.app_response = app_response;
    }
}
