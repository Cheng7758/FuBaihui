package com.example.zhanghao.woaisiji.serverdata;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.MemberShipInfosBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzz on 2016/12/15.
 */

public class ObtainUserInfo {

    private MemberShipInfosBean memberShipInfos;


    //===========================回调接口，用于返回数据============================================
    public SendDataListener listener;

    public void setSendDataListener(SendDataListener listener) {
        this.listener = listener;
    }

    public interface SendDataListener {

        public void sendStringData(MemberShipInfosBean.MemberInfo data);
    }

    public void getUserInfo(final String uid){
        StringRequest obtainUserInfoRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MEMBER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 解析服务器返回的数据
                transServerData(response);
                if (memberShipInfos.code == 200){
                    if (listener != null){
                        listener.sendStringData(memberShipInfos.info);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(obtainUserInfoRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        memberShipInfos = gson.fromJson(response,MemberShipInfosBean.class);
    }
}
