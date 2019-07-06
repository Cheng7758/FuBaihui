package com.example.zhanghao.woaisiji.resp;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespMerchantList extends RespBase{

    public List<MerchantInfoDetailBean> data;

    public List<MerchantInfoDetailBean> getData() {
        return data;
    }
    public void setData(List<MerchantInfoDetailBean> data) {
        this.data = data;
    }


    public static class MerchantInfoDetailBean implements Serializable {
        private String id;//店铺id，
        private String name;//店铺名称
        private String contacts;//联系人
        private String phone;//联系电话，
        private String longitude;//经度，
        private String latitude;//纬度，
        private String logo;//店铺图片，
        private String content;//商家简介，
        private String juli;//距离数(公里)

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getJuli() {
            String result="";
            if (TextUtils.isEmpty(juli))
                return result;
            double temp = Float.valueOf(juli);
            DecimalFormat myformat=new DecimalFormat("0.00");
            result = myformat.format(temp);
            return result+" 公里";
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }
    }
}
