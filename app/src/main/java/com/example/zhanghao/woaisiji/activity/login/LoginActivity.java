package com.example.zhanghao.woaisiji.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.PaySuccessActivity;
import com.example.zhanghao.woaisiji.bean.my.PersonalWalletBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.Constants;
import com.example.zhanghao.woaisiji.global.LoginUtils;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespGetPersonalInfo;
import com.example.zhanghao.woaisiji.resp.RespLogin;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.serverdata.GetToken;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.gold.UserManager;

import org.apache.http.util.TextUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/2.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText editUsername, editPassword;//输入框
    private Button btnLogin;
    private Button btnForgotPass, btnRegister;//忘记密码和注册
    private SharedPreferences mSharedPreferences;

    private CheckBox ck_login_agree_protocol;
    private String mPhone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        initView();
        // 如果用户名改变，清空用户密码
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editPassword.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnLogin.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        initData();
    }

    private void initView() {
        editUsername = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnForgotPass = (Button) findViewById(R.id.forgot_password);
        btnRegister = (Button) findViewById(R.id.register);
        ck_login_agree_protocol = (CheckBox) findViewById(R.id.ck_login_agree_protocol);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
        initData();

        mPhone_number = getIntent().getStringExtra("phone_number");
        if (!TextUtils.isEmpty(mPhone_number)) {
            editUsername.setText(mPhone_number);
        }
    }

    private void initData() {
        String username = PrefUtils.getString(LoginActivity.this, "username", null);
        String password = PrefUtils.getString(LoginActivity.this, "password", null);
        if (!TextUtils.isEmpty(username)) {
            editUsername.setText(username);
        }

        if (!TextUtils.isEmpty(password)) {
//            editPassword.setText(password);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
//                loginService(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
                if (ck_login_agree_protocol.isChecked()) {
                    // 访问服务器数据，验证用户名密码是否正确
                    loginService(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
                } else {
                    Toast.makeText(this, "请勾选协议", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgot_password://跳转忘记密码界面
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.register://跳转注册界面
//                startActivity(new Intent(LoginActivity.this, RegisterOneActivity.class));
                startActivity(new Intent(LoginActivity.this, NewRegisterActivity.class));
                break;
        }
    }

    private RespLogin respLogin;

    private void loginService(final String username, final String password) {
        showProgressDialog();
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (android.text.TextUtils.isEmpty(response)) {
                            dismissProgressDialog();
                            return;
                        }
                        // 解析服务器返回的数据
                        Gson gson = new Gson();
                        respLogin = gson.fromJson(response, RespLogin.class);
                        if (respLogin.getCode() == 200) {
                            WoAiSiJiApp.setUid(respLogin.getData().getUid());
                            PrefUtils.setString(LoginActivity.this, "username", username);
                            PrefUtils.setString(LoginActivity.this, "password", password);
                            PrefUtils.setString(LoginActivity.this, "uid", respLogin.getData().getUid());
                            PrefUtils.setString(LoginActivity.this, "user_type", respLogin.getData().getUser_type());
                            //获取TOKEN
//                            token = new GetToken();
                            token.getToken(new GetToken.GetTokenCallBack() {
                                @Override
                                public void getToken(boolean isSuccessful) {
                                    getPersonalInfoFromServer();
                                }
                            });
                            //用户注册环信
                            LoginUtils loginUtils = new LoginUtils(LoginActivity.this);
                            loginUtils.emRegister(respLogin.getData().getUid(), Constants.EMPASSWORD());
                            loginUtils.accessServerData(username, password);
                            qianbao();
                        } else {
                            if (!android.text.TextUtils.isEmpty(respLogin.getMsg())){
                                Toast.makeText(LoginActivity.this, "" + respLogin.getMsg(), Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                finish();
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

    private void qianbao() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (android.text.TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespPersonalWallet respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                SharedPrefrenceUtils.remove(ActivityUtils.getTopActivity(), "yue");
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(ActivityUtils.getTopActivity(), "yue", respPersonalWallet);
                    PersonalWalletBean data = respPersonalWallet.getData();
                    UserManager.silver = data.getSilver();
                    UserManager.balance = data.getBalance();
                    UserManager.gold = data.getScore();
                    UserManager.storeGold = data.getStore_score();
                } else {
                    if (!android.text.TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        ToastUtils.showShort(respPersonalWallet.getMsg());
                }
                dismissProgressDialog();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(ActivityUtils.getTopActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    /**
     * 获取用户信息
     */
    public void getPersonalInfoFromServer() {
        StringRequest userRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (android.text.TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespGetPersonalInfo respGetPersonalInfo = gson.fromJson(response, RespGetPersonalInfo.class);
                        if (respGetPersonalInfo.getCode() == 200) {
                            String personalInforGsonString = gson.toJson(respGetPersonalInfo.getData());
                            PrefUtils.setString(LoginActivity.this, "personal_info", personalInforGsonString);
                            WoAiSiJiApp.setCurrentUserInfo(respGetPersonalInfo.getData());

                        } else {
                            if (!TextUtils.isEmpty(respGetPersonalInfo.getMsg()))
                                Toast.makeText(LoginActivity.this, respGetPersonalInfo.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        dismissProgressDialog();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("token", WoAiSiJiApp.token);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(userRequest);
    }
}
