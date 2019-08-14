package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.shoppingcar.ShoppingCarStoreInfo;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespShoppingCarList extends RespBase{

    private List<ShoppingCarStoreInfo> data;

    public List<ShoppingCarStoreInfo> getData() {
        return data;
    }

    public void setData(List<ShoppingCarStoreInfo> data) {
        this.data = data;
    }

}
