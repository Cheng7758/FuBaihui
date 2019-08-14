package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.bean.GlobalSlideShow;

import java.util.List;

/**
 * Created by zhanghao on 2016/9/19.
 */
public class RespGlobalSlideShow extends RespBase {

    private List<GlobalSlideShow> data;
    public List<GlobalSlideShow> getData() {
        return data;
    }

    public void setData(List<GlobalSlideShow> data) {
        this.data = data;
    }
}
