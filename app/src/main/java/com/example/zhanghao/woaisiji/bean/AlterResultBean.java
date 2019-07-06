package com.example.zhanghao.woaisiji.bean;

/**
 * Created by admin on 2016/9/7.
 */

/**
 *
 * 添加评论时，返回结果
 * 添加购物车时，返回结果
 * 发布游记时，返回结果
 * 取消订单时，返回结果
 * 司机圈点赞，返回结果
 * 司机圈发布评论，返回结果
 * 司机圈添加收藏，返回结果
 */
public class AlterResultBean {
    public int code;
    public String msg;

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
}
