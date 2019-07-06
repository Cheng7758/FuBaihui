package com.example.zhanghao.woaisiji.bean;

import java.util.List;

public class MapGpsBeans {

    /**
     * msg : 查找成功
     * info : [{"id":"6","name":"欣欣汽修厂","province":"16","city":"1609","phone":"18612026002","pic":"/Uploads/Garage/pic/20180417/5ad55dbca09aa.jpg","ctime":"1523674560","content":"<img src=\"/Uploads/Editor/2018-04-14/5ad16e98d5840.jpg\" alt=\"\" />黄骅欣欣汽修厂","address_detail":"黄骅","longitude":"117.32502","latitude":"38.38614","cid":"2","geohash":"wwg6mgcrrp5","weight":"5","screen":"1","distance":174796},{"id":"5","name":"北京宝信行4S店","province":"1","city":"105","phone":"4009197226","pic":"/Uploads/Garage/pic/20180313/5aa784f4902e1.jpg","ctime":"1520927988","content":"欢迎光临！","address_detail":"将台乡安家楼西十里67号","longitude":"39.90881","latitude":"116.42032","cid":"2","geohash":"uzurupuxyxv","weight":"1","screen":"1","distance":6707669},{"id":"4","name":"奔驰汽修保养","province":"1","city":"105","phone":"13311558113","pic":"/Uploads/Garage/pic/20180313/5aa784610c34f.jpg","ctime":"1520927841","content":"给您最贴心的服务。","address_detail":"十八里店北桥866号","longitude":"39.90855","latitude":"116.46233","cid":"2","geohash":"uzurupuxbxg","weight":"2","screen":"1","distance":6710042}]
     * code : 200
     */

    private String msg;
    private int code;
    private List<InfoBean> info;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 6
         * name : 欣欣汽修厂
         * province : 16
         * city : 1609
         * phone : 18612026002
         * pic : /Uploads/Garage/pic/20180417/5ad55dbca09aa.jpg
         * ctime : 1523674560
         * content : <img src="/Uploads/Editor/2018-04-14/5ad16e98d5840.jpg" alt="" />黄骅欣欣汽修厂
         * address_detail : 黄骅
         * longitude : 117.32502
         * latitude : 38.38614
         * cid : 2
         * geohash : wwg6mgcrrp5
         * weight : 5
         * screen : 1
         * distance : 174796
         */

        private String id;
        private String name;
        private String province;
        private String city;
        private String phone;
        private String pic;
        private String ctime;
        private String content;
        private String address_detail;
        private String longitude;
        private String latitude;
        private String cid;
        private String geohash;
        private String weight;
        private String screen;
        private int distance;

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getGeohash() {
            return geohash;
        }

        public void setGeohash(String geohash) {
            this.geohash = geohash;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
