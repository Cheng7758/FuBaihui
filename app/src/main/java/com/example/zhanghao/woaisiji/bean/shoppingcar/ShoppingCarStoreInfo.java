package com.example.zhanghao.woaisiji.bean.shoppingcar;

import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCarStoreInfo {
    private String  store_name,store_id ;
    public PersonalCouponBean couponBean;
    private List<ShoppingCarGoodsInfo> goods = new ArrayList<>();

    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public List<ShoppingCarGoodsInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCarGoodsInfo> goods) {
        this.goods = goods;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
