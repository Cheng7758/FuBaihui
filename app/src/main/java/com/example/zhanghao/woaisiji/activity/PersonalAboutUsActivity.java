package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.utils.Util;

public class PersonalAboutUsActivity extends BaseActivity {

    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;

    private TextView tv_personal_about_us_current_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_about_us);

        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("关于我们");

        tv_personal_about_us_current_version = (TextView) findViewById(R.id.tv_personal_about_us_current_version);
        tv_personal_about_us_current_version.setText("当前版本："+ Util.getVersionCode());
    }
}
