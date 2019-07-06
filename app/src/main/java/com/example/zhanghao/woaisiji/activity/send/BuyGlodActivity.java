package com.example.zhanghao.woaisiji.activity.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.zhanghao.woaisiji.activity.PaymentMethodActivity;
import com.example.zhanghao.woaisiji.bean.GoldBuyBean;
import com.example.zhanghao.woaisiji.bean.GoldRatioBean;
import com.example.zhanghao.woaisiji.bean.RegisterResultBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BuyGlodActivity extends AppCompatActivity {
    private EditText edt;
    private Double num;
    private TextView tv1, tv2;
    private ImageView registerBack;
    private TextView tvRegisterTitle;

    private RegisterResultBean registerResult;
    private Button btn_buygold;
    private String gold_num, gold_ratio, good_price;
    private int ordernum,option;
    private Double rmb;
    private Double finalPrice;
    private String dingdanhao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_glod);
        edt = (EditText) findViewById(R.id.num_0);
        tv1 = (TextView) findViewById(R.id.num_1);
        tv2 = (TextView) findViewById(R.id.num_2);
        btn_buygold = (Button) findViewById(R.id.btn_buy_gold);
        edt.addTextChangedListener(new OnTextChangeListener());
        btn_buygold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goldBuy();
            }
        });

        goldRatio();
        initView();

        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goldBuy() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GLOD_EXCHANGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GoldBuyBean goldBuyBean = gson.fromJson(response, GoldBuyBean.class);
                if (goldBuyBean == null) {
                    Log.d("商品详情获取服务器废了", "" + response);
                } else {
                    dingdanhao = String.valueOf(goldBuyBean.getOrderid());
                    finalPrice = Double.valueOf(goldBuyBean.getMoney());
                    option = 3;
                    Intent intent = new Intent(BuyGlodActivity.this, PaymentMethodActivity.class);
//                    Toast.makeText(OrdersubmissionActivity.this,"金币支付",Toast.LENGTH_LONG).show();
                    intent.putExtra("option",option);
                    intent.putExtra("ordernum",dingdanhao);
                    intent.putExtra("finalPrice",finalPrice);
                    intent.putExtra("price",finalPrice);
                    startActivity(intent);
                }
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("money", gold_num);
                params.put("gold", gold_ratio);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void goldRatio() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GLOD_RATIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GoldRatioBean goldRatioBean = gson.fromJson(response, GoldRatioBean.class);
                if (goldRatioBean == null) {
                    Log.d("商品详情获取服务器废了", "" + response);
                } else {
                    tv1.setText(goldRatioBean.getGold());
                    gold_ratio = goldRatioBean.getGold();
                }
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }


    /**
     * EditText输入变化事件监听器
     */
    class OnTextChangeListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            //Editable s  可变字符串  表示输入后的字符串
            String numString = s.toString();
            if (numString == null || numString.equals("")) {
                num = 0.00;
            } else {
                //把String转化成int类型
                Double numInt = Double.valueOf(Integer.parseInt(numString));
                if (numInt < 0) {
                    Toast.makeText(BuyGlodActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
                } else {
                    //设置EditText光标位置 为文本末端
                    edt.setSelection(edt.getText().toString().length());
                    num = numInt;
                    String unit_price = tv1.getText().toString();
                    int price = Integer.parseInt(unit_price);
                    String sum = num / price + "";
//                    DecimalFormat df = new DecimalFormat("0.00");
                    tv2.setText(sum);
                    gold_num = sum;
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    private void initView() {
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("购买金币");
        registerBack = (ImageView) findViewById(R.id.iv_register_back);
    }
}