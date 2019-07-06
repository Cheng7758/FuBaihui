package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.MyAnswerAdapter;
import com.example.zhanghao.woaisiji.bean.forum.QuestionBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.SpUtils;
import com.example.zhanghao.woaisiji.utils.TimeFormatUtil;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
public class QuestionsMyQuestionActivity extends Activity
        implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    //    private SiJiWenDaListView blvMyQuestions;
    private PullToRefreshLayout refreshView;
    private ImageView back;
    private BaseListView blvMyQuestions;


    private QuestionBean questionBean;
    private List<QuestionBean.ListBean> questionLists;
    private MyAnswerAdapter myAnswerAdapter;
    private int pager = 1;
    private boolean isFirst = true;
    private MyQuestionAdapter myQuestionAdapter;
    private LinearLayout llMyAnswerAdoptStatus;

    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        initView();
        responseClickListener();
        initData();
    }

    private void responseClickListener() {
        back.setOnClickListener(this);
        refreshView.setOnRefreshListener(this);
    }

    private void initView() {

        //后退
        back = (ImageView) findViewById(R.id.iv_question_answer_back);
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);

        //listview每一栏的状态
        blvMyQuestions = (BaseListView) findViewById(R.id.blv_my_questions);
    }

    protected void initData() {
//        initList();
        questionLists = new ArrayList<>();
        myQuestionAdapter = new MyQuestionAdapter();
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
                        if (isFirst) {
                            blvMyQuestions.setAdapter(myQuestionAdapter);
                            isFirst = false;
                        } else {
                            myQuestionAdapter.notifyDataSetChanged();
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
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("p", String.valueOf(pager));
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
        switch (view.getId()) {
            case R.id.iv_question_answer_back:
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


    //适配器
    class MyQuestionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return questionLists.size();
        }

        @Override
        public QuestionBean.ListBean getItem(int position) {
            return questionLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(QuestionsMyQuestionActivity.this).inflate(R.layout.question_my_answer_item, null);
                viewHolder.tvMyAnswerTitle = (TextView) convertView.findViewById(R.id.tv_my_answer_title);
                viewHolder.tvMyAnswerAdoptStatus = (TextView) convertView.findViewById(R.id.tv_my_answer_adopt_status);
                viewHolder.tvMyAnswerNum = (TextView) convertView.findViewById(R.id.tv_my_answer_num);
                viewHolder.tvMyAnswerTime = (TextView) convertView.findViewById(R.id.tv_my_answer_time);
                viewHolder.llMyAnswerAdoptStatus =  (LinearLayout) convertView.findViewById(R.id.ll_my_answer_adopt_status);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final QuestionBean.ListBean item = getItem(position);
            viewHolder.tvMyAnswerTitle.setText(item.title);

            if (!item.is_cnyj.equals("0")){
                viewHolder.tvMyAnswerAdoptStatus.setText("已解决");
                viewHolder.tvMyAnswerAdoptStatus.setTextColor(Color.GREEN);
            }else {
                viewHolder.tvMyAnswerAdoptStatus.setText("待采纳");
            }


            viewHolder.tvMyAnswerNum.setText("回答："+item.num);
            viewHolder.tvMyAnswerTime.setText(TimeFormatUtil.format(item.create_time));

//            Toast.makeText(QuestionsMyQuestionActivity.this,item.id,Toast.LENGTH_SHORT).show();
//            Toast.makeText(QuestionsMyQuestionActivity.this,item.uid,Toast.LENGTH_SHORT).show();

            //待采纳的点击事件
            viewHolder.llMyAnswerAdoptStatus.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionsMyQuestionActivity.this, QuestionDetailActivity.class);
                    intent.putExtra("nickname",item.nickname);
                    intent.putExtra("pid",item.id);
//                    intent.putExtra("is_cnyj", item.is_cnyj);

//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.nickname, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.uid, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.id, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.getType_id(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.type_id, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.status, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(QuestionsMyQuestionActivity.this, item.is_cnyj, Toast.LENGTH_SHORT).show();


                    // 添加标记:去采纳
                    intent.addFlags(1);
                    startActivity(intent);
                }
            });

//        viewHolder. wenti.setText(items.get(position));
            return convertView;
        }

    }

    static class ViewHolder {
        public TextView tvMyAnswerAdoptStatus;
        public TextView tvMyAnswerNum;
        public TextView tvMyAnswerTime;
        public TextView tvMyAnswerTitle;
        public LinearLayout llMyAnswerAdoptStatus;
    }
}
