package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.order.OrderBean;

import java.util.List;

public class RespAddOrder extends RespBase{

    private List<OrderBean> data;

    public List<OrderBean> getData() {
        return data;
    }

    public void setData(List<OrderBean> data) {
        this.data = data;
    }

}
