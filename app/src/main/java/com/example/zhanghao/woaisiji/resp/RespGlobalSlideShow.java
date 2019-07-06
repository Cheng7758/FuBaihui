package com.example.zhanghao.woaisiji.resp;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/19.
 */
public class RespGlobalSlideShow extends RespBase {

    private List<GlobalSlideShow> data;
    public List<GlobalSlideShow> getData() {
        return data;
    }

    public void setData(List<GlobalSlideShow> data) {
        this.data = data;
    }

    public static class GlobalSlideShow{
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
