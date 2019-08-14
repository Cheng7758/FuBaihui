package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.fbh.FBHBusinessDetails;

public class RespFBHCommodityDetails extends RespBase{

    private FBHBusinessDetails data;

    public FBHBusinessDetails getData() {
        return data;
    }

    public void setData(FBHBusinessDetails data) {
        this.data = data;
    }

}
