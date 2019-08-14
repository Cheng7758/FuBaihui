package com.example.zhanghao.woaisiji.bean.merchant;

import java.io.Serializable;

public class GoodInfoBean implements Serializable {
    private String id;//店铺id，
    private String title;//店铺名称
    private String cover;//联系人
    private String description;//联系电话，

    public GoodInfoBean() {
        super();
    }

    @Override
    public String toString() {
        return "GoodInfoBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public GoodInfoBean(String pId, String pTitle, String pCover, String pDescription) {
        id = pId;
        title = pTitle;
        cover = pCover;
        description = pDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String pCover) {
        cover = pCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }
}
