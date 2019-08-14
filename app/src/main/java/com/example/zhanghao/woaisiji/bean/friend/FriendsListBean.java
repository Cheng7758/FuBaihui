package com.example.zhanghao.woaisiji.bean.friend;

import java.util.List;

/**
 * Created by Cheng on 2019/7/18.
 */

public class FriendsListBean {
    /**
     * code : 200
     * data : [{"add_who":"707","nickname":"藤椒鸡","headpic":"/Uploads/headpic/20190718/5d2fd31cda946.jpg"},{"add_who":"720","nickname":"15234543002","headpic":"/Uploads/headpic/20190718/5d302adbdd0e1.jpg"},{"add_who":"727","nickname":"马宇杰","headpic":"/Uploads/headpic/20190717/5d2ed23c84f94.jpg"}]
     */

    private int code;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "FriendsListBean{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "add_who='" + add_who + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", headpic='" + headpic + '\'' +
                    '}';
        }

        /**
         * add_who : 707
         * nickname : 藤椒鸡
         * headpic : /Uploads/headpic/20190718/5d2fd31cda946.jpg
         */

        private String add_who;
        private String nickname;
        private String headpic;

        public String getAdd_who() {
            return add_who;
        }

        public void setAdd_who(String add_who) {
            this.add_who = add_who;
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
    }
}
