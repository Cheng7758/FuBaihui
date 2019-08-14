package com.example.zhanghao.woaisiji.bean.homesearch;

import java.util.List;

/**
 * Created by Cheng on 2019/7/14.
 */

public class HomeSearch {
    /**
     * code : 200
     * data : {"store":[{"id":"158","name":"米线油","logo":"/Uploads/Garage/pic/20190701/5d19aa22820df.png","content":"","juli":0}],"goods":[{"id":"1622","title":"长城摩托车专用油","cover":"/Uploads/Goods/20190625/5d11889a5ae01.png","description":"商品是为了出售而生产的劳动成果，是人类社会生产力发展到一定历史阶段的产物，是用于交换的劳动产品。恩格斯对此进行了科学的总结：商品\u201c首先是私人产品。但是，只有这些私人产品不是为自己消费，而是为他人的消费，即为社会的消费而生产时，它们才成为商品；它们通过交换进入社会的消费\u201d。 [1] \r\n会计学中商品的定义是商品流通企业外购或委托加工完成，验收入库用于销售的各种商品。在人教版必修一政治书中的定义是用于交换的劳动产品。商品的基本属性是价值和使用价值。价值是商品的本质属性，使用价值是商品的自然属性。"}]}
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

        public static class StoreBean {
            /**
             * id : 158
             * name : 米线油
             * logo : /Uploads/Garage/pic/20190701/5d19aa22820df.png
             * content :
             * juli : 0
             */

            private String id;
            private String name;
            private String logo;
            private String content;
            private int juli;

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

            public int getJuli() {
                return juli;
            }

            public void setJuli(int juli) {
                this.juli = juli;
            }
        }

        public static class GoodsBean {
            /**
             * id : 1622
             * title : 长城摩托车专用油
             * cover : /Uploads/Goods/20190625/5d11889a5ae01.png
             * description : 商品是为了出售而生产的劳动成果，是人类社会生产力发展到一定历史阶段的产物，是用于交换的劳动产品。恩格斯对此进行了科学的总结：商品“首先是私人产品。但是，只有这些私人产品不是为自己消费，而是为他人的消费，即为社会的消费而生产时，它们才成为商品；它们通过交换进入社会的消费”。 [1]
             会计学中商品的定义是商品流通企业外购或委托加工完成，验收入库用于销售的各种商品。在人教版必修一政治书中的定义是用于交换的劳动产品。商品的基本属性是价值和使用价值。价值是商品的本质属性，使用价值是商品的自然属性。
             */

            private String id;
            private String title;
            private String cover;
            private String description;

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
    }
}
