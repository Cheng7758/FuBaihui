package com.example.zhanghao.woaisiji.activity.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.main.HomeActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.CountDownTimerUtils;
import com.example.zhanghao.woaisiji.utils.PhoneJudgeUtils;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admin on 2016/8/13.
 */
public class NewRegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * TitleBar
     */
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    /**
     * 组件
     */
    private EditText et_new_register_input_phone_number, et_new_register_input_verification_code,
            et_new_register_input_pwd, et_new_register_input_invitation_code;
    private TextView tv_new_register_go_register, tv_new_register_go_login, tv_new_register_get_verification_code;
    private CheckBox ck_new_register_agree_protocol;

    private boolean verificationPhoneNumber = false, verificationCode = false, verificationPwd = false;

    private CountDownTimerUtils mCountDownTimerUtils;
    private String province, city, district;
    public LocationClient mLocationClient = null;
    public BaiduMapLocationListener myListener = new BaiduMapLocationListener();
    private static final int PERMISSION_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        setContentView(R.layout.activity_new_register);
        permission();
        initTitleBar();
        initView();
    }
    private void permission() {
        if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NewRegisterActivity.this,"请开启位置信息权限",Toast.LENGTH_SHORT).show();
                //没有授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUESTCODE);
            } else {
                //已经授权
                initLocation();
            }
        }else {
            initLocation();
        }
    }
    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initTitleBar() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setOnClickListener(this);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setImageResource(R.drawable.back);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("注册");
    }

    private void initView() {

        et_new_register_input_phone_number = (EditText) findViewById(R.id.et_new_register_input_phone_number);
        et_new_register_input_verification_code = (EditText) findViewById(R.id.et_new_register_input_verification_code);
        et_new_register_input_pwd = (EditText) findViewById(R.id.et_new_register_input_pwd);
        et_new_register_input_invitation_code = (EditText) findViewById(R.id.et_new_register_input_invitation_code);

        tv_new_register_go_register = (TextView) findViewById(R.id.tv_new_register_go_register);
        tv_new_register_go_login = (TextView) findViewById(R.id.tv_new_register_go_login);
        ck_new_register_agree_protocol = (CheckBox) findViewById(R.id.ck_new_register_agree_protocol);
        tv_new_register_get_verification_code = (TextView) findViewById(R.id.tv_new_register_get_verification_code);
        tv_new_register_go_register.setOnClickListener(this);
        tv_new_register_get_verification_code.setOnClickListener(this);

        mCountDownTimerUtils = new CountDownTimerUtils(tv_new_register_get_verification_code, 60000, 1000);

        setProtocolText();
        setGoLogin();

        // 判断手机号格式是否正确
        et_new_register_input_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 11 && PhoneJudgeUtils.isPhone(charSequence.toString())) {
                    verificationPhoneNumber = true;
                } else
                    verificationPhoneNumber = false;
            }
        });
        //验证码
        et_new_register_input_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 4) {
                    verificationCode = true;
                } else
                    verificationCode = false;
            }
        });
        //密码
        et_new_register_input_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() >= 6) {
                    verificationPwd = true;
                } else
                    verificationPwd = false;
            }
        });
        //邀请码
        et_new_register_input_invitation_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    verificationCode = true;
                } else
                    verificationCode = false;
            }
        });
    }

    private void setGoLogin() {
        String loginText = "已有账号，去登录";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(loginText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(NewRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
            }
        };
        spannableStringBuilder.setSpan(new UnderlineSpan(), loginText.length() - 2, loginText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(clickableSpan, loginText.length() - 2, loginText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_new_register_go_login.setText(spannableStringBuilder);
        tv_new_register_go_login.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setProtocolText() {
        String protocolText = "确认注册表示您已阅读同意《福百惠注册协议》";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(protocolText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint ds) {
            }
        };
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.main_color_green)),
                protocolText.indexOf("《"), protocolText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        spannableStringBuilder.setSpan(clickableSpan,protocolText.indexOf("《"),protocolText.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ck_new_register_agree_protocol.setText(spannableStringBuilder);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left://返回按钮
                finish();
                break;
            case R.id.tv_new_register_get_verification_code://获取验证码
                if (!verificationPhoneNumber) {
                    Toast.makeText(NewRegisterActivity.this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCountDownTimerUtils.start();
                loginOutSendCodesFromServer(et_new_register_input_phone_number.getText().toString());
                break;
            case R.id.tv_new_register_go_register://注册
                if(province!=null&&city!=null&&district!=null) {
                    getGoRegister();
                }else {
                    permission();
                }
                break;
        }
    }

    /**
     * 注册
     */
    private void getGoRegister() {
        if (!verificationPhoneNumber) {
            Toast.makeText(NewRegisterActivity.this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!verificationCode) {
            Toast.makeText(NewRegisterActivity.this, "请正确输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!verificationPwd) {
            Toast.makeText(NewRegisterActivity.this, "密码长度必须大于6", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (!ck_new_register_agree_protocol.isChecked()) {
//            Toast.makeText(NewRegisterActivity.this, "请阅读协议", Toast.LENGTH_SHORT).show();
//            return;
//        }
        final String phone_number = et_new_register_input_phone_number.getText().toString();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_NEW_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
                    Toast.makeText(NewRegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewRegisterActivity.this, LoginActivity.class);
                    intent.putExtra("phone_number", phone_number);
                    startActivity(intent);
                    finish();
                } else {
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(NewRegisterActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewRegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", et_new_register_input_phone_number.getText().toString());
                params.put("password", et_new_register_input_pwd.getText().toString());
                params.put("recommend_code", et_new_register_input_invitation_code.getText().toString());
                params.put("agree", String.valueOf(ck_new_register_agree_protocol.isChecked() ? "1" : "0"));
                params.put("yzm", et_new_register_input_verification_code.getText().toString());

                params.put("province", province);
                params.put("city", city);
                params.put("district", district);

                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    public class BaiduMapLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            province = location.getProvince();
            city = location.getCity();
            district = location.getDistrict();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }
}
