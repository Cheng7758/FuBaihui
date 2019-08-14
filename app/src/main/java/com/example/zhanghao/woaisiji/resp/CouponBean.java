package com.example.zhanghao.woaisiji.resp;

import java.util.List;

public class CouponBean {


    /**
     * code : 200
     * msg : success
     * data : [{"id":"1","name":"平台优惠券","silver":"2.00","money":"2.00","money_condition":"10.00",
     * "gold":"2.00","gold_condition":"10.00","use_start_time":"2019-05-25 02:07:47",
     * "use_end_time":"2019-07-24 02:07:47"},{"id":"2","name":"平台优惠券","silver":"1.00",
     * "money":"10.00","money_condition":"20.00","gold":"10.00","gold_condition":"20.00",
     * "use_start_time":"2019-05-24 02:12:41","use_end_time":"2021-07-16 02:12:41"}]
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

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 平台优惠券
         * silver : 2.00
         * money : 2.00
         * money_condition : 10.00
         * gold : 2.00
         * gold_condition : 10.00
         * use_start_time : 2019-05-25 02:07:47
         * use_end_time : 2019-07-24 02:07:47
         */

        private String id;
        private String name;
        private String silver;
        private String money;
        private String money_condition;
        private String gold;
        private String gold_condition;
        private String use_start_time;
        private String use_end_time;

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

        public String getSilver() {
            return silver;
        }

        public void setSilver(String silver) {
            this.silver = silver;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMoney_condition() {
            return money_condition;
        }

        public void setMoney_condition(String money_condition) {
            this.money_condition = money_condition;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getGold_condition() {
            return gold_condition;
        }

        public void setGold_condition(String gold_condition) {
            this.gold_condition = gold_condition;
        }

        public String getUse_start_time() {
            return use_start_time;
        }

        public void setUse_start_time(String use_start_time) {
            this.use_start_time = use_start_time;
        }

        public String getUse_end_time() {
            return use_end_time;
        }

        public void setUse_end_time(String use_end_time) {
            this.use_end_time = use_end_time;
        }
    }
}
