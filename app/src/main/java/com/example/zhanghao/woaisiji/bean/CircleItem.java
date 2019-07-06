package com.example.zhanghao.woaisiji.bean;

import android.text.TextUtils;

import java.util.List;


public class CircleItem extends BaseBean {

    public final static String TYPE_URL = "1";
    public final static String TYPE_IMG = "2";
    public final static String TYPE_VIDEO = "3";
    private static final long serialVersionUID = 1L;

    // 说说的id
    private String id;
    private String content;
    private String createTime;
    private String type;//1:链接  2:图片 3:视频
    private String linkImg;
    private String linkTitle;
    private List<String> photos;
    private List<FavortItem> favorters;
    private List<CommentItem> comments;
    private List<CommentItem> newComments;
    private User user;
    private String videoUrl;
    private String videoImgUrl;
    private String favortNum;
    private String favortId;

    public String getFavortId() {
        return favortId;
    }

    public void setFavortId(String favortId) {
        this.favortId = favortId;
    }

    public String getFavorNum() {
        return favortNum;
    }

    public void setFavorNum(String favorNum) {
        this.favortNum = favorNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FavortItem> getFavorters() {
        return favorters;
    }

    public void setFavorters(List<FavortItem> favorters) {
        this.favorters = favorters;
    }

    public List<CommentItem> getNewComments() {
        return newComments;
    }
    public void setNewComments(List<CommentItem> newComments) {
        this.newComments = newComments;
    }

    public List<CommentItem> getComments() {
        return comments;
    }
    public void setComments(List<CommentItem> comments) {
        this.comments = comments;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public boolean hasFavort() {
        if (!("0".equals(favortNum))) {
            return true;
        }
        return false;
    }

    public boolean hasComment() {
        if (comments != null && comments.size() > 0) {
            return true;
        }
        return false;
    }

    public String getCurUserFavortId(String curUserId) {
        String favortid = "";
        if (!TextUtils.isEmpty(curUserId) && hasFavort()) {
            for (FavortItem item : favorters) {
                if (curUserId.equals(item.getUser().getId())) {
                    favortid = item.getId();
                    return favortid;
                }
            }
        }
        return favortid;
    }
}
