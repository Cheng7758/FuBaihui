package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by Cheng on 2019/7/29.
 */

public class BannerBean {
    /**
     * code : 200
     * data : [{"title":"福百惠广告1","picture":"4620","tzurl":"","img":"/Uploads/Goods/20190729/5d3e905e48f40.jpg"},{"title":"福百惠广告2","picture":"4621","tzurl":"","img":"/Uploads/Goods/20190729/5d3e90fac1c4a.jpg"}]
     */

    private int code;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "BannerBean{" +
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
                    "title='" + title + '\'' +
                    ", picture='" + picture + '\'' +
                    ", tzurl='" + tzurl + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }

        /**
         * title : 福百惠广告1
         * picture : 4620
         * tzurl :
         * img : /Uploads/Goods/20190729/5d3e905e48f40.jpg
         */

        private String title;
        private String picture;
        private String tzurl;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTzurl() {
            return tzurl;
        }

        public void setTzurl(String tzurl) {
            this.tzurl = tzurl;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
