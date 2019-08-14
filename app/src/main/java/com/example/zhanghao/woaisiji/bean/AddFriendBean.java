package com.example.zhanghao.woaisiji.bean;

import java.util.List;

public class AddFriendBean {
    /**
     * code : 200
     * msg : success
     * data : [{"nickname":"A","headpic":"","uid":"671","username":"18525241451","is_friend":"0     是否是好友 0不是 1已是好友"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickname : A
         * headpic :
         * uid : 671
         * username : 18525241451
         * is_friend : 0     是否是好友 0不是 1已是好友
         */

        private String nickname;
        private String headpic;
        private String uid;
        private String username;
        private String is_friend;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIs_friend() {
            return is_friend;
        }

        public void setIs_friend(String is_friend) {
            this.is_friend = is_friend;
        }
    }
/*
    *//**
     * code : 200
     * msg : 查找成功
     * data : [{"id":"306","username":"18001077129","password":"9bbfdbd67dc3e6f8bc2352fb5c1cf689","confirm_password":"","email":null,"mobile":"","reg_time":"1515555941","reg_ip":"2071389247","last_login_time":"1523257720","last_login_ip":"1908954575","update_time":"1515555941","status":"1","place":null,"sex":"0","borthday":null,"likes":null,"relname":null,"marriage":"0","offer":null,"infonumber":null,"school":null,"working":null,"headpic":null,"code":null,"nickname":"18001077129"}]
     *//*

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        *//**
     * id : 306
     * username : 18001077129
     * password : 9bbfdbd67dc3e6f8bc2352fb5c1cf689
     * confirm_password :
     * email : null
     * mobile :
     * reg_time : 1515555941
     * reg_ip : 2071389247
     * last_login_time : 1523257720
     * last_login_ip : 1908954575
     * update_time : 1515555941
     * status : 1
     * place : null
     * sex : 0
     * borthday : null
     * likes : null
     * relname : null
     * marriage : 0
     * offer : null
     * infonumber : null
     * school : null
     * working : null
     * headpic : null
     * code : null
     * nickname : 18001077129
     *//*

        private String id;
        private String username;
        private String password;
        private String confirm_password;
        private Object email;
        private String mobile;
        private String reg_time;
        private String reg_ip;
        private String last_login_time;
        private String last_login_ip;
        private String update_time;
        private String status;
        private Object place;
        private String sex;
        private Object borthday;
        private Object likes;
        private Object relname;
        private String marriage;
        private Object offer;
        private Object infonumber;
        private Object school;
        private Object working;
        private Object headpic;
        private Object code;
        private String nickname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getBorthday() {
            return borthday;
        }

        public void setBorthday(Object borthday) {
            this.borthday = borthday;
        }

        public Object getLikes() {
            return likes;
        }

        public void setLikes(Object likes) {
            this.likes = likes;
        }

        public Object getRelname() {
            return relname;
        }

        public void setRelname(Object relname) {
            this.relname = relname;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public Object getOffer() {
            return offer;
        }

        public void setOffer(Object offer) {
            this.offer = offer;
        }

        public Object getInfonumber() {
            return infonumber;
        }

        public void setInfonumber(Object infonumber) {
            this.infonumber = infonumber;
        }

        public Object getSchool() {
            return school;
        }

        public void setSchool(Object school) {
            this.school = school;
        }

        public Object getWorking() {
            return working;
        }

        public void setWorking(Object working) {
            this.working = working;
        }

        public Object getHeadpic() {
            return headpic;
        }

        public void setHeadpic(Object headpic) {
            this.headpic = headpic;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }*/

}
