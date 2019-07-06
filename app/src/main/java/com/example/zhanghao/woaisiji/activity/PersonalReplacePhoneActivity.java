package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.UpdatePhoneResultBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PhoneJudgeUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalReplacePhoneActivity extends Activity {

    private EditText editReplacePhone;
    private Button btnCentainPhone;
    private UpdatePhoneResultBean updatePhoneResult;
    private String phone;
    private ImageView ivSecurityBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_replace_phone);

        initView();

        // 监听edittext，判断输入的手机号格式是否正确
        // 判断手机号格式是否正确
        editReplacePhone.addTextChangedListener(new TextWatcher() {
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
                Log.d("手机号", mTemp.toString());
                String phoneNumber = mTemp.toString();

                if ((null != phoneNumber) && PhoneJudgeUtils.isPhone(phoneNumber)) {
                    btnCentainPhone.setEnabled(true);
                    btnCentainPhone.setBackgroundResource(R.drawable.btn_register_selected);

                } else {
                    btnCentainPhone.setEnabled(false);
                    btnCentainPhone.setBackgroundResource(R.drawable.btn_register_default);
                }
            }
        });

        btnCentainPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 提交到服务器
                phone = editReplacePhone.getText().toString();
                updatePhoneToServer();
                finish();
//                Toast.makeText(PersonalReplacePhoneActivity.this,"点击了按钮",Toast.LENGTH_SHORT).show();
            }
        });
        ivSecurityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updatePhoneToServer() {
        StringRequest updatePhoneRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_REPLACE_PHONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
               if (updatePhoneResult.code == 200){
                   // 修改成功
                   WoAiSiJiApp.memberShipInfos.info.mobile = phone;
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("phone",phone);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(updatePhoneRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        updatePhoneResult = gson.fromJson(response,UpdatePhoneResultBean.class);
    }

    private void initView() {
        ivSecurityBack = (ImageView) findViewById(R.id.iv_security_back);

        editReplacePhone = (EditText) findViewById(R.id.edit_replace_phone);
        btnCentainPhone = (Button) findViewById(R.id.btn_certain_phone);
        btnCentainPhone.setEnabled(false);
    }
}
