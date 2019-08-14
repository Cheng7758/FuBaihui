package com.example.zhanghao.woaisiji.global;

import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.wxapi.MD5Util;
import com.ta.utdid2.android.utils.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2016/8/17.
 */
public class Constants {
    // 中国地区服务器地址
    public final static String CHINA_ADDRESS = "http://qxu1194850212.my3w.com/Public/get_city";
    /**
     * 是否第一次运行
     */
    public final static String KEY_IS_FIRST_RUN = "is_first_run";

    // 我爱司机用户的环信登录密码，不可删除
//    public final static String EMPASSWORD = "111111";
    public final static String BYTES = "fbhim" ;
    public static String EMPASSWORD (){
        String uid = WoAiSiJiApp.getUid();
        if (StringUtils.isEmpty(uid)){
            Toast.makeText(WoAiSiJiApp.getContext(), "无法获取到用户UID", Toast.LENGTH_SHORT).show();
            return "";
        }
        String sourceStr = BYTES + uid;

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
            return  buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        ToastUtils.showShort("密码加密失败");
        return "";
    }
    // 我爱司机微信appkey
    // wxfcfa44a55987c87e

}
