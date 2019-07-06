package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.bean.DriverShoppingBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/29.
 */
public class CategoryGoodDetailPager2 extends BasePagerDetail implements PullToRefreshLayout.OnRefreshListener{

    private StringRequest stringRequest;
    List<DriverShoppingBean.ListBean> DriverShopingList;
    private GridView gvNormalShopping;
    private int type =1;
    private int pid;
    private String url = ServerAddress.URL_DEIVERSHOPPING;
    public final static String ID = "id";
    public final static String MALLTYPE = "mall_type";
    private PullToRefreshLayout refreshView;
    private int pager = 1;
    private boolean isFirst = true;
    private boolean isCache = false;
    private GridViewItemAdapter gridViewItemAdapter;
    private DriverShoppingBean driverShopping;

    private String cacheName = "";

    public CategoryGoodDetailPager2(Activity activity, int pid, int type) {
        super(activity);
        this.pid = pid;
        this.type = type;
        cacheName = "mallDetailCache" + pid;
        /**
         *  type=1  正品商城
         *  type=2  银币商城
         *  type=3  养护连锁
         */

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.grid_view_refresh_layout, null);
        gvNormalShopping = (GridView) view.findViewById(R.id.gv_lubricating);
        refreshView = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void initData() {

        String mallDetailCache = "";
        switch (type){
            case 1:
                url = ServerAddress.URL_DEIVERSHOPPING;
//                mallDetailCache = PrefUtils.getString(mActivity,"categoryGoodCache01","");
                break;
            case 2:
                url = ServerAddress.URL_EXCHANGE_DEIVERSHOPPING;
//                mallDetailCache = PrefUtils.getString(mActivity,"categoryGoodCache02","");
                break;
            case 3:
                url = ServerAddress.URL_CURING_DEIVERSHOPPING;
//                mallDetailCache = PrefUtils.getString(mActivity,"categoryGoodCache03","");

                break;
        }


        DriverShopingList = new ArrayList<>();
        gridViewItemAdapter = new GridViewItemAdapter(mActivity);//gridview的每一项的adapter

        if (!TextUtils.isEmpty(mallDetailCache)){
            transServerData(mallDetailCache);
            for (int i = 0; i < driverShopping.getList().size(); i++) {
                DriverShoppingBean.ListBean listBean = driverShopping.getList().get(i);
                DriverShopingList.add(listBean);
            }
            gridViewItemAdapter.mGoodLists = DriverShopingList;
            gvNormalShopping.setAdapter(gridViewItemAdapter);//gridview设置adapter
            isFirst = false;
            isCache = true;
        }else {
            getDataFromServer();
        }

        //item的点击事件
        gvNormalShopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kucun = DriverShopingList.get(position).getNumber();

                if (kucun.equals("0")) {
                    Toast.makeText(mActivity, "没有库存喽哟！ 亲～", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mActivity, ProductDetailActivity2.class);//跳转图文详情
                    String Id = DriverShopingList.get(position).getId();
                    intent.putExtra(ID, Id);
                    intent.putExtra("type", type);
                    mActivity.startActivity(intent);

                }
            }
        });
    }

    private void getDataFromServer() {
        if (stringRequest != null) {//你自己想想为啥，想不明白回头我给你讲为啥取消，怕数据乱。
            stringRequest.cancel();
        }
        //没有数据提示
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (driverShopping == null) {
                    Log.d("正品商城服务器废了", "" + response);
                }
                if (driverShopping.getCode() == 200) {
                    if (driverShopping.getList() == null) {
                        //没有数据提示
                        Toast.makeText(mActivity, "没有更多数据...", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    // 数据缓存
                    // 数据缓存
                    /*if (!isCache){
                        switch (type){
                            case 1:
                                PrefUtils.setString(mActivity,"categoryGoodCache01",response);
                                break;
                            case 2:
                                PrefUtils.setString(mActivity,"categoryGoodCache02",response);
                                break;
                            case 3:
                                PrefUtils.setString(mActivity,"categoryGoodCache03",response);
                                break;
                        }
                        isCache = true;
                    }*/

                    for (int i = 0; i < driverShopping.getList().size(); i++) {

                        DriverShoppingBean.ListBean listBean = driverShopping.getList().get(i);
                        DriverShopingList.add(listBean);
                    }
                    gridViewItemAdapter.mGoodLists = DriverShopingList;
                    if (isFirst){
                        gvNormalShopping.setAdapter(gridViewItemAdapter);
                        isFirst = false;
                    }else {
                        gridViewItemAdapter.notifyDataSetChanged();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "正品商城未获取到服务器数据", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("cateid", String.valueOf(pid));
                map.put("p", String.valueOf(pager));
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        driverShopping = gson.fromJson(response, DriverShoppingBean.class);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pager = 1;
                getDataFromServer();
                // 千万别忘了告诉控件刷新完毕了哦！
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
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
        }.sendEmptyMessageDelayed(0, 3000);
    }
}
