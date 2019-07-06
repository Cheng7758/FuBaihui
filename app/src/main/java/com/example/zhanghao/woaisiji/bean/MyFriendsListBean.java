package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by admin on 2016/10/14.
 */
public class MyFriendsListBean {

    /**
     * code : 200
     * list : [{"uid":"10","nickname":"宸浩帅","sex":"0","birthday":"0000-00-00","qq":"","silver":"10890","score":"0","login":"6","reg_ip":"0","reg_time":"0","last_login_ip":"2067983053","last_login_time":"1475921927","status":"1","sign_time":"0","sign_last":"0","continues":"0","succession":"0","imid":null,"headpic":"/Uploads/Picture/2016-08-05/57a3f1e4e7f67.png","id":"32"},{"uid":"13","nickname":"aaaa","sex":"0","birthday":"0000-00-00","qq":"","silver":"12280","score":"50","login":"5","reg_ip":"0","reg_time":"0","last_login_ip":"1780928165","last_login_time":"1470824091","status":"1","sign_time":"0","sign_last":"0","continues":"0","succession":"0","imid":null,"headpic":"/Uploads/Picture/2016-08-05/57a3e4d587b2f.png","id":"89"},{"uid":"14","nickname":"西瓜","sex":"0","birthday":"0000-00-00","qq":"","silver":"10009","score":"2","login":"42","reg_ip":"0","reg_time":"0","last_login_ip":"1780990334","last_login_time":"1476176890","status":"1","sign_time":"1476344751","sign_last":"1476344751","continues":"1","succession":"1","imid":null,"headpic":"/Uploads/Picture/2016-09-17/57dd4e9090f48.jpg","id":"57"},{"uid":"15","nickname":"12345678000","sex":"0","birthday":"0000-00-00","qq":"","silver":"12328","score":"1","login":"2","reg_ip":"0","reg_time":"0","last_login_ip":"1780928167","last_login_time":"1470964398","status":"1","sign_time":"0","sign_last":"0","continues":"0","succession":"0","imid":null,"headpic":"/Uploads/Picture/head/logo.png","id":"88"},{"uid":"32","nickname":"Funk biu biu biu","sex":"1","birthday":"2003-05-27","qq":"","silver":"12470","score":"0","login":"60","reg_ip":"1780990327","reg_time":"1472698484","last_login_ip":"1780990327","last_login_time":"1476352769","status":"1","sign_time":"1476344804","sign_last":"1476344804","continues":"2","succession":"1","imid":null,"headpic":"/Uploads/Picture/2016-10-10/57fb64c95a7bb.jpg","id":"119"}]
     */

    public int code;
    /**
     * uid : 10
     * nickname : 宸浩帅
     * sex : 0
     * birthday : 0000-00-00
     * qq :
     * silver : 10890
     * score : 0
     * login : 6
     * reg_ip : 0
     * reg_time : 0
     * last_login_ip : 2067983053
     * last_login_time : 1475921927
     * status : 1
     * sign_time : 0
     * sign_last : 0
     * continues : 0
     * succession : 0
     * imid : null
     * headpic : /Uploads/Picture/2016-08-05/57a3f1e4e7f67.png
     * id : 32
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

    public static class ListBean {
        public String uid;
        public String nickname;
        public String sex;
        public String birthday;
        public String qq;
        public String silver;
        public String score;
        public String login;
        public String reg_ip;
        public String reg_time;
        public String last_login_ip;
        public String last_login_time;
        public String status;
        public String sign_time;
        public String sign_last;
        public String continues;
        public String succession;
        public Object imid;
        public String headpic;
        public String id;
        public String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getSilver() {
            return silver;
        }

        public void setSilver(String silver) {
            this.silver = silver;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getSign_last() {
            return sign_last;
        }

        public void setSign_last(String sign_last) {
            this.sign_last = sign_last;
        }

        public String getContinues() {
            return continues;
        }

        public void setContinues(String continues) {
            this.continues = continues;
        }

        public String getSuccession() {
            return succession;
        }

        public void setSuccession(String succession) {
            this.succession = succession;
        }

        public Object getImid() {
            return imid;
        }

        public void setImid(Object imid) {
            this.imid = imid;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
