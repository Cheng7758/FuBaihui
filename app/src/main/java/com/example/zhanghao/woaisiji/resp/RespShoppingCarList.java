package com.example.zhanghao.woaisiji.resp;

import java.util.ArrayList;
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

    public static class ShoppingCarStoreInfo {
        private String  store_name,store_id ;
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

    public static class ShoppingCarGoodsInfo {

        public ShoppingCarGoodsInfo(String title, String id, String g_id, int num, int pay_price, String number, String store_id, String subtotal) {
            this.title = title;
            this.id = id;
            this.g_id = g_id;
            this.num = num;
            this.pay_price = pay_price;
            this.number = number;
            this.store_id = store_id;
            this.subtotal = subtotal;
        }

        protected boolean isChoosed;
        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean isChoosed) {
            this.isChoosed = isChoosed;
        }

        private String title;
        //	购物车id
        private String id;
        // 商品id
        private String g_id ;
        //	每个商品购物车数量
        private int num ;
        //	商品价格
        private int pay_price ;
        //商品库存
        private String number ;
        //	店铺id
        private String store_id ;
        //	商品图
        private String img ;
        //	每个商品小计（数量*价格）
        private String subtotal ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getG_id() {
            return g_id;
        }

        public void setG_id(String g_id) {
            this.g_id = g_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPay_price() {
            return pay_price;
        }

        public void setPay_price(int pay_price) {
            this.pay_price = pay_price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }
    }
}
