package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
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
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.resp.RespRechargeGoldIntegral;
import com.example.zhanghao.woaisiji.resp.RespRechargeGoldIntegralWX;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.OrderInfoUtil2_0;
import com.example.zhanghao.woaisiji.utils.PayResult;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.example.zhanghao.woaisiji.wxapi.WeChatPayService;
import com.google.gson.Gson;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 微信支付
     */
    private IWXAPI msgApi;

    private static final int SDK_PAY_FLAG = 1;

    private PayOrderBean intentOrderBean = null;
    private String paymentType, paymentPrice, paymentOrderID, paymentOrderNumber;

    private LinearLayout ll_payment_method_crash_payment_method_root, ll_payment_method_integral_payment_method_root;
    private ImageView iv_payment_method_exist_payment_page, iv_payment_method_weixin_payment, iv_payment_method_ali_payment;
    private TextView tv_payment_method_need_gold_integral, tv_payment_method_balance_gold_integral,
            tv_payment_method_sure_payment, tv_payment_method_recharge_gold_integral;

    private RespPersonalWallet respPersonalWallet;
    private String merge, orderBean, pay_type;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PaymentMethodActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        showProgressDialog();
                        FunctionUtils.paymentEndCallBack(paymentType, paymentOrderID, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dismissProgressDialog();
                                if (TextUtils.isEmpty(response))
                                    return;
                                Gson gson = new Gson();
                                RespData respConversion = gson.fromJson(response, RespData.class);
                                if (respConversion.getCode() == 200) {
                                    Intent intent = new Intent(PaymentMethodActivity.this, PaySuccessActivity.class);
                                    intent.putExtra("order_num", paymentOrderID);
                                    intent.putExtra("good_price", paymentPrice);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dismissProgressDialog();
                            }
                        });
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PaymentMethodActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Intent intent = getIntent();
        intentOrderBean = (PayOrderBean) intent.getSerializableExtra("CurrentPayMethod");
        merge = intent.getStringExtra("merge");
        pay_type = intent.getStringExtra("Pay_type");
        if (intentOrderBean != null) {
            paymentType = intentOrderBean.getPay_type();
            paymentPrice = intentOrderBean.getPrice();
//            paymentPrice = "0.01";
            paymentOrderID = intentOrderBean.getId();
            paymentOrderNumber = intentOrderBean.getOrderNumber();
        }
        initView();
    }

    private void initView() {
        iv_payment_method_exist_payment_page = (ImageView) findViewById(R.id.iv_payment_method_exist_payment_page);
        iv_payment_method_exist_payment_page.setOnClickListener(this);

        ll_payment_method_crash_payment_method_root = (LinearLayout) findViewById(R.id.ll_payment_method_crash_payment_method_root);
        iv_payment_method_weixin_payment = (ImageView) findViewById(R.id.iv_payment_method_weixin_payment);
        iv_payment_method_ali_payment = (ImageView) findViewById(R.id.iv_payment_method_ali_payment);
        iv_payment_method_weixin_payment.setOnClickListener(this);
        iv_payment_method_ali_payment.setOnClickListener(this);

        ll_payment_method_integral_payment_method_root = (LinearLayout) findViewById(R.id.ll_payment_method_integral_payment_method_root);
        tv_payment_method_balance_gold_integral = (TextView) findViewById(R.id.tv_payment_method_balance_gold_integral);
        tv_payment_method_need_gold_integral = (TextView) findViewById(R.id.tv_payment_method_need_gold_integral);
        tv_payment_method_sure_payment = (TextView) findViewById(R.id.tv_payment_method_sure_payment);
        tv_payment_method_recharge_gold_integral = (TextView) findViewById(R.id.tv_payment_method_recharge_gold_integral);

        tv_payment_method_sure_payment.setOnClickListener(this);
        tv_payment_method_recharge_gold_integral.setOnClickListener(this);

        if ("0".equals(paymentType)) {//现金支付
            ll_payment_method_crash_payment_method_root.setVisibility(View.VISIBLE);
            ll_payment_method_integral_payment_method_root.setVisibility(View.GONE);
        } else {//金币支付
            respPersonalWallet = SharedPrefrenceUtils.getObject(PaymentMethodActivity.this, "yue");
            ll_payment_method_crash_payment_method_root.setVisibility(View.GONE);
            ll_payment_method_integral_payment_method_root.setVisibility(View.VISIBLE);
            if ("3".equals(pay_type)) {
                tv_payment_method_need_gold_integral.setText("所需金积分：" + paymentPrice);
                if (respPersonalWallet != null && respPersonalWallet.getData() != null)
                    tv_payment_method_balance_gold_integral.setText("金积分余额：" + respPersonalWallet.getData().getScore());
            } else if ("4".equals(pay_type)) {
                tv_payment_method_need_gold_integral.setText("所需银积分：" + paymentPrice);
                if (respPersonalWallet != null && respPersonalWallet.getData() != null)
                    tv_payment_method_balance_gold_integral.setText("银积分余额：" + respPersonalWallet.getData().getSilver());
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_payment_method_exist_payment_page:
                finish();
                break;
            case R.id.tv_payment_method_recharge_gold_integral://充值
                Intent intent = new Intent(PaymentMethodActivity.this, RechargeGoldIntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_payment_method_ali_payment:
                paymentType = "1";
                getALiPayParams();
                break;
            case R.id.iv_payment_method_weixin_payment:
                paymentType = "2";
                getWxPayParams();
                break;
            case R.id.tv_payment_method_sure_payment:
                if ("3".equals(pay_type)) {
                    paymentType = "3";
                }else if ("4".equals(pay_type)){
                    paymentType = "4";
                }
//                paymentType = pay_type ? "3" : "4";
                paymentIndent();
                break;
        }
    }

    /**
     * 金积分支付
     */
    private void paymentIndent() {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PAY_ORDER_USE_INTEGRAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response)) return;
                        Gson gson = new Gson();
                        RespNull respPayOrder = gson.fromJson(response, RespNull.class);
                        if (respPayOrder.getCode() == 200) {
                            Intent intent = new Intent(PaymentMethodActivity.this, PaySuccessActivity.class);
                            intent.putExtra("order_num", paymentOrderID);
                            intent.putExtra("good_price", paymentPrice);
                            startActivity(intent);
                            finish();
                        } else {
                            if (TextUtils.isEmpty(respPayOrder.getMsg()))
                                Toast.makeText(PaymentMethodActivity.this, "支付失败!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(PaymentMethodActivity.this, respPayOrder.getMsg(), Toast.LENGTH_SHORT).show();
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
                params.put("id", paymentOrderID);
                params.put("pay_type", paymentType);
                params.put("merge", merge);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }


    /**
     * 获取支付宝支付的参数
     */
    private void getALiPayParams() {
        showProgressDialog();
        StringRequest getAliPayRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PAY_ORDER_GET_ALI_PAY_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        final RespRechargeGoldIntegral respRechargeGoldIntegral = gson.fromJson(response, RespRechargeGoldIntegral.class);
                        if (respRechargeGoldIntegral.getCode() == 200) {
                            paymentOrderID = respRechargeGoldIntegral.getData().getOut_trade_no();
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PaymentMethodActivity.this);
                                    Map<String, String> result = alipay.payV2(respRechargeGoldIntegral.getData().getResponse(), true);
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else {
                            if (!TextUtils.isEmpty(respRechargeGoldIntegral.getMsg()))
                                Toast.makeText(PaymentMethodActivity.this, respRechargeGoldIntegral.getMsg(), Toast.LENGTH_SHORT).show();
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
                params.put("body", "福百惠商城");
                params.put("subject", "福百惠商城");
                params.put("out_trade_no", paymentOrderNumber);
                params.put("total_amount", paymentPrice);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(getAliPayRequest);
    }

    /**
     * 获取微信支付的参数
     */
    private void getWxPayParams() {
        showProgressDialog();
        StringRequest getWXPayRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PAY_ORDER_GET_WEIXIN_PAY_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespRechargeGoldIntegralWX respRechargeGoldIntegralWX = gson.fromJson(response, RespRechargeGoldIntegralWX.class);
                        if (respRechargeGoldIntegralWX.getErrorCode() == 0) {
                            paymentOrderID = respRechargeGoldIntegralWX.getOut_trade_no();
                            msgApi = WXAPIFactory.createWXAPI(PaymentMethodActivity.this, null);
                            // 将该app注册到微信
                            msgApi.registerApp("wxc1184669ab904cdd");
                            WeChatPayService weChatPay = new WeChatPayService(PaymentMethodActivity.this,
                                    Integer.valueOf(paymentType), paymentOrderID, "订单编号:" + paymentOrderID, paymentPrice);
                            weChatPay.pay(respRechargeGoldIntegralWX.getResponseData().getApp_response());
                        } else {
                            if (!TextUtils.isEmpty(respRechargeGoldIntegralWX.getErrorMsg()))
                                Toast.makeText(PaymentMethodActivity.this, respRechargeGoldIntegralWX.getErrorMsg(),
                                        Toast.LENGTH_SHORT).show();
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
                params.put("ordernum", paymentOrderNumber);
                params.put("money", paymentPrice);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(getWXPayRequest);
    }
}
