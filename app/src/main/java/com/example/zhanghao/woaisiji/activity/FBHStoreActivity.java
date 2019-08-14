package com.example.zhanghao.woaisiji.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoActivity;
import com.example.zhanghao.woaisiji.adapter.FBHStoreAdapter;
import com.example.zhanghao.woaisiji.adapter.FBHStoreCouponAdapter;
import com.example.zhanghao.woaisiji.adapter.FBHStoreDataDetailAdapter;
import com.example.zhanghao.woaisiji.bean.fbh.FBHStoreCategory;
import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespAddOrder;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.example.zhanghao.woaisiji.resp.RespFBHStoreCategory;
import com.example.zhanghao.woaisiji.resp.RespPersonalCoupon;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.view.BaseQuickLoadMoreView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FBHStoreActivity extends BaseActivity {
    /**
     * TitleBar
     */
    private LinearLayout ll_title_bar_view_search_root;
    private EditText et_title_bar_view_search;
    private ImageView iv_title_bar_view_left_left, iv_title_bar_view_do_search;
    private TextView tv_fbh_store_no_data, tv_title_bar_view_centre_title;

    //分类集合
    private List<FBHStoreCategory> mCategoryListData;
    private ListView lv_fbh_store_category;
    private FBHStoreAdapter fbhStoreCategoryAdapter;
    //展示详情数据
    private RecyclerView gv_fbh_store_category_data;
    private RespFBHStoreCategory respFBHStoreCategory;

    private FBHStoreDataDetailAdapter fbhStoreDataDetailAdapter;
    private FBHStoreCouponAdapter fbhStoreCouponAdapter;

    private List<RespCommodityList.CommodityDataDetail> commodityDataDetailList;
    private List<PersonalCouponBean> personalCouponBeansList;

    private int fromType = 0;//0-FBH   1-银积分
    private int currentPage = 1;
    private String currentCategoryId = "";
    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        fromType = getIntent().getIntExtra("fromType", 0);
        setContentView(R.layout.activity_fbh_store);

        mCategoryListData = new ArrayList<>();
        commodityDataDetailList = new ArrayList<>();
        personalCouponBeansList = new ArrayList<>();

        initView();
        initListener();
        getCategoryDataFromServer();
    }

    private void initView() {
        ll_title_bar_view_search_root = (LinearLayout) findViewById(R.id.ll_title_bar_view_search_root);
        et_title_bar_view_search = (EditText) findViewById(R.id.et_title_bar_view_search);
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_do_search = (ImageView) findViewById(R.id.iv_title_bar_view_do_search);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
//        ll_title_bar_view_search_root.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setText("福百惠商城");

        tv_fbh_store_no_data = (TextView) findViewById(R.id.tv_fbh_store_no_data);
        lv_fbh_store_category = (ListView) findViewById(R.id.lv_fbh_store_category);
        fbhStoreCategoryAdapter = new FBHStoreAdapter(FBHStoreActivity.this, mCategoryListData);
        lv_fbh_store_category.setAdapter(fbhStoreCategoryAdapter);

        gv_fbh_store_category_data = (RecyclerView) findViewById(R.id.gv_fbh_store_category_data);
        gv_fbh_store_category_data.setLayoutManager(new GridLayoutManager(FBHStoreActivity.this, 2));

        fbhStoreDataDetailAdapter = new FBHStoreDataDetailAdapter(FBHStoreActivity.this,
                commodityDataDetailList,fromType);
        fbhStoreDataDetailAdapter.setLoadMoreView(new BaseQuickLoadMoreView());

        fbhStoreCouponAdapter = new FBHStoreCouponAdapter(FBHStoreActivity.this, personalCouponBeansList);
        fbhStoreCouponAdapter.setLoadMoreView(new BaseQuickLoadMoreView());
        fbhStoreCouponAdapter.setItemClickListener(new FBHStoreCouponAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PersonalCouponBean item) {
                exchangeCoupon(item.getId());
            }
        });

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
                String keywords = et_title_bar_view_search.getText().toString();
                Intent intent = new Intent(FBHStoreActivity.this, JoinAutoActivity.class);
                intent.putExtra("SearchWord", keywords);
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
                if (currentPage != 0)
                    currentPage++;
                getDataDetailFromServer();
            }
        }, gv_fbh_store_category_data);
    }

    /**
     * 获取分类数据
     */
    public void getCategoryDataFromServer() {
        StringRequest reqFBHStoreCategory = new StringRequest(Request.Method.GET, ServerAddress.URL_COMMODITY_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response))
                            return;
                        if (fromType == 0) {
                            PrefUtils.setString(FBHStoreActivity.this, "FBHStoreCategoryCache", response);
                        } else {
                            PrefUtils.setString(FBHStoreActivity.this, "SliverStoreCategoryCache", response);
                        }

                        Gson gson = new Gson();
                        respFBHStoreCategory = gson.fromJson(response, RespFBHStoreCategory.class);
                        //判断code是否等于200
                        if (respFBHStoreCategory.getCode() == 200 && respFBHStoreCategory.getData().size() > 0) {
                            mCategoryListData.clear();
                            FBHStoreCategory fbhStoreCategory = new FBHStoreCategory();
                            fbhStoreCategory.setId("0");
                            fbhStoreCategory.setTitle("优惠券");
                            mCategoryListData.add(fbhStoreCategory);
                            mCategoryListData.addAll(respFBHStoreCategory.getData());

                            fbhStoreCategoryAdapter.notifyDataSetChanged();//分类刷新
                            //获取第一种分类
                            currentCategoryId = mCategoryListData.get(0).getId();
                            fbhStoreCategoryAdapter.setSelectPos(0);
                            getDataDetailFromServer();
                        }
                    }
                    //请求网络失败
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String categoryCache = "";
                if (fromType == 0) {
                    categoryCache = PrefUtils.getString(FBHStoreActivity.this, "FBHStoreCategoryCache", "");
                } else {
                    categoryCache = PrefUtils.getString(FBHStoreActivity.this, "SliverStoreCategoryCache", "");
                }

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

    //优惠券 - 商家
    private void getcouponListServer() {
        StringBuffer url = new StringBuffer(ServerAddress.URL_MY_PERSONAL_INFO_COUPON);
        url.append("?page=" + currentPage + "&type=" + fromType + "&store_id=" + 0 + "&row_num=10");
        String temp = url.toString();
        Log.d("aaa", temp);
        //没有数据提示a
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fbhStoreCouponAdapter.loadMoreComplete();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespPersonalCoupon respCommodityList = gson.fromJson(response, RespPersonalCoupon.class);
                if (respCommodityList.getCode() == 200) {
                    if (respCommodityList.getData() == null) {
                        tv_fbh_store_no_data.setVisibility(View.GONE);
                        if (currentPage == 1) {
                            tv_fbh_store_no_data.setVisibility(View.VISIBLE);
                            fbhStoreCouponAdapter.setNewData(new ArrayList<PersonalCouponBean>());
                            fbhStoreCouponAdapter.loadMoreFail();
                            fbhStoreCouponAdapter.loadMoreEnd(true);
                        } else
                            fbhStoreCouponAdapter.loadMoreEnd();
                        return;
                    }
//                    if (currentPage == 1) {
                    fbhStoreCouponAdapter.setNewData(respCommodityList.getData());
                    personalCouponBeansList = respCommodityList.getData();

//                    } else {
//                        fbhStoreDataDetailAdapter.addData(respCommodityList.getData());
//                    }
                    fbhStoreCouponAdapter.loadMoreEnd();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FBHStoreActivity.this, "未获取到服务器商城数据", Toast.LENGTH_LONG).show();
            }
        });
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /**
     * 兑换优惠券
     */
    private void exchangeCoupon(String id) {
        showProgressDialog();
        RequestParams entity = new RequestParams(ServerAddress.URL_MY_PERSONAL_INFO_EXCHANGE_COUPON);
        entity.addBodyParameter("uid", WoAiSiJiApp.getUid());
        entity.addBodyParameter("id", id);
        entity.addBodyParameter("token", WoAiSiJiApp.token);


        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(result))
                    return;
                Gson gson = new Gson();
                RespAddOrder respAddOrder = gson.fromJson(result, RespAddOrder.class);
                if (respAddOrder.getCode() == 200) {

                    Toast.makeText(FBHStoreActivity.this, "兑换成功", Toast.LENGTH_LONG).show();
                } else {
                    if (respAddOrder.getMsg().contains("token") || respAddOrder.getMsg().contains("uid")) {
                        Toast.makeText(FBHStoreActivity.this, "兑换失败,登录信息已过期,请重新登录", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FBHStoreActivity.this, "兑换失败," + respAddOrder.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissProgressDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissProgressDialog();
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    /**
     * 获取商品列表数据
     */
    private void getDataDetailFromServer() {
        if (currentCategoryId.equals("0")) {
//            fbhStoreDataDetailAdapter.

            gv_fbh_store_category_data.setAdapter(fbhStoreCouponAdapter);
            getcouponListServer();
            return;
        }
        gv_fbh_store_category_data.setAdapter(fbhStoreDataDetailAdapter);
        StringBuffer url = new StringBuffer(ServerAddress.URL_FBH_COMMODITY_DATA_LIST);
        url.append("?cid=" + currentCategoryId + "&page=" + currentPage + "&type=" + fromType + "&store_id=" + fromType + "&row_num=10");
        String temp = url.toString();
        Log.d("aaa", temp);
        //没有数据提示a
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fbhStoreDataDetailAdapter.loadMoreComplete();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespCommodityList respCommodityList = gson.fromJson(response, RespCommodityList.class);
                if (respCommodityList.getCode() == 200) {
                    if (respCommodityList.getData() == null) {
                        tv_fbh_store_no_data.setVisibility(View.GONE);
                        if (currentPage == 1) {
                            tv_fbh_store_no_data.setVisibility(View.VISIBLE);
                            fbhStoreDataDetailAdapter.setNewData(new ArrayList<RespCommodityList.CommodityDataDetail>());
                            fbhStoreDataDetailAdapter.loadMoreFail();
                            fbhStoreDataDetailAdapter.loadMoreEnd(true);
                        } else
                            fbhStoreDataDetailAdapter.loadMoreEnd();
                        return;
                    }
                    if (currentPage == 1) {
                        fbhStoreDataDetailAdapter.setNewData(respCommodityList.getData());
                    } else {
                        fbhStoreDataDetailAdapter.addData(respCommodityList.getData());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FBHStoreActivity.this, "未获取到服务器商城数据", Toast.LENGTH_LONG).show();
            }
        });
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

}
