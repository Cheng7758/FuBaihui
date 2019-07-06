package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.GridViewAdapter;
import com.example.zhanghao.woaisiji.base.mall.GridViewItemAdapter;
import com.example.zhanghao.woaisiji.bean.DriverShoppingBean;
import com.example.zhanghao.woaisiji.bean.SearchProductBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.refresh.PullableGridView;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zzz on 2016/11/25.
 */

public class SearchProductActivity extends Activity implements PullToRefreshLayout.OnRefreshListener{

    private Button btnBack;
    private TextView tvMallTitle;
    private PullToRefreshLayout refreshView;
    private PullableGridView gvLuBricating;


    private StringRequest stringRequest;
    List<SearchProductBean.ListBean> searchProductLists;
    private int type =1;
    private int pid;
    private String url = ServerAddress.URL_DEIVERSHOPPING;
    public final static String ID = "id";
    public final static String MALLTYPE = "mall_type";
    private int pager = 1;
    private boolean isFirst = true;
    private boolean isCache = false;
    private GridViewItemAdapter gridViewItemAdapter;
    private DriverShoppingBean driverShopping;
    private String keyWords;
    private SearchProductBean searchProductResult;
    private GridViewAdapter mGridViewAdapter;
    private int flags;
    String searchUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        keyWords =  getIntent().getStringExtra("keywords");
        flags = getIntent().getFlags();

        switch (flags){
            case 1:
                searchUrl = ServerAddress.URL_PRODUCT_SEARCH;
                type = 1;
                break;
            case 2:
                searchUrl = ServerAddress.URL_EXCHANGE_PRODUCT_SEARCH;
                type = 2;
                break;
            case 3:
                searchUrl = ServerAddress.URL_CURING_PRODUCT_SEARCH;
                type = 3;
                break;
        }

        initView();
        refreshView.setOnRefreshListener(this);
        obtainServerData();
    }

    private void obtainServerData() {
        searchProductLists = new ArrayList<>();
        mGridViewAdapter = new GridViewAdapter(SearchProductActivity.this);

        getDataFromServer();

        gvLuBricating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String kucun = searchProductLists.get(position).getNumber();
                if (kucun.equals("0")) {
                    Toast.makeText(SearchProductActivity.this, "没有库存喽哟！ 亲～", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchProductActivity.this, ProductDetailActivity2.class);
                    String Id = searchProductLists.get(position).getId();
                    intent.putExtra(ID, Id);
                    intent.putExtra("type", type);
                    startActivity(intent);

                }
            }
        });
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvMallTitle = (TextView) findViewById(R.id.tv_mall_title);
        tvMallTitle.setText("搜索结果");
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        gvLuBricating = (PullableGridView) findViewById(R.id.gv_lubricating);
    }

    private void getDataFromServer() {
        if (stringRequest != null) {//你自己想想为啥，想不明白回头我给你讲为啥取消，怕数据乱。
            stringRequest.cancel();
        }
        //没有数据提示
        stringRequest = new StringRequest(Request.Method.POST, searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transServerData(response);

                if (searchProductResult.list != null) {
                    //没有数据提示
//                        Toast.makeText(SearchProductActivity.this, "没有更多数据...", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < searchProductResult.list.size(); i++) {
                        SearchProductBean.ListBean listBean = searchProductResult.list.get(i);
                        searchProductLists.add(listBean);
                    }
                    mGridViewAdapter.mSearchProductList = searchProductLists;
                    if (isFirst){
                        gvLuBricating.setAdapter(mGridViewAdapter);
                        isFirst = false;
                    }else {
                        mGridViewAdapter.notifyDataSetChanged();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchProductActivity.this, "正品商城未获取到服务器数据", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("keywords", keyWords);
                map.put("p", String.valueOf(pager));
//                map.put("pos", "2");
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
//        driverShopping = gson.fromJson(response, DriverShoppingBean.class);
        searchProductResult = gson.fromJson(response,SearchProductBean.class);
    }
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pager = 1;
                searchProductLists.clear();
                searchProductLists = new ArrayList<SearchProductBean.ListBean>();
                isFirst = true;
                getDataFromServer();
                // 千万别忘了告诉控件刷新完毕了哦！
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pager = pager + 1;
                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }
}
