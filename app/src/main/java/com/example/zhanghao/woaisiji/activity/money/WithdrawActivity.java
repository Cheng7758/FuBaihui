package com.example.zhanghao.woaisiji.activity.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.zhanghao.woaisiji.activity.PersonalDepositActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class WithdrawActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private EditText input_number;
    private Button immediately_binding;
    private String mType;
    private String mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        mType = getIntent().getStringExtra("type");
        initView();
    }

    private void initView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setText("绑定账号");

        input_number = (EditText) findViewById(R.id.input_number);
        immediately_binding = (Button) findViewById(R.id.immediately_binding);

        immediately_binding.setOnClickListener(this);
        iv_title_bar_view_left_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.immediately_binding:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        mNumber = input_number.getText().toString().trim();
        if (TextUtils.isEmpty(mNumber)) {
            Toast.makeText(this, "请输入支付宝/微信账号", Toast.LENGTH_SHORT).show();
            return;
        } else {
            bindingWXOrZFB();
        }
    }

    /**
     * 绑定微信支付宝openid
     */
    private void bindingWXOrZFB() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_BINDING_WX_ZFB_OPENID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
//                    SharedPrefrenceUtils.putObject(WithdrawActivity.this, "binding", respNull);
                    Intent intent = new Intent(WithdrawActivity.this, PersonalDepositActivity.class);
                    intent.putExtra("number", mNumber);
                    setResult(20, intent);
                    Toast.makeText(WithdrawActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(WithdrawActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(WithdrawActivity.this, "已经绑定了账号", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Log.e("----money", error.toString());
                Toast.makeText(WithdrawActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("openid", mNumber);
                params.put("type", mType);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }
}
