package com.example.zhanghao.woaisiji.resp;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalCoupon extends RespBase{

    private List<PersonalCouponBean> data;

    public List<PersonalCouponBean> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RespPersonalCoupon{" +
                "data=" + data +
                '}';
    }

    public void setData(List<PersonalCouponBean> data) {
        this.data = data;
    }

    public static class PersonalCouponBean{
        private String id ;
        private String name ;
        private String silver ;

        @Override
        public String toString() {
            return "PersonalCouponBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", silver='" + silver + '\'' +
                    ", money='" + money + '\'' +
                    ", money_condition='" + money_condition + '\'' +
                    ", gold='" + gold + '\'' +
                    ", gold_condition='" + gold_condition + '\'' +
                    ", use_start_time='" + use_start_time + '\'' +
                    ", use_end_time='" + use_end_time + '\'' +
                    '}';
        }

        private String money ;
        private String money_condition ;
        private String gold ;
        private String gold_condition ;
        private String use_start_time ;
        private String use_end_time ;

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
