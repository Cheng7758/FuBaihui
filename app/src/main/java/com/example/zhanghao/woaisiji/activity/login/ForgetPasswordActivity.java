package com.example.zhanghao.woaisiji.activity.login;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.zhanghao.woaisiji.resp.RespSendSms;
import com.example.zhanghao.woaisiji.utils.CountDownTimerUtils;
import com.example.zhanghao.woaisiji.utils.PhoneJudgeUtils;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/31.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    /**
     * TitleBar
     */
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private RelativeLayout rl_title_bar_view_root;

    /**
     * 输入密码
     */
    private boolean proclaimedPwd = false;//是否是明文密码
    private EditText et_forget_password_input_phone_number,et_forget_password_input_verification_code,
            et_forget_password_set_new_pwd;
    private TextView tv_forget_password_send_verification_code,tv_forget_password_check_proclaimed_pwd,
            tv_forget_password_next;

    // 手机验证码
    private RespSendSms phoneCodes;
    //修改成功
    private RespNull updatePwdResp;
    private String phone;
    private CountDownTimerUtils mCountDownTimerUtils;

    private boolean verificationPhoneNumber = false,verificationCode = false,verificationPWD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        setContentView(R.layout.activity_forget_password);
        initView();
        initClickListener();

        // 初始化倒计时
        mCountDownTimerUtils = new CountDownTimerUtils(tv_forget_password_send_verification_code, 60000, 1000);
    }

    private void initClickListener() {
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_forget_password_check_proclaimed_pwd.setOnClickListener(this);
        tv_forget_password_send_verification_code.setOnClickListener(this);
        tv_forget_password_next.setOnClickListener(ForgetPasswordActivity.this);

        // 判断证码是否正确
        et_forget_password_input_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String temp = charSequence.toString();
                if (temp.length()==4) {
                    verificationCode = true;
                    if (verificationPWD && verificationPhoneNumber)
                        tv_forget_password_next.setEnabled(true);
                }else
                    verificationCode = false;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // 判断手机号格式是否正确
        et_forget_password_input_phone_number.addTextChangedListener(new TextWatcher() {
            private CharSequence mTemp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTemp = charSequence;
                if (mTemp.toString().length()==11){
                    verificationPhoneNumber = true;
                    if (verificationPWD && verificationCode)
                        tv_forget_password_next.setEnabled(true);
                }else
                    verificationPhoneNumber = false;
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String phoneNumber = mTemp.toString();
                if ((!TextUtils.isEmpty(phoneNumber)) && PhoneJudgeUtils.isPhone(phoneNumber)) {
                    phone = phoneNumber;
                    tv_forget_password_send_verification_code.setEnabled(true);
                } else {
                    tv_forget_password_send_verification_code.setEnabled(false);
                }
            }
        });
        et_forget_password_set_new_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tempPwd = charSequence.toString();
                if (tempPwd.length()>=6){
                    verificationPWD = true;
                    if (verificationPhoneNumber && verificationCode)
                        tv_forget_password_next.setEnabled(true);
                }else
                    verificationPWD = false;
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initView() {
        rl_title_bar_view_root = (RelativeLayout) findViewById(R.id.rl_title_bar_view_root);
        rl_title_bar_view_root.setBackgroundColor(ContextCompat.getColor(ForgetPasswordActivity.this,R.color.white));
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setImageResource(R.drawable.back);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setTextColor(ContextCompat.getColor(ForgetPasswordActivity.this,R.color.black));
        tv_title_bar_view_centre_title.setText("忘记密码");

        et_forget_password_input_phone_number = (EditText) findViewById(R.id.et_forget_password_input_phone_number);
        et_forget_password_input_verification_code = (EditText) findViewById(R.id.et_forget_password_input_verification_code);
        tv_forget_password_send_verification_code = (TextView) findViewById(R.id.tv_forget_password_send_verification_code);
        tv_forget_password_send_verification_code.setText("获取验证码");

        et_forget_password_set_new_pwd = (EditText) findViewById(R.id.et_forget_password_set_new_pwd);
        tv_forget_password_check_proclaimed_pwd = (TextView) findViewById(R.id.tv_forget_password_check_proclaimed_pwd);

        tv_forget_password_next = (TextView) findViewById(R.id.tv_forget_password_next);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_bar_view_left_left:
            case R.id.iv_title_bar_view_left_right_exit:
                finish();
                break;
            case R.id.tv_forget_password_check_proclaimed_pwd:
                proclaimedPwd = !proclaimedPwd;
                if (proclaimedPwd)//可见
                    et_forget_password_set_new_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    et_forget_password_set_new_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case R.id.tv_forget_password_send_verification_code:
                loginOutSendCodesFromServer(et_forget_password_input_phone_number.getText().toString());
                mCountDownTimerUtils.start();
                break;
            case R.id.tv_forget_password_next://跳转确认密码
                updatePwd();
                break;
        }
    }

    /**
     * 更新
     */
    private void updatePwd() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_FORGET_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                updatePwdResp = gson.fromJson(response, RespNull.class);
                if (updatePwdResp.getCode()==200){
                    Toast.makeText(ForgetPasswordActivity.this,updatePwdResp.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    if (!TextUtils.isEmpty(updatePwdResp.getMsg()))
                        Toast.makeText(ForgetPasswordActivity.this,updatePwdResp.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPasswordActivity.this,"服务器异常", Toast.LENGTH_SHORT);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("password",et_forget_password_set_new_pwd.getText().toString());
                params.put("yzm",et_forget_password_input_verification_code.getText().toString());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }
}
