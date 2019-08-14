package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.merchant.SearchDataBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespSearchDataList extends RespBase {

    private SearchDataBean data;

    public SearchDataBean getData() {
        return data;
    }

    public void setData(SearchDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespSearchDataList{" +
                "data=" + data +
                '}';
    }
}
