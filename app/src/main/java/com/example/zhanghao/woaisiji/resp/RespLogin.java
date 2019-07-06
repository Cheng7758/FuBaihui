package com.example.zhanghao.woaisiji.resp;

public class RespLogin extends RespBase{

    private UserLoginBean data ;

    public UserLoginBean getData() {
        return data;
    }

    public void setData(UserLoginBean data) {
        this.data = data;
    }

    public static class UserLoginBean{
        private String uid;
        private String user_type;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }
}
