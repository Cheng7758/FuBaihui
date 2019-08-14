package com.example.zhanghao.woaisiji.my;

import java.util.List;

/**
 * Created by chenzheng on 2019/7/4.
 * The only genius that is worth anything is the genius for hard work
 *
 * @author chenzheng
 * @date 2019/7/4
 * @description
 */
public class ShopsDetailBean {

    /**
     * code : 200
     * data : {"id":"1561","title":"苹果","description":"","content":"流量监控那买吧","price":"60.00","number":"1000","cover":"/Uploads/Goods/20190516/5cdd0ae57c771.png","price_sc":"100.00","sorts":"60.00","silver":"100.00","people":"0","reservation":"1","images":["/Uploads/Goods/20190516/5cdd0b543929e.png","/Uploads/Goods/20190516/5cdcd0019e547.png"],"timeList":["09:30-10:00","11:00-12:00","13:00-14:00","19:00-21:00"],"date":"2019-05-21"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1561
         * title : 苹果
         * description :
         * content : 流量监控那买吧
         * price : 60.00
         * number : 1000
         * cover : /Uploads/Goods/20190516/5cdd0ae57c771.png
         * price_sc : 100.00
         * sorts : 60.00
         * silver : 100.00
         * people : 0
         * reservation : 1
         * images : ["/Uploads/Goods/20190516/5cdd0b543929e.png","/Uploads/Goods/20190516/5cdcd0019e547.png"]
         * timeList : ["09:30-10:00","11:00-12:00","13:00-14:00","19:00-21:00"]
         * date : 2019-05-21
         */

        private String id;
        private String title;
        private String description;
        private String content;
        private String price;
        private String number;
        private String cover;
        private String price_sc;
        private String sorts;
        private String silver;
        private String people;
        private String reservation;
        private String date;
        private List<String> images;
        private List<String> timeList;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPrice_sc() {
            return price_sc;
        }

        public void setPrice_sc(String price_sc) {
            this.price_sc = price_sc;
        }

        public String getSorts() {
            return sorts;
        }

        public void setSorts(String sorts) {
            this.sorts = sorts;
        }

        public String getSilver() {
            return silver;
        }

        public void setSilver(String silver) {
            this.silver = silver;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public String getReservation() {
            return reservation;
        }

        public void setReservation(String reservation) {
            this.reservation = reservation;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<String> timeList) {
            this.timeList = timeList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", content='" + content + '\'' +
                    ", price='" + price + '\'' +
                    ", number='" + number + '\'' +
                    ", cover='" + cover + '\'' +
                    ", price_sc='" + price_sc + '\'' +
                    ", sorts='" + sorts + '\'' +
                    ", silver='" + silver + '\'' +
                    ", people='" + people + '\'' +
                    ", reservation='" + reservation + '\'' +
                    ", date='" + date + '\'' +
                    ", images=" + images +
                    ", timeList=" + timeList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShopsDetailBean{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
