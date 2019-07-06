package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.PersonalMyRecommendationAdapter;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalMyRecommendation;
import com.example.zhanghao.woaisiji.tools.CircleTransform;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalMyRecommendationActivity extends BaseActivity {
    private RelativeLayout rl_title_bar_view_root;
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private ImageView iv_personal_my_recommendation_hv;
    private TextView tv_personal_my_recommendation_nick;
    private LinearLayout ll_personal_my_recommendation_gold_integral,ll_personal_my_recommendation_balance,
            ll_personal_my_recommendation_merchant_gold_integral,ll_personal_my_recommendation_silver_integral;
    private TextView tv_personal_my_recommendation_gold_integral,tv_personal_my_recommendation_balance,
            tv_personal_my_recommendation_merchant_gold_integral,tv_personal_my_recommendation_silver_integral;

    private RecyclerView recyclerview_personal_my_recommendation_show_data;
    private PersonalMyRecommendationAdapter personalMyRecommendationAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_my_recommendation);
        
        initData();
        initTitleBar();
        initView();
    }

    private void initData() {
        StringRequest imageRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_RECOMMENDATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespPersonalMyRecommendation respPersonalMyRecommendation = gson.fromJson(response, RespPersonalMyRecommendation.class);
                if (respPersonalMyRecommendation.getCode()==200){
                    if (respPersonalMyRecommendation.getData()!=null && respPersonalMyRecommendation.getData().size()>0){
                        personalMyRecommendationAdapter.setNewDataSource(respPersonalMyRecommendation.getData());
                        tv_personal_my_recommendation_gold_integral.setText(respPersonalMyRecommendation.getData().get(0).get_score());
                        tv_personal_my_recommendation_balance.setText(respPersonalMyRecommendation.getData().get(0).get_balance());
                        tv_personal_my_recommendation_merchant_gold_integral.setText(respPersonalMyRecommendation.getData().get(0).get_store_score());
                        tv_personal_my_recommendation_silver_integral.setText(respPersonalMyRecommendation.getData().get(0).get_silver());
                    }else{
                        Toast.makeText(PersonalMyRecommendationActivity.this,"暂无推荐信息",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(PersonalMyRecommendationActivity.this,"暂无推荐信息",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("pageno","1");
                params.put("pagesize","100");
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(imageRequest);

    }

    private void initView() {
        iv_personal_my_recommendation_hv = (ImageView) findViewById(R.id.iv_personal_my_recommendation_hv);
        Picasso.with(PersonalMyRecommendationActivity.this).load(WoAiSiJiApp.getCurrentUserInfo().getPic())
                .error(R.drawable.icon_loading).transform(new CircleTransform(PersonalMyRecommendationActivity.this))
                .into(iv_personal_my_recommendation_hv);
        tv_personal_my_recommendation_nick = (TextView) findViewById(R.id.tv_personal_my_recommendation_nick);
        tv_personal_my_recommendation_nick.setText(WoAiSiJiApp.getCurrentUserInfo().getNickname());
        ll_personal_my_recommendation_gold_integral = (LinearLayout) findViewById(R.id.ll_personal_my_recommendation_gold_integral);
        ll_personal_my_recommendation_balance = (LinearLayout) findViewById(R.id.ll_personal_my_recommendation_balance);
        ll_personal_my_recommendation_merchant_gold_integral = (LinearLayout) findViewById(R.id.ll_personal_my_recommendation_merchant_gold_integral);
        ll_personal_my_recommendation_silver_integral = (LinearLayout) findViewById(R.id.ll_personal_my_recommendation_silver_integral);

        tv_personal_my_recommendation_gold_integral = (TextView) findViewById(R.id.tv_personal_my_recommendation_gold_integral);
        tv_personal_my_recommendation_balance = (TextView) findViewById(R.id.tv_personal_my_recommendation_balance);
        tv_personal_my_recommendation_merchant_gold_integral = (TextView) findViewById(R.id.tv_personal_my_recommendation_merchant_gold_integral);
        tv_personal_my_recommendation_silver_integral = (TextView) findViewById(R.id.tv_personal_my_recommendation_silver_integral);

        recyclerview_personal_my_recommendation_show_data = (RecyclerView) findViewById(R.id.recyclerview_personal_my_recommendation_show_data);
        recyclerview_personal_my_recommendation_show_data.setLayoutManager(new LinearLayoutManager(PersonalMyRecommendationActivity.this));
        personalMyRecommendationAdapter = new PersonalMyRecommendationAdapter(PersonalMyRecommendationActivity.this);
        recyclerview_personal_my_recommendation_show_data.setAdapter(personalMyRecommendationAdapter);
        personalMyRecommendationAdapter.setNewDataSource(new ArrayList<RespPersonalMyRecommendation.PersonalMyRecommendationBean>());
    }
    private void initTitleBar() {
        rl_title_bar_view_root = (RelativeLayout) findViewById(R.id.rl_title_bar_view_root);
        rl_title_bar_view_root.setBackgroundColor(ContextCompat.getColor(PersonalMyRecommendationActivity.this,R.color.white));
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setImageResource(R.mipmap.black_right);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setTextColor(ContextCompat.getColor(PersonalMyRecommendationActivity.this,R.color.black));
        tv_title_bar_view_centre_title.setText("我的推荐");
    }
}
