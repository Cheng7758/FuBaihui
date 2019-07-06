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
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/29.
 */
public class FBHStoreGoodDetailPager extends BasePagerDetail implements PullToRefreshLayout.OnRefreshListener{

    private StringRequest stringRequest;
    List<RespCommodityList.CommodityDataDetail> commodityDataDetailList;
    private GridView gv_fbh_store_show_data;
    /**
     *  type=0  福百惠商城
     *  type=1  银积分商品
     */
    private int type = 0;
    private String categoryID;
    private StringBuffer url;
    private PullToRefreshLayout prv_fbh_store_show_data;
    private int pager = 1;
    private FBHStoreItemAdapter gridViewItemAdapter;
    private RespCommodityList respCommodityList;

    public FBHStoreGoodDetailPager(Activity activity, String pid, int type) {
        super(activity);
        this.categoryID = pid;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_fbh_store_show_data, null);
        gv_fbh_store_show_data = (GridView) view.findViewById(R.id.gv_fbh_store_show_data);
        prv_fbh_store_show_data = (PullToRefreshLayout) view.findViewById(R.id.prv_fbh_store_show_data);
        prv_fbh_store_show_data.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void initData() {
        url = new StringBuffer();
        switch (type){
            case 0:
                url.append(ServerAddress.URL_FBH_COMMODITY_DATA_LIST);
                break;
            case 1:
                url.append(ServerAddress.URL_EXCHANGE_DEIVERSHOPPING);
                break;
        }
        commodityDataDetailList = new ArrayList<>();
        gridViewItemAdapter = new FBHStoreItemAdapter(mActivity);//gridview的每一项的adapter
        gv_fbh_store_show_data.setAdapter(gridViewItemAdapter);
        gridViewItemAdapter.setNewDataSource(commodityDataDetailList);
        getDataListFromServer();
        //item的点击事件
        gv_fbh_store_show_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kucun = commodityDataDetailList.get(position).getNumber();
                if (kucun.equals("0")) {
                    Toast.makeText(mActivity, "没有库存喽哟！ 亲～", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mActivity, ProductDetailActivity2.class);//跳转图文详情
                    String Id = commodityDataDetailList.get(position).getId();
                    intent.putExtra("id", Id);
                    intent.putExtra("type", type);
                    mActivity.startActivity(intent);

                }
            }
        });
    }

    /**
     * 获取商品列表数据
     */
    private void getDataListFromServer() {
        if (stringRequest != null) {
            stringRequest.cancel();
        }
        url.append("?cid="+categoryID+"&page="+pager+"&type="+type+"&store_id="+type+"&row_num=10");
        String temp = url.toString() ;
        Log.d("aaa",temp) ;
        //没有数据提示a
        stringRequest = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isBlank(response))
                    return;
                Gson gson = new Gson();
                respCommodityList = gson.fromJson(response, RespCommodityList.class);
                if (respCommodityList == null) {
                    Log.d("正品商城服务器废了", "" + response);
                }
                if (respCommodityList.getCode() == 200) {
                    if (respCommodityList.getData() == null) {
                        prv_fbh_store_show_data.refreshFinish(1);
                        prv_fbh_store_show_data.loadmoreFinish(1);
                        Toast.makeText(mActivity, "没有更多数据...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (pager==1)
                        gridViewItemAdapter.setNewDataSource(respCommodityList.getData());
                    else{
                        commodityDataDetailList.addAll(respCommodityList.getData());
                        gridViewItemAdapter.notifyDataSetChanged();
                        prv_fbh_store_show_data.refreshFinish(0);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "未获取到服务器商城数据", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("cid", String.valueOf(categoryID));//分类id
                map.put("page", String.valueOf(pager));//页数
                map.put("type", String.valueOf(type));//默认0 福百惠商品 1银积分商品
                map.put("store_id", String.valueOf(type));//默认0 平台 商家id
                map.put("row_num", "10");//默认6 每页条数
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pager=1;
                getDataListFromServer();
                // 千万别忘了告诉控件刷新完毕了哦！
                prv_fbh_store_show_data.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pager = pager++;
                getDataListFromServer();
                prv_fbh_store_show_data.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }
}
