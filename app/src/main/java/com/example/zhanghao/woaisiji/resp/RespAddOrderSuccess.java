package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.order.OrderSuccessBean;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespAddOrderSuccess extends RespBase{

    private OrderSuccessBean id;

    public OrderSuccessBean getId() {
        return id;
    }

    public void setId(OrderSuccessBean id) {
        this.id = id;
    }
}
