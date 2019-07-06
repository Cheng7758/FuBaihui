package com.example.zhanghao.woaisiji.resp;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalWallet extends RespBase{

    private PersonalWalletBean data;

    public PersonalWalletBean getData() {
        return data;
    }

    public void setData(PersonalWalletBean data) {
        this.data = data;
    }

    public static class PersonalWalletBean{
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
}
