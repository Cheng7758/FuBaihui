package com.example.zhanghao.woaisiji.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.zhanghao.woaisiji.bean.PhoneCodesBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.CountDownTimerUtils;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;
//import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/13.
 */
public class RegisterTwoActivity extends Activity implements View.OnClickListener{
    private Button btnNextTwo;
    private TextView tvRegisterTitle;
    private TextView tvCountTime;
    private TextView tvText;
    private CountDownTimerUtils mCountDownTimerUtils;
    // 手机验证码
    private PhoneCodesBean phoneCodes;
    private EditText editPhoneCodes;
    private String phone;

    private ImageView registerBack;
    private TextView tvPhoneTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_two);

       phone = getIntent().getStringExtra("phone");
        // 初始化布局
        initView();

        // 响应点击事件
        responseClickListener();

        // 从服务器获取验证码
        obtainCodesFromServer();

        // 初始化倒计时
        mCountDownTimerUtils = new CountDownTimerUtils(tvCountTime, 60000, 1000);
        mCountDownTimerUtils.start();
    }


    private void initView() {
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("注册2/3");
        tvText = (TextView) findViewById(R.id.tv_text);
        tvText.setText("验证码");
        tvPhoneTip = (TextView) findViewById(R.id.tv_phone_tip);
        tvPhoneTip.setText("验证码短信发送至"+phone);
        tvCountTime = (TextView) findViewById(R.id.tv_count_time);
        btnNextTwo = (Button) findViewById(R.id.btn_next_two);
        editPhoneCodes = (EditText) findViewById(R.id.edit_phone_codes);

        registerBack = (ImageView) findViewById(R.id.iv_register_back);
    }

    private void responseClickListener() {
        tvCountTime.setOnClickListener(this);
        registerBack.setOnClickListener(this);

        btnNextTwo.setBackgroundResource(R.drawable.btn_register_selected);
        btnNextTwo.setEnabled(true);
        btnNextTwo.setClickable(true);
        btnNextTwo.setOnClickListener(RegisterTwoActivity.this);
    }

    // 从服务器获取验证码
    private void obtainCodesFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_CODES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if(phoneCodes.code == 200){
                }else{
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("请求失败", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("phone",phone);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void checkCodesIsTrue(final String resultCode) {
        editPhoneCodes.addTextChangedListener(new TextWatcher() {
            private CharSequence mTemp;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTemp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String codes = mTemp.toString();
                Log.d("验证码", codes);
                // Log.d("服务器验证码",phoneCodes.content);
                if((codes != null) && (resultCode!=null) &&codes.equals(resultCode)){
                    btnNextTwo.setBackgroundResource(R.drawable.btn_register_selected);
                    btnNextTwo.setEnabled(true);
                    btnNextTwo.setClickable(true);
                    btnNextTwo.setOnClickListener(RegisterTwoActivity.this);
                }else {
                    btnNextTwo.setBackgroundResource(R.drawable.btn_register_default);
                    btnNextTwo.setClickable(false);
                    btnNextTwo.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next_two://下一步，跳转到密码界面
                if (phoneCodes.content.equals(editPhoneCodes.getText().toString())){
                    Intent intent = new Intent(RegisterTwoActivity.this, RegisterThreeActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterTwoActivity.this,"验证码输入错误！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_count_time://验证码
                obtainCodesFromServer();
//                checkCodesIsTrue();
                mCountDownTimerUtils.start();
//                Toast.makeText(RegisterTwoActivity.this,"textview点击事件",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_register_back:
                finish();
                break;
        }
    }
    // 解析服务器返回的数据
    private void transServerData(String response) {
        Gson gson = new Gson();
        phoneCodes = gson.fromJson(response, PhoneCodesBean.class);
    }


}
