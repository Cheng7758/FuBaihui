package com.example.zhanghao.woaisiji.widget.PickerView;

import java.io.Serializable;

/**
 * Created by Cheng on 2019/7/2.
 */

public class Pickers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String showConetnt;

    private String pickersId ;

    public String getPickersId() {
        return pickersId;
    }

    public void setPickersId(String pickersId) {
        this.pickersId = pickersId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getShowConetnt() {
        return showConetnt;
    }

    public void setShowConetnt(String pShowConetnt) {
        showConetnt = pShowConetnt;
    }

    public Pickers() {
        super();
    }

    public Pickers(String pShowConetnt, String pickersId) {
        showConetnt = pShowConetnt;
        this.pickersId = pickersId;
    }
}
