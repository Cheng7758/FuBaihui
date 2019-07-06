package com.example.zhanghao.woaisiji.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2016/9/6.
 */
public class AgeAndConstellation {
    public static int getAge(String birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
//        cal.setTime(birthDay);

        int yearBirth = Integer.parseInt(birthDay.split("-")[0]);
        int monthBirth = Integer.parseInt(birthDay.split("-")[1]);
        int dayOfMonthBirth = Integer.parseInt(birthDay.split("-")[2]);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }
        return age;
    }

    public final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    public final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };


   public static String getConstellation(String birthDay) throws Exception{
        int year = Integer.parseInt(birthDay.split("-")[0]);
        int month = Integer.parseInt(birthDay.split("-")[1]);
        int day = Integer.parseInt(birthDay.split("-")[2]);
//        return "星座";
       if (month<1 || month > 12){
           return "摩羯座";
       }
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }
}
