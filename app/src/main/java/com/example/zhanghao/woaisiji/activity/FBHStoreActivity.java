package com.example.zhanghao.woaisiji.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoActivity;
import com.example.zhanghao.woaisiji.adapter.FBHStoreAdapter;
import com.example.zhanghao.woaisiji.adapter.FBHStoreDataDetailAdapter;
import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.mall.CategoryGoodPager2;
import com.example.zhanghao.woaisiji.base.mall.FBHStoreGoodPager;
import com.example.zhanghao.woaisiji.base.mall.FBHStorePagerAdapter;
import com.example.zhanghao.woaisiji.base.mall.MallViewPagerAdapter;
import com.example.zhanghao.woaisiji.base.mall.RecommendGoodPager2;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.example.zhanghao.woaisiji.resp.RespFBHStoreCategory;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.view.BaseQuickLoadMoreView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FBHStoreActivity extends BaseActivity {

    /**
     * TitleBar
     */
    private LinearLayout ll_title_bar_view_search_root;
    private EditText et_title_bar_view_search;
    private ImageView iv_title_bar_view_left_left , iv_title_bar_view_do_search;
    private TextView tv_fbh_store_no_data;

    //分类集合
    private List<RespFBHStoreCategory.FBHStoreCategory> mCategoryListData ;
    private ListView lv_fbh_store_category;
    private FBHStoreAdapter fbhStoreCategoryAdapter ;
    //展示详情数据
    private RecyclerView gv_fbh_store_category_data;
    private RespFBHStoreCategory respFBHStoreCategory;
    private FBHStoreDataDetailAdapter fbhStoreDataDetailAdapter ;
    private List<RespCommodityList.CommodityDataDetail> commodityDataDetailList;

    private int fromType = 0;
    private int currentPage= 1;
    private String currentCategoryId="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        fromType = getIntent().getIntExtra("fromType" , 0);
        setContentView(R.layout.activity_fbh_store);

        mCategoryListData = new ArrayList<>();
        commodityDataDetailList = new ArrayList<>();

        initView();
        initListener();
        getCategoryDataFromServer();
    }

    private void initView() {
        ll_title_bar_view_search_root = (LinearLayout) findViewById(R.id.ll_title_bar_view_search_root);
        et_title_bar_view_search = (EditText) findViewById(R.id.et_title_bar_view_search);
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_do_search = (ImageView) findViewById(R.id.iv_title_bar_view_do_search);
        ll_title_bar_view_search_root.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);

        tv_fbh_store_no_data = (TextView) findViewById(R.id.tv_fbh_store_no_data);
        lv_fbh_store_category = (ListView) findViewById(R.id.lv_fbh_store_category);
        fbhStoreCategoryAdapter = new FBHStoreAdapter(FBHStoreActivity.this, mCategoryListData);
        lv_fbh_store_category.setAdapter(fbhStoreCategoryAdapter);

        gv_fbh_store_category_data = (RecyclerView) findViewById(R.id.gv_fbh_store_category_data);

        gv_fbh_store_category_data.setLayoutManager(new GridLayoutManager(FBHStoreActivity.this,2));
        fbhStoreDataDetailAdapter = new FBHStoreDataDetailAdapter(FBHStoreActivity.this, commodityDataDetailList);
        fbhStoreDataDetailAdapter.setLoadMoreView(new BaseQuickLoadMoreView());
        gv_fbh_store_category_data.setAdapter(fbhStoreDataDetailAdapter);
    }

    private void initListener() {
        //添加商品界面
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_title_bar_view_do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywords =et_title_bar_view_search.getText().toString();
                Intent intent = new Intent(FBHStoreActivity.this,JoinAutoActivity.class);
                intent.putExtra("SearchWord",keywords);
                startActivity(intent);
            }
        });
        //listView的itme点击事件
        lv_fbh_store_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                currentPage = 1;
                currentCategoryId = mCategoryListData.get(position).getId();
                getDataDetailFromServer();
                fbhStoreCategoryAdapter.setSelectPos(position);
            }
        });

        fbhStoreDataDetailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (currentPage!=0)
                    currentPage++;
                getDataDetailFromServer();
            }
        },gv_fbh_store_category_data);
    }

    /**
     * 获取分类数据
     */
    public void getCategoryDataFromServer() {
        StringRequest reqFBHStoreCategory = new StringRequest(Request.Method.GET, ServerAddress.URL_COMMODITY_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                PrefUtils.setString(FBHStoreActivity.this,"FBHStoreCategoryCache",response);
                Gson gson = new Gson();
                respFBHStoreCategory = gson.fromJson(response, RespFBHStoreCategory.class);
                //判断code是否等于200
                if (respFBHStoreCategory.getCode() == 200 && respFBHStoreCategory.getData().size() > 0) {
                    mCategoryListData.clear();
                    mCategoryListData.addAll(respFBHStoreCategory.getData());
                    fbhStoreCategoryAdapter.notifyDataSetChanged();//分类刷新
                    //获取第一种分类
                    currentCategoryId = mCategoryListData.get(0).getId();
                    getDataDetailFromServer();
                }
            }
            //请求网络失败
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String categoryCache = PrefUtils.getString(FBHStoreActivity.this,"FBHStoreCategoryCache","");
                if (TextUtils.isEmpty(categoryCache))
                    return;
                Gson gson = new Gson();
                respFBHStoreCategory = gson.fromJson(categoryCache, RespFBHStoreCategory.class);
                //判断code是否等于200
                if (respFBHStoreCategory.getCode() == 200 && respFBHStoreCategory.getData().size() > 0) {
                    mCategoryListData.clear();
                    mCategoryListData.addAll(respFBHStoreCategory.getData());
                    //listView适配器
                    fbhStoreCategoryAdapter.notifyDataSetChanged();
                }
            }
        });
        //添加网络请求队列
        WoAiSiJiApp.mRequestQueue.add(reqFBHStoreCategory);
    }

    /**
     * 获取商品列表数据
     */
    private void getDataDetailFromServer() {
        showProgressDialog();
        StringBuffer url = new StringBuffer(ServerAddress.URL_FBH_COMMODITY_DATA_LIST);
        url.append("?cid="+currentCategoryId+"&page="+currentPage+"&type="+fromType+"&store_id="+fromType+"&row_num=10");
        String temp = url.toString() ;
        Log.d("aaa",temp) ;
        //没有数据提示a
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fbhStoreDataDetailAdapter.loadMoreComplete();
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespCommodityList respCommodityList = gson.fromJson(response, RespCommodityList.class);
                if (respCommodityList.getCode() == 200) {
                    if (respCommodityList.getData() == null) {
                        tv_fbh_store_no_data.setVisibility(View.GONE);
                        if (currentPage==1){
                            tv_fbh_store_no_data.setVisibility(View.VISIBLE);
                            fbhStoreDataDetailAdapter.setNewData(new ArrayList<RespCommodityList.CommodityDataDetail>());
                            fbhStoreDataDetailAdapter.loadMoreFail();
                            fbhStoreDataDetailAdapter.loadMoreEnd(true);
                        }else
                            fbhStoreDataDetailAdapter.loadMoreEnd();
                        return;
                    }
                    if (currentPage==1)
                        fbhStoreDataDetailAdapter.setNewData(respCommodityList.getData());
                    else{
                        fbhStoreDataDetailAdapter.addData(respCommodityList.getData());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(FBHStoreActivity.this, "未获取到服务器商城数据", Toast.LENGTH_LONG).show();
            }
        });
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

}
