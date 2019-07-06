package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.ReplyListAdapter;
import com.example.zhanghao.woaisiji.bean.forum.QuestionDetailBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.utils.TimeFormatUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/29.
 */
public class QuestionDetailActivity extends Activity
        implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{

    private TextView tvMallTitle;
    private Button btnBack;
    private String nickName;
    private String pid;
    private String is_cnyj;
    private PullToRefreshLayout refreshView;
    private ListView lvReplyList;
    private Button btnMyReply;
    private QuestionDetailBean questionDetailBean;
    private List<QuestionDetailBean.InfoBean.ReplyBean> replyBeanList;
    private ReplyListAdapter replyListAdapter;
    private TextView tvQuestionDetailPrice;
    private TextView tvQuestionDetailContent;
    private TextView tvQuestionDetailTime;
    private TextView tvQuestionDetailCount;
    private String questionTitle;
    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        nickName = getIntent().getStringExtra("nickname");
        pid =  getIntent().getStringExtra("pid");
        flag = getIntent().getFlags();
//        is_cnyj = getIntent().getStringExtra("item.is_cnyj");

//        Toast.makeText(this ,pid,Toast.LENGTH_SHORT).show();

        initView();
        initClickListener();

        initData();
    }

    private void initData() {
        replyBeanList = new ArrayList<>();
        replyListAdapter = new ReplyListAdapter(QuestionDetailActivity.this,flag);
        getDataFromServer();
    }

    public void getDataFromServer() {
        StringRequest replyRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PROBLEM_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (questionDetailBean.code == 200){
                    setLayoutData();
//                    is_cnyj = questionDetailBean.info.is_cnyj;




                    if (questionDetailBean.info.reply != null){
                        replyBeanList.clear();
                        for (QuestionDetailBean.InfoBean.ReplyBean bean : questionDetailBean.info.reply){
                            replyBeanList.add(bean);
                        }


                        replyListAdapter.replyBeanList = replyBeanList;
                        replyListAdapter.is_cnyj = questionDetailBean.info.is_cnyj;

                        lvReplyList.setAdapter(replyListAdapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",pid);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(replyRequest);
    }

    private void setLayoutData() {
        questionTitle = questionDetailBean.info.title;
        tvQuestionDetailPrice.setText(questionDetailBean.info.price);
        tvQuestionDetailContent.setText(questionTitle);
        tvQuestionDetailTime.setText(TimeFormatUtil.format(questionDetailBean.info.create_time));
        int num = 0;
        if (questionDetailBean.info.reply != null){
            num = questionDetailBean.info.reply.size();
        }
        tvQuestionDetailCount.setText("回答："+num);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        questionDetailBean = gson.fromJson(response,QuestionDetailBean.class);
    }


    private void initView() {
        tvMallTitle = (TextView) findViewById(R.id.tv_mall_title);
        tvMallTitle.setText(nickName);
        btnBack = (Button) findViewById(R.id.btn_back);

        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);

        //listview
        lvReplyList = (ListView) findViewById(R.id.lv_reply_list);

        btnMyReply = (Button) findViewById(R.id.btn_my_reply);

        tvQuestionDetailPrice = (TextView) findViewById(R.id.tv_question_detail_price);
        tvQuestionDetailContent = (TextView) findViewById(R.id.tv_question_detail_content);
        tvQuestionDetailTime = (TextView) findViewById(R.id.tv_question_detail_time);
        tvQuestionDetailCount = (TextView) findViewById(R.id.tv_question_detail_count);
    }
    private void initClickListener() {
        btnBack.setOnClickListener(this);
        refreshView.setOnRefreshListener(this);
        btnMyReply.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_my_reply://我来回答按钮
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())){
                    Toast.makeText(QuestionDetailActivity.this,"请先登录!",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(QuestionDetailActivity.this,QuestionEditAnswerActivity.class);
                    intent.putExtra("title",questionTitle);
                    intent.putExtra("id",pid);
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件刷新完毕了哦！
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
//                pager++;
//                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        initData();
//        getDataFromServer();
    }
}
