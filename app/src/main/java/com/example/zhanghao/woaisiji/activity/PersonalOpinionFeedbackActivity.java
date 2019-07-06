package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;

/**
 * Created by zzz on 2016/12/12.
 */

public class PersonalOpinionFeedbackActivity extends BaseActivity {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private EditText editPersonalOpinion;
    private Button btnSubmitOpinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_opinion_feedback);

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
        tv_title_bar_view_centre_title.setText("意见反馈");

        editPersonalOpinion = (EditText) findViewById(R.id.edit_personal_opinion);
        btnSubmitOpinion = (Button) findViewById(R.id.btn_submit_opinion);
        btnSubmitOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(editPersonalOpinion.getText())){
                    Toast.makeText(PersonalOpinionFeedbackActivity.this,"感谢您的宝贵意见！",Toast.LENGTH_SHORT).show();
                    editPersonalOpinion.setText("");
                    finish();
                }else {
                    Toast.makeText(PersonalOpinionFeedbackActivity.this,"请输入您的意见！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
