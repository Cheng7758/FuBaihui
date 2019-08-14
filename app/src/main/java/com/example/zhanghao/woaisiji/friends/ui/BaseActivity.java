/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.zhanghao.woaisiji.friends.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespSendSms;
import com.example.zhanghao.woaisiji.serverdata.GetToken;
import com.example.zhanghao.woaisiji.utils.DialogUtil;
import com.google.gson.Gson;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import java.util.HashMap;
import java.util.Map;
//import com.umeng.analytics.MobclickAgent;

@SuppressLint("Registered")
public abstract class BaseActivity extends EaseBaseActivity {

    private boolean isRunning = false ;
    public GetToken token = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        isRunning = true;
        if (token==null)
            token = new GetToken();
    }

    // 从服务器获取验证码
    public void sendCodesFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_SEND_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RespSendSms phoneCodes = gson.fromJson(response, RespSendSms.class);
                if(phoneCodes.getCode() == 200){
                    Toast.makeText(BaseActivity.this,R.string.send_sms_success, Toast.LENGTH_SHORT).show();
                }else{
                    if (!TextUtils.isEmpty(phoneCodes.getContent()))
                        Toast.makeText(BaseActivity.this,phoneCodes.getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("phone",WoAiSiJiApp.getCurrentUserInfo().getUsername());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    // 从服务器获取验证码
    public void loginOutSendCodesFromServer(final String phoneNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_SEND_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RespSendSms phoneCodes = gson.fromJson(response, RespSendSms.class);
                if(phoneCodes.getCode() == 200){
                    Toast.makeText(BaseActivity.this,R.string.send_sms_success, Toast.LENGTH_SHORT).show();
                }else{
                    if (!TextUtils.isEmpty(phoneCodes.getContent()))
                        Toast.makeText(BaseActivity.this,phoneCodes.getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("phone",phoneNumber);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /**
     * 显示滚动条
     */
    public void showProgressDialog() {
        if (isRunning) {
            DialogUtil.newProgInstance(this, DialogUtil.PROGDIALOG_STYLE_BLACK,
                    true, null);
            DialogUtil.showProgressDialog();
        }
    }
    /**
     * 取消滚动条
     */
    public void dismissProgressDialog() {
        DialogUtil.cancelProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (token!=null)
            token.setGetTokenCallBack(null);
        isRunning = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
//        MobclickAgent.onPause(this);
    }

}
