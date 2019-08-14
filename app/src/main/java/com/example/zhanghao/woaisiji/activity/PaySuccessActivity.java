package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by zzz on 2016/12/12.
 */

public class PaySuccessActivity extends BaseActivity {

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private TextView tvGoodsNum;
    private TextView tvGoodsPrice;
    private Button btnBackMall;
    private String orderNum;
    private String goodPrice ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        orderNum = getIntent().getStringExtra("order_num");
        goodPrice = getIntent().getStringExtra("good_price" );

        initView();
        qianbao();
    }

    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Activity activity : PublicActivityList.activityList) {
                    activity.finish();
                }
            }
        });
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("支付成功");

        tvGoodsNum = (TextView) findViewById(R.id.tv_goods_num);
        tvGoodsNum.setText(orderNum);
        tvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        if (TextUtils.isEmpty(goodPrice)) {
            goodPrice = "0.00";
        }
        tvGoodsPrice.setText("￥" + goodPrice);

        btnBackMall = (Button) findViewById(R.id.btn_back_mall);
        btnBackMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });
    }

    private void qianbao() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespPersonalWallet respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                SharedPrefrenceUtils.remove(PaySuccessActivity.this, "yue");
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(PaySuccessActivity.this, "yue", respPersonalWallet);
                } else {
                    if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        Toast.makeText(PaySuccessActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PaySuccessActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
