package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.zhanghao.woaisiji.activity.my.BillingDetailsActivity;
import com.example.zhanghao.woaisiji.activity.send.BuyGlodActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.gold.UserManager;

import java.util.HashMap;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalWalletActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 2019 06.20
     */
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private LinearLayout ll_personal_wallet_gold_integral, ll_personal_wallet_balance, ll_personal_wallet_merchant_gold_integral,
            ll_personal_wallet_silver_integral;
    private RelativeLayout rl_personal_my_wallet_merchant_collection_code, rl_personal_my_wallet_billing_detail,
            rl_personal_my_wallet_buy_gold_integral;

    private TextView tv_personal_wallet_gold_integral, tv_personal_wallet_balance, tv_personal_wallet_merchant_gold_integral,
            tv_personal_wallet_silver_integral;
    private RespPersonalWallet respPersonalWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_wallet);
        initTitleBarView();
        initView();
        initData();
        // 响应点击事件
        initClickListener();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        respPersonalWallet = SharedPrefrenceUtils.getObject(PersonalWalletActivity.this, "yue");
        tv_personal_wallet_gold_integral.setText(respPersonalWallet.getData().getScore());
        tv_personal_wallet_balance.setText(respPersonalWallet.getData().getBalance());
        tv_personal_wallet_silver_integral.setText(respPersonalWallet.getData().getSilver());
        tv_personal_wallet_merchant_gold_integral.setText(respPersonalWallet.getData().getStore_score());
    }*/

    /*@Override
    protected void onRestart() {
        super.onRestart();
        respPersonalWallet = SharedPrefrenceUtils.getObject(PersonalWalletActivity.this, "yue");
        if (respPersonalWallet != null) {
            tv_personal_wallet_gold_integral.setText(respPersonalWallet.getData().getScore());
            tv_personal_wallet_balance.setText(respPersonalWallet.getData().getBalance());
            tv_personal_wallet_silver_integral.setText(respPersonalWallet.getData().getSilver());
            tv_personal_wallet_merchant_gold_integral.setText(respPersonalWallet.getData().getStore_score());
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        respPersonalWallet = SharedPrefrenceUtils.getObject(PersonalWalletActivity.this, "yue");
        if (respPersonalWallet != null) {
            tv_personal_wallet_gold_integral.setText(respPersonalWallet.getData().getScore());
            tv_personal_wallet_balance.setText(respPersonalWallet.getData().getBalance());
            tv_personal_wallet_silver_integral.setText(respPersonalWallet.getData().getSilver());
            tv_personal_wallet_merchant_gold_integral.setText(respPersonalWallet.getData().getStore_score());
//            SharedPrefrenceUtils.remove(PersonalWalletActivity.this, "yue");
        }
    }

    /**
     * 初始化TitleBar
     */
    private void initTitleBarView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setImageResource(R.drawable.back);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("我的钱包");
    }

    private void initClickListener() {
        ll_personal_wallet_gold_integral.setOnClickListener(this);
        ll_personal_wallet_balance.setOnClickListener(this);
        ll_personal_wallet_merchant_gold_integral.setOnClickListener(this);
        ll_personal_wallet_silver_integral.setOnClickListener(this);
        rl_personal_my_wallet_merchant_collection_code.setOnClickListener(this);
        rl_personal_my_wallet_billing_detail.setOnClickListener(this);
        rl_personal_my_wallet_buy_gold_integral.setOnClickListener(this);
    }

    private void initView() {
        ll_personal_wallet_gold_integral = (LinearLayout) findViewById(R.id.ll_personal_wallet_gold_integral);
        ll_personal_wallet_balance = (LinearLayout) findViewById(R.id.ll_personal_wallet_balance);
        ll_personal_wallet_merchant_gold_integral = (LinearLayout) findViewById(R.id.ll_personal_wallet_merchant_gold_integral);
        ll_personal_wallet_silver_integral = (LinearLayout) findViewById(R.id.ll_personal_wallet_silver_integral);
        rl_personal_my_wallet_merchant_collection_code = (RelativeLayout) findViewById(R.id.rl_personal_my_wallet_merchant_collection_code);
        rl_personal_my_wallet_billing_detail = (RelativeLayout) findViewById(R.id.rl_personal_my_wallet_billing_detail);
        rl_personal_my_wallet_buy_gold_integral = (RelativeLayout) findViewById(R.id.rl_personal_my_wallet_buy_gold_integral);

        tv_personal_wallet_gold_integral = (TextView) findViewById(R.id.tv_personal_wallet_gold_integral);  //金积分
        tv_personal_wallet_balance = (TextView) findViewById(R.id.tv_personal_wallet_balance);  //余额
        tv_personal_wallet_merchant_gold_integral = (TextView) findViewById(R.id.tv_personal_wallet_merchant_gold_integral); //商家金积分
        tv_personal_wallet_silver_integral = (TextView) findViewById(R.id.tv_personal_wallet_silver_integral);  //银积分
    }

    //获取钱包的数据
    private void initData() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        if (TextUtils.isEmpty(response)) return;
                        Gson gson = new Gson();
                        RespPersonalWallet respPersonalWallet = null;
                        try {
                            respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                        }catch (Exception e){

                        }
                        if (respPersonalWallet == null)
                            return;
                        if (respPersonalWallet.getCode() == 200) {
                            String score = respPersonalWallet.getData().getScore();
                            UserManager.gold = score;
                            tv_personal_wallet_gold_integral.setText(score);//金积分
                            String balance = respPersonalWallet.getData().getBalance();
                            UserManager.balance = balance;
                            tv_personal_wallet_balance.setText(balance);//余额
                            String silver = respPersonalWallet.getData().getSilver();
                            UserManager.silver = silver;
                            tv_personal_wallet_silver_integral.setText(silver); //银积分
                            String store_score = respPersonalWallet.getData().getStore_score();
                            UserManager.storeGold = store_score;
                            tv_personal_wallet_merchant_gold_integral.setText(store_score); //商家金积分
                            SharedPrefrenceUtils.putObject(PersonalWalletActivity.this, "yue", respPersonalWallet);
                        } else {
                            if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                                Toast.makeText(PersonalWalletActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalWalletActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.ll_personal_wallet_gold_integral://金积分余额
                Intent intent = new Intent(PersonalWalletActivity.this, PersonalDepositActivity.class);
                intent.putExtra("FromType", "1");
                startActivityForResult(intent,11);
//                startActivity(intent);
                break;
            case R.id.ll_personal_wallet_merchant_gold_integral://商家金积分
//            case R.id.ll_personal_wallet_silver_integral://银积分余额
                Intent intent1 = new Intent(PersonalWalletActivity.this, PersonalDepositActivity.class);
                intent1.putExtra("FromType", "2");
                startActivityForResult(intent1,11);
//                startActivity(intent1);
                break;
            case R.id.ll_personal_wallet_balance://余额
                Intent intent2 = new Intent(PersonalWalletActivity.this, PersonalDepositActivity.class);
                intent2.putExtra("FromType", "3");
                startActivityForResult(intent2,11);
//                startActivity(intent2);
                break;
            case R.id.rl_personal_my_wallet_billing_detail://账单明细
//                startActivity(new Intent(PersonalWalletActivity.this, PersonalDetailBillActivity.class));
                startActivity(new Intent(PersonalWalletActivity.this, BillingDetailsActivity.class));
                break;
            case R.id.rl_personal_my_wallet_buy_gold_integral://购买积分
                startActivity(new Intent(PersonalWalletActivity.this, RechargeGoldIntegralActivity.class));
                break;
            case R.id.rl_personal_my_wallet_merchant_collection_code://商家收款码
                startActivity(new Intent(PersonalWalletActivity.this,
                        PersonalMerchantRecommendationCodeActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 20) {
            String Score = data.getStringExtra("Score");
            String Silver = data.getStringExtra("Silver");
            String Store_score = data.getStringExtra("Store_score");
            String Balance = data.getStringExtra("Balance");
            tv_personal_wallet_gold_integral.setText(Score);
            tv_personal_wallet_balance.setText(Balance);
            tv_personal_wallet_silver_integral.setText(Silver);
            tv_personal_wallet_merchant_gold_integral.setText(Store_score);
        }
    }

}
