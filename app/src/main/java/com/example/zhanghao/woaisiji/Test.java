package com.example.zhanghao.woaisiji;

import com.blankj.utilcode.util.ToastUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args){
        String s = s();
        System.out.println(s.equalsIgnoreCase("7C9598DFC4F2379188BC036757B1139D"));
    }
    public static String s(){
        String sourceStr = "fubaihuifubaihui";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String s = buf.toString();
            System.out.println(s);
            return s;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        ToastUtils.showShort("密码架米失败");
        return "";
    }
}
