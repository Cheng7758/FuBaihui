package com.example.zhanghao.woaisiji.resp;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespFBHStoreCategory extends RespBase{

    private List<FBHStoreCategory> data;

    public List<FBHStoreCategory> getData() {
        return data;
    }

    public void setData(List<FBHStoreCategory> data) {
        this.data = data;
    }

    public static class FBHStoreCategory {

        private String id ,title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
