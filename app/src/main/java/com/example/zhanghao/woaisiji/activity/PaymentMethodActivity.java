package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.zhanghao.woaisiji.activity.send.BuyGlodActivity;
import com.example.zhanghao.woaisiji.bean.GoldBuyBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.OrderInfoUtil2_0;
import com.example.zhanghao.woaisiji.utils.PayResult;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.example.zhanghao.woaisiji.wxapi.WXPayEntryActivity;
import com.example.zhanghao.woaisiji.wxapi.WeChatPayService;
import com.google.gson.Gson;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {

    // 包名签名
    private String package_sign = "a94c6499c96cec5c6c8fc78a9281a1ca";
    /**
     * 微信支付
     */
    private IWXAPI msgApi;
    private String APP_ID = "wxc1184669ab904cdd";


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016082501803446";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088222308722059";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMZ19fUp0yeBmfjPytWGex7Zfjplq2UZ7kvm20HEJ9LpkwHvXWl9pO/Vi7WKEx3Omo/uisvRlgRdpOpjiwsah3t+XVwOYM1ZsU57TnQQ2KPwT21Lmwx2VD95sBX74o6cfT9IcsW5r177Iw07VU3IQFPtWN6g5g21GW7odS/w6iGRAgMBAAECgYA/pneTAK4LPqK+TQ6VuwLj2weD/TWiQFXQHCN4DUxkdatDdJy2B6kNjqS3Pahx5+BkhzIWuoptrgcFcZWXoxOOM2qrEXu5RtQ3jTfWQcKao2ZnwPX9j9fnNoR4/8e4wK1TRf+E+CouOqrANAiiSXhSmQXR2pxBZFO+CTYFc2JD/QJBAPywMWU+WMVfshiRzX6MqBTHmOyvxqytq57zlrHTOzVX1szZFf5RiLMcuwAORY3FM6Cj8rzb01WOpcG8j3tnOAMCQQDJD9OWgKd7lqbOVnJyxBGDN4ESLeyl9Y+GjkUXv/t5fMi+zAMqPxp8+BKlLgk2tcgXgHQvk1tDSL6LI977wb3bAkBn5f55dZhEl6OADLtk9GrMGIHdmCzlpYcylU/6EDOBLHvqwLwVVOsTUpXlYPjiYRpoS+EsHGsZ+8xWC7UndXqHAkEAjhl/Ywj+VZx9u6rhLDdiYSijMiP+8rQAggqezlBC/POyNRaezgbXQ6dYFiHZJS+IgQOqGc29XGpXMKNjfu7IrQJBAMjUVjVKh4k6NtwqQHwZ1uFdgtuNG8doDFNoMDD7oKDwpMD6KGmci7AflRApQSb4rA3SKVw+6mxio4TsbtCsNG8=";
    public static final String huidiao = "http://www.woaisiji.com/notify_url.php";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private int option;
    private String ordernum;
    private Double finalPrice;
    private Double price;
    private ImageView back;
    private TextView orderNum;
    private TextView price1;
    private RelativeLayout zhifubao;
    private RelativeLayout weixinzhifu;
    private ImageView zhiFuBaoXuanXiang;
    private ImageView weiXinZhiFuXuanXiang;
    private TextView payment;
    private RelativeLayout confirmPayment;
    private int type;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
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
                        Toast.makeText(PaymentMethodActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (option == 3) {
                            payEnds();
                        } else {
                            payEnd();
                        }

                        Intent intent = new Intent(PaymentMethodActivity.this, PaySuccessActivity.class);
                        intent.putExtra("order_num", ordernum);
                        intent.putExtra("good_price", finalPrice);
                        startActivity(intent);
                        finish();
//                        PayEndResult.payEnd(option,ordernum);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PaymentMethodActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                        payEnd();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    public void payEnds() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GLOD_BACK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("orderid", ordernum);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    public void payEnd() {
        String url = ServerAddress.URL_PAY_END + "/orderid/" + ordernum + "/uid/" + ((WoAiSiJiApp)getApplication()).getUid();
        if (option == 2) {
            url = ServerAddress.URL_CASH_PAY_END + "/orderid/" + ordernum + "/uid/" + ((WoAiSiJiApp)getApplication()).getUid();
        }

        StringRequest payEndRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        WoAiSiJiApp.mRequestQueue.add(payEndRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Intent intent = getIntent();
        //判断选用哪个支付方式
        option = intent.getIntExtra("option", 0);
        //订单编号
        ordernum = intent.getStringExtra("ordernum");
        finalPrice = intent.getDoubleExtra("finalPrice", 0.00);
//        finalPrice = 0.01;
        price = intent.getDoubleExtra("price", 0.00);
//        price = 0.01;
        initView();

        initData();

    }


    private void initView() {
        back = (ImageView) findViewById(R.id.iv_register_back);
        orderNum = (TextView) findViewById(R.id.zhifu_number);
        price1 = (TextView) findViewById(R.id.zhifu_Price);
        zhifubao = (RelativeLayout) findViewById(R.id.zhifu_zhifubao);
        weixinzhifu = (RelativeLayout) findViewById(R.id.zhifu_weixinzhifu);
        zhiFuBaoXuanXiang = (ImageView) findViewById(R.id.zhifu_zhifubaoSelected);
        weiXinZhiFuXuanXiang = (ImageView) findViewById(R.id.zhifu_weixinSelected);
        payment = (TextView) findViewById(R.id.zhifu_payment);
        confirmPayment = (RelativeLayout) findViewById(R.id.ConfirmPayment);
    }

    private void initData() {
        back.setOnClickListener(this);
        orderNum.setText(ordernum);
        payment.setText("￥" + finalPrice);
        price1.setText("￥" + price);
        if (option == 1) {
            weixinzhifu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                for (Activity activity : PublicActivityList.activityList) {
                    activity.finish();
                }
//                finish();
                break;
            case R.id.zhifu_zhifubao:
                type = 1;
                zhiFuBaoXuanXiang.setImageResource(R.drawable.gongquan);
                weiXinZhiFuXuanXiang.setImageResource(R.drawable.baiquan);

                break;
            case R.id.zhifu_weixinzhifu:
                type = 2;
                weiXinZhiFuXuanXiang.setImageResource(R.drawable.gongquan);
                zhiFuBaoXuanXiang.setImageResource(R.drawable.baiquan);
                msgApi = WXAPIFactory.createWXAPI(PaymentMethodActivity.this, null);
                // 将该app注册到微信
                msgApi.registerApp("wxc1184669ab904cdd");
                break;
            case R.id.ConfirmPayment:
                if (type == 1) {
//                    Toast.makeText(PaymentMethodActivity.this,"支付宝",Toast.LENGTH_LONG).show();
                    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, finalPrice, ordernum, huidiao);
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                    String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
                    final String orderInfo = orderParam + "&" + sign;
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PaymentMethodActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
//                            Log.i("msp", result.toString());

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else if (type == 2) {

                    WeChatPayService weChatPay = new WeChatPayService(this, type, ordernum, "订单编号:" + ordernum, String.valueOf(finalPrice));
                    weChatPay.pay();
//                    finish();
                } else {
                    Toast.makeText(PaymentMethodActivity.this, "请选择支付方式", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(PaymentMethodActivity.this,PaySuccessActivity.class));
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (Activity activity : PublicActivityList.activityList) {
                activity.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
