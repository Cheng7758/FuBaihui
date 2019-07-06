package com.example.zhanghao.woaisiji.bean.forum;

import java.util.List;

/**
 * Created by admin on 2016/10/28.
 */
public class MyAnswerBean {

    /**
     * code : 200
     * list : [{"id":"1","title":"咋的","uid":"56","status":"1","create_time":"1463386827","update_time":"1476164478","type_id":"1","price":"10","is_sticky":"1","is_cnyj":"5","num":"2"},{"id":"2","title":"为什么1+1=2","uid":"88","status":"1","create_time":"1463386959","update_time":"1476164479","type_id":"1","price":"30","is_sticky":"1","is_cnyj":"0","num":"12"},{"id":"3","title":"1+2 = ","uid":"2","status":"1","create_time":"1463392667","update_time":"1476164479","type_id":"4","price":"20","is_sticky":"1","is_cnyj":"0","num":"3"},{"id":"5","title":"南海仲裁结果到底是啥","uid":"3","status":"1","create_time":"1468809435","update_time":"0","type_id":"0","price":"10","is_sticky":"0","is_cnyj":"2","num":"2"},{"id":"22","title":"谁啊  来十个","uid":"3","status":"1","create_time":"1470794340","update_time":"0","type_id":"0","price":"10","is_sticky":"0","is_cnyj":"0","num":"1"},{"id":"23","title":"我悬赏20个 腾飞回答","uid":"3","status":"1","create_time":"1470796101","update_time":"0","type_id":"0","price":"20","is_sticky":"0","is_cnyj":"1","num":"2"},{"id":"24","title":"腾飞再来答题","uid":"3","status":"1","create_time":"1470799431","update_time":"0","type_id":"0","price":"30","is_sticky":"0","is_cnyj":"5","num":"2"},{"id":"26","title":"ddddddddddddddddddddddddddddddd","uid":"4","status":"1","create_time":"1470818157","update_time":"0","type_id":"0","price":"60","is_sticky":"0","is_cnyj":"1","num":"2"},{"id":"27","title":"谁来回答我悬赏50银币","uid":"3","status":"1","create_time":"1470897459","update_time":"0","type_id":"0","price":"50","is_sticky":"0","is_cnyj":"1","num":"6"},{"id":"47","title":"各位朋友你们好，我想问问，我的车使用的尿素，怎么经常堵喷嘴呢，哪位朋友给我分析分析","uid":"20","status":"1","create_time":"1472208665","update_time":"0","type_id":"6","price":"20","is_sticky":"0","is_cnyj":"0","num":"1"},{"id":"59","title":"露肩镂空咯连锁快乐","uid":"9","status":"1","create_time":"1476685548","update_time":"1476685548","type_id":"6","price":"50","is_sticky":"0","is_cnyj":"11","num":"2"}]
     */

    public int code;
    /**
     * id : 1
     * title : 咋的
     * uid : 56
     * status : 1
     * create_time : 1463386827
     * update_time : 1476164478
     * type_id : 1
     * price : 10
     * is_sticky : 1
     * is_cnyj : 5
     * num : 2
     */

    public List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean {
        public String id;
        public String title;
        public String uid;
        public String status;
        public String create_time;
        public String update_time;
        public String type_id;
        public String price;
        public String is_sticky;
        public String is_cnyj;
        public String num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIs_sticky() {
            return is_sticky;
        }

        public void setIs_sticky(String is_sticky) {
            this.is_sticky = is_sticky;
        }

        public String getIs_cnyj() {
            return is_cnyj;
        }

        public void setIs_cnyj(String is_cnyj) {
            this.is_cnyj = is_cnyj;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
