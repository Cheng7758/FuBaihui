package com.example.zhanghao.woaisiji.bean.merchant;

import java.util.List;

public class SearchDataBean {

    private List<MerchantInfoDetailBean> store;
    private List<GoodInfoBean> goods;

    public List<GoodInfoBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodInfoBean> goods) {
        this.goods = goods;
    }

    public List<MerchantInfoDetailBean> getStore() {
        return store;
    }

    public void setStore(List<MerchantInfoDetailBean> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "SearchDataBean{" +
                "store=" + store +
                ", goods=" + goods +
                '}';
    }
}
