package com.example.zhanghao.woaisiji.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/21.
 */
public class AppMallBean {
    public int appMallIcon;
    public int appMallIvEnter;
    public String appMallName;
    public List<String> appMallClassifyList;
    public AppMallBean(){
        appMallClassifyList = new ArrayList<>();
    }

    public int getAppMallIcon() {
        return appMallIcon;
    }

    public void setAppMallIcon(int appMallIcon) {
        this.appMallIcon = appMallIcon;
    }

    public int getAppMallIvEnter() {
        return appMallIvEnter;
    }

    public void setAppMallIvEnter(int appMallIvEnter) {
        this.appMallIvEnter = appMallIvEnter;
    }

    public String getAppMallName() {
        return appMallName;
    }

    public void setAppMallName(String appMallName) {
        this.appMallName = appMallName;
    }
}
