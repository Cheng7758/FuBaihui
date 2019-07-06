package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;

/**
 * 金币页面.
 */
public class PersonalWalletGoldActivity extends Activity {
    private Button btnLookDetail;
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private Button btnRegisterMore;
    private Button btnSign;
    private TextView tvMyCoins;
    private ImageView ivMyCoins;
    private TextView tvMyCoinsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_gold_silver);
        initView();
        initData();

        // 响应点击事件
        responseClickListener();

    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalWalletGoldActivity.this, ILoveShopActivity.class));
            }
        });
        btnLookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalWalletGoldActivity.this, PersonalDetailBillActivity.class));
            }
        });
    }


    private void initView() {
        // 标题栏
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
        btnRegisterMore.setVisibility(View.GONE);
        tvMyCoins = (TextView) findViewById(R.id.tv_my_coins);
        ivMyCoins = (ImageView) findViewById(R.id.iv_my_coins);
        tvMyCoinsNumber = (TextView) findViewById(R.id.tv_my_coins_number);

        btnLookDetail = (Button) findViewById(R.id.btn_look_detail);
        btnSign = (Button) findViewById(R.id.btn_sign);
    }

    private void initData() {
        tvRegisterTitle.setText("金币");
        tvMyCoins.setText("我的金币");
        btnSign.setText("抢购送金币");
        ivMyCoins.setImageResource(R.drawable.gold_coins_small);
        tvMyCoinsNumber.setText(WoAiSiJiApp.memberShipInfos.info.score);
    }
}
