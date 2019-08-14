package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
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
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.PersonalCouponAdapter;
import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalCoupon;
import com.example.zhanghao.woaisiji.utils.SpacesItemDecoration;
import com.google.gson.Gson;
import com.example.network.utils.MGson;

import java.util.HashMap;

public class PersonalCouponActivity extends BaseActivity implements PersonalCouponAdapter.ItemListener<PersonalCouponBean> {

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
        initListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null)
            return;
        String store_id = intent.getStringExtra("store_id");
        if (StringUtils.isTrimEmpty(store_id))
            return;
        this.store_id = store_id;
        initData();
    }

    private void initListener() {

    }

    @Override
    protected void onDestroy() {
        store_id = null;
        super.onDestroy();
    }

    private void initData() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_MY_PERSONAL_INFO_MY_COUPON_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                Log.e("tag", "tags" + response);
                RespPersonalCoupon respPersonalCoupon = null;
                try {
                    respPersonalCoupon = gson.fromJson(response, RespPersonalCoupon.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (respPersonalCoupon != null) {
                    if (respPersonalCoupon.getCode() == 200) {
                        if (respPersonalCoupon.getData() != null && respPersonalCoupon.getData().size() > 0) {
                            personalCouponAdapter.setNewDataSource(respPersonalCoupon.getData());
                            personalCouponAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PersonalCouponActivity.this, "暂无优惠券数据", Toast.LENGTH_SHORT).show();
                        }
                    } /*else {
                        Toast.makeText(PersonalCouponActivity.this, "暂无优惠券数据", Toast.LENGTH_SHORT).show();
                        return;
                    }*/
                }else {
                    Toast.makeText(PersonalCouponActivity.this, "暂无优惠券数据", Toast.LENGTH_SHORT).show();
                    return;
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
                    params.put("uid", (WoAiSiJiApp.getUid()));
                    /*params.put("pageno", "1");
                    params.put("pagesize", "50");*/
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    private void initView() {
        rl_title_bar_view_root = (RelativeLayout) findViewById(R.id.rl_title_bar_view_root);
//        rl_title_bar_view_root.setBackgroundColor(ContextCompat.getColor(PersonalCouponActivity.this, R.color.white));
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("优惠券");
        tv_title_bar_view_centre_title.setTextColor(ContextCompat.getColor(PersonalCouponActivity.this, R.color.black));
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
//        iv_title_bar_view_left_left.setImageResource(R.mipmap.black_right);
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
        personalCouponAdapter.setListener(this);
        recyclerView_personal_coupon_show_data.addItemDecoration(new SpacesItemDecoration(30)); //设置条目的间距
    }

    @Override
    public void onItemClick(View itemView, PersonalCouponBean personalCouponBean) {
        Intent intent = new Intent(this, SliverIntegralStoreDetail.class);
        intent.putExtra("IntentSliverDetailCommodityID", personalCouponBean.getStore_id());
        intent.putExtra("store_data",MGson.toJson(personalCouponBean));
        intent.putExtra("type","0");
        Intent in = getIntent();
        int jump = in.getIntExtra("jump", 0);
        boolean isSilver = in.getBooleanExtra("isSilver", false);
        intent.putExtra("isSilver",isSilver);
        if (StringUtils.isTrimEmpty(store_id)) {
            ActivityUtils.startActivity(intent);
        }else
            setResult(111,intent);
        finish();
    }
}
