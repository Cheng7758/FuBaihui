package com.example.zhanghao.woaisiji.wxapi.share;

import android.app.Activity;
import android.graphics.Bitmap;

public class ShereFactory {
    public ShereFactory(String action, String title, String desc, String imgUrl, String url, Activity context) {



        this.action = action;
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.url = url;
        this.context = context;
    }
    public ShereFactory(String action, Bitmap imgBitmap, Activity context) {



        this.action = action;
        this.imgBitmap = imgBitmap;
        this.context = context;
    }

    public abstract class ShereType {
      public final static String QQ = "QQ";
      public final static String QQZONE="QQZONE";
      public final static String WEIXIN = "WEIXIN";
      public final static String WEIXINZONE = "WEIXINZONE";
      public final static String WEIBO = "WEIBO";
    }
    private String action;
    private String title;
    private String desc;
    private String imgUrl;
    private String url;

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    private Bitmap imgBitmap;
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    private Activity context;
}
