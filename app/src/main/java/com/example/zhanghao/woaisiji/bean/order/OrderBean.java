package com.example.zhanghao.woaisiji.bean.order;

import java.util.List;

public class OrderBean {

    private String store_name;
    private String store_total_price;
    private List<OrderGoodsBean> goods;
    private List<OrderCouponListBean>coupon_list;
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_total_price() {
        return store_total_price;
    }

    public void setStore_total_price(String store_total_price) {
        this.store_total_price = store_total_price;
    }

    public List<OrderGoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoodsBean> goods) {
        this.goods = goods;
    }

    public List<OrderCouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<OrderCouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }
}
