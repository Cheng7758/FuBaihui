package com.example.zhanghao.woaisiji.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.RegisterResultBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.Constants;
import com.example.zhanghao.woaisiji.global.LoginUtils;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/13.
 */
public class RegisterThreeActivity extends BaseActivity {
    private TextView tvRegisterTitle;
    private Button btnRegister;
    private EditText etRegisterPassWord;
    private EditText etRegisterConfirmPassWord;
    private String password;
    private RegisterResultBean registerResult;

    private ImageView registerBack;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_three);

        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = etRegisterPassWord.getText().toString();
                String passwordConfirm = etRegisterConfirmPassWord.getText().toString();
                if((password != null) && password.equals(passwordConfirm)){
                    // 把密码发送到服务器
                    submitDataToServer();

                }else {
                    Toast.makeText(RegisterThreeActivity.this, "密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void submitDataToServer() {
        username =  getIntent().getStringExtra("phone");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 解析服务器返回的数据
                transServerData(response);

                if (registerResult.code == 200){
                    Toast.makeText(RegisterThreeActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    WoAiSiJiApp.setUid(registerResult.uid);
                    LoginUtils loginUtils = new LoginUtils(RegisterThreeActivity.this);
                    loginUtils.emRegister(registerResult.uid, Constants.EMPASSWORD());
//                    loginUtils.accessServerData(username,password);
                    for (Activity activity : PublicActivityList.activityList){
                        activity.finish();
                    }
//                    finish();
                }else{
                    Toast.makeText(RegisterThreeActivity.this,registerResult.msg,Toast.LENGTH_SHORT).show();
                }
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterThreeActivity.this,"服务器忙!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        registerResult = gson.fromJson(response,RegisterResultBean.class);
    }

    private void initView() {
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("注册3/3");
        btnRegister = (Button) findViewById(R.id.btn_register);
        etRegisterPassWord = (EditText) findViewById(R.id.et_register_password);
        etRegisterConfirmPassWord = (EditText) findViewById(R.id.et_register_confirm_password);

        registerBack = (ImageView) findViewById(R.id.iv_register_back);
    }
}
