package com.example.zhanghao.woaisiji.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoActivity;
import com.example.zhanghao.woaisiji.adapter.FBHStoreAdapter;
import com.example.zhanghao.woaisiji.adapter.FBHStoreDataDetailAdapter;
import com.example.zhanghao.woaisiji.bean.BannerBean;
import com.example.zhanghao.woaisiji.bean.fbh.FBHStoreCategory;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.example.zhanghao.woaisiji.resp.RespFBHStoreCategory;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.example.zhanghao.woaisiji.view.BaseQuickLoadMoreView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FBHStoreActivity2 extends AppCompatActivity {

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
    private List<RespCommodityList.CommodityDataDetail> commodityDataDetailList;

    private int fromType = 0;//0-FBH   1-银积分
    private int currentPage = 1;
    private String currentCategoryId = "";
    private boolean isRefresh = false;
    private Banner fbh_banner;
    private List<BannerBean.DataBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        fromType = getIntent().getIntExtra("fromType", 0);
        setContentView(R.layout.activity_fbh_store);

        mCategoryListData = new ArrayList<>();
        commodityDataDetailList = new ArrayList<>();
        WoAiSiJiApp.APPLICATION_SHOP_TYPE=0;
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
        fbh_banner = (Banner) findViewById(R.id.fbh_banner);
        showBanner();   //轮播图

        tv_fbh_store_no_data = (TextView) findViewById(R.id.tv_fbh_store_no_data);
        lv_fbh_store_category = (ListView) findViewById(R.id.lv_fbh_store_category);
        fbhStoreCategoryAdapter = new FBHStoreAdapter(FBHStoreActivity2.this, mCategoryListData);
        lv_fbh_store_category.setAdapter(fbhStoreCategoryAdapter);

        gv_fbh_store_category_data = (RecyclerView) findViewById(R.id.gv_fbh_store_category_data);
        gv_fbh_store_category_data.setLayoutManager(new GridLayoutManager(FBHStoreActivity2.this, 2));
        fbhStoreDataDetailAdapter = new FBHStoreDataDetailAdapter(FBHStoreActivity2.this,
                commodityDataDetailList, fromType);
        fbhStoreDataDetailAdapter.setLoadMoreView(new BaseQuickLoadMoreView());
        gv_fbh_store_category_data.setAdapter(fbhStoreDataDetailAdapter);
    }

    private void showBanner() {
        NetManager.getNetManager().getMyService(Myserver.url)
                .getBannerBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean pBannerBean) {
                        if (pBannerBean.getData() != null) {
                            Log.e("-----banner", pBannerBean.getData().toString());
                            mData = pBannerBean.getData();
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override   //请求成功
                    public void onComplete() {
                        ArrayList<String> imgList = new ArrayList<>();  //放图片地址的集合
                        ArrayList<String> titleList = new ArrayList<>();    //放标题的集合
                        for (int i = 0; i < mData.size(); i++) {
                            imgList.add(ServerAddress.SERVER_ROOT + mData.get(i).getImg());
                            titleList.add(mData.get(i).getTitle());
                        }
                        fbh_banner.setImages(imgList).setBannerTitles(titleList)
                                //设置图片加载器
                                .setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        Glide.with(context).load((String) path).into(imageView);
                                    }
                                }).setBannerAnimation(Transformer.Default) //设置轮播的动画效果
                                .setBannerStyle(BannerConfig.NUM_INDICATOR)   //设置轮播图样式
                                .setDelayTime(5000)    //设置轮播间隔时间
                                .start();   //必须最后调用的方法，启动轮播图
                    }
                });
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
                Intent intent = new Intent(FBHStoreActivity2.this, JoinAutoActivity.class);
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
                            PrefUtils.setString(FBHStoreActivity2.this, "FBHStoreCategoryCache", response);
                        } else {
                            PrefUtils.setString(FBHStoreActivity2.this, "SliverStoreCategoryCache", response);
                        }

                        Gson gson = new Gson();
                        respFBHStoreCategory = gson.fromJson(response, RespFBHStoreCategory.class);
                        //判断code是否等于200
                        if (respFBHStoreCategory.getCode() == 200 && respFBHStoreCategory.getData().size() > 0) {
                            mCategoryListData.clear();
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
                    categoryCache = PrefUtils.getString(FBHStoreActivity2.this, "FBHStoreCategoryCache", "");
                } else {
                    categoryCache = PrefUtils.getString(FBHStoreActivity2.this, "SliverStoreCategoryCache", "");
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

    /**
     * 获取商品列表数据
     */
    private void getDataDetailFromServer() {
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
                Toast.makeText(FBHStoreActivity2.this, "未获取到服务器商城数据", Toast.LENGTH_LONG).show();
            }
        });
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }
}
