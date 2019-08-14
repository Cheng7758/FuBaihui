package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.example.zhanghao.woaisiji.bean.pay.RechargeGoldIntegralWXAppResponse;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.resp.RespRechargeGoldIntegral;
import com.example.zhanghao.woaisiji.resp.RespRechargeGoldIntegralWX;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.PayResult;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.example.zhanghao.woaisiji.wxapi.WeChatPayService;
import com.google.gson.Gson;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 金币充值
 */
public class RechargeGoldIntegralActivity extends BaseActivity {

    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;

    private TextView tv_recharge_gold_integral_residue, tv_recharge_gold_integral_sure, tv_recharge_gold_integral_conversion;
    private EditText et_recharge_gold_integral_input_deposit_amount;
    private RadioButton rb_recharge_gold_integral_zfb, rb_recharge_gold_integral_wx;

    private String payType = "1";
    private String paymentOrderID, rechargeAmount;
    private RespPersonalWallet respPersonalWallet;

    /**
     * 微信支付
     */
    private IWXAPI msgApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_gold_integral);
        initData();
        initView();
    }

    private void initView() {
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("充值");
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_recharge_gold_integral_residue = (TextView) findViewById(R.id.tv_recharge_gold_integral_residue);
        tv_recharge_gold_integral_sure = (TextView) findViewById(R.id.tv_recharge_gold_integral_sure);
        et_recharge_gold_integral_input_deposit_amount = (EditText) findViewById(R.id.et_recharge_gold_integral_input_deposit_amount);
        rb_recharge_gold_integral_zfb = (RadioButton) findViewById(R.id.rb_recharge_gold_integral_zfb);
        rb_recharge_gold_integral_wx = (RadioButton) findViewById(R.id.rb_recharge_gold_integral_wx);
        tv_recharge_gold_integral_conversion = (TextView) findViewById(R.id.tv_recharge_gold_integral_conversion);

//        tv_recharge_gold_integral_residue.setText(respPersonalWallet.getData().getScore());
        if (respPersonalWallet != null)
            tv_recharge_gold_integral_residue.setText(respPersonalWallet.getData().getScore());

        rb_recharge_gold_integral_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "2";
            }
        });
        rb_recharge_gold_integral_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "1";
            }
        });
        tv_recharge_gold_integral_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rechargeAmount = et_recharge_gold_integral_input_deposit_amount.getText().toString();
                if (!TextUtils.isEmpty(rechargeAmount)) {
                    rechargeGoldIntegral();
                } else {
                    Toast.makeText(RechargeGoldIntegralActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                }
            }
        });
        et_recharge_gold_integral_input_deposit_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString()) && Double.valueOf(charSequence.toString()) > 0f) {
                    rechargeGoldIntegralConversion();
                } else {
                    tv_recharge_gold_integral_conversion.setText("充值金积分");
                }
            }
        });
    }

    /**
     * 点击确认充值
     */
    private void rechargeGoldIntegral() {
        showProgressDialog();
        StringRequest request = new StringRequest(Request.Method.POST, ServerAddress.URL_RECHARGE_GOLD_INTEGRAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        if ("1".equals(payType)) {
                            RespRechargeGoldIntegral respRechargeGoldIntegral = gson.fromJson(response,
                                    RespRechargeGoldIntegral.class);
                            if (respRechargeGoldIntegral.getCode() == 200) {
                                paymentOrderID = respRechargeGoldIntegral.getData().getOut_trade_no();
                                notifyALiPay(respRechargeGoldIntegral.getData().getResponse());
                            } else {
                                if (!TextUtils.isEmpty(respRechargeGoldIntegral.getMsg()))
                                    Toast.makeText(RechargeGoldIntegralActivity.this, respRechargeGoldIntegral.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            RespRechargeGoldIntegralWX respRechargeGoldIntegralWX = gson.fromJson(response, RespRechargeGoldIntegralWX.class);
                            if (respRechargeGoldIntegralWX.getErrorCode() == 0) {
                                paymentOrderID = respRechargeGoldIntegralWX.getOut_trade_no();
                                notifyWXPay(respRechargeGoldIntegralWX.getResponseData().getApp_response());
                            } else {
                                if (!TextUtils.isEmpty(respRechargeGoldIntegralWX.getErrorMsg()))
                                    Toast.makeText(RechargeGoldIntegralActivity.this, respRechargeGoldIntegralWX.getErrorMsg(),
                                            Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(RechargeGoldIntegralActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", WoAiSiJiApp.getUid());
                params.put("money", rechargeAmount);
                params.put("type", payType);
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(request);

    }

    /**
     * 唤醒支付宝支付
     */
    private void notifyALiPay(final String responseParams) {
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, Double.valueOf(rechargeAmount),
//                paymentOrderID, huidiao);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
//        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeGoldIntegralActivity.this);
                Map<String, String> result = alipay.payV2(responseParams, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 唤起微信支付
     */
    private void notifyWXPay(RechargeGoldIntegralWXAppResponse rechargeGoldIntegralWXAppResponse) {
        msgApi = WXAPIFactory.createWXAPI(RechargeGoldIntegralActivity.this, null);
        // 将该app注册到微信
        msgApi.registerApp("wxc1184669ab904cdd");
        WeChatPayService weChatPay = new WeChatPayService(this, Integer.valueOf(payType), paymentOrderID,
                "订单编号:" + paymentOrderID, rechargeAmount);
        weChatPay.pay(rechargeGoldIntegralWXAppResponse);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeGoldIntegralActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        payEnd();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeGoldIntegralActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void payEnd() {
        showProgressDialog();
        FunctionUtils.paymentEndCallBack(payType, paymentOrderID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespData respConversion = gson.fromJson(response, RespData.class);
                if (respConversion.getCode() == 200) {
                    Intent intent = new Intent(RechargeGoldIntegralActivity.this, PaySuccessActivity.class);
                    intent.putExtra("order_num", paymentOrderID);
                    intent.putExtra("good_price", rechargeAmount);
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
    }

    /**
     * 充值换算
     */
    private void rechargeGoldIntegralConversion() {
        StringRequest request = new StringRequest(Request.Method.POST, ServerAddress.URL_RECHARGE_GOLD_INTEGRAL_CONVERSION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespData respConversion = gson.fromJson(response, RespData.class);
                        if (respConversion.getCode() == 200) {
                            tv_recharge_gold_integral_conversion.setText("充值金积分：" + respConversion.getData());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("money", et_recharge_gold_integral_input_deposit_amount.getText().toString());
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(request);
    }

    private void initData() {
        respPersonalWallet = SharedPrefrenceUtils.getObject(RechargeGoldIntegralActivity.this, "yue");
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespPersonalWallet respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                        SharedPrefrenceUtils.remove(RechargeGoldIntegralActivity.this, "yue");
                        if (respPersonalWallet.getCode() == 200) {
//                            tv_personal_wallet_gold_integral.setText(respPersonalWallet.getData().getScore());//金积分
//                            tv_personal_wallet_balance.setText(respPersonalWallet.getData().getBalance());//余额
//                            tv_personal_wallet_silver_integral.setText(respPersonalWallet.getData().getSilver()); //银积分
//                            tv_personal_wallet_merchant_gold_integral.setText(respPersonalWallet.getData().getStore_score()); //商家金积分
                            SharedPrefrenceUtils.putObject(RechargeGoldIntegralActivity.this, "yue", respPersonalWallet);
                            respPersonalWallet = respPersonalWallet;
                            tv_recharge_gold_integral_residue.setText(respPersonalWallet.getData().getScore());
                        } else {
                            if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                                Toast.makeText(RechargeGoldIntegralActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(RechargeGoldIntegralActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
