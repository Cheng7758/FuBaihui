package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/19.
 */
public class DetailsBean {


    /**
     * code : 200
     * info : {"id":"65","category":"65","title":"赛美乐汽油机油Sj级别15W-40优质基础机油4","uid":"1","price":"150","number":"73","cover":["371"],"content":"<img src=\"/Uploads/Editor/2016-08-31/57c67768a5755.jpg\" alt=\"\" />","status":"1","template":"","description":"","keywords":"","tags":"","supid":"1","maxchit":"148","position":"2","create_time":"1472624637","update_time":"1473835120","carrmb":"0","price_sc":"180","orid":"0","people":"22","picture":"371","f_sorts":"22","f_silver":"33","numberone":"0","qg_time":"1"}
     * pllist : [{"id":"15","user_id":"9","user_name":"伱吥嗳我","g_id":"65","content":"12123","ctime":"1473842928","starts":"/Uploads/Picture/2016-08-27/57c0ea2621dcc.jpg"},{"id":"17","user_id":"3","user_name":"大鹏123","g_id":"65","content":"你好啊 真不错的润滑油","ctime":"1474430406","starts":"/Uploads/Picture/2016-08-08/57a7f5b929d9e.jpg"},{"id":"18","user_id":"3","user_name":"大鹏123","g_id":"65","content":"你好啊 真不错的润滑油","ctime":"1474430412","starts":"/Uploads/Picture/2016-08-08/57a7f5b929d9e.jpg"}]
     * plcount : 3
     */

    private int code;
    /**
     * id : 65
     * category : 65
     * title : 赛美乐汽油机油Sj级别15W-40优质基础机油4
     * uid : 1
     * price : 150
     * number : 73
     * cover : ["371"]
     * content : <img src="/Uploads/Editor/2016-08-31/57c67768a5755.jpg" alt="" />
     * status : 1
     * template :
     * description :
     * keywords :
     * tags :
     * supid : 1
     * maxchit : 148
     * position : 2
     * create_time : 1472624637
     * update_time : 1473835120
     * carrmb : 0
     * price_sc : 180
     * orid : 0
     * people : 22
     * picture : 371
     * f_sorts : 22
     * f_silver : 33
     * numberone : 0
     * qg_time : 1
     */

    private InfoBean info;
    private String plcount;
    /**
     * id : 15
     * user_id : 9
     * user_name : 伱吥嗳我
     * g_id : 65
     * content : 12123
     * ctime : 1473842928
     * starts : /Uploads/Picture/2016-08-27/57c0ea2621dcc.jpg
     */

    private List<PllistBean> pllist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getPlcount() {
        return plcount;
    }

    public void setPlcount(String plcount) {
        this.plcount = plcount;
    }

    public List<PllistBean> getPllist() {
        return pllist;
    }

    public void setPllist(List<PllistBean> pllist) {
        this.pllist = pllist;
    }

    public static class InfoBean {
        private String id;
        private String category;
        private String title;
        private String uid;
        private String price;
        private String number;
        private String content;
        private String status;
        private String template;
        private String description;
        private String keywords;
        private String tags;
        private String supid;
        private String maxchit;
        private String position;
        private String create_time;
        private String update_time;
        private String carrmb;
        private String price_sc;
        private String orid;
        private String people;
        private String picture;
        private String f_sorts;
        private String f_silver;
        private String numberone;
        private String qg_time;
        private List<String> cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getSupid() {
            return supid;
        }

        public void setSupid(String supid) {
            this.supid = supid;
        }

        public String getMaxchit() {
            return maxchit;
        }

        public void setMaxchit(String maxchit) {
            this.maxchit = maxchit;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCarrmb() {
            return carrmb;
        }

        public void setCarrmb(String carrmb) {
            this.carrmb = carrmb;
        }

        public String getPrice_sc() {
            return price_sc;
        }

        public void setPrice_sc(String price_sc) {
            this.price_sc = price_sc;
        }

        public String getOrid() {
            return orid;
        }

        public void setOrid(String orid) {
            this.orid = orid;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getF_sorts() {
            return f_sorts;
        }

        public void setF_sorts(String f_sorts) {
            this.f_sorts = f_sorts;
        }

        public String getF_silver() {
            return f_silver;
        }

        public void setF_silver(String f_silver) {
            this.f_silver = f_silver;
        }

        public String getNumberone() {
            return numberone;
        }

        public void setNumberone(String numberone) {
            this.numberone = numberone;
        }

        public String getQg_time() {
            return qg_time;
        }

        public void setQg_time(String qg_time) {
            this.qg_time = qg_time;
        }

        public List<String> getCover() {
            return cover;
        }

        public void setCover(List<String> cover) {
            this.cover = cover;
        }
    }

    public static class PllistBean {
        private String id;
        private String user_id;
        private String user_name;
        private String g_id;
        private String content;
        private String ctime;
        private String starts;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getG_id() {
            return g_id;
        }

        public void setG_id(String g_id) {
            this.g_id = g_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getStarts() {
            return starts;
        }

        public void setStarts(String starts) {
            this.starts = starts;
        }
    }
}
