package com.example.zhanghao.woaisiji.my;

import java.util.List;

/**
 * Created by Cheng on 2019/6/30.
 */

public class MyCollectionBean {
    /**
     * code : 200
     * data : [{"title":"夏季棉麻马甲女薄款韩版中长款收腰马夹百搭坎肩无袖风衣款女大码","price":"1000.00","cover":"3430","description":"","sorts":"0.00","silver":"0.00","img":"/Uploads/Goods/20190625/5d1186f45dad6.png"}]
     */

    private int code;
    private List<DataBean> data;

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

    @Override
    public String toString() {
        return "MyCollectionBean{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * title : 夏季棉麻马甲女薄款韩版中长款收腰马夹百搭坎肩无袖风衣款女大码
         * price : 1000.00
         * cover : 3430
         * description :
         * sorts : 0.00
         * silver : 0.00
         * img : /Uploads/Goods/20190625/5d1186f45dad6.png
         */

        private String id;
        private String title;
        private String price;
        private String cover;
        private String description;
        private String sorts;
        private String silver;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String pId) {
            id = pId;
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", price='" + price + '\'' +
                    ", cover='" + cover + '\'' +
                    ", description='" + description + '\'' +
                    ", sorts='" + sorts + '\'' +
                    ", silver='" + silver + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

}
