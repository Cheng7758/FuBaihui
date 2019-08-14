package com.example.zhanghao.woaisiji.activity.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.example.zhanghao.woaisiji.adapter.my.MyBillingAdapter;
import com.example.zhanghao.woaisiji.bean.billing.BillingDetailsBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.widget.DividerGridItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BillingDetailsActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private XRecyclerView mXRlv;
    private MyBillingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        initView();
        initData();
    }

    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        ivRegisterBack.setVisibility(View.VISIBLE);
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRegisterTitle = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tvRegisterTitle.setText("账单明细");
        mXRlv = (XRecyclerView) findViewById(R.id.billing_xrlv);
        mXRlv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyBillingAdapter(BillingDetailsActivity.this);
        mXRlv.setAdapter(mAdapter);
        mXRlv.setLoadingListener(BillingDetailsActivity.this);
        mXRlv.addItemDecoration(new DividerGridItemDecoration(BillingDetailsActivity.this));
    }

    private void initData() {
        // 访问服务器数据库，获取数据
        StringRequest obtainBillRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_DETAIL_BILL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                BillingDetailsBean detailsBean = null;
                try {
                    detailsBean = gson.fromJson(response, BillingDetailsBean.class);
                } catch (Exception e) {
                    Toast.makeText(BillingDetailsActivity.this, "暂无账单数据", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (detailsBean != null) {
                    if (detailsBean.getCode() == 200 && detailsBean.getData() != null) {
                        mAdapter.addData(detailsBean.getData());
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
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(obtainBillRequest);
    }

    @Override
    public void onRefresh() {
        initData();
        mXRlv.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        mXRlv.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
