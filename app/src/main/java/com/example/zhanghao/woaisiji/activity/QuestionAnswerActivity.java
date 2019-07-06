package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.WenDaAdapter;
import com.example.zhanghao.woaisiji.bean.forum.QuestionBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.utils.DpTransPx;
import com.example.zhanghao.woaisiji.view.SiJiWenDaListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionAnswerActivity extends AppCompatActivity
        implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private boolean isFirstIn = true;
    private PullToRefreshLayout ptrl;
    private SiJiWenDaListView listView;
    private Button back;
    private String SIJIWENDA_WOWEN = "sijiwenda_wowen";
    private ImageView iv_xiaoxi;

    private int pager = 1;
    private QuestionBean questionBean;
    private List<QuestionBean.ListBean> questionLists;
    private boolean isFirst = true;
    private boolean isHighPrice = false;
    private boolean isSticky = false;
    private WenDaAdapter adapter;
    private RadioGroup rgQuestionAnswerIndicator;
    private ImageView ivGreenRectangle;

    private RelativeLayout.LayoutParams params;
    private RadioGroup rgQuestionAnswerType;

    private RadioButton rbQuestions;
    private RadioButton rbMyAnswer;
    private RadioButton rbMyQuestions;
    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_question_answer);


        initView();
        initData();
        initListView();

    }

    //初始化控件
    private void initView() {
        //消息
        iv_xiaoxi = (ImageView) findViewById(R.id.iv_xiaoxi);
        //后退
//        back = (Button) findViewById(R.id.btn_back);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        //提问
        rbQuestions = (RadioButton) findViewById(R.id.rb_questions);
        //我的回答
        rbMyAnswer = (RadioButton) findViewById(R.id.rb_my_answer);
        //我的提问
        rbMyQuestions = (RadioButton) findViewById(R.id.rb_my_questions);
        //第三方
        ptrl = (PullToRefreshLayout) findViewById(R.id.refresh_view);

        //司机问答listview
        listView = (SiJiWenDaListView) findViewById(R.id.lv_wenda);


        // 提问  我的回答  我的提问
        rgQuestionAnswerType = (RadioGroup) findViewById(R.id.rg_question_answer_type);

        // 最新  推荐  高悬赏
        rgQuestionAnswerIndicator = (RadioGroup) findViewById(R.id.rg_question_answer_indicator);
        ivGreenRectangle = (ImageView) findViewById(R.id.iv_green_rectangle);
        // 最新

    }

    private void initData() {
        //后退点击事件
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SiJiWenDa.this, MainActivity.class));
                finish();
            }
        });
        ptrl.setOnRefreshListener(this);

        rbQuestions.setOnClickListener(this);
        rbMyAnswer.setOnClickListener(this);
        rbMyQuestions.setOnClickListener(this);

        params = (RelativeLayout.LayoutParams) ivGreenRectangle.getLayoutParams();
        //3个tab
        rgQuestionAnswerIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                int position = 0;
                switch (checked) {
                    // 最新
                    case R.id.rb_newest_indicator:
                        isSticky = false;
                        isHighPrice = false;
                        position = 0;
                        break;
                    // 推荐
                    case R.id.rb_recommend_indicator:
                        isSticky = true;
                        isHighPrice = false;
                        position = 1;
                        break;
                    // 高悬赏
                    case R.id.rb_high_price_indicator:
                        isHighPrice = true;
                        isSticky = false;
                        position = 2;
                        break;
                }
                pager = 1;
                isFirst = true;
                params.leftMargin = DpTransPx.Dp2Px(QuestionAnswerActivity.this, 80) * position;
                ivGreenRectangle.setLayoutParams(params);
                questionLists = new ArrayList<>();
                getDataFromServer();
            }
        });


    }


    //listView的适配
    private void initListView() {

        questionLists = new ArrayList<>();
        adapter = new WenDaAdapter(QuestionAnswerActivity.this);
        // 获取服务器数据
        getDataFromServer();

    }

    private void getDataFromServer() {
        StringRequest questionRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PROBLEM_INDEX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (questionBean.code == 200) {
                    if (questionBean.list != null) {
                        for (QuestionBean.ListBean bean : questionBean.list) {
                            questionLists.add(bean);
                        }
//                        adapter.questionLists.clear();
                        adapter.questionLists = questionLists;
                        if (isFirst) {
                            listView.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p", String.valueOf(pager));
                if (isSticky == true) {
                    params.put("is_sticky", String.valueOf(1));
                }
                if (isHighPrice == true) {
                    params.put("gxs", String.valueOf(1));
                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(questionRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        questionBean = gson.fromJson(response, QuestionBean.class);
    }

    @Override
    public void onClick(View view) {
//
        if (TextUtils.isEmpty(WoAiSiJiApp.getUid())){
            Toast.makeText(QuestionAnswerActivity.this,"请先登录!",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {

            case R.id.rb_questions:
                //提问页面
                startActivity(new Intent(QuestionAnswerActivity.this, QuestionsActivity.class));
                break;
            //我的回答页面
            case R.id.rb_my_answer:
                startActivity(new Intent(QuestionAnswerActivity.this, QuestionsMyAnswerActivity.class));
                break;
            //我的提问页面
            case R.id.rb_my_questions:
                startActivity(new Intent(QuestionAnswerActivity.this, QuestionsMyQuestionActivity.class));
                break;
            default:
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
                ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
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
                pager++;
                getDataFromServer();
                ptrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isFirst = true;
        initListView();
    }
}


