package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by admin on 2016/11/4.
 */
public class LoveBuyBean {


    /**
     * time : 1478226000
     * t10 : 1478224800
     * t11 : 1478228400
     * t12 : 1478232000
     * t13 : 1478235600
     * qg_time : 2
     * data : [{"id":"162","category":"90","title":"棕色牛皮腰带美豹斯进口腰带时尚腰带","uid":"1","price":"398","number":"100","cover":["729","730","731"],"content":"<img src=\"/Uploads/Editor/2016-09-08/57d155cd42726.jpg\" alt=\"\" />","status":"1","template":"","description":"","keywords":"","tags":"","supid":"1","maxchit":"398","position":"1","create_time":"1473336900","update_time":"1473337221","carrmb":"0","price_sc":"0","orid":"0","people":"2","picture":"729,730,731","f_sorts":"20","f_silver":"0","numberone":"1","qg_time":"2","num":0.02,"with":2},{"id":"163","category":"90","title":"红色牛皮腰带进口纯手工腰带时尚腰带韩版腰带","uid":"1","price":"358","number":"10","cover":["732","733","734"],"content":"<img src=\"/Uploads/Editor/2016-09-08/57d15a888522c.jpg\" alt=\"\" />","status":"1","template":"","description":"","keywords":"","tags":"","supid":"1","maxchit":"358","position":"1","create_time":"1473337994","update_time":"1473337998","carrmb":"0","price_sc":"0","orid":"0","people":"0","picture":"732,733,734","f_sorts":"15","f_silver":"0","numberone":"0","qg_time":"2","num":"0","with":0}]
     */

    public int time;
    public int t10;
    public int t11;
    public int t12;
    public int t13;
    public String qg_time;
    /**
     * id : 162
     * category : 90
     * title : 棕色牛皮腰带美豹斯进口腰带时尚腰带
     * uid : 1
     * price : 398
     * number : 100
     * cover : ["729","730","731"]
     * content : <img src="/Uploads/Editor/2016-09-08/57d155cd42726.jpg" alt="" />
     * status : 1
     * template :
     * description :
     * keywords :
     * tags :
     * supid : 1
     * maxchit : 398
     * position : 1
     * create_time : 1473336900
     * update_time : 1473337221
     * carrmb : 0
     * price_sc : 0
     * orid : 0
     * people : 2
     * picture : 729,730,731
     * f_sorts : 20
     * f_silver : 0
     * numberone : 1
     * qg_time : 2
     * num : 0.02
     * with : 2
     */

    public List<DataBean> data;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getT10() {
        return t10;
    }

    public void setT10(int t10) {
        this.t10 = t10;
    }

    public int getT11() {
        return t11;
    }

    public void setT11(int t11) {
        this.t11 = t11;
    }

    public int getT12() {
        return t12;
    }

    public void setT12(int t12) {
        this.t12 = t12;
    }

    public int getT13() {
        return t13;
    }

    public void setT13(int t13) {
        this.t13 = t13;
    }

    public String getQg_time() {
        return qg_time;
    }

    public void setQg_time(String qg_time) {
        this.qg_time = qg_time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public String id;
        public String category;
        public String title;
        public String uid;
        public String price;
        public String number;
        public String content;
        public String status;
        public String template;
        public String description;
        public String keywords;
        public String tags;
        public String supid;
        public String maxchit;
        public String position;
        public String create_time;
        public String update_time;
        public String carrmb;
        public String price_sc;
        public String orid;
        public String people;
        public String picture;
        public String f_sorts;
        public String f_silver;
        public String numberone;
        public String qg_time;
        public double num;
        public int with;
        public List<String> cover;

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

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public int getWith() {
            return with;
        }

        public void setWith(int with) {
            this.with = with;
        }

        public List<String> getCover() {
            return cover;
        }

        public void setCover(List<String> cover) {
            this.cover = cover;
        }
    }
}
