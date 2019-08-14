package com.example.zhanghao.woaisiji.resp;

import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.List;


public class RespCommodityList extends RespBase {

    private List<CommodityDataDetail> data;

    public List<CommodityDataDetail> getData() {
        return data;
    }

    public void setData(List<CommodityDataDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespCommodityList{" +
                "data=" + data +
                '}';
    }

    public static class CommodityDataDetail {
        private String id;
        private String category;
        private String title;
        private String price;
        private String number;
        private String cover;
        private String price_sc;
        private String sorts;
        private String silver;
        private String people;

        @Override
        public String toString() {
            return "CommodityDataDetail{" +
                    "id='" + id + '\'' +
                    ", category='" + category + '\'' +
                    ", title='" + title + '\'' +
                    ", price='" + price + '\'' +
                    ", number='" + number + '\'' +
                    ", cover='" + cover + '\'' +
                    ", price_sc='" + price_sc + '\'' +
                    ", sorts='" + sorts + '\'' +
                    ", silver='" + silver + '\'' +
                    ", people='" + people + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
            return ServerAddress.SERVER_ROOT + cover;
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
    }
}
