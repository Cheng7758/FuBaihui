package com.example.zhanghao.woaisiji.bean.my;

import java.io.Serializable;

public class PersonalWalletBean implements Serializable {
    private static final long serialVersionUID = -1261746444868100025L;
    private String score;
    private String store_score;
    private String silver;
    private String balance;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStore_score() {
        return store_score;
    }

    public void setStore_score(String store_score) {
        this.store_score = store_score;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
