package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalDetailBillBean {
    public int code;
    public List<DetailBillBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DetailBillBean> getList() {
        return list;
    }

    public void setList(List<DetailBillBean> list) {
        this.list = list;
    }

    public static class DetailBillBean {
        public String id;
        public String uid;
        public String oid;
        public String silver;
        public String score;
        public String ctime;
        public String status;
        public String back1;
        public String back2;
        public String back3;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
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

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBack1() {
            return back1;
        }

        public void setBack1(String back1) {
            this.back1 = back1;
        }

        public String getBack2() {
            return back2;
        }

        public void setBack2(String back2) {
            this.back2 = back2;
        }

        public String getBack3() {
            return back3;
        }

        public void setBack3(String back3) {
            this.back3 = back3;
        }
    }


}
