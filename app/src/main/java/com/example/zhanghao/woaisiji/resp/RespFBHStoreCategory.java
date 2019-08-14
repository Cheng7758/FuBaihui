package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.fbh.FBHStoreCategory;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespFBHStoreCategory extends RespBase{

    private List<FBHStoreCategory> data;

    public List<FBHStoreCategory> getData() {
        return data;
    }

    public void setData(List<FBHStoreCategory> data) {
        this.data = data;
    }

}
