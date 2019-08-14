package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.example.zhanghao.woaisiji.bean.my.PersonalInfoBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.resp.RespSendSms;
import com.example.zhanghao.woaisiji.utils.CountDownTimerUtils;
import com.example.zhanghao.woaisiji.utils.KeyPool;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.StringUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PersonalModifyPhoneActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;
    private EditText et_personal_modify_phone_input_new_number,et_personal_modify_phone_input_verification_code;
    private TextView tv_personal_modify_phone_input_verification_code,tv_personal_modify_phone_submit;

    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_modify_phone);

        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("修改手机号");

        et_personal_modify_phone_input_new_number= (EditText) findViewById(R.id.et_personal_modify_phone_input_new_number);
        et_personal_modify_phone_input_verification_code= (EditText) findViewById(R.id.et_personal_modify_phone_input_verification_code);

        tv_personal_modify_phone_input_verification_code= (TextView) findViewById(R.id.tv_personal_modify_phone_input_verification_code);
        tv_personal_modify_phone_submit= (TextView) findViewById(R.id.tv_personal_modify_phone_submit);
        tv_personal_modify_phone_submit.setOnClickListener(this);

        mCountDownTimerUtils = new CountDownTimerUtils(tv_personal_modify_phone_input_verification_code,60000,1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.tv_personal_modify_phone_input_verification_code:
                if (StringUtil.isPhoneNumber(et_personal_modify_phone_input_new_number.getText().toString())){
                    mCountDownTimerUtils.start();
                    sendCodesFromServer();
                }else{
                    Toast.makeText(this,"请正确输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_personal_modify_phone_submit:
                if (StringUtil.isPhoneNumber(et_personal_modify_phone_input_new_number.getText().toString())&&
                        !TextUtils.isEmpty(et_personal_modify_phone_input_verification_code.getText().toString())&&
                        et_personal_modify_phone_input_verification_code.getText().toString().length()==4){
                    modifyPhone();
                }else{
                    Toast.makeText(this,"请正确输入信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 修改手机号
     */
    private void modifyPhone() {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MODIFY_PHONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if(respNull.getCode() == 200){
                    PersonalInfoBean currentUserInfo = gson.fromJson(PrefUtils.getString(PersonalModifyPhoneActivity.this,
                            "personal_info", ""),PersonalInfoBean.class);
                    currentUserInfo.setUsername(et_personal_modify_phone_input_new_number.getText().toString());
                    String modifyNickNameStr=gson.toJson(currentUserInfo);
                    PrefUtils.setString(PersonalModifyPhoneActivity.this,"personal_info",modifyNickNameStr);
                    WoAiSiJiApp.setCurrentUserInfo(currentUserInfo);
                    Toast.makeText(PersonalModifyPhoneActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setAction(KeyPool.ACTION_MODIFY_PERSONAL_INFO);
                    LocalBroadcastManager.getInstance(PersonalModifyPhoneActivity.this).sendBroadcast(intent);
                    finish();
                }else{
                    Toast.makeText(PersonalModifyPhoneActivity.this,"修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("oldPhone",WoAiSiJiApp.getCurrentUserInfo().getUsername());
                params.put("phone",et_personal_modify_phone_input_new_number.getText().toString());
                params.put("token",WoAiSiJiApp.token);
                params.put("yzm",et_personal_modify_phone_input_verification_code.getText().toString());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }
}
