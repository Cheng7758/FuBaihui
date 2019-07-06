package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/20.
 */
public class ProductPictureBean {

    /**
     * code : 200
     * list : ["/Uploads/Picture/2016-09-17/57dd5090e5097.jpg"]
     */

    private int code;
    private List<String> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
