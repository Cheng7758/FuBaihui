package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by admin on 2016/9/12.
 */
public class DriverHealthBean     {
    public int code;
    public List<DriverHealthList> list;

    public class DriverHealthList{
        public String id;
        public String title;
        public String content;
        public String[] picture;
        public String uid;
        public String status;
        public String video;
        public String create_time;
        public String update_time;
        public String type_id;
        public String is_sticky;
        public String view_num;
        public String nickname;
        public String headpic;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String[] getPicture() {
            return picture;
        }

        public void setPicture(String[] picture) {
            this.picture = picture;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getIs_sticky() {
            return is_sticky;
        }

        public void setIs_sticky(String is_sticky) {
            this.is_sticky = is_sticky;
        }

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DriverHealthList> getList() {
        return list;
    }

    public void setList(List<DriverHealthList> list) {
        this.list = list;
    }
}
