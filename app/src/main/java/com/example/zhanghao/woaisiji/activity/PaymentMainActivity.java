package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.example.zhanghao.woaisiji.bean.pay.PayOrderBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.resp.RespPayOrder;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PaymentMainActivity extends BaseActivity implements View.OnClickListener {

    //1支付宝 2微信 3金积分 4银积分
    private TextView tv_payment_main_sliver_jifen_payment_select, tv_payment_main_gold_jifen_payment_select,
            tv_payment_main_cash_payment_select;
    private RelativeLayout rl_payment_main_cash_payment, rl_payment_main_gold_jifen_payment,
            rl_payment_main_sliver_jifen_payment;
    private TextView tv_payment_main_exist, tv_payment_main_need_pay_gold_jifen, tv_payment_main_submit_payment;

    private String currentPayType = "";
    private PayOrderBean orderBean = null;
    private RespPersonalWallet mWallet;
    private String mPrice,orderID,orderNumber,merge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_main);
        mWallet = SharedPrefrenceUtils.getObject(this, "yue");
        mPrice = getIntent().getStringExtra("price");
        orderID = getIntent().getStringExtra("orderID");
        merge = getIntent().getStringExtra("merge");
        orderNumber = getIntent().getStringExtra("orderNumber");

        initData();
        initView();
    }

    //URL_PAY_ORDER_PAGE
    private void initData() {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PAY_ORDER_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespPayOrder respPayOrder = gson.fromJson(response, RespPayOrder.class);
                        if (respPayOrder.getCode() == 200) {
                            orderBean = respPayOrder.getData();
                            if (!TextUtils.isEmpty(orderBean.getMember_money()) && !TextUtils.isEmpty(orderBean.getPay_type())) {
                                setViewState(orderBean.getPay_type());
//                                tv_payment_main_need_pay_gold_jifen.setText("余额" + orderBean.getMember_money());
                            }
                        } else {
                            if (!TextUtils.isEmpty(respPayOrder.getMsg()))
                                Toast.makeText(PaymentMainActivity.this, respPayOrder.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("token", WoAiSiJiApp.token);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void initView() {
        rl_payment_main_cash_payment = (RelativeLayout) findViewById(R.id.rl_payment_main_cash_payment);
        rl_payment_main_cash_payment.setOnClickListener(this);
        rl_payment_main_gold_jifen_payment = (RelativeLayout) findViewById(R.id.rl_payment_main_gold_jifen_payment);
        rl_payment_main_gold_jifen_payment.setOnClickListener(this);
        rl_payment_main_sliver_jifen_payment = (RelativeLayout) findViewById(R.id.rl_payment_main_sliver_jifen_payment);
        rl_payment_main_sliver_jifen_payment.setOnClickListener(this);

        tv_payment_main_sliver_jifen_payment_select = (TextView) findViewById(R.id.tv_payment_main_sliver_jifen_payment_select);
        tv_payment_main_gold_jifen_payment_select = (TextView) findViewById(R.id.tv_payment_main_gold_jifen_payment_select);
        tv_payment_main_cash_payment_select = (TextView) findViewById(R.id.tv_payment_main_cash_payment_select);
        tv_payment_main_exist = (TextView) findViewById(R.id.tv_payment_main_exist);
        tv_payment_main_exist.setOnClickListener(this);
        tv_payment_main_need_pay_gold_jifen = (TextView) findViewById(R.id.tv_payment_main_need_pay_gold_jifen);
        tv_payment_main_submit_payment = (TextView) findViewById(R.id.tv_payment_main_submit_payment);
        tv_payment_main_submit_payment.setOnClickListener(this);

        tv_payment_main_need_pay_gold_jifen.setText(mPrice);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_payment_main_exist:
                finish();
                break;
            case R.id.rl_payment_main_cash_payment:
                if("4".equals(orderBean.getPay_type())) {
                    Toast.makeText(PaymentMainActivity.this, "当前支付商品只能使用银积分支付!", Toast.LENGTH_SHORT).show();
                    return;
                }else  {
                    setViewState("0");
                }
                break;
            case R.id.rl_payment_main_gold_jifen_payment:
                if("4".equals(orderBean.getPay_type())) {
                    Toast.makeText(PaymentMainActivity.this, "当前支付商品只能使用银积分支付!", Toast.LENGTH_SHORT).show();
                }else {
                    setViewState("3");
                }
                break;
            case R.id.rl_payment_main_sliver_jifen_payment:
                if(!"4".equals(orderBean.getPay_type())) {
                    Toast.makeText(PaymentMainActivity.this, "当前支付商品不能使用银积分支付!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    setViewState("4");
                }
                break;
            case R.id.tv_payment_main_submit_payment:
                if (!TextUtils.isEmpty(currentPayType)) {
                    //跳转页面
                    Intent intent = new Intent(PaymentMainActivity.this, PaymentMethodActivity.class);
                    orderBean.setPay_type(currentPayType);
                    orderBean.setId(orderID);
                    orderBean.setOrderNumber(orderNumber);
                    intent.putExtra("CurrentPayMethod", orderBean);
                    intent.putExtra("merge", merge);
                    intent.putExtra("Pay_type", orderBean.getPay_type());
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    /**
     * 改变View状态
     */
    private void setViewState(String payType) {
        if (TextUtils.isEmpty(payType))
            return;
        this.currentPayType = payType;
        switch (payType) {
            case "0":   //现金支付 微信、支付宝、余额
            case "1":
            case "2":
                tv_payment_main_cash_payment_select.setBackgroundResource(R.drawable.checkbox_checked);
                tv_payment_main_gold_jifen_payment_select.setBackgroundResource(R.drawable.checkbox_uncheck);
                tv_payment_main_sliver_jifen_payment_select.setBackgroundResource(R.drawable.checkbox_uncheck);
//                tv_payment_main_need_pay_gold_jifen.setText("余额" + orderBean.getMember_money());
                break;
            case "3":   //金积分
                tv_payment_main_cash_payment_select.setBackgroundResource(R.drawable.checkbox_uncheck);
                tv_payment_main_gold_jifen_payment_select.setBackgroundResource(R.drawable.checkbox_checked);
                tv_payment_main_sliver_jifen_payment_select.setBackgroundResource(R.drawable.checkbox_uncheck);
//                tv_payment_main_need_pay_gold_jifen.setText("金积分" + mWallet.getData().getScore());
                break;
            case "4":   //银积分
                rl_payment_main_cash_payment.setVisibility(View.GONE);
                rl_payment_main_gold_jifen_payment.setVisibility(View.GONE);
                tv_payment_main_sliver_jifen_payment_select.setBackgroundResource(R.drawable.checkbox_checked);
//                tv_payment_main_need_pay_gold_jifen.setText("银积分" + mWallet.getData().getSilver());
                break;
            default:
                break;
        }

    }

}
