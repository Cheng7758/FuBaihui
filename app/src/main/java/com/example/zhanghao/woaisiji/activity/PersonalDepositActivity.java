package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.google.gson.Gson;

import java.util.HashMap;

public class PersonalDepositActivity extends BaseActivity {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private EditText et_personal_deposit_shift_to_balance,et_personal_deposit_input_deposit_amount;
    private TextView tv_personal_deposit_transferable_balance,tv_personal_deposit_sure_shift,tv_personal_deposit_now;
    private RadioGroup rg_personal_deposit_way;

    private String type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_deposit);

        type = getIntent().getStringExtra("FromType");
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
        tv_title_bar_view_centre_title.setText("提现");

        et_personal_deposit_shift_to_balance = (EditText) findViewById(R.id.et_personal_deposit_shift_to_balance);
        et_personal_deposit_input_deposit_amount = (EditText) findViewById(R.id.et_personal_deposit_input_deposit_amount);
        rg_personal_deposit_way = (RadioGroup) findViewById(R.id.rg_personal_deposit_way);

        tv_personal_deposit_transferable_balance = (TextView) findViewById(R.id.tv_personal_deposit_transferable_balance);
        tv_personal_deposit_sure_shift = (TextView) findViewById(R.id.tv_personal_deposit_sure_shift);
        tv_personal_deposit_now = (TextView) findViewById(R.id.tv_personal_deposit_now);
        tv_personal_deposit_sure_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_personal_deposit_shift_to_balance.getText().toString())){
                    float depositToBalance = Float.valueOf(et_personal_deposit_shift_to_balance.getText().toString());
                    if (depositToBalance > 0) {
                        depositToBalance();
                    }
                }

            }
        });
        tv_personal_deposit_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_personal_deposit_input_deposit_amount.getText().toString())){
                    float depositToBalance = Float.valueOf(et_personal_deposit_input_deposit_amount.getText().toString());
                    if (depositToBalance > 0) {
                        isDepositToWXOrZFBSuccessful();
                    }
                }
            }
        });
    }

    /**
     * 提现到余额
     */
    private void depositToBalance() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response,RespNull.class);
                if (respNull.getCode()==200){
                    Toast.makeText(PersonalDepositActivity.this,"提现成功",Toast.LENGTH_SHORT).show();
                }else{
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(PersonalDepositActivity.this,respNull.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", type);
                params.put("balance", et_personal_deposit_shift_to_balance.getText().toString());
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    /**
     * 提现到 微信 支付宝
     */
    private void depositToWXOrZFB() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_WX_ZFB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response,RespNull.class);
                if (respNull.getCode()==200){
                    Toast.makeText(PersonalDepositActivity.this,"提现成功",Toast.LENGTH_SHORT).show();
                }else{
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(PersonalDepositActivity.this,respNull.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", rg_personal_deposit_way.getCheckedRadioButtonId()==R.id.rb_personal_deposit_wx
                        ?"1":"2");
                params.put("money", et_personal_deposit_input_deposit_amount.getText().toString());
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    /**
     * 是否提现成功
     */
    private void isDepositToWXOrZFBSuccessful() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_SUCCEED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response,RespNull.class);
                if (respNull.getCode()==200){
                    depositToWXOrZFB();
                }else{
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(PersonalDepositActivity.this,respNull.getMsg(),Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(PersonalDepositActivity.this,"有提现申请，不可发起提现",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
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
}
