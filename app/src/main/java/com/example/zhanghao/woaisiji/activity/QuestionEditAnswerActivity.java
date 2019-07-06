package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.zhanghao.woaisiji.global.ServerAddress;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/10/30.
 */
public class QuestionEditAnswerActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;
    private Button btnTravelRelease;
    private TextView tvQuestionTitle;
    private EditText etMyReplyContent;
    private String title;
    private String pid;
    private String replyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit_answer);
        title = getIntent().getStringExtra("title");
        pid = getIntent().getStringExtra("id");
        initView();

        initClickListener();

        initData();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnTravelRelease = (Button) findViewById(R.id.btn_travel_release);
        tvQuestionTitle = (TextView) findViewById(R.id.tv_question_title);
        etMyReplyContent = (EditText) findViewById(R.id.my_edit_reply_content);
    }

    private void initClickListener() {
        ivBack.setOnClickListener(this);
        btnTravelRelease.setOnClickListener(this);
    }

    private void initData() {
        tvQuestionTitle.setText(title);
    }

    private void submitToServer() {
        StringRequest submitRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PROBLEM_ADD_REPLY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("pid",pid);
                params.put("content",replyContent);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(submitRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_travel_release:
                replyContent = etMyReplyContent.getText().toString().trim();
                if (TextUtils.isEmpty(replyContent)){
                    Toast.makeText(QuestionEditAnswerActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    submitToServer();
                    finish();
                }

                break;
        }
    }
}
