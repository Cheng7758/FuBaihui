package com.example.zhanghao.woaisiji.resp;

import java.io.Serializable;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespNull extends RespBase implements Serializable {
    private static final long serialVersionUID = 9101167678068012503L;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String pData) {
        data = pData;
    }
}
