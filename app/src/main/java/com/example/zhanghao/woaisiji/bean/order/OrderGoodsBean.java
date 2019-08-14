package com.example.zhanghao.woaisiji.bean.order;

import android.text.TextUtils;

import com.example.zhanghao.woaisiji.global.ServerAddress;

public class OrderGoodsBean {

    private String cart_num, title, cover, price, subtotal, number, cart_id;

    public String getCart_num() {
        return cart_num;
    }

    public void setCart_num(String cart_num) {
        this.cart_num = cart_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        if (!TextUtils.isEmpty(cover)) {
            if (!cover.contains(ServerAddress.SERVER_ROOT)) {
                cover = ServerAddress.SERVER_ROOT + cover;
            }
        }
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
}
