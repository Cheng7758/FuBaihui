package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/13.
 */
public class DriverShoppingBean {
    private int code;
    private String ctitle;
    private Object count;

    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
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
}
