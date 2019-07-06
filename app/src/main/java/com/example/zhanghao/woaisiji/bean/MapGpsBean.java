package com.example.zhanghao.woaisiji.bean;

import java.util.List;

public class MapGpsBean {

    /**
     * info : [{"id":"4","name":"奔驰汽修保养","province":"1","city":"105","phone":"13311558113","pic":"/Uploads/Garage/pic/20180313/5aa784610c34f.jpg","ctime":"1520927841","content":"给您最贴心的服务。","address_detail":"十八里店北桥866号","latitude":"39.90855","longitude":"116.46233","m":19969},{"id":"5","name":"北京宝信行4S店","province":"1","city":"105","phone":"4009197226","pic":"/Uploads/Garage/pic/20180313/5aa784f4902e1.jpg","ctime":"1520927988","content":"欢迎光临！","address_detail":"将台乡安家楼西十里67号","latitude":"39.90881","longitude":"116.42032","m":23510}]
     * code : 200
     * msg : 搜索成功
     */

    private int code;
    private String msg;
    public List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 4
         * name : 奔驰汽修保养
         * province : 1
         * city : 105
         * phone : 13311558113
         * pic : /Uploads/Garage/pic/20180313/5aa784610c34f.jpg
         * ctime : 1520927841
         * content : 给您最贴心的服务。
         * address_detail : 十八里店北桥866号
         * latitude : 39.90855
         * longitude : 116.46233
         * m : 19969
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
