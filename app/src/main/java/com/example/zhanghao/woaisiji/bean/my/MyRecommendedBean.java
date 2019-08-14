package com.example.zhanghao.woaisiji.bean.my;

import java.util.List;

/**
 * Created by Cheng on 2019/8/4.
 */

public class MyRecommendedBean {
    /**
     * code : 200
     * data : [{"nickname":"齐冬哥","headpic":"/Uploads/headpic/20190801/5d42349b60493.png","reg_time":"2019-08-01 08:22:43"},{"nickname":"马宇杰","headpic":"/Uploads/headpic/20190801/5d4233c6a27fa.jpg","reg_time":"2019-08-01 08:26:18"},{"nickname":"一品县令","headpic":"/Uploads/headpic/20190801/5d425a0cf3de8.png","reg_time":"2019-08-01 08:44:09"},{"nickname":"sj_8969","headpic":"","reg_time":"2019-08-01 10:23:01"},{"nickname":"sj_4682","headpic":"/Uploads/headpic/20190802/5d43ada0cc0ca.png","reg_time":"2019-08-02 08:45:58"},{"nickname":"sj_7826","headpic":"","reg_time":"2019-08-02 09:39:18"}]
     */

    private int code;
    private List<DataBean> data;

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
        /**
         * nickname : 齐冬哥
         * headpic : /Uploads/headpic/20190801/5d42349b60493.png
         * reg_time : 2019-08-01 08:22:43
         */

        private String nickname;
        private String headpic;
        private String reg_time;

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

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }
    }
}
