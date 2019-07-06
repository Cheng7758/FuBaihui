package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by admin on 2016/9/20.
 */
public class OrderFormBean {
    public int code;
    public List<OrderFormAll> list;


    public class OrderFormAll {
        public String id;
        public String ordernum;
        public String user_id;
        public String status;
        public String pay_status;
        public String time;
        public String place;
        public String rmb;
        public String scores;
        public String alipay;
        public String alipay_ord;
        public String logistics;
        public String number;
        public String beizhu;
        public String nickname;
        public String mobile;

        public List<OrderFormData> data;

        public List<OrderFormData> getData() {
            return data;
        }

        public void setData(List<OrderFormData> data) {
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getRmb() {
            return rmb;
        }

        public void setRmb(String rmb) {
            this.rmb = rmb;
        }

        public String getScores() {
            return scores;
        }

        public void setScores(String scores) {
            this.scores = scores;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getAlipay_ord() {
            return alipay_ord;
        }

        public void setAlipay_ord(String alipay_ord) {
            this.alipay_ord = alipay_ord;
        }

        public String getLogistics() {
            return logistics;
        }

        public void setLogistics(String logistics) {
            this.logistics = logistics;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public class OrderFormData {
        public String id;
        public String user_id;
        public String order_id;
        public String goods_id;
        public String goods_name;
        public String goods_num;
        public String goods_price;
        public String goods_maxchit;
        public String place;
        public String status;
        public String goods_f_sorts;
        public String goods_f_silver;
        public String goods_celnum;
        public String admin_id;
        public String admin_name;
        public String celback;
        public String cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getGoods_maxchit() {
            return goods_maxchit;
        }

        public void setGoods_maxchit(String goods_maxchit) {
            this.goods_maxchit = goods_maxchit;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGoods_f_sorts() {
            return goods_f_sorts;
        }

        public void setGoods_f_sorts(String goods_f_sorts) {
            this.goods_f_sorts = goods_f_sorts;
        }

        public String getGoods_f_silver() {
            return goods_f_silver;
        }

        public void setGoods_f_silver(String goods_f_silver) {
            this.goods_f_silver = goods_f_silver;
        }

        public String getGoods_celnum() {
            return goods_celnum;
        }

        public void setGoods_celnum(String goods_celnum) {
            this.goods_celnum = goods_celnum;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getCelback() {
            return celback;
        }

        public void setCelback(String celback) {
            this.celback = celback;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<OrderFormAll> getList() {
        return list;
    }

    public void setList(List<OrderFormAll> list) {
        this.list = list;
    }
}
