package com.example.zhanghao.woaisiji.bean;

/**
 * Created by zhanghao on 2016/10/24.
 */
public class OrderadvancepaymentBean {

    /**
     * id : 101
     * ordernum : 1473144603
     * user_id : 3
     * status : 0
     * pay_status : 0
     * time : 0
     * place : 北京市朝阳区中弘北京像素南区6号楼 0223
     * rmb : 169
     * scores : 0
     * alipay : 0
     * alipay_order :
     * logistics :
     * number :
     * beizhu : null
     * nickname : 大鹏测试账
     * mobile : 18612875188
     */

    private ListBean list;
    /**
     * list : {"id":"101","ordernum":"1473144603","user_id":"3","status":"0","pay_status":"0","time":"0","place":"北京市朝阳区中弘北京像素南区6号楼 0223","rmb":"169","scores":"0","alipay":"0","alipay_order":"","logistics":"","number":"","beizhu":null,"nickname":"大鹏测试账","mobile":"18612875188"}
     * jifen : 2668
     */

    private String jifen;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    public static class ListBean {
        private String id;
        private String ordernum;
        private String user_id;
        private String status;
        private String pay_status;
        private String time;
        private String place;
        private String rmb;
        private String scores;
        private String alipay;
        private String alipay_order;
        private String logistics;
        private String number;
        private Object beizhu;
        private String nickname;
        private String mobile;

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

        public String getAlipay_order() {
            return alipay_order;
        }

        public void setAlipay_order(String alipay_order) {
            this.alipay_order = alipay_order;
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

        public Object getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(Object beizhu) {
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
}
