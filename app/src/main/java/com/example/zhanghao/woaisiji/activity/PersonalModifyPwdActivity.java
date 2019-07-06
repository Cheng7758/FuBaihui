package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.CountDownTimerUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalModifyPwdActivity extends BaseActivity {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private CountDownTimerUtils mCountDownTimerUtils;

    private EditText et_personal_modify_pwd_input_new_pwd_first,
            et_personal_modify_pwd_input_new_pwd_twice , et_personal_modify_pwd_input_verification_code;
    private TextView tv_personal_modify_pwd_input_verification_code,tv_personal_modify_pwd_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_modify_pwd);
        initView();
    }


    private void initView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("修改密码");

        et_personal_modify_pwd_input_new_pwd_first = (EditText) findViewById(R.id.et_personal_modify_pwd_input_new_pwd_first);
        et_personal_modify_pwd_input_new_pwd_twice = (EditText) findViewById(R.id.et_personal_modify_pwd_input_new_pwd_twice);
        et_personal_modify_pwd_input_verification_code = (EditText) findViewById(R.id.et_personal_modify_pwd_input_verification_code);

        tv_personal_modify_pwd_input_verification_code = (TextView) findViewById(R.id.tv_personal_modify_pwd_input_verification_code);
        tv_personal_modify_pwd_input_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstPwd  = et_personal_modify_pwd_input_new_pwd_first.getText().toString();
                String twicePwd  = et_personal_modify_pwd_input_new_pwd_twice.getText().toString();
                if (!TextUtils.isEmpty(firstPwd) && !TextUtils.isEmpty(twicePwd)){
                    if (firstPwd.equals(twicePwd)) {
                        mCountDownTimerUtils.start();
                        sendCodesFromServer();
                    }else
                        Toast.makeText(PersonalModifyPwdActivity.this,"两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PersonalModifyPwdActivity.this,"请正确输入手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_personal_modify_pwd_submit = (TextView) findViewById(R.id.tv_personal_modify_pwd_submit);
        tv_personal_modify_pwd_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPwd = et_personal_modify_pwd_input_new_pwd_first.getText().toString();
                if (TextUtils.isEmpty(newPwd)){
                    Toast.makeText(PersonalModifyPwdActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_personal_modify_pwd_input_new_pwd_twice.getText().toString())){
                    Toast.makeText(PersonalModifyPwdActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPwd.equals(et_personal_modify_pwd_input_new_pwd_twice.getText().toString())){
                    Toast.makeText(PersonalModifyPwdActivity.this,"两次新密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(et_personal_modify_pwd_input_verification_code.getText().toString()) &&
                        et_personal_modify_pwd_input_verification_code.getText().toString().length()==4){
                    Toast.makeText(PersonalModifyPwdActivity.this,"请正确输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePasswordToServer();
            }
        });
        mCountDownTimerUtils = new CountDownTimerUtils(tv_personal_modify_pwd_input_verification_code,60000,1000);
    }

    /**
     * 网络请求
     */
    private void updatePasswordToServer() {
        StringRequest updatePasswordRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_FORGET_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespNull modifyPwdResp = gson.fromJson(response, RespNull.class);
                        if (modifyPwdResp.getCode()==200){
                            Toast.makeText(PersonalModifyPwdActivity.this,"修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(PersonalModifyPwdActivity.this,"修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("phone",WoAiSiJiApp.getCurrentUserInfo().getUsername());
                params.put("password",et_personal_modify_pwd_input_new_pwd_first.getText().toString());
                params.put("yzm",et_personal_modify_pwd_input_verification_code.getText().toString());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(updatePasswordRequest);
    }
}
