package com.example.zhanghao.woaisiji.bean;

import java.util.List;

public class AutoServiceBean {

    /**
     * category : [{"id":"1","name":"全部"},{"id":"2","name":"汽修厂"},{"id":"3","name":"加油站"}]
     * screen : [{"id":"1","name":"全部"},{"id":"2","name":"旗舰店"},{"id":"3","name":"加盟店"}]
     * code : 200
     * msg : 查找成功
     */

    private int code;
    private String msg;
    private List<CategoryBean> category;
    private List<ScreenBean> screen;

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

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<ScreenBean> getScreen() {
        return screen;
    }

    public void setScreen(List<ScreenBean> screen) {
        this.screen = screen;
    }

    public static class CategoryBean {
        /**
         * id : 1
         * name : 全部
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ScreenBean {
        /**
         * id : 1
         * name : 全部
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
