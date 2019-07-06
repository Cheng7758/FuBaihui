package com.example.zhanghao.woaisiji.bean;

/**
 * 手机验证码
 * Created by admin on 2016/9/1.
 */
public class PhoneCodesBean {
    public int code;
    public String content;
//    public String returncode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }*/
}
