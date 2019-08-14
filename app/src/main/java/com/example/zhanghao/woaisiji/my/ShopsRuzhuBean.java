package com.example.zhanghao.woaisiji.my;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cheng on 2019/7/1.
 */

public class ShopsRuzhuBean {

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DpflBean> dpfl;
        private List<FlbqBean> flbq;

        private List<ShengBean> ssx;

        public List<DpflBean> getDpfl() {
            return dpfl;
        }

        public void setDpfl(List<DpflBean> dpfl) {
            this.dpfl = dpfl;
        }

        public List<FlbqBean> getFlbq() {
            return flbq;
        }

        public void setFlbq(List<FlbqBean> flbq) {
            this.flbq = flbq;
        }

        public List<ShengBean> getSsx() {
            return ssx;
        }

        public void setSsx(List<ShengBean> ssx) {
            this.ssx = ssx;
        }

        public static class DpflBean {
            /**
             * id : 14
             * name : 副食
             * business : 13
             * golden_integral : 1.00
             * silver_integral : 0.50
             */

            private String id;
            private String name;
            private String business;
            private String golden_integral;
            private String silver_integral;

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

            public String getBusiness() {
                return business;
            }

            public void setBusiness(String business) {
                this.business = business;
            }

            public String getGolden_integral() {
                return golden_integral;
            }

            public void setGolden_integral(String golden_integral) {
                this.golden_integral = golden_integral;
            }

            public String getSilver_integral() {
                return silver_integral;
            }

            public void setSilver_integral(String silver_integral) {
                this.silver_integral = silver_integral;
            }
        }

        public static class FlbqBean {
            /**
             * id : 2
             * name : 旗舰店
             * time : 2018-04-20 10:39:11
             */

            private String id;
            private String name;
            private String time;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class ShengBean implements Serializable {
            private String region_id;
            private String parent_id;
            private String region_name;
            private List<ShiBean> shi;

            public String getRegion_id() {
                return region_id;
            }
            public void setRegion_id(String region_id) {
                this.region_id = region_id;
            }

            public String getParent_id() {
                return parent_id;
            }
            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getRegion_name() {
                return region_name;
            }
            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }

            public List<ShiBean> getShi() {
                return shi;
            }
            public void setShi(List<ShiBean> shi) {
                this.shi = shi;
            }
        }

        public static class ShiBean implements Serializable {
            private String region_id;
            private String parent_id;
            private String region_name;

            private List<XianBean> xian;

            public String getRegion_id() {
                return region_id;
            }

            public void setRegion_id(String region_id) {
                this.region_id = region_id;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }

            public List<XianBean> getXian() {
                return xian;
            }

            public void setXian(List<XianBean> xian) {
                this.xian = xian;
            }
        }

        public static class XianBean implements Serializable {

            private String region_id;
            private String parent_id;
            private String region_name;

            public String getRegion_id() {
                return region_id;
            }
            public void setRegion_id(String region_id) {
                this.region_id = region_id;
            }

            public String getParent_id() {
                return parent_id;
            }
            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getRegion_name() {
                return region_name;
            }
            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }

        }
    }
}
