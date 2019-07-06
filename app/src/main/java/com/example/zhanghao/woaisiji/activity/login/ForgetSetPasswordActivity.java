package com.example.zhanghao.woaisiji.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/10/30.
 */
public class ForgetSetPasswordActivity extends Activity {

    private TextView tvRegisterTitle;
    private Button btnRegister;
    private EditText etRegisterPassWord;
    private EditText etRegisterConfirmPassWord;
    private String password;
    private AlterResultBean resultBean;

    private ImageView registerBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        setContentView(R.layout.activity_register_three);

        initView();

//        applyKitKatTranslucency();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = etRegisterPassWord.getText().toString();
                String passwordConfirm = etRegisterConfirmPassWord.getText().toString();
                if(password.equals(passwordConfirm)){
                    // 把密码发送到服务器
                    submitDataToServer();

                }else {
                    Toast.makeText(ForgetSetPasswordActivity.this, "密码不一致",Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(ForgetSetPasswordActivity.this, "注册成功实现",Toast.LENGTH_SHORT).show();
            }
        });
        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("找回密码");
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setText("提交");
        etRegisterPassWord = (EditText) findViewById(R.id.et_register_password);
        etRegisterConfirmPassWord = (EditText) findViewById(R.id.et_register_confirm_password);

        registerBack = (ImageView) findViewById(R.id.iv_register_back);
    }

    private void submitDataToServer() {
        final String phone = getIntent().getStringExtra("phone");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_FORGET_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 解析服务器返回的数据
                transServerData(response);

                if (resultBean.code == 200){
                    Toast.makeText(ForgetSetPasswordActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                    for (Activity activity : PublicActivityList.activityList){
                        activity.finish();
                    }
                }else{
                    Toast.makeText(ForgetSetPasswordActivity.this,resultBean.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetSetPasswordActivity.this,"服务器忙!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("userpassword",password);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        resultBean = gson.fromJson(response,AlterResultBean.class);
    }


}
