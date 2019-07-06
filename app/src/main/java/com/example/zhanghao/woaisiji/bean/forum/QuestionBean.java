package com.example.zhanghao.woaisiji.bean.forum;

import java.util.List;

/**
 * Created by admin on 2016/10/27.
 */
public class QuestionBean {

    /**
     * code : 200
     * list : [{"id":"50","title":"标题","uid":"3","status":"1","create_time":"1475904813","update_time":"1475904813","type_id":"1","price":"1","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"49","title":"刹车不会了怎么办","uid":"3","status":"1","create_time":"1473324494","update_time":"0","type_id":"1","price":"20","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"36","title":"kkjjk","uid":"3","status":"1","create_time":"1471335752","update_time":"0","type_id":"1","price":"10","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"35","title":"11","uid":"3","status":"1","create_time":"1470995420","update_time":"0","type_id":"5","price":"20","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"34","title":"12312321312312313","uid":"3","status":"1","create_time":"1470994881","update_time":"0","type_id":"0","price":"10","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"33","title":"123123123123","uid":"3","status":"1","create_time":"1470992441","update_time":"0","type_id":"4","price":"0","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"32","title":"我在提问题 我选择了体育活动","uid":"3","status":"1","create_time":"1470992100","update_time":"0","type_id":"0","price":"30","is_sticky":"0","is_cnyj":"0","num":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"27","title":"谁来回答我悬赏50银币","uid":"3","status":"1","create_time":"1470897459","update_time":"0","type_id":"0","price":"50","is_sticky":"0","is_cnyj":"1","num":"6","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"24","title":"腾飞再来答题","uid":"3","status":"1","create_time":"1470799431","update_time":"0","type_id":"0","price":"30","is_sticky":"0","is_cnyj":"5","num":"2","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"},{"id":"23","title":"我悬赏20个 腾飞回答","uid":"3","status":"1","create_time":"1470796101","update_time":"0","type_id":"0","price":"20","is_sticky":"0","is_cnyj":"1","num":"2","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png","phone":"18612875188"}]
     */

    public int code;
    /**
     * id : 50
     * title : 标题
     * uid : 3
     * status : 1
     * create_time : 1475904813
     * update_time : 1475904813
     * type_id : 1
     * price : 1
     * is_sticky : 0
     * is_cnyj : 0
     * num : 0
     * nickname : 大鹏
     * headpic : /Uploads/Picture/2016-10-13/57ff5fdb071e1.png
     * phone : 18612875188
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
        public String nickname;
        public String headpic;
        public String phone;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
