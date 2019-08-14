package com.example.zhanghao.woaisiji.bean.merchandise;

import java.util.List;

/**
 * Created by Cheng on 2019/7/31.
 */

public class MerchandiseDetails {
    /**
     * code : 200
     * data : [{"id":"59","user_id":"707","user_name":"藤***鸡","g_id":"1694","content":"","goods_c":"5","logistics_c":"5","service_c":"5","img":["/Uploads/Pingjia/1694//20190729/5d3efb07de6d6.jpg","/Uploads/Pingjia/1694//20190729/5d3efb07e9beb.jpg","/Uploads/Pingjia/1694//20190729/5d3efb07eabf4.jpg"],"ctime":"1564408583","add_time":"2019-07-29 21:56:23"},{"id":"58","user_id":"707","user_name":"藤***鸡","g_id":"1694","content":"","goods_c":"5","logistics_c":"5","service_c":"5","img":["/Uploads/Pingjia/1694//20190729/5d3eee8b07dad.jpg","/Uploads/Pingjia/1694//20190729/5d3eee8b0d9f6.jpg"],"ctime":"1564405387","add_time":"2019-07-29 21:03:07"}]
     */

    private int code;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "MerchandiseDetails{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 59
         * user_id : 707
         * user_name : 藤***鸡
         * g_id : 1694
         * content :
         * goods_c : 5
         * logistics_c : 5
         * service_c : 5
         * img : ["/Uploads/Pingjia/1694//20190729/5d3efb07de6d6.jpg","/Uploads/Pingjia/1694//20190729/5d3efb07e9beb.jpg","/Uploads/Pingjia/1694//20190729/5d3efb07eabf4.jpg"]
         * ctime : 1564408583
         * add_time : 2019-07-29 21:56:23
         */

        private String id;
        private String user_id;
        private String user_name;
        private String g_id;
        private String content;
        private String goods_c;
        private String logistics_c;
        private String service_c;
        private String ctime;
        private String add_time;
        private List<String> img;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", g_id='" + g_id + '\'' +
                    ", content='" + content + '\'' +
                    ", goods_c='" + goods_c + '\'' +
                    ", logistics_c='" + logistics_c + '\'' +
                    ", service_c='" + service_c + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", add_time='" + add_time + '\'' +
                    ", img=" + img +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getG_id() {
            return g_id;
        }

        public void setG_id(String g_id) {
            this.g_id = g_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGoods_c() {
            return goods_c;
        }

        public void setGoods_c(String goods_c) {
            this.goods_c = goods_c;
        }

        public String getLogistics_c() {
            return logistics_c;
        }

        public void setLogistics_c(String logistics_c) {
            this.logistics_c = logistics_c;
        }

        public String getService_c() {
            return service_c;
        }

        public void setService_c(String service_c) {
            this.service_c = service_c;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
