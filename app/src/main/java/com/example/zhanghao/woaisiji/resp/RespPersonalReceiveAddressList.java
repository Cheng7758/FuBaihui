package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.my.PersonalHarvestBean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespPersonalReceiveAddressList extends RespBase{

    private List<PersonalHarvestBean> list;

    public List<PersonalHarvestBean> getList() {
        return list;
    }

    public void setList(List<PersonalHarvestBean> list) {
        this.list = list;
    }
}
