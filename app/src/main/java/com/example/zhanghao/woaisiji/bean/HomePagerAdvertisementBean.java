package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class HomePagerAdvertisementBean {

    private int code;
    private ContentData data;

//    private List<ListBean> list;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

//    public List<ListBean> getList() {
//        return list;
//    }
//    public void setList(List<ListBean> list) {
//        this.list = list;
//    }

    public ContentData getData() {
        return data;
    }
    public void setData(ContentData data) {
        this.data = data;
    }

//    public static class ListBean {
//        private String id;
//        private String title;
//        private String catewz;
//        private String picture;
//        private String brief;
//        private String content;
//        private String tzurl;
//        private String status;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getCatewz() {
//            return catewz;
//        }
//
//        public void setCatewz(String catewz) {
//            this.catewz = catewz;
//        }
//
//        public String getPicture() {
//            return picture;
//        }
//
//        public void setPicture(String picture) {
//            this.picture = picture;
//        }
//
//        public String getBrief() {
//            return brief;
//        }
//
//        public void setBrief(String brief) {
//            this.brief = brief;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getTzurl() {
//            return tzurl;
//        }
//
//        public void setTzurl(String tzurl) {
//            this.tzurl = tzurl;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }

    public static class ContentData{
        private List<JingXuan> jingxuan;
        private List<GongGao> gonggao;

        public List<JingXuan> getJingxuan() {
            return jingxuan;
        }

        public void setJingxuan(List<JingXuan> jingxuan) {
            this.jingxuan = jingxuan;
        }

        public List<GongGao> getGonggao() {
            return gonggao;
        }

        public void setGonggao(List<GongGao> gonggao) {
            this.gonggao = gonggao;
        }
    }
    /**
     * 精选
     */
    public static class JingXuan{
        private String id;
        private String path;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
    }

    /**
     * 公獒
     */
    public static class GongGao{
        private String nid;
        private String title;

        public String getNid() {
            return nid;
        }
        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
    }

}
