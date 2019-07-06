package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.serverdata.FriendsListData;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
public class QuestionsActivity extends Activity implements View.OnClickListener{

    private final static String[] boinsNum = {"100","500","1000","2000","3000","4000","5000","6000"};
    private List<String> boinList ;
    private EditText edQuestionTitle;
    private TextView tvQuestionNumLimit;
    private EditText et_wowen_buchong;
    private Button bt_back;
    private ImageView ivAddPicture;
    private ImageView ivRewardBoins;
    private ImageView ivQuestionAnswerBack;
    private TextView tvQuestionAnswerTitle;
    private Button btnTravelRelease;
    private LinearLayout llQuestionSetBoins;
    private GridView gvQuestionBoinNum;
    private TextGridViewAdapter textGridViewAdapter;
    private AlterResultBean resultBean;

    private String title;
    private String typeId = "1";
    private String price = "0";
    private TextView tvMyHaveBoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        FriendsListData friendsListData = new FriendsListData();
        friendsListData.obtainDataFromServer();
        initView();

        initClickListener();

        initData();
        initGridView();

    }

    private void initGridView() {
        boinList = new ArrayList<>();
        textGridViewAdapter = new TextGridViewAdapter();
        for (int i=0;i<boinsNum.length;i++){
            boinList.add(boinsNum[i]);
        }

        //悬赏
        gvQuestionBoinNum.setAdapter(textGridViewAdapter);
        gvQuestionBoinNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(WoAiSiJiApp.memberShipInfos.info.silver)>Integer.parseInt(boinList.get(i))) {
                    price = boinList.get(i);
                    Toast.makeText(QuestionsActivity.this,"您设置的悬赏值为:"+price+"银A币",Toast.LENGTH_SHORT).show();
                    llQuestionSetBoins.setVisibility(View.GONE);
                }else {
                    Toast.makeText(QuestionsActivity.this,"您的银A币不足！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initClickListener() {
        //返回
        ivQuestionAnswerBack.setOnClickListener(this);
        ivRewardBoins.setOnClickListener(this);
        ivAddPicture.setOnClickListener(this);
        btnTravelRelease.setOnClickListener(this);
    }

    private void initView() {
        // 返回
        ivQuestionAnswerBack = (ImageView) findViewById(R.id.iv_question_answer_back);
        tvQuestionAnswerTitle = (TextView) findViewById(R.id.tv_question_answer_title);
        tvQuestionAnswerTitle.setText("提问");
        btnTravelRelease = (Button) findViewById(R.id.btn_travel_release);
        btnTravelRelease.setVisibility(View.VISIBLE);

        llQuestionSetBoins = (LinearLayout) findViewById(R.id.ll_question_set_boins);
        gvQuestionBoinNum = (GridView) findViewById(R.id.gv_question_boin_num);
        tvMyHaveBoins = (TextView) findViewById(R.id.tv_my_have_boins);

        //你的问题
        edQuestionTitle = (EditText) findViewById(R.id.et_question_title);
        //字的长短比例
        tvQuestionNumLimit = (TextView) findViewById(R.id.tv_question_num_limit);
        //补充说明
        et_wowen_buchong = (EditText) findViewById(R.id.et_wowen_buchong);

        //提交
        ivAddPicture = (ImageView) findViewById(R.id.iv_add_picture);
        //银币
        ivRewardBoins = (ImageView) findViewById(R.id.iv_reward_boins);
    }

    private void initData() {
        tvMyHaveBoins.setText("您可用的银A币："+WoAiSiJiApp.memberShipInfos.info.silver);
        initEditText();
    }

    //initEditText()点击文本输入框清楚预设文字
    private void initEditText() {
        edQuestionTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et=(EditText)v;
                if (!hasFocus) {// 失去焦点v
                    et.setHint(et.getTag().toString());
                } else {
                    String hint=et.getHint().toString();
                    et.setTag(hint);//保存预设字
                    et.setHint(null);
                }
            }
        });

        et_wowen_buchong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et=(EditText)v;
                if (!hasFocus) {// 失去焦点
                    et.setHint(et.getTag().toString());
                } else {
                    String hint=et.getHint().toString();
                    et.setTag(hint);//保存预设字
                    et.setHint(null);
                }
            }
        });

        //动态显示字的数量
        edQuestionTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvQuestionNumLimit.setText( (0+charSequence.length()) +"/100" );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_question_answer_back:
                finish();
                break;
            case R.id.iv_reward_boins://悬赏按钮
                llQuestionSetBoins.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_add_picture:
                break;
            case R.id.btn_travel_release://提交按钮
                title = edQuestionTitle.getText().toString().trim();
                if (!TextUtils.isEmpty(title)){
                    releaseQuestionToServer();
                    finish();
                }
                break;
        }
    }

    private void releaseQuestionToServer() {
        StringRequest releaseQuestionRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PROBLEM_ADD_QUESTION,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("title",title);
                params.put("price",price);
                params.put("type_id",typeId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(releaseQuestionRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        resultBean = gson.fromJson(response,AlterResultBean.class);
    }

    public class TextGridViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return boinList.size();
        }

        @Override
        public String getItem(int i) {
            return boinList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null){
                view = View.inflate(QuestionsActivity.this,R.layout.question_grid_view_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tvQuestionValue = (TextView) view.findViewById(R.id.tv_question_value);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tvQuestionValue.setText(getItem(i));
            if (Integer.parseInt(WoAiSiJiApp.memberShipInfos.info.silver)>Integer.parseInt(getItem(i))){
                viewHolder.tvQuestionValue.setBackgroundResource(R.color.value01);
                viewHolder.tvQuestionValue.setTextColor(Color.WHITE);

            }else {
                viewHolder.tvQuestionValue.setBackgroundResource(R.color.value02);
                viewHolder.tvQuestionValue.setTextColor(Color.GRAY);

            }


            return view;
        }
    }

    static class ViewHolder{
        public TextView tvQuestionValue;
    }

}
