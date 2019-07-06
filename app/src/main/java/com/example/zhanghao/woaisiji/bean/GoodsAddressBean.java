package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * 收获地址
 * Created by admin on 2016/9/7.
 */
public class GoodsAddressBean {
    public int code;
    public List<ObtainGoods> list;

    public class ObtainGoods {
        public String id;
        public String new_user_id;
        public String new_nickname;
        public String new_place;
        public String new_mobile;
        public String status;
        public String ctime;
        public String new_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNew_user_id() {
            return new_user_id;
        }

        public void setNew_user_id(String new_user_id) {
            this.new_user_id = new_user_id;
        }

        public String getNew_nickname() {
            return new_nickname;
        }

        public void setNew_nickname(String new_nickname) {
            this.new_nickname = new_nickname;
        }

        public String getNew_place() {
            return new_place;
        }

        public void setNew_place(String new_place) {
            this.new_place = new_place;
        }

        public String getNew_mobile() {
            return new_mobile;
        }

        public void setNew_mobile(String new_mobile) {
            this.new_mobile = new_mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getNew_code() {
            return new_code;
        }

        public void setNew_code(String new_code) {
            this.new_code = new_code;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ObtainGoods> getList() {
        return list;
    }

    public void setList(List<ObtainGoods> list) {
        this.list = list;
    }
}
