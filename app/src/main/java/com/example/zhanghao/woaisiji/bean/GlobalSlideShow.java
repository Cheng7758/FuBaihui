package com.example.zhanghao.woaisiji.bean;

import android.text.TextUtils;

import com.example.zhanghao.woaisiji.global.ServerAddress;

public class GlobalSlideShow {

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
        if (!TextUtils.isEmpty(img))
            img = ServerAddress.SERVER_ROOT + img;
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
