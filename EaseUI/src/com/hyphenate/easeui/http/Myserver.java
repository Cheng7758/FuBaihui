package com.hyphenate.easeui.http;

import com.hyphenate.easeui.bean.FriendsBean;
import com.hyphenate.easeui.utils.SetUserInfoUtils;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Cheng on 2019/6/30.
 */

public interface Myserver {
    String url = "http://wasj.zhangtongdongli.com";

    //获取会员信息
    @POST("/APP/Xuser/touxiang")
    @FormUrlEncoded
    Observable<SetUserInfoUtils.Bean> getFriendsBean(@FieldMap Map<String, String> map);
}
