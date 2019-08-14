package com.example.zhanghao.woaisiji.my;

import java.util.List;

/**
 * Created by Cheng on 2019/7/1.
 */

public class OrderBean {
    /**
     * code : 200
     * data : [{"id":"2530","goods_id":"1406","goods_name":"大双肩旅行者背包（A）","goods_num":"1","goods_price":"830.63","discount":"0.00","pay_price":830.63,"status_o":2,"status_m":"卖家已发货","goods_img":"/Uploads/Picture/2018-11-19/5bf2b3fa0e988.jpg","store_id":"1","store_name":null,"symbol":"/Public/Mobile/images/x.png"},{"id":"2531","goods_id":"1548","goods_name":"汽车专用鹿皮擦车巾","goods_num":"1","goods_price":"27.00","discount":"0.00","pay_price":27,"status_o":-1,"status_m":"订单已取消","goods_img":"/Uploads/Picture/2017-10-24/59eeb4a826b3b.jpg","store_id":"649","store_name":null,"symbol":"/Public/Mobile/images/jin.png"},{"id":"2532","goods_id":"1616","goods_name":"预约","goods_num":"1","goods_price":"1.00","discount":"0.00","pay_price":1,"status_o":1,"status_m":"待发货","goods_img":"/Uploads/Goods/20190617/5d075253c471e.png","ymd":"2019-06-18","ytime":"12:00-13:00","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/yin.png"}]
     */

    private int code;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "OrderBean{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_num='" + goods_num + '\'' +
                    ", goods_price='" + goods_price + '\'' +
                    ", discount='" + discount + '\'' +
                    ", pay_price=" + pay_price +
                    ", status_o=" + status_o +
                    ", status_m='" + status_m + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", store_id='" + store_id + '\'' +
                    ", store_name=" + store_name +
                    ", symbol='" + symbol + '\'' +
                    ", ymd='" + ymd + '\'' +
                    ", ytime='" + ytime + '\'' +
                    '}';
        }

        /**
         * id : 2530
         * goods_id : 1406
         * goods_name : 大双肩旅行者背包（A）
         * goods_num : 1
         * goods_price : 830.63
         * discount : 0.00
         * pay_price : 830.63
         * status_o : 2
         * status_m : 卖家已发货
         * goods_img : /Uploads/Picture/2018-11-19/5bf2b3fa0e988.jpg
         * store_id : 1
         * store_name : null
         * symbol : /Public/Mobile/images/x.png
         * ymd : 2019-06-18
         * ytime : 12:00-13:00
         */

        private String id;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String goods_price;
        private String discount;
        private double pay_price;
        private int status_o;
        private String status_m;
        private String goods_img;
        private String store_id;
        private String store_name;
        private String symbol;
        private String ymd;
        private String ytime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public double getPay_price() {
            return pay_price;
        }

        public void setPay_price(double pay_price) {
            this.pay_price = pay_price;
        }

        public int getStatus_o() {
            return status_o;
        }

        public void setStatus_o(int status_o) {
            this.status_o = status_o;
        }

        public String getStatus_m() {
            return status_m;
        }

        public void setStatus_m(String status_m) {
            this.status_m = status_m;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getYmd() {
            return ymd;
        }

        public void setYmd(String ymd) {
            this.ymd = ymd;
        }

        public String getYtime() {
            return ytime;
        }

        public void setYtime(String ytime) {
            this.ytime = ytime;
        }
    }
}
