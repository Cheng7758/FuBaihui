package com.example.zhanghao.woaisiji.bean.my;

import android.text.TextUtils;

import com.example.zhanghao.woaisiji.global.ServerAddress;

public class PersonalInfoBean {

    //若果有的值为 0  那就是保密
    private String sex, marriage, age, borthday, offer, school, pic;
    private String nickname, reg_time, username;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBorthday() {
        return borthday;
    }

    public void setBorthday(String borthday) {
        this.borthday = borthday;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPic() {
        if (TextUtils.isEmpty(pic)) {
            pic = null;
        }
        return pic;
    }

    public void setPic(String picTemp) {
        /*if (!TextUtils.isEmpty(picTemp)) {
            if (!picTemp.contains(ServerAddress.SERVER_ROOT)) {
                picTemp = ServerAddress.SERVER_ROOT + picTemp;
            }
        }*/
        this.pic = picTemp;
    }
}
