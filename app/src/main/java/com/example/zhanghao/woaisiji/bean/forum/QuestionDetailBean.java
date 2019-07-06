package com.example.zhanghao.woaisiji.bean.forum;

import java.util.List;

/**
 * Created by admin on 2016/10/29.
 */
public class QuestionDetailBean {

    /**
     * code : 200
     * info : {"id":"1","title":"咋的","uid":"56","status":"1","create_time":"1463386827","update_time":"1476164478","type_id":"1","price":"10","is_sticky":"1","is_cnyj":"5","reply":[{"id":"1","content":"古人定义的","pid":"1","uid":"4","create_time":"1463390736","top":"0","shit":"0","nickname":"不会飞的小猪","headpic":"/Uploads/Picture/head/logo.png"},{"id":"59","content":"回答内容","pid":"1","uid":"3","create_time":"1475910124","top":"0","shit":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png"}]}
     */

    public int code;
    /**
     * id : 1
     * title : 咋的
     * uid : 56
     * status : 1
     * create_time : 1463386827
     * update_time : 1476164478
     * type_id : 1
     * price : 10
     * is_sticky : 1
     * is_cnyj : 5
     * reply : [{"id":"1","content":"古人定义的","pid":"1","uid":"4","create_time":"1463390736","top":"0","shit":"0","nickname":"不会飞的小猪","headpic":"/Uploads/Picture/head/logo.png"},{"id":"59","content":"回答内容","pid":"1","uid":"3","create_time":"1475910124","top":"0","shit":"0","nickname":"大鹏","headpic":"/Uploads/Picture/2016-10-13/57ff5fdb071e1.png"}]
     */

    public InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        public String id;
        public String title;
        public String uid;
        public String status;
        public String create_time;
        public String update_time;
        public String type_id;
        public String price;
        public String is_sticky;
        public String is_cnyj;
        /**
         * id : 1
         * content : 古人定义的
         * pid : 1
         * uid : 4
         * create_time : 1463390736
         * top : 0
         * shit : 0
         * nickname : 不会飞的小猪
         * headpic : /Uploads/Picture/head/logo.png
         */

        public List<ReplyBean> reply;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIs_sticky() {
            return is_sticky;
        }

        public void setIs_sticky(String is_sticky) {
            this.is_sticky = is_sticky;
        }

        public String getIs_cnyj() {
            return is_cnyj;
        }

        public void setIs_cnyj(String is_cnyj) {
            this.is_cnyj = is_cnyj;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class ReplyBean {
            public String id;
            public String content;
            public String pid;
            public String uid;
            public String create_time;
            public String top;
            public String shit;
            public String nickname;
            public String headpic;
            public String status;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getShit() {
                return shit;
            }

            public void setShit(String shit) {
                this.shit = shit;
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
    }
}
