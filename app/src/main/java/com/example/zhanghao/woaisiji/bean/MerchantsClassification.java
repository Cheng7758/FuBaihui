package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by Cheng on 2019/8/13.
 */

public class MerchantsClassification {
    /**
     * code : 200
     * data : [{"id":"14","name":"副食"},{"id":"3","name":"大车维修"},{"id":"4","name":"汽修汽保"},{"id":"5","name":"汽车装饰"},{"id":"6","name":"洗车美容"},{"id":"7","name":"钣金补胎"},{"id":"8","name":"轮胎电瓶"},{"id":"9","name":"饭店酒店"},{"id":"10","name":"旅店宾馆"},{"id":"11","name":"综合服务"},{"id":"12","name":"商店超市"},{"id":"15","name":"美容美发"},{"id":"20","name":"待定"},{"id":"21","name":"美容保健"},{"id":"22","name":"综合服务A"},{"id":"23","name":"饭店酒店A"}]
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
         * id : 14
         * name : 副食
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
