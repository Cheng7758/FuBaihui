package com.example.zhanghao.woaisiji.bean;

/**
 * Created by kongsou_android on 2018/03/16.
 */

public class GoldBuyBean {


    /**
     * code : 200
     * msg : 订单已生成
     * orderid : 1521687120
     * money : 5.5
     */

    private int code;
    private String msg;
    private int orderid;
    private String money;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
