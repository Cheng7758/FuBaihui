package com.example.zhanghao.woaisiji.bean.my;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String region_name;
    private String parent_id;
    private String region_id;
    private String region_type;
    private String agency_id;
    private List<CityBean> city;

    public JsonBean(String pRegion_name, String pParent_id, String pRegion_id, String pRegion_type, String pAgency_id, List<CityBean> pCity) {

        region_name = pRegion_name;
        parent_id = pParent_id;
        region_id = pRegion_id;
        region_type = pRegion_type;
        agency_id = pAgency_id;
        city = pCity;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String pRegion_name) {
        region_name = pRegion_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String pParent_id) {
        parent_id = pParent_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String pRegion_id) {
        region_id = pRegion_id;
    }

    public String getRegion_type() {
        return region_type;
    }

    public void setRegion_type(String pRegion_type) {
        region_type = pRegion_type;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String pAgency_id) {
        agency_id = pAgency_id;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> pCity) {
        city = pCity;
    }

    public String getName() {
        return region_name;
    }

    public void setName(String name) {
        this.region_name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。

    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String region_name;
        private String parent_id;
        private String region_id;
        private String region_type;
        private String agency_id;
        private List<County> County;

        public CityBean(String pRegion_name, String pParent_id, String pRegion_id, String pRegion_type, String pAgency_id, List<CityBean.County> pCounty) {
            region_name = pRegion_name;
            parent_id = pParent_id;
            region_id = pRegion_id;
            region_type = pRegion_type;
            agency_id = pAgency_id;
            County = pCounty;
        }

        @Override
        public String toString() {
            return "CityBean{" +
                    "region_name='" + region_name + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", region_id='" + region_id + '\'' +
                    ", region_type='" + region_type + '\'' +
                    ", agency_id='" + agency_id + '\'' +
                    ", County=" + County +
                    '}';
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String pRegion_name) {
            region_name = pRegion_name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String pParent_id) {
            parent_id = pParent_id;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String pRegion_id) {
            region_id = pRegion_id;
        }

        public String getRegion_type() {
            return region_type;
        }

        public void setRegion_type(String pRegion_type) {
            region_type = pRegion_type;
        }

        public String getAgency_id() {
            return agency_id;
        }

        public void setAgency_id(String pAgency_id) {
            agency_id = pAgency_id;
        }

        public List<CityBean.County> getCounty() {
            return County;
        }

        public void setCounty(List<CityBean.County> pCounty) {
            County = pCounty;
        }

        public String getName() {
            return region_name;
        }

        public void setName(String name) {
            this.region_name = name;
        }

        public List<County> getArea() {
            return County;
        }

        public void setArea(List<County> area) {
            this.County = area;
        }

        public static class County {
            private String region_name;
            private String parent_id;
            private String region_id;
            private String region_type;
            private String agency_id;

            public County(String pRegion_name, String pParent_id, String pRegion_id, String pRegion_type, String pAgency_id) {
                region_name = pRegion_name;
                parent_id = pParent_id;
                region_id = pRegion_id;
                region_type = pRegion_type;
                agency_id = pAgency_id;
            }

            @Override
            public String toString() {
                return "County{" +
                        "region_name='" + region_name + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", region_id='" + region_id + '\'' +
                        ", region_type='" + region_type + '\'' +
                        ", agency_id='" + agency_id +"}"
                        ;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String pRegion_name) {
                region_name = pRegion_name;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String pParent_id) {
                parent_id = pParent_id;
            }

            public String getRegion_id() {
                return region_id;
            }

            public void setRegion_id(String pRegion_id) {
                region_id = pRegion_id;
            }

            public String getRegion_type() {
                return region_type;
            }

            public void setRegion_type(String pRegion_type) {
                region_type = pRegion_type;
            }

            public String getAgency_id() {
                return agency_id;
            }

            public void setAgency_id(String pAgency_id) {
                agency_id = pAgency_id;
            }
        }
    }
}
