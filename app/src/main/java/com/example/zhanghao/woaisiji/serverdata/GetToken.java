package com.example.zhanghao.woaisiji.serverdata;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.google.gson.Gson;

import java.util.HashMap;

public class GetToken {
    public static void getToken(){
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GET_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RespData tokenRsp = gson.fromJson(response, RespData.class);
                if (tokenRsp.getCode() == 200) {
                    WoAiSiJiApp.token = tokenRsp.getData();
                    if (tokenCallBack!=null)
                        tokenCallBack.getToken(true);
                }else {
                    if (tokenCallBack != null)
                        tokenCallBack.getToken(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (tokenCallBack != null)
                    tokenCallBack.getToken(false);
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("app_key", "okhgkuejg97DhukoodkjkdjYIidnjkdjiipsteom");
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(loginRequest);
    }
    public static void getToken(final GetTokenCallBack callBack){
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GET_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RespData tokenRsp = gson.fromJson(response, RespData.class);
                if (tokenRsp.getCode() == 200) {
                    WoAiSiJiApp.token = tokenRsp.getData();
                    if (callBack!=null)
                        callBack.getToken(true);
                }else {
                    if (callBack != null)
                        callBack.getToken(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (callBack != null)
                    callBack.getToken(false);
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("app_key", "okhgkuejg97DhukoodkjkdjYIidnjkdjiipsteom");
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(loginRequest);
    }

    public interface GetTokenCallBack{
        void getToken(boolean isSuccessful);
    }

    private static GetTokenCallBack tokenCallBack=null;
    public static void setGetTokenCallBack(GetTokenCallBack getTokenCallBack){
        if (getTokenCallBack!=null)
            tokenCallBack = getTokenCallBack;
    }
}
