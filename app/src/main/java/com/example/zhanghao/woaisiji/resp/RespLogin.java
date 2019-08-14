package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.UserLoginBean;

public class RespLogin extends RespBase{

    private UserLoginBean data ;

    public UserLoginBean getData() {
        return data;
    }

    public void setData(UserLoginBean data) {
        this.data = data;
    }

}
