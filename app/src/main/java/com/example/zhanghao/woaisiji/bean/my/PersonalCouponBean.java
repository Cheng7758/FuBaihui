package com.example.zhanghao.woaisiji.bean.my;

public class PersonalCouponBean {
    /**
     * money : 1.00
     * money_condition : 100.00
     * gold : 0.00
     * gold_condition : 0.00
     * store_id : 153
     * order_id : 0
     * use_start_time : 1560278547
     * use_end_time : 1565462547
     * name : 米线
     */

    private String money;
    private String money_condition;
    private String gold;
    private String gold_condition;
    private String store_id;
    private String order_id;
    private String use_start_time;
    private String use_end_time;
    private String name;

    public PersonalCouponBean(String pMoney, String pMoney_condition, String pGold, String pGold_condition, String pStore_id, String pOrder_id, String pUse_start_time, String pUse_end_time, String pName) {
        money = pMoney;
        money_condition = pMoney_condition;
        gold = pGold;
        gold_condition = pGold_condition;
        store_id = pStore_id;
        order_id = pOrder_id;
        use_start_time = pUse_start_time;
        use_end_time = pUse_end_time;
        name = pName;
    }

    public PersonalCouponBean() {
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

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String silver ;
    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }
    private String id ;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*private String id ;
    private String name ;
    private String silver ;
    private String money ;
    private String money_condition ;
    private String gold ;
    private String gold_condition ;
    private String use_start_time ;
    private String use_end_time ;

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
    }*/
}
