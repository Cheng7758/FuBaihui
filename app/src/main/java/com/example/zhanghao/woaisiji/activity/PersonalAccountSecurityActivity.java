package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalAccountSecurityActivity extends Activity implements View.OnClickListener{
    private ImageButton ibReplacePhone;
    private ImageButton ibReplacePassword;
    private ImageView ivSecurityBack;
    private TextView tvSecurityNickName;
    private TextView tvSecurityMobile;
    private LinearLayout llSecurityLoginPassword;
    private LinearLayout llReplacePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account_security);
        initView();

        initData();

        //
        responseClickListener();

    }




    private void initView() {
        ivSecurityBack = (ImageView) findViewById(R.id.iv_security_back);

        tvSecurityNickName = (TextView) findViewById(R.id.tv_security_nick_name);
        tvSecurityMobile = (TextView) findViewById(R.id.tv_security_mobile);

        ibReplacePhone = (ImageButton) findViewById(R.id.ib_replace_phone);
        ibReplacePassword = (ImageButton) findViewById(R.id.ib_replace_password);

        llSecurityLoginPassword = (LinearLayout) findViewById(R.id.ll_security_login_password);
        llReplacePhone = (LinearLayout) findViewById(R.id.ll_replace_phone);
    }

    private void initData() {
        tvSecurityNickName.setText(WoAiSiJiApp.memberShipInfos.info.nickname);
        tvSecurityMobile.setText(WoAiSiJiApp.memberShipInfos.info.mobile);
    }

    private void responseClickListener() {
        ivSecurityBack.setOnClickListener(this);

        ibReplacePhone.setOnClickListener(this);
        ibReplacePassword.setOnClickListener(this);

        llReplacePhone.setOnClickListener(this);
        llSecurityLoginPassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_security_back:
                finish();
                break;
            case R.id.ib_replace_phone:
            case R.id.ll_replace_phone:
                startActivity(new Intent(PersonalAccountSecurityActivity.this,PersonalReplacePhoneActivity.class));
                break;
            case R.id.ib_replace_password:
            case R.id.ll_security_login_password:
                startActivity(new Intent(PersonalAccountSecurityActivity.this, PersonalModifyPwdActivity.class));
                break;
        }
    }
}
