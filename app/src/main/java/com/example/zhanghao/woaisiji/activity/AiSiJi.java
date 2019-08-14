package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.login.LoginActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;

public class AiSiJi extends BaseActivity implements View.OnClickListener{

    private ImageButton ibIconLove;
    private ImageButton ibIconHealth;
    private ImageButton ibIconDrive;
    private ImageButton ibIconConsult;
    private ImageButton ibIconAppMall;
    private Button btnBack;
    private TextView tvDriverTitle;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_si_ji);

        initView();

        initData();

        responseClickListener();

    }

    private void initView() {
//        btnBack = (Button) findViewById(R.id.btn_back);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvDriverTitle = (TextView) findViewById(R.id.love_driver_title);

        ibIconLove = (ImageButton) findViewById(R.id.ib_icon_love);//搜索会员
        ibIconHealth = (ImageButton) findViewById(R.id.ib_icon_health);
        ibIconDrive = (ImageButton) findViewById(R.id.ib_icon_drive);
        ibIconConsult = (ImageButton) findViewById(R.id.ib_icon_consult);
        ibIconAppMall = (ImageButton) findViewById(R.id.ib_icon_app_mall);
    }

    private void initData() {
        tvDriverTitle.setText("搜索会员");
    }
    private void responseClickListener() {
        ivBack.setOnClickListener(this);


        ibIconLove.setOnClickListener(this);
        ibIconHealth.setOnClickListener(this);
        ibIconDrive.setOnClickListener(this);
        ibIconConsult.setOnClickListener(this);
        ibIconAppMall.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ib_icon_love://搜索会员
                if (TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
                    startActivity(new Intent(AiSiJi.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(AiSiJi.this, LoveDriverSociallyActivity.class));
                }
                break;
            case R.id.ib_icon_health://司机健康
                startActivity(new Intent(AiSiJi.this, LoveDriverHealthActivity.class));
                break;
            case R.id.ib_icon_drive://自助游
                startActivity(new Intent(AiSiJi.this, LoveDriverSelfDriveActivity.class));
                break;
            case R.id.ib_icon_consult://司机资讯
                startActivity(new Intent(AiSiJi.this, LoveDriverInformationActivity.class));
                break;
            case R.id.ib_icon_app_mall:
                Toast.makeText(AiSiJi.this,"正在努力开发，敬请期待！",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AiSiJi.this, LoveDriverAppMallActivity.class));
                break;
        }
    }
}
