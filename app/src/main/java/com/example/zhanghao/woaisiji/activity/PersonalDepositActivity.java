package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.zhanghao.woaisiji.activity.main.MainActivity;
import com.example.zhanghao.woaisiji.activity.money.WithdrawActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class PersonalDepositActivity extends BaseActivity {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private EditText et_personal_deposit_shift_to_balance, et_personal_deposit_input_deposit_amount;
    private TextView tv_personal_deposit_transferable_balance, tv_personal_cash_balance_balance,
            tv_personal_deposit_sure_shift, tv_personal_deposit_now, binding_zfb;
    private LinearLayout shift_to, withdraw_deposit;
    private RadioGroup rg_personal_deposit_way;
    private RespPersonalWallet respPersonalWallet;
    private RespNull respNull;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_deposit);
        type = getIntent().getStringExtra("FromType");
        if (TextUtils.isEmpty(type)) {
            Toast.makeText(this, "Type Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        respPersonalWallet = SharedPrefrenceUtils.getObject(PersonalDepositActivity.this, "yue");
//        respNull = SharedPrefrenceUtils.getObject(PersonalDepositActivity.this, "binding");
//        binding_zfb.setText(respNull.getData());
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
        tv_personal_cash_balance_balance = (TextView) findViewById(R.id.tv_personal_cash_balance_balance);
        shift_to = (LinearLayout) findViewById(R.id.shift_to);
        withdraw_deposit = (LinearLayout) findViewById(R.id.withdraw_deposit);

        String score = respPersonalWallet.getData().getScore();
        String store_score = respPersonalWallet.getData().getStore_score();
        String balance = respPersonalWallet.getData().getBalance();
        if (type.equals("1") && respPersonalWallet.getData() != null) {
            shift_to.setVisibility(View.VISIBLE);
            withdraw_deposit.setVisibility(View.GONE);
            tv_personal_deposit_transferable_balance.setText("可转入余额：" + score);  //金积分可转入余额
            tv_personal_cash_balance_balance.setText("可提现金额：" + balance);  //可提现余额
        } else if (type.equals("2") && respPersonalWallet.getData() != null) {
            shift_to.setVisibility(View.VISIBLE);
            withdraw_deposit.setVisibility(View.GONE);
            tv_personal_deposit_transferable_balance.setText("可转入余额：" + store_score);  //商家金积分可转入余额
            tv_personal_cash_balance_balance.setText("可提现金额：" + balance);  //可提现余额
        } else if (type.equals("3") && respPersonalWallet.getData() != null) {
            shift_to.setVisibility(View.GONE);
            withdraw_deposit.setVisibility(View.VISIBLE);
            tv_personal_deposit_transferable_balance.setText("可转入余额：" + score);  //金积分可转入余额
            tv_personal_cash_balance_balance.setText("可提现金额：" + balance);  //可提现余额
        }
        //提现到余额
        tv_personal_deposit_sure_shift = (TextView) findViewById(R.id.tv_personal_deposit_sure_shift);
        tv_personal_deposit_sure_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_personal_deposit_shift_to_balance.getText().toString())) {
                    float depositToBalance = Float.valueOf(et_personal_deposit_shift_to_balance.getText().toString());
                    if (depositToBalance > 0) {
                        depositToBalance();
                    }
                } else {
                    Toast.makeText(PersonalDepositActivity.this, "请输入转入余额", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        //立即提现  提现到支付宝
        tv_personal_deposit_now = (TextView) findViewById(R.id.tv_personal_deposit_now);
        tv_personal_deposit_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_personal_deposit_input_deposit_amount.getText().toString())) {
                    float depositToBalance = Float.valueOf(et_personal_deposit_input_deposit_amount.getText().toString());
                    Log.e("-----depositToBalance", String.valueOf(depositToBalance));
                    if (depositToBalance > 0) {
                        isBindingWXOrZFB();
//                        isDepositToWXOrZFBSuccessful();
                    }
                } else {
                    Toast.makeText(PersonalDepositActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        binding_zfb = (TextView) findViewById(R.id.binding_zfb);
        binding_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isBindingWXOrZFB();
                Intent intent = new Intent(PersonalDepositActivity.this, WithdrawActivity.class);
                intent.putExtra("type", rg_personal_deposit_way.getCheckedRadioButtonId()
                        == R.id.rb_personal_deposit_wx ? "1" : "2");
                startActivityForResult(intent, 11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 20) {
            String number = data.getStringExtra("number");
            binding_zfb.setText(number);
        }
    }

    /**
     * 提现到余额
     */
    private void depositToBalance() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
                    qianbao();
                    Intent intent = new Intent();
                    intent.putExtra("Score", respPersonalWallet.getData().getScore());
                    intent.putExtra("Silver", respPersonalWallet.getData().getSilver());
                    intent.putExtra("Store_score", respPersonalWallet.getData().getStore_score());
                    intent.putExtra("Balance", respPersonalWallet.getData().getBalance());
                    setResult(20, intent);
                    Toast.makeText(PersonalDepositActivity.this, "提现成功", Toast.LENGTH_SHORT).show();

                } else {
                    if (!TextUtils.isEmpty(respNull.getMsg()))
                        Toast.makeText(PersonalDepositActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Log.e("----余额", error.toString());
                Toast.makeText(PersonalDepositActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_WX_ZFB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
                    isDepositToWXOrZFBSuccessful();
                } else {
                    if (!TextUtils.isEmpty(respNull.getMsg())) {
                        Log.e("-----提现", respNull.getMsg());
                        Toast.makeText(PersonalDepositActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", rg_personal_deposit_way.getCheckedRadioButtonId() == R.id.rb_personal_deposit_wx
                        ? "1" : "2");
                params.put("money", et_personal_deposit_input_deposit_amount.getText().toString());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    /**
     * 检测是否绑定微信或者支付宝
     */
    private void isBindingWXOrZFB() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_MY_WALLET_BINDING_WX_ZFB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200 && !TextUtils.isEmpty(respNull.getData())) {
                    Log.e("-----绑定", respNull.getMsg());
//                    SharedPrefrenceUtils.putObject(PersonalDepositActivity.this, "binding", respNull);
                    depositToWXOrZFB();
//                    isDepositToWXOrZFBSuccessful();
                } else {
                    Intent intent = new Intent(PersonalDepositActivity.this, WithdrawActivity.class);
                    intent.putExtra("type", rg_personal_deposit_way.getCheckedRadioButtonId()
                            == R.id.rb_personal_deposit_wx ? "1" : "2");
                    startActivityForResult(intent, 11);
                    Toast.makeText(PersonalDepositActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", rg_personal_deposit_way.getCheckedRadioButtonId() == R.id.rb_personal_deposit_wx
                        ? "1" : "2");
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
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_SUCCEED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
//                    depositToWXOrZFB();
                    qianbao();
                    Intent intent = new Intent();
                    intent.putExtra("Score", respPersonalWallet.getData().getScore());
                    intent.putExtra("Silver", respPersonalWallet.getData().getSilver());
                    intent.putExtra("Store_score", respPersonalWallet.getData().getStore_score());
                    intent.putExtra("Balance", respPersonalWallet.getData().getBalance());
                    setResult(20, intent);
                    Toast.makeText(PersonalDepositActivity.this, "提现成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (!TextUtils.isEmpty(respNull.getMsg())) {
                        Log.e("-----提现审核", respNull.getMsg());
                        Toast.makeText(PersonalDepositActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PersonalDepositActivity.this, "有提现申请，不可发起提现", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Log.e("----money", error.toString());
                Toast.makeText(PersonalDepositActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    private void qianbao() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespPersonalWallet respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                SharedPrefrenceUtils.remove(PersonalDepositActivity.this, "yue");
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(PersonalDepositActivity.this, "yue", respPersonalWallet);
                } else {
                    if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        Toast.makeText(PersonalDepositActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalDepositActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
