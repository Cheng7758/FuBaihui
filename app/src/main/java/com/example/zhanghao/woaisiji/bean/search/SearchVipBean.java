package com.example.zhanghao.woaisiji.bean.search;

import java.util.List;

/**
 * Created by Cheng on 2019/7/14.
 */

public class SearchVipBean {
    /**
     * code : 200
     * msg : success
     * data : [{"nickname":"15234543002","headpic":"","uid":"720","username":"15234543002","is_friend":0}]
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

    @Override
    public String toString() {
        return "SearchVipBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "nickname='" + nickname + '\'' +
                    ", headpic='" + headpic + '\'' +
                    ", uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    ", is_friend=" + is_friend +
                    '}';
        }

        /**
         * nickname : 15234543002
         * headpic :
         * uid : 720
         * username : 15234543002
         * is_friend : 0
         */

        private String nickname;
        private String headpic;
        private String uid;
        private String username;
        private int is_friend;

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

        public int getIs_friend() {
            return is_friend;
        }

        public void setIs_friend(int is_friend) {
            this.is_friend = is_friend;
        }
    }
}
