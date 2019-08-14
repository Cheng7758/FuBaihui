package com.example.zhanghao.woaisiji.bean.my;

public class PersonalRecommendCodeBean {

    /**
     * uid:用户id
     * nickname:用户名称
     * pid：推荐人id
     * recommend_code:邀请码
     * name:推荐人名称
     */
    private String uid;
    private String nickname;
    private String pid;
    private String recommend_code;
    private String name;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRecommend_code() {
        return recommend_code;
    }

    public void setRecommend_code(String recommend_code) {
        this.recommend_code = recommend_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
