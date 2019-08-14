package com.example.zhanghao.woaisiji.resp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cheng on 2019/7/1.
 */

public class OrderBean implements Parcelable {

    /**
     * code : 200
     * data : [{"id":"2713","ordernum":"20190717110145","goods_id":"1671","goods_name":"海尔施特劳斯净水机","goods_num":"1","goods_price":"3280.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":3280,"status_o":0,"status_m":"未付款","goods_img":"/Uploads/Goods/20190712/5d281a66030d2.jpg","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"},{"id":"2711","ordernum":"20190717108628","goods_id":"1622","goods_name":"长城摩托车专用油","goods_num":"1","goods_price":"50.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":50,"status_o":1,"status_m":"待发货","goods_img":"/Uploads/Goods/20190625/5d11889a5ae01.png","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"},{"id":"2710","ordernum":"20190717111814","goods_id":"1671","goods_name":"海尔施特劳斯净水机","goods_num":"1","goods_price":"3280.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":3280,"status_o":1,"status_m":"待发货","goods_img":"/Uploads/Goods/20190712/5d281a66030d2.jpg","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"},{"id":"2707","ordernum":"20190717098327","goods_id":"1621","goods_name":"夏季棉麻马甲女薄款韩版中长款收腰马夹百搭坎肩无袖风衣款女大码","goods_num":"1","goods_price":"1000.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":1000,"status_o":0,"status_m":"未付款","goods_img":"/Uploads/Goods/20190625/5d1186f45dad6.png","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"},{"id":"2706","ordernum":"20190717097002","goods_id":"1673","goods_name":"贵妻芒果百色特有的桂七，桂七--唯一一款视为贡品的香果","goods_num":"1","goods_price":"1.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":1,"status_o":0,"status_m":"未付款","goods_img":"/Uploads/Goods/20190713/5d299cbcc750a.jpg","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"},{"id":"2705","ordernum":"20190717097829","goods_id":"1673","goods_name":"贵妻芒果百色特有的桂七，桂七--唯一一款视为贡品的香果","goods_num":"1","goods_price":"1.00","wuliu":"","wuliunum":"","discount":"0.00","pay_price":1,"status_o":0,"status_m":"未付款","goods_img":"/Uploads/Goods/20190713/5d299cbcc750a.jpg","store_id":0,"store_name":"福百惠自营","symbol":"/Public/Mobile/images/x.png"}]
     */

    private int code;
    private List<DataBean> data;

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


    public static class DataBean implements Parcelable {

        /**
         * id : 2713
         * ordernum : 20190717110145
         * goods_id : 1671
         * goods_name : 海尔施特劳斯净水机
         * goods_num : 1
         * goods_price : 3280.00
         * wuliu :
         * wuliunum :
         * discount : 0.00
         * pay_price : 3280
         * status_o : 0
         * status_m : 未付款
         * goods_img : /Uploads/Goods/20190712/5d281a66030d2.jpg
         * store_id : 0
         * store_name : 福百惠自营
         * symbol : /Public/Mobile/images/x.png
         */

        private String id;
        private String ordernum;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String goods_price;
        private String wuliu;
        private String wuliunum;
        private String discount;
        private int pay_price;
        private int status_o;
        private String status_m;
        private String goods_img;
        private int store_id;
        private String store_name;
        private String symbol;

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

        public String getWuliu() {
            return wuliu;
        }

        public void setWuliu(String wuliu) {
            this.wuliu = wuliu;
        }

        public String getWuliunum() {
            return wuliunum;
        }

        public void setWuliunum(String wuliunum) {
            this.wuliunum = wuliunum;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getPay_price() {
            return pay_price;
        }

        public void setPay_price(int pay_price) {
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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.ordernum);
            dest.writeString(this.goods_id);
            dest.writeString(this.goods_name);
            dest.writeString(this.goods_num);
            dest.writeString(this.goods_price);
            dest.writeString(this.wuliu);
            dest.writeString(this.wuliunum);
            dest.writeString(this.discount);
            dest.writeInt(this.pay_price);
            dest.writeInt(this.status_o);
            dest.writeString(this.status_m);
            dest.writeString(this.goods_img);
            dest.writeInt(this.store_id);
            dest.writeString(this.store_name);
            dest.writeString(this.symbol);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.ordernum = in.readString();
            this.goods_id = in.readString();
            this.goods_name = in.readString();
            this.goods_num = in.readString();
            this.goods_price = in.readString();
            this.wuliu = in.readString();
            this.wuliunum = in.readString();
            this.discount = in.readString();
            this.pay_price = in.readInt();
            this.status_o = in.readInt();
            this.status_m = in.readString();
            this.goods_img = in.readString();
            this.store_id = in.readInt();
            this.store_name = in.readString();
            this.symbol = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.data);
    }

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        this.code = in.readInt();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>
            () {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
