package com.example.zhanghao.woaisiji.my;

/**
 * Created by chenzheng on 2019/7/4.
 * The only genius that is worth anything is the genius for hard work
 *
 * @author chenzheng
 * @date 2019/7/4
 * @description
 */
public class ShopsDetails {

    /**
     * code : 200
     * data : {"username":"123","phone":"17601635700","address":"123","remark":"","order_sn":"1548620190531","add_time":"2019-05-31 09:12:45","goods_id":"1627","goods_price":"830.63","goods_num":"1","goods_name":"大双肩旅行者背包（A）","goods_img":"/Uploads/Goods/20190701/5d19d002e5b93.png","status_o":2,"status_m":"卖家已发货","symbol":"/Public/Mobile/images/x.png","total_price":"830.63","discount":"0.00","pay_price":"830.63"}
     * msg : success
     */

    private int code;
    private DataBean data;
    private String msg;

    @Override
    public String toString() {
        return "ShopsDetails{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", remark='" + remark + '\'' +
                    ", order_sn='" + order_sn + '\'' +
                    ", add_time='" + add_time + '\'' +
                    ", goods_id='" + goods_id + '\'' +
                    ", goods_price='" + goods_price + '\'' +
                    ", goods_num='" + goods_num + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", status_o=" + status_o +
                    ", status_m='" + status_m + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", total_price='" + total_price + '\'' +
                    ", discount='" + discount + '\'' +
                    ", pay_price='" + pay_price + '\'' +
                    '}';
        }

        /**
         * username : 123
         * phone : 17601635700
         * address : 123
         * remark :
         * order_sn : 1548620190531
         * add_time : 2019-05-31 09:12:45
         * goods_id : 1627
         * goods_price : 830.63
         * goods_num : 1
         * goods_name : 大双肩旅行者背包（A）
         * goods_img : /Uploads/Goods/20190701/5d19d002e5b93.png
         * status_o : 2
         * status_m : 卖家已发货
         * symbol : /Public/Mobile/images/x.png
         * total_price : 830.63
         * discount : 0.00
         * pay_price : 830.63
         */

        private String username;
        private String phone;
        private String address;
        private String remark;
        private String order_sn;
        private String add_time;
        private String goods_id;
        private String goods_price;
        private String goods_num;
        private String goods_name;
        private String goods_img;
        private int status_o;
        private String status_m;
        private String symbol;
        private String total_price;
        private String discount;
        private String pay_price;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
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

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }
    }
}
