package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.ShangchengAdapter;
import com.example.zhanghao.woaisiji.base.BasePager;
import com.example.zhanghao.woaisiji.base.mall.CategoryGoodPager2;
import com.example.zhanghao.woaisiji.base.mall.MallViewPagerAdapter;
import com.example.zhanghao.woaisiji.base.mall.RecommendGoodPager2;
import com.example.zhanghao.woaisiji.bean.DriverMall;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class YangHuLianSuo extends AppCompatActivity {

    private Button back;
    private RelativeLayout shousuo;
    private ListView feilei;
    private ShangchengAdapter shangchengAdapter;
    //分类集合
    private List<DriverMall.ListBean> mListData = new ArrayList<>();
    private TextView tvMallTitle;
    private ViewPager mallViewPagerList;
    private List<BasePager> mBasePagerLists;
    private DriverMall driverMall;
    private ImageButton ibSearchButton;
    private EditText editKeyQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbh_store);
        mBasePagerLists = new ArrayList<>();
        initView();
        initDate();
//        getCategoryDataFromServer();
        mBasePagerLists.add(new RecommendGoodPager2(this,3));
        mBasePagerLists.get(0).initData();
        mallViewPagerList.setAdapter(new MallViewPagerAdapter(this, mBasePagerLists));
//        RecommendLoad();

        /*String categoryCache = PrefUtils.getString(YangHuLianSuo.this,"categoryCache03","");
        if (!TextUtils.isEmpty(categoryCache)){
            transServerData(categoryCache);
            for (int i = 0; i < driverMall.list.size(); i++) {
                DriverMall.ListBean listBean = driverMall.list.get(i);
                mListData.add(listBean);
            }
            //listView适配器
            shangchengAdapter = new ShangchengAdapter(YangHuLianSuo.this, mListData);
            feilei.setAdapter(shangchengAdapter);
        }*/
        obtainDataFromServer();

    }
    /**
     * 服务器请求
     */
    public void obtainDataFromServer() {
        /**
         * 分类网络获取
         */
        StringRequest DriveMallRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_CURING_CLASSIFICATION, new Response.Listener<String>() {
            //请求网络数据成功

            @Override
            public void onResponse(String response) {

                PrefUtils.setString(YangHuLianSuo.this,"categoryCache03",response);

                transServerData(response);
                if (driverMall == null) {
                    Log.d("分类网络获取服务器废了", "" + response);
                }
                //判断code是否等于200
                if (driverMall.getCode() == 200) {

                    for (int i = 0; i < driverMall.list.size(); i++) {
                        DriverMall.ListBean listBean = driverMall.list.get(i);
                        mListData.add(listBean);
                    }
                    //listView适配器
                    shangchengAdapter = new ShangchengAdapter(YangHuLianSuo.this, mListData);
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
        //返回按钮
        back = (Button) findViewById(R.id.btn_back);
        tvMallTitle = (TextView) findViewById(R.id.tv_mall_title);
        tvMallTitle.setText("积分商城");
        //搜索按钮
//        shousuo = (RelativeLayout) findViewById(R.id.sousuo_shangcheng);
        ibSearchButton = (ImageButton) findViewById(R.id.ib_search_button);
        editKeyQuery = (EditText) findViewById(R.id.edit_key_query);
        //左边的listView
        feilei = (ListView) findViewById(R.id.lv_fbh_store_category);
        //viewPager
//        mallViewPagerList = (ViewPager) findViewById(R.id.vp_fbh_store_category_data);
    }

    private void initDate() {
        //添加商品界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(FBHStoreActivity.this, MainActivity.class));
                finish();
            }
        });

        ibSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywords =editKeyQuery.getText().toString();
                startActivity(new Intent(YangHuLianSuo.this,SearchProductActivity.class).putExtra("keywords",keywords).addFlags(3));
//                Toast.makeText(FBHStoreActivity.this,"搜索功能",Toast.LENGTH_SHORT).show();
            }
        });

        //listView的itme点击事件
        feilei.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                              final String Id =  mListData.get(position).getId();

                mBasePagerLists.clear();
                mBasePagerLists.add(new CategoryGoodPager2(YangHuLianSuo.this, Integer.parseInt(Id),3));
                mallViewPagerList.setAdapter(new MallViewPagerAdapter(YangHuLianSuo.this, mBasePagerLists));
//
                shangchengAdapter.setSelectPos(position);
            }
        });
    }
}
