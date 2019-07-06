package com.example.zhanghao.woaisiji.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.ShangchengAdapter;
import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.mall.CategoryGoodPager;
import com.example.zhanghao.woaisiji.base.mall.MallViewPagerAdapter;
import com.example.zhanghao.woaisiji.base.mall.RecommendGoodPager;
import com.example.zhanghao.woaisiji.bean.DriverMall;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SliverIntegralShangCheng extends BaseActivity {
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private LinearLayout ll_title_bar_view_search_root;
    private EditText et_title_bar_view_search;
    private ImageView iv_title_bar_view_do_search;


    private RelativeLayout shousuo;
    private ListView feilei;//左边的listview
    private ShangchengAdapter shangchengAdapter;//左边listview的适配器
    private List<BasePager> mBasePagerLists;

    private ViewPager mallViewPagerList;//右边的viewpager
    //分类集合
    private List<DriverMall.ListBean> mListData = new ArrayList<>();
    private DriverMall driverMall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fbh_store);
        mBasePagerLists = new ArrayList<>();
        initView();
        initDate();

        //初始化页面显示
        mBasePagerLists.add(new RecommendGoodPager(this,2));//推荐的商品页面
        mBasePagerLists.get(0).initData();
        mallViewPagerList.setAdapter(new MallViewPagerAdapter(this, mBasePagerLists));//右边的viewpager（推荐的）

        obtainDataFromServer();//服务器请求
    }
    /**
     * 服务器请求
     */
    public void obtainDataFromServer() {
        /**
         * 分类网络获取请求
         */
        StringRequest DriveMallRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_EXCHANGE_CLASSIFICATION, new Response.Listener<String>() {

            //请求网络数据成功
            @Override
            public void onResponse(String response) {

                PrefUtils.setString(SliverIntegralShangCheng.this,"categoryCache02",response);//sharepreference保存
                transServerData(response);//gson解析
                if (driverMall == null) {
                    Log.d("分类网络获取服务器废了", "" + response);
                }
                //判断code是否等于200
                if (driverMall.getCode() == 200) {

                    for (int i = 0; i < driverMall.list.size(); i++) {
                        DriverMall.ListBean listBean = driverMall.list.get(i);
                        mListData.add(listBean);
                    }
                    //左边的listView设置数据
                    shangchengAdapter = new ShangchengAdapter(SliverIntegralShangCheng.this, mListData);
                    feilei.setAdapter(shangchengAdapter);
                }
            }
            //请求网络失败
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("分类请求失败", "" + error);
            }
        });
        //添加网络请求队列
        WoAiSiJiApp.mRequestQueue.add(DriveMallRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        driverMall = gson.fromJson(response, DriverMall.class);
    }

    private void initView() {
        initTitleBar();

        feilei = (ListView) findViewById(R.id.lv_fbh_store_category); //左边的listView
        //viewPager
//        mallViewPagerList = (ViewPager) findViewById(R.id.vp_fbh_store_category_data);
    }

    private void initTitleBar() {
        ll_title_bar_view_search_root = (LinearLayout) findViewById(R.id.ll_title_bar_view_search_root);
        ll_title_bar_view_search_root.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setVisibility(View.GONE);
        //搜索按钮
        iv_title_bar_view_do_search = (ImageView) findViewById(R.id.iv_title_bar_view_do_search);
        et_title_bar_view_search = (EditText) findViewById(R.id.et_title_bar_view_search);
    }

    private void initDate() {
        //添加商品界面
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_title_bar_view_do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//搜索框的点击
                searchFunction();
            }
        });

        et_title_bar_view_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                    searchFunction();
                }
                return handled;
            }
        });

        //左边竖条listView的的点击事件，点击后viewpager换了商品和视图
        feilei.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String Id =  mListData.get(position).getId();

                mBasePagerLists.clear();
                //点击后的适配器，里面有改后的数据
                mBasePagerLists.add(new CategoryGoodPager(SliverIntegralShangCheng.this, Integer.parseInt(Id),2));
                //右边的viewpager
                mallViewPagerList.setAdapter(new MallViewPagerAdapter(SliverIntegralShangCheng.this, mBasePagerLists));

                shangchengAdapter.setSelectPos(position);
            }
        });
    }

    /**
     * 搜索
     */
    private void searchFunction() {
        String keywords =et_title_bar_view_search.getText().toString();
        if (!TextUtils.isEmpty(keywords))
            startActivity(new Intent(SliverIntegralShangCheng.this,SearchProductActivity.class).putExtra("keywords",keywords).addFlags(2));
        else{
            Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
