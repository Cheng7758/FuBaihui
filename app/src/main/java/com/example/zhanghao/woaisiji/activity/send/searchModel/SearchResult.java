package com.example.zhanghao.woaisiji.activity.send.searchModel;

import com.example.zhanghao.woaisiji.resp.RespBase;

import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019/7/20
 * Description:
 */
public class SearchResult extends RespBase {

    /**
     * data : {"store":[{"id":"110","name":"积分消费，天天乐面包房，编号FS20190318电话81308459/13716745560","logo":"/Uploads/Garage/pic/20190318/5c8f0e7958073.jpg","content":"<div style=\"text-align:center;\">\r\n\t<img src=\"/Uploads/Editor/2019-03-18/5c8f30acdc53d.jpg\" alt=\"\" /><br />\r\n<\/div>","longitude":"115.98405","latitude":"39.69051","juli":14326.23},{"id":"112","name":"四平总店","logo":"/Uploads/Garage/pic/20190701/5d19aaf78a0e6.png","content":"<p>\r\n\t<p>\r\n\t\t测试数据\r\n\t<\/p>\r\n<\/p>","longitude":"124.37382","latitude":"43.17497","juli":14465.82},{"id":"162","name":"小面馆","logo":null,"content":"","longitude":"115.986959","latitude":"39.656003","juli":14322.52}],"goods":[{"id":"1621","title":"夏季棉麻马甲女薄款韩版中长款收腰马夹百搭坎肩无袖风衣款女大码","cover":"/Uploads/Goods/20190625/5d1186f45dad6.png","description":"商品是为了出售而生产的劳动成果，是人类社会生产力发展到一定历史阶段的产物，是用于交换的劳动产品。恩格斯对此进行了科学的总结：商品\u201c首先是私人产品。但是，只有这些私人产品不是为自己消费，而是为他人的消费，即为社会的消费而生产时，它们才成为商品；它们通过交换进入社会的消费\u201d。 [1] \r\n会计学中商品的定义是商品流通企业外购或委托加工完成，验收入库用于销售的各种商品。在人教版必修一政治书中的定义是用于交换的劳动产品。商品的基本属性是价值和使用价值。价值是商品的本质属性，使用价值是商品的自然属性。"},{"id":"1623","title":"长城润滑油 工业齿轮油","cover":"/Uploads/Goods/20190625/5d1189047a1ba.png","description":"商品是为了出售而生产的劳动成果，是人类社会生产力发展到一定历史阶段的产物，是用于交换的劳动产品。恩格斯对此进行了科学的总结：商品\u201c首先是私人产品。但是，只有这些私人产品不是为自己消费，而是为他人的消费，即为社会的消费而生产时，它们才成为商品；它们通过交换进入社会的消费\u201d。 [1] \r\n会计学中商品的定义是商品流通企业外购或委托加工完成，验收入库用于销售的各种商品。在人教版必修一政治书中的定义是用于交换的劳动产品。商品的基本属性是价值和使用价值。价值是商品的本质属性，使用价值是商品的自然属性。"},{"id":"1633","title":"润滑油","cover":"/Uploads/Goods/20190711/5d26aa47ba17e.png","description":"润滑油是用在各种类型汽车、机械设备上以减少摩擦，保护机械及加工件的液体或半固体润滑剂，主要起润滑、辅助冷却、防锈、清洁、密封和缓冲等作用（Roab)。"},{"id":"1659","title":"云泡沫净颜洁面慕斯    100ml","cover":"/Uploads/Goods/20190712/5d2806cb33f13.jpg","description":"云泡沫净颜洁面慕斯"},{"id":"1663","title":"沁润植养面膜    25*5片","cover":"/Uploads/Goods/20190712/5d2813bce9d0c.png","description":"沁润植养面膜"}]}
     */

    private DataBean data;

    @Override
    public String toString() {
        return "SearchResult{" +
                "data=" + data +
                '}';
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "store=" + store +
                    ", goods=" + goods +
                    '}';
        }

        private List<StoreBean> store;
        private List<GoodsBean> goods;

        public List<StoreBean> getStore() {
            return store;
        }

        public void setStore(List<StoreBean> store) {
            this.store = store;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class StoreBean extends ItemData {
            @Override
            public String toString() {
                return "StoreBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", logo='" + logo + '\'' +
                        ", content='" + content + '\'' +
                        ", longitude='" + longitude + '\'' +
                        ", latitude='" + latitude + '\'' +
                        ", juli='" + juli + '\'' +
                        '}';
            }

            /**
             * id : 110
             * name : 积分消费，天天乐面包房，编号FS20190318电话81308459/13716745560
             * logo : /Uploads/Garage/pic/20190318/5c8f0e7958073.jpg
             * content : <div style="text-align:center;">
             * <img src="/Uploads/Editor/2019-03-18/5c8f30acdc53d.jpg" alt="" /><br />
             * </div>
             * longitude : 115.98405
             * latitude : 39.69051
             * juli : 14326.23
             */

            private String id;
            private String name;
            private String logo;
            private String content;
            private String longitude;
            private String latitude;
            private String juli;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getJuli() {
                return juli;
            }

            public void setJuli(String juli) {
                this.juli = juli;
            }
        }

        public static class GoodsBean extends ItemData {
            /**
             * id : 1621
             * title : 夏季棉麻马甲女薄款韩版中长款收腰马夹百搭坎肩无袖风衣款女大码
             * cover : /Uploads/Goods/20190625/5d1186f45dad6.png
             * description : 商品是为了出售而生产的劳动成果，是人类社会生产力发展到一定历史阶段的产物，是用于交换的劳动产品。恩格斯对此进行了科学的总结：商品“首先是私人产品。但是，只有这些私人产品不是为自己消费，而是为他人的消费，即为社会的消费而生产时，它们才成为商品；它们通过交换进入社会的消费”。 [1]
             * 会计学中商品的定义是商品流通企业外购或委托加工完成，验收入库用于销售的各种商品。在人教版必修一政治书中的定义是用于交换的劳动产品。商品的基本属性是价值和使用价值。价值是商品的本质属性，使用价值是商品的自然属性。
             */

            private String id;
            private String title;
            private String cover;
            private String description;

            @Override
            public String toString() {
                return "GoodsBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", cover='" + cover + '\'' +
                        ", description='" + description + '\'' +
                        '}';
            }

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
        }

        public static class ItemData {
        }
    }
}
