package com.example.zhanghao.woaisiji.bean;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/17.
 */
public class OrderPreviewBean {
    private InfoBean info;
    private int code;
    private int rmb;
    private int ldrmb;
    private int fjbrmb;
    private int fybrmb;



    private int myyb;
    private List<String> g_num;
    private List<ListBean> list;
    private List<PlclistBean> plclist;
    public int getMyyb() {
        return myyb;
    }

    public void setMyyb(int myyb) {
        this.myyb = myyb;
    }
    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public int getLdrmb() {
        return ldrmb;
    }

    public void setLdrmb(int ldrmb) {
        this.ldrmb = ldrmb;
    }

    public int getFjbrmb() {
        return fjbrmb;
    }

    public void setFjbrmb(int fjbrmb) {
        this.fjbrmb = fjbrmb;
    }

    public int getFybrmb() {
        return fybrmb;
    }

    public void setFybrmb(int fybrmb) {
        this.fybrmb = fybrmb;
    }

    public List<String> getG_num() {
        return g_num;
    }

    public void setG_num(List<String> g_num) {
        this.g_num = g_num;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<PlclistBean> getPlclist() {
        return plclist;
    }

    public void setPlclist(List<PlclistBean> plclist) {
        this.plclist = plclist;
    }

    public static class InfoBean {
        private String uid;
        private String nickname;
        private String sex;
        private String birthday;
        private String qq;
        private String silver;
        private String score;
        private String login;
        private String reg_ip;
        private String reg_time;
        private String last_login_ip;
        private String last_login_time;
        private String status;
        private String sign_time;
        private String sign_last;
        private String continues;
        private String succession;
        private Object imid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getSilver() {
            return silver;
        }

        public void setSilver(String silver) {
            this.silver = silver;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getSign_last() {
            return sign_last;
        }

        public void setSign_last(String sign_last) {
            this.sign_last = sign_last;
        }

        public String getContinues() {
            return continues;
        }

        public void setContinues(String continues) {
            this.continues = continues;
        }

        public String getSuccession() {
            return succession;
        }

        public void setSuccession(String succession) {
            this.succession = succession;
        }

        public Object getImid() {
            return imid;
        }

        public void setImid(Object imid) {
            this.imid = imid;
        }
    }

    public static class ListBean {
        private String id;
        private String category;
        private String title;
        private String uid;
        private String price;
        private String number;
        private String cover;
        private String content;
        private String status;
        private String template;
        private String description;
        private String keywords;
        private String tags;
        private String supid;
        private String maxchit;
        private String position;
        private String create_time;
        private String update_time;
        private String carrmb;
        private String price_sc;
        private String orid;
        private String people;
        private String picture;
        private String f_sorts;
        private String f_silver;
        private String numberone;
        private String qg_time;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getSupid() {
            return supid;
        }

        public void setSupid(String supid) {
            this.supid = supid;
        }

        public String getMaxchit() {
            return maxchit;
        }

        public void setMaxchit(String maxchit) {
            this.maxchit = maxchit;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public String getCarrmb() {
            return carrmb;
        }

        public void setCarrmb(String carrmb) {
            this.carrmb = carrmb;
        }

        public String getPrice_sc() {
            return price_sc;
        }

        public void setPrice_sc(String price_sc) {
            this.price_sc = price_sc;
        }

        public String getOrid() {
            return orid;
        }

        public void setOrid(String orid) {
            this.orid = orid;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getF_sorts() {
            return f_sorts;
        }

        public void setF_sorts(String f_sorts) {
            this.f_sorts = f_sorts;
        }

        public String getF_silver() {
            return f_silver;
        }

        public void setF_silver(String f_silver) {
            this.f_silver = f_silver;
        }

        public String getNumberone() {
            return numberone;
        }

        public void setNumberone(String numberone) {
            this.numberone = numberone;
        }

        public String getQg_time() {
            return qg_time;
        }

        public void setQg_time(String qg_time) {
            this.qg_time = qg_time;
        }
    }

    public static class PlclistBean {
        private String id;
        private String new_user_id;
        private String new_nickname;
        private String new_place;
        private String new_mobile;
        private String status;
        private String ctime;
        private String new_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNew_user_id() {
            return new_user_id;
        }

        public void setNew_user_id(String new_user_id) {
            this.new_user_id = new_user_id;
        }

        public String getNew_nickname() {
            return new_nickname;
        }

        public void setNew_nickname(String new_nickname) {
            this.new_nickname = new_nickname;
        }

        public String getNew_place() {
            return new_place;
        }

        public void setNew_place(String new_place) {
            this.new_place = new_place;
        }

        public String getNew_mobile() {
            return new_mobile;
        }

        public void setNew_mobile(String new_mobile) {
            this.new_mobile = new_mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getNew_code() {
            return new_code;
        }

        public void setNew_code(String new_code) {
            this.new_code = new_code;
        }
    }
}