package com.example.zhanghao.woaisiji.global;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.friends.db.DemoDBManager;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespLogin;
import com.example.zhanghao.woaisiji.serverdata.FriendsListData;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/11/1.
 */
public class LoginUtils {

    private BaseActivity mActivity;

    private static final String TAG = "LoginActivity";
    private static final int TYPE_LOGIN = 0;
    private static final int TYPE_GET_INFO = 1;
    private static final int TYPE_GET_TOKEN = 2;

    // 用户请求登录，返回的信息
    private RespLogin respLogin;

    private boolean autoLogin = false;
    private boolean loginSuccess = false;
    private MemberShipInfosBean memberShipInfos;
    private RespData tokenRsp;

    public LoginUtils(BaseActivity activity){
        this.mActivity = activity;
    }

    public void accessServerData(final String username, final String password) {
        mActivity.showProgressDialog();
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_LOGIN,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    mActivity.dismissProgressDialog();
                    return;
                }
                // 解析服务器返回的数据
                transServerData(response,TYPE_LOGIN);
                if (respLogin.getCode() == 200) {
                    PrefUtils.setString(mActivity, "username", username);
                    PrefUtils.setString(mActivity, "password", password);
                    PrefUtils.setString(mActivity,"uid",respLogin.getData().getUid());
                    PrefUtils.setString(mActivity,"user_type",respLogin.getData().getUser_type());
                    //获取TOKEN
                    // 从服务器获取会员信息
//                    obtainDataFromServer();
                    // 从服务器获取好友列表
                    obtainFriendsFromServer();
                    // 环信登录
                    emLogin(WoAiSiJiApp.getUid(), Constants.EMPASSWORD());
                    if (!loginSuccess){
                        // 环信注册
                        emRegister(WoAiSiJiApp.getUid(),Constants.EMPASSWORD());
                        // 环信登录
                        emLogin(WoAiSiJiApp.getUid(),Constants.EMPASSWORD());
                    }

                } else {
                    if (!TextUtils.isEmpty(respLogin.getMsg()))
                        Toast.makeText(mActivity, "" + respLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("请求失败", "" + error);
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(loginRequest);
    }

    // 获取用户好友列表
    public void obtainFriendsFromServer() {
        FriendsListData friendsListData = new FriendsListData();
        friendsListData.obtainFriendsListFromServer();
        friendsListData.setSendDataListener(new FriendsListData.SendDataListener() {
            @Override
            public void sendData(Map<String, EaseUser> userList) {
                WoAiSiJiApp.m =userList;
            }

            @Override
            public void sendStringData(String data) {

            }
        });
    }

    // 获取用户信息
    public void obtainDataFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MEMBER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 解析服务器返回的数据
                        transServerData(response,TYPE_GET_INFO);
                        if (memberShipInfos.code == 200){
                            WoAiSiJiApp.memberShipInfos = memberShipInfos;
                        }else{}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("请求失败", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    // 用户注册环信账户
    public void emRegister(final String username, final String password) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(username, password);
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            // save current user
                            DemoHelper.getInstance().setCurrentUserName(username);
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                            // finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    // 用户登录环信账户
    public void emLogin(String username, String password) {
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(WoAiSiJiApp.currentUserNick);

        final long start = System.currentTimeMillis();
        loginSuccess = false;
        // call login method
//        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(WoAiSiJiApp.getUid(), password, new EMCallBack() {

            @Override
            public void onSuccess() {
//                Log.d(TAG, "login: onSuccess");
                loginSuccess = true;

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        WoAiSiJiApp.currentUserNick.trim());

                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
//                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
//                            Toast.makeText(getApplicationContext(),getString(R.string.Login_failed)+message,Toast.LENGTH_SHORT).show();
            }
        });
        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
    }

    private void transServerData(String response,int type) {
        Gson gson = new Gson();
        switch (type){
            case TYPE_LOGIN:
                respLogin = gson.fromJson(response, RespLogin.class);
                break;
            case TYPE_GET_INFO:
                memberShipInfos = gson.fromJson(response, MemberShipInfosBean.class);
                break;
            case TYPE_GET_TOKEN:
                tokenRsp = gson.fromJson(response, RespData.class);
                break;
        }
    }
}
