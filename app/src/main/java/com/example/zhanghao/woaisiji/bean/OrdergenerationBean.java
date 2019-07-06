package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/24.
 */
public class OrdergenerationBean {

    /**
     * code : 200
     * msg : 订单生成成功!
     * ordernum : 1477274746
     * post : {"beuzhu":"","car_rmb":["0"],"goods_f_silver":["392"],"goods_f_sorts":["0"],"goods_id":["257"],"goods_max":["97"],"goods_name":["爱孚威三元催化器清洗剂三元催化清洗剂排放达标节省燃油"],"goods_num":["1"],"goods_price":["98"],"plcid":"41","user_id":"3"}
     */

    private int code;
    private String msg;
    private String ordernum;
    /**
     * beuzhu :
     * car_rmb : ["0"]
     * goods_f_silver : ["392"]
     * goods_f_sorts : ["0"]
     * goods_id : ["257"]
     * goods_max : ["97"]
     * goods_name : ["爱孚威三元催化器清洗剂三元催化清洗剂排放达标节省燃油"]
     * goods_num : ["1"]
     * goods_price : ["98"]
     * plcid : 41
     * user_id : 3
     */

    private PostBean post;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    public static class PostBean {
        private String beuzhu;
        private String plcid;
        private String user_id;
        private List<String> car_rmb;
        private List<String> goods_f_silver;
        private List<String> goods_f_sorts;
        private List<String> goods_id;
        private List<String> goods_max;
        private List<String> goods_name;
        private List<String> goods_num;
        private List<String> goods_price;

        public String getBeuzhu() {
            return beuzhu;
        }

        public void setBeuzhu(String beuzhu) {
            this.beuzhu = beuzhu;
        }

        public String getPlcid() {
            return plcid;
        }

        public void setPlcid(String plcid) {
            this.plcid = plcid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<String> getCar_rmb() {
            return car_rmb;
        }

        public void setCar_rmb(List<String> car_rmb) {
            this.car_rmb = car_rmb;
        }

        public List<String> getGoods_f_silver() {
            return goods_f_silver;
        }

        public void setGoods_f_silver(List<String> goods_f_silver) {
            this.goods_f_silver = goods_f_silver;
        }

        public List<String> getGoods_f_sorts() {
            return goods_f_sorts;
        }

        public void setGoods_f_sorts(List<String> goods_f_sorts) {
            this.goods_f_sorts = goods_f_sorts;
        }

        public List<String> getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(List<String> goods_id) {
            this.goods_id = goods_id;
        }

        public List<String> getGoods_max() {
            return goods_max;
        }

        public void setGoods_max(List<String> goods_max) {
            this.goods_max = goods_max;
        }

        public List<String> getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(List<String> goods_name) {
            this.goods_name = goods_name;
        }

        public List<String> getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(List<String> goods_num) {
            this.goods_num = goods_num;
        }

        public List<String> getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(List<String> goods_price) {
            this.goods_price = goods_price;
        }
    }
}
