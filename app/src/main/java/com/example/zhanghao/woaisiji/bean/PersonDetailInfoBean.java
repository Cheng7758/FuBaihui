package com.example.zhanghao.woaisiji.bean;

/**
 * Created by admin on 2016/9/29.
 */
public class PersonDetailInfoBean {

    /**
     * code : 200
     * info : {"id":"3","uid":"3","sex":"2","name":"鹏鹏","age":"25","area_id":"0","portrait":"1045","marriage":"1","height":"190","is_sticky":"1","status":"1","create_time":"0","update_time":"1474447291","present":"讽德诵功帝国地方给对方干豆腐花给对方换地方","address":"","constellation":"狮子座","zodiac":"蛇","blood_type":"AB","view_num":"57","body_weight":"51000","hobby":"","year":"2015","month":"5","day":"4","prov":"28","city":"2806","dist":null,"signature":null,"detail":{"id":"6","nation":"彝","degree":"初中","is_car":"还没有","is_purchase":"已有","smoke":"经常","liqueur":"经常","gx_prov":"5","gx_city":"501","gx_dist":null,"jg_prov":"1","jg_city":"117","jg_dist":null,"monthly_salary":"0~3000","is_remote":"可以"},"nickname":"大鹏","headpic":"/Uploads/Picture/2016-09-21/57e2446538ccb.png","circle":{"id":"45","title":"123123","content":"123123","picture":"","uid":"3","status":"1","video":"","create_time":"1471505203","update_time":"1471505203","type_id":"1","is_sticky":"0","sort":"0","view_num":"0","like_num":"0","competence":"1"},"charm":9}
     */

    public int code;
    /**
     * id : 3
     * uid : 3
     * sex : 2
     * name : 鹏鹏
     * age : 25
     * area_id : 0
     * portrait : 1045
     * marriage : 1
     * height : 190
     * is_sticky : 1
     * status : 1
     * create_time : 0
     * update_time : 1474447291
     * present : 讽德诵功帝国地方给对方干豆腐花给对方换地方
     * address :
     * constellation : 狮子座
     * zodiac : 蛇
     * blood_type : AB
     * view_num : 57
     * body_weight : 51000
     * hobby :
     * year : 2015
     * month : 5
     * day : 4
     * prov : 28
     * city : 2806
     * dist : null
     * signature : null
     * detail : {"id":"6","nation":"彝","degree":"初中","is_car":"还没有","is_purchase":"已有","smoke":"经常","liqueur":"经常","gx_prov":"5","gx_city":"501","gx_dist":null,"jg_prov":"1","jg_city":"117","jg_dist":null,"monthly_salary":"0~3000","is_remote":"可以"}
     * nickname : 大鹏
     * headpic : /Uploads/Picture/2016-09-21/57e2446538ccb.png
     * circle : {"id":"45","title":"123123","content":"123123","picture":"","uid":"3","status":"1","video":"","create_time":"1471505203","update_time":"1471505203","type_id":"1","is_sticky":"0","sort":"0","view_num":"0","like_num":"0","competence":"1"}
     * charm : 9
     */

    public InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public class InfoBean {
        public String id;
        public String uid;
        public String sex;
        public String name;
        public String age;
        public String area_id;
        public String portrait;
        public String marriage;
        public String height;
        public String is_sticky;
        public String status;
        public String create_time;
        public String update_time;
        public String present; // 个人独白
        public String address;
        public String constellation;
        public String zodiac;
        public String blood_type;
        public String view_num;
        public String body_weight;
        public String hobby;
        public String year;
        public String month;
        public String day;
        public String prov;
        public String city;
        public Object dist;
        public Object signature; // 个性签名
        /**
         * id : 6
         * nation : 彝
         * degree : 初中
         * is_car : 还没有
         * is_purchase : 已有
         * smoke : 经常
         * liqueur : 经常
         * gx_prov : 5
         * gx_city : 501
         * gx_dist : null
         * jg_prov : 1
         * jg_city : 117
         * jg_dist : null
         * monthly_salary : 0~3000
         * is_remote : 可以
         */

        public DetailBean detail;
        public String nickname;
        public String headpic;
        /**
         * id : 45
         * title : 123123
         * content : 123123
         * picture :
         * uid : 3
         * status : 1
         * video :
         * create_time : 1471505203
         * update_time : 1471505203
         * type_id : 1
         * is_sticky : 0
         * sort : 0
         * view_num : 0
         * like_num : 0
         * competence : 1
         */

        public CircleBean circle;  // 司机圈
        public int charm;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getIs_sticky() {
            return is_sticky;
        }

        public void setIs_sticky(String is_sticky) {
            this.is_sticky = is_sticky;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPresent() {
            return present;
        }

        public void setPresent(String present) {
            this.present = present;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getZodiac() {
            return zodiac;
        }

        public void setZodiac(String zodiac) {
            this.zodiac = zodiac;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getBody_weight() {
            return body_weight;
        }

        public void setBody_weight(String body_weight) {
            this.body_weight = body_weight;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getDist() {
            return dist;
        }

        public void setDist(Object dist) {
            this.dist = dist;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public CircleBean getCircle() {
            return circle;
        }

        public void setCircle(CircleBean circle) {
            this.circle = circle;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public   class DetailBean {
            public String id;
            public String nation;
            public String degree;
            public String is_car;
            public String is_purchase;
            public String smoke;   // 吸烟
            public String liqueur; // 喝酒
            public String gx_prov;
            public String gx_city;
            public Object gx_dist;
            public String jg_prov;
            public String jg_city;
            public Object jg_dist;
            public String monthly_salary;
            public String is_remote;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getIs_car() {
                return is_car;
            }

            public void setIs_car(String is_car) {
                this.is_car = is_car;
            }

            public String getIs_purchase() {
                return is_purchase;
            }

            public void setIs_purchase(String is_purchase) {
                this.is_purchase = is_purchase;
            }

            public String getSmoke() {
                return smoke;
            }

            public void setSmoke(String smoke) {
                this.smoke = smoke;
            }

            public String getLiqueur() {
                return liqueur;
            }

            public void setLiqueur(String liqueur) {
                this.liqueur = liqueur;
            }

            public String getGx_prov() {
                return gx_prov;
            }

            public void setGx_prov(String gx_prov) {
                this.gx_prov = gx_prov;
            }

            public String getGx_city() {
                return gx_city;
            }

            public void setGx_city(String gx_city) {
                this.gx_city = gx_city;
            }

            public Object getGx_dist() {
                return gx_dist;
            }

            public void setGx_dist(Object gx_dist) {
                this.gx_dist = gx_dist;
            }

            public String getJg_prov() {
                return jg_prov;
            }

            public void setJg_prov(String jg_prov) {
                this.jg_prov = jg_prov;
            }

            public String getJg_city() {
                return jg_city;
            }

            public void setJg_city(String jg_city) {
                this.jg_city = jg_city;
            }

            public Object getJg_dist() {
                return jg_dist;
            }

            public void setJg_dist(Object jg_dist) {
                this.jg_dist = jg_dist;
            }

            public String getMonthly_salary() {
                return monthly_salary;
            }

            public void setMonthly_salary(String monthly_salary) {
                this.monthly_salary = monthly_salary;
            }

            public String getIs_remote() {
                return is_remote;
            }

            public void setIs_remote(String is_remote) {
                this.is_remote = is_remote;
            }
        }

        public   class CircleBean {
            public String id;
            public String title;
            public String content;
            public String picture;
            public String uid;
            public String status;
            public String video;
            public String create_time;
            public String update_time;
            public String type_id;
            public String is_sticky;
            public String sort;
            public String view_num;
            public String like_num;
            public String competence;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getIs_sticky() {
                return is_sticky;
            }

            public void setIs_sticky(String is_sticky) {
                this.is_sticky = is_sticky;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getView_num() {
                return view_num;
            }

            public void setView_num(String view_num) {
                this.view_num = view_num;
            }

            public String getLike_num() {
                return like_num;
            }

            public void setLike_num(String like_num) {
                this.like_num = like_num;
            }

            public String getCompetence() {
                return competence;
            }

            public void setCompetence(String competence) {
                this.competence = competence;
            }
        }
    }
}
