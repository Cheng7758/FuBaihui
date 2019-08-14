package com.hyphenate.easeui.bean;

/**
 * Created by Cheng on 2019/7/29.
 */

public class FriendsBean {
    /**
     * code : 200
     * data : {"sex":"0","marriage":"0","age":"0","borthday":"0000-00-00","offer":"0","school":"0","username":"17630940857","type":"0","pic":"/Uploads/headpic/20190729/5d3eb0b62bfb7.png","nickname":"雷好啊","reg_time":"1564389475"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sex : 0
         * marriage : 0
         * age : 0
         * borthday : 0000-00-00
         * offer : 0
         * school : 0
         * username : 17630940857
         * type : 0
         * pic : /Uploads/headpic/20190729/5d3eb0b62bfb7.png
         * nickname : 雷好啊
         * reg_time : 1564389475
         */

        private String sex;
        private String marriage;
        private String age;
        private String borthday;
        private String offer;
        private String school;
        private String username;
        private String type;
        private String pic;
        private String nickname;
        private String reg_time;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBorthday() {
            return borthday;
        }

        public void setBorthday(String borthday) {
            this.borthday = borthday;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }
    }
}
