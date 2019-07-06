package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.zhanghao.woaisiji.adapter.PersonalCouponAdapter;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalCoupon;
import com.google.gson.Gson;

import java.util.HashMap;

public class PersonalCouponActivity extends BaseActivity {

    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;
    private RelativeLayout rl_title_bar_view_root;

    private RecyclerView recyclerView_personal_coupon_show_data;
    private PersonalCouponAdapter personalCouponAdapter;

    private String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_coupon);
        store_id = getIntent().getStringExtra("store_id");
        initData();
        initView();
    }

    private void initData() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MY_COUPON_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespPersonalCoupon respPersonalCoupon = gson.fromJson(response, RespPersonalCoupon.class);
                Log.e("--youhui", respPersonalCoupon.getData()+"");
                if (respPersonalCoupon.getCode() == 200) {
                    if (respPersonalCoupon.getData() != null && respPersonalCoupon.getData().size() > 0) {
                        personalCouponAdapter.setNewDataSource(respPersonalCoupon.getData());
                        personalCouponAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PersonalCouponActivity.this, "暂无优惠券数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonalCouponActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                if (!TextUtils.isEmpty(store_id))
                    params.put("store_id", store_id);
                else {
                    params.put("uid", (WoAiSiJiApp.getUid()));
                    params.put("pageno", "1");
                    params.put("pagesize", "50");
                }
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(registerRequest);

    }

    private void initView() {
        rl_title_bar_view_root = (RelativeLayout) findViewById(R.id.rl_title_bar_view_root);
        rl_title_bar_view_root.setBackgroundColor(ContextCompat.getColor(PersonalCouponActivity.this, R.color.white));
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("优惠券");
        tv_title_bar_view_centre_title.setTextColor(ContextCompat.getColor(PersonalCouponActivity.this, R.color.black));
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setImageResource(R.mipmap.black_right);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView_personal_coupon_show_data = (RecyclerView) findViewById(R.id.recyclerView_personal_coupon_show_data);
        recyclerView_personal_coupon_show_data.setLayoutManager(new LinearLayoutManager(this));
        personalCouponAdapter = new PersonalCouponAdapter(this);
        recyclerView_personal_coupon_show_data.setAdapter(personalCouponAdapter);
    }
}
