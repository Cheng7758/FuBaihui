package com.example.zhanghao.woaisiji.activity.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.zhanghao.woaisiji.activity.PersonalWalletActivity;
import com.example.zhanghao.woaisiji.activity.RechargeGoldIntegralActivity;
import com.example.zhanghao.woaisiji.bean.pay.MerchantsPayBean;
import com.example.zhanghao.woaisiji.bean.pay.PaySignBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MerchantPayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private TextView member_gold_points;
    private EditText input_pay_money;
    private TextView tv_confirm_payment;
    private TextView tv_buy_gold_points;
    private RespPersonalWallet respPersonalWallet;
    private String mStore_id;
    private String mMoney;
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_pay);
        mStore_id = getIntent().getStringExtra("store_id").replace("id=", "");
        respPersonalWallet = SharedPrefrenceUtils.getObject(MerchantPayActivity.this, "yue");
        initView();
    }

    private void initView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_title_bar_view_centre_title.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setText("商家支付码");

        member_gold_points = (TextView) findViewById(R.id.member_gold_points);
        input_pay_money = (EditText) findViewById(R.id.input_pay_money);
        tv_confirm_payment = (TextView) findViewById(R.id.tv_confirm_payment);
        tv_buy_gold_points = (TextView) findViewById(R.id.tv_buy_gold_points);
        tv_confirm_payment.setOnClickListener(this);
        tv_buy_gold_points.setOnClickListener(this);

        member_gold_points.setText("会员金积分：" + respPersonalWallet.getData().getScore());

    }

    private void submit() {
        mMoney = input_pay_money.getText().toString().trim();
        if (TextUtils.isEmpty(mMoney)) {
            Toast.makeText(this, "money不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.tv_confirm_payment:
                Log.e("----pay","zhifu");
                submit();
                //支付签名
                paySign();
                break;
            case R.id.tv_buy_gold_points:
                startActivity(new Intent(MerchantPayActivity.this, RechargeGoldIntegralActivity.class));
                break;
        }
    }

    private void confirmPay() {
//        showProgressDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", WoAiSiJiApp.getUid());
        map.put("token", WoAiSiJiApp.token);
        map.put("store_id", mStore_id);
        map.put("price", mMoney);
        map.put("sign", mData);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMerchantsPayBean(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MerchantsPayBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MerchantsPayBean value) {
//                        dismissProgressDialog();
                        if (value.getCode() == 200) {
                            qianbao();
                            Toast.makeText(MerchantPayActivity.this, value.getMsg() + "", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(MerchantPayActivity.this, value.getMsg() + "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void paySign() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", WoAiSiJiApp.getUid());
        map.put("token", WoAiSiJiApp.token);
        map.put("store_id", mStore_id);
        map.put("price", mMoney);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getPaySignBean(map) //商家支付
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaySignBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PaySignBean value) {
                        if (value != null) {
                            mData = value.getData();
                            //商家支付
                            confirmPay();
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(MerchantPayActivity.this, "yue", respPersonalWallet);
                } else {
                    if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        Toast.makeText(MerchantPayActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MerchantPayActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
