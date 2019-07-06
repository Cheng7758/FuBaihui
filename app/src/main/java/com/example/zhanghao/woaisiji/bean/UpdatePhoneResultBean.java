package com.example.zhanghao.woaisiji.bean;

/**
 * Created by admin on 2016/9/12.
 */

/**
 * 修改手机号和修改密码共用一个bean文件
 */
public class UpdatePhoneResultBean {
    public int code;
    public String msg;

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
}
