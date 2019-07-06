package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.MyAnswerAdapter;
import com.example.zhanghao.woaisiji.bean.forum.MyAnswerBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.SiJiWenDaListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
public class QuestionsMyAnswerActivity extends Activity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{
    private SiJiWenDaListView lvMyAnswer;
    private ImageView ivQuestionAnswerBack;
    private PullToRefreshLayout refreshView;
    private TextView tvQuestionAnswerTitle;
    private MyAnswerBean answerBean;
    private List<MyAnswerBean.ListBean> answerLists;
    private boolean isFirst = true;
    private MyAnswerAdapter myAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);
        initView();
        responseClickListener();
        initData();
    }

    private void responseClickListener() {
        ivQuestionAnswerBack.setOnClickListener( this);
        refreshView.setOnRefreshListener(this);
    }

    private void initView() {

        lvMyAnswer = (SiJiWenDaListView) findViewById(R.id.lv_my_answer);
        //后退
        ivQuestionAnswerBack = (ImageView) findViewById(R.id.iv_question_answer_back);
        tvQuestionAnswerTitle = (TextView) findViewById(R.id.tv_question_answer_title);
        tvQuestionAnswerTitle.setText("我的回答");
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
//        pullToRefreshLayout.setOnRefreshListener(new wenda_WoDeHuiDa());
    }

    protected void initData() {
        myAnswerAdapter = new MyAnswerAdapter(this);
//        lvMyAnswer.setAdapter(myAnswerAdapter);
        answerLists = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        StringRequest myAnswerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PROBLEM_MY_ANSWER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (answerBean.code == 200){
                    if (answerBean.list != null){
                        for (MyAnswerBean.ListBean bean : answerBean.list){
                            answerLists.add(bean);
                        }
                        myAnswerAdapter.myAnswerLists = answerLists;
                        if (isFirst){
                            lvMyAnswer.setAdapter(myAnswerAdapter);
                            isFirst = false;
                        }else {
                            myAnswerAdapter.notifyDataSetChanged();
                        }
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
                params.put("uid", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(myAnswerRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        answerBean = gson.fromJson(response,MyAnswerBean.class);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_question_answer_back:
//                startActivity(new Intent(LoveDriverMyAnswerActivity.this,LoveDriverQuestionAnswerActivity.class));
                finish();
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
}
