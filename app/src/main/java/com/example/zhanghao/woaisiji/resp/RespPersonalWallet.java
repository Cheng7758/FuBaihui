package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.PersonalWalletBean;

import java.io.Serializable;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalWallet extends RespBase implements Serializable {

    private static final long serialVersionUID = -8623530635913497070L;
    private PersonalWalletBean data;

    public PersonalWalletBean getData() {
        return data;
    }

    public void setData(PersonalWalletBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespPersonalWallet{" +
                "data=" + data +
                '}';
    }
}
