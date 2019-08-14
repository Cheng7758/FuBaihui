package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.pay.RechargeGoldIntegralWXResponseData;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespRechargeGoldIntegralWX {

    private int errorCode ;
    private String errorMsg ;
    private RechargeGoldIntegralWXResponseData responseData ;
    private String out_trade_no ;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public RechargeGoldIntegralWXResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(RechargeGoldIntegralWXResponseData responseData) {
        this.responseData = responseData;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
