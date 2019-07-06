package com.example.zhanghao.woaisiji.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.friends.LogoutEm;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.Constants;
import com.example.zhanghao.woaisiji.serverdata.GetToken;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.SharedPrefUtil;

public class SplashActivity extends BaseActivity {
    private ImageView mIvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //干什么用？
        if (TextUtils.isEmpty(WoAiSiJiApp.getUid())){
            LogoutEm.logout();
        }
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    private void initView() {
        mIvSplash = (ImageView) findViewById(R.id.iv_splash);
    }

    private void initData() {
        //干什么用？
        WoAiSiJiApp.setUid(PrefUtils.getString(SplashActivity.this,"uid",""));
        if (TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
            LogoutEm.logout();
            jumpToMainActivity();
        }else{
            token.getToken(new GetToken.GetTokenCallBack(){
                @Override
                public void getToken(boolean isSuccessful) {
                    jumpToMainActivity();
                }
            });
        }
    }

    private void jumpToMainActivity() {
        Animation animation = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.splash);
        mIvSplash.startAnimation(animation);
        // 动画结束时, 跳转页面
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstRun = SharedPrefUtil.getBoolean(getApplicationContext(),
                        Constants.KEY_IS_FIRST_RUN, true);
                if (isFirstRun) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        });
    }
}
