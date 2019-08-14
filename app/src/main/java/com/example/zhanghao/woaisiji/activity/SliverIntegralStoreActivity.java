package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.MyClassificationAdapter;
import com.example.zhanghao.woaisiji.adapter.SliverIntegralStoreAdapter;
import com.example.zhanghao.woaisiji.bean.MerchantsClassification;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.resp.RespMerchantList;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.example.zhanghao.woaisiji.view.SiJiWenDaListView;
import com.example.zhanghao.woaisiji.widget.DivItemDecoration;
import com.example.zhanghao.woaisiji.widget.DividerGridItemDecoration;
import com.example.zhanghao.woaisiji.widget.PickerView.Pickers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 银积分商城
 */
public class SliverIntegralStoreActivity extends BaseActivity implements MyClassificationAdapter.OnItemClickListener {

    private LinearLayout ll_sliver_integral_store_location, ll_sliver_integral_store_province,
            ll_sliver_integral_store_city, ll_sliver_integral_store_country,
            ll_recruitment_province_city_country_root;
    ;
    private TextView tv_sliver_integral_store_province, tv_sliver_integral_store_city,
            tv_sliver_integral_store_country;
    private RelativeLayout relative;
    private TextView cancel, confirm;   //取消 确定
    private Pickers currentDpflPickers, currentFlbqPickers;

    //省的WheelView控件/市的WheelView控件//区的WheelView控件/
    private WheelView wheelview_recruitment_province, wheelview_recruitment_city,
            wheelview_recruitment_country;
    private List<String> provinceData = new ArrayList<>();  //省份
    private HashMap<String, List<String>> cityDataHash = new HashMap<String, List<String>>();
    //省份下面的市数据
    private HashMap<String, List<String>> provinceDataHash = new HashMap<String, List<String>>();
    //市添加区
    private List<ShopsRuzhuBean.DataBean.ShengBean> mShengBeans;
    private List<ShopsRuzhuBean.DataBean.DpflBean> mDpflBeanList;
    private List<ShopsRuzhuBean.DataBean.FlbqBean> mFlbqBeanList;
    private boolean isFirst = true;

    private SiJiWenDaListView listView;
    private RespMerchantList respMerchantDetail;
    private SliverIntegralStoreAdapter adapter;
    //Title
    private ImageView iv_title_bar_view_left_left,classify;
    private TextView tv_title_bar_view_centre_title;

    private String province, city, district;
    public LocationClient mLocationClient = null;
    public BaiduMapLocationListener myListener = new BaiduMapLocationListener();
    private double mLatitude;
    private double mLongitude;
    private ShopsRuzhuBean.DataBean.ShiBean mShiBean;
    private ShopsRuzhuBean.DataBean.XianBean mXianBean;
    private String imageName, currentXian;
    private RecyclerView classify_rlv;
    private List<MerchantsClassification.DataBean> mBeanList;
    private MyClassificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliver_integral_store);
        getDataFromServer();
        initLocation();
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLocation();
    }

    //进入银积分商城页面隐藏滚动选择器
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirst) {
            isFirst = false;
            relative.setVisibility(View.GONE);
            ll_recruitment_province_city_country_root.setVisibility(View.GONE);
        }
    }

    /**
     * 通过地址获得加盟商家列表
     */
    private void getAddressDataFromServer() {
        String url = ServerAddress.URL_JOIN_PARTNER_REQUEST_JOIN_LIST;  //加盟商家地址列表数据
        StringRequest questionRequest = new StringRequest(Request.Method.POST, url, new Response
                .Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                respMerchantDetail = gson.fromJson(response, RespMerchantList.class);
                if (respMerchantDetail.getCode() == 200) {
                    if (respMerchantDetail.getData() != null) {
                        adapter.getDataSource().clear();
                        adapter.getDataSource().addAll(respMerchantDetail.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        listView.removeAllViewsInLayout();
                        Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                } else if (respMerchantDetail.getCode() == 400) {
                    listView.removeAllViewsInLayout();
                    Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sheng", mShiBean.getParent_id());
                params.put("shi", mShiBean.getRegion_id());
                if (mShiBean.getXian() != null) {
                    params.put("xian", mXianBean.getRegion_id());
                } else {
                    params.put("xian", "");
                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(questionRequest);
    }

    private void initData() {
        currentDpflPickers = new Pickers();
        currentFlbqPickers = new Pickers();
        WoAiSiJiApp.APPLICATION_SHOP_TYPE = 4;
        String shengStr = PrefUtils.getString(this, "GeographicInfo", "");
        String dpfl = PrefUtils.getString(this, "RecruitDpfl", "");
        String flbq = PrefUtils.getString(this, "RecruitFlbq", "");
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(flbq))
            mFlbqBeanList = gson.fromJson(flbq, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean
                    .FlbqBean>>() {
            }.getType());
        if (!TextUtils.isEmpty(dpfl))
            mDpflBeanList = gson.fromJson(dpfl, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean
                    .DpflBean>>() {
            }.getType());
        if (!TextUtils.isEmpty(shengStr))
            mShengBeans = gson.fromJson(shengStr, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean
                    .ShengBean>>() {
            }.getType());
        if (mShengBeans != null && mShengBeans.size() > 0) {
            for (int i = 0; i < mShengBeans.size(); i++) {
                ShopsRuzhuBean.DataBean.ShengBean shengBean = mShengBeans.get(i);
                provinceData.add(shengBean.getRegion_name());
                List<String> cityDataList = new ArrayList<>();  //shi
                for (int j = 0; j < shengBean.getShi().size(); j++) {
                    ShopsRuzhuBean.DataBean.ShiBean shiBean = shengBean.getShi().get(j);
                    List<String> countyDataList = new ArrayList<>();  //县
                    cityDataList.add(shiBean.getRegion_name());
//                    Log.d("Log",mShengBeans.get(i).getRegion_name()+" "+shiBean.getRegion_name());
                    if (shiBean.getXian() != null && shiBean.getXian().size() > 0) {
                        for (int k = 0; k < shiBean.getXian().size(); k++) {
                            ShopsRuzhuBean.DataBean.XianBean xianBean = shiBean.getXian().get(k);
                            countyDataList.add(xianBean.getRegion_name());
                        }
                    } else {
                        countyDataList.add("");
                    }
                    cityDataHash.put(shiBean.getRegion_name(), countyDataList);
                }
                provinceDataHash.put(shengBean.getRegion_name(), cityDataList);
            }
        }
    }

    private void initView() {
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("银积分商城");
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        classify = (ImageView) findViewById(R.id.classify);
        classify_rlv = (RecyclerView) findViewById(R.id.classify_rlv);
        classify_rlv.setLayoutManager(new LinearLayoutManager(SliverIntegralStoreActivity.this));
        classify_rlv.addItemDecoration(new DividerGridItemDecoration(SliverIntegralStoreActivity.this));

        tv_sliver_integral_store_province = (TextView) findViewById(R.id.tv_sliver_integral_store_province);
        tv_sliver_integral_store_city = (TextView) findViewById(R.id.tv_sliver_integral_store_city);
        tv_sliver_integral_store_country = (TextView) findViewById(R.id.tv_sliver_integral_store_country);

//        listView = (SiJiWenDaListView) findViewById(R.id.lv_sliver_integral_store_show_data);
        listView = (SiJiWenDaListView) findViewById(R.id.lv_join_auto_us_show_data);
        adapter = new SliverIntegralStoreAdapter(SliverIntegralStoreActivity.this);
        listView.setAdapter(adapter);

        ll_sliver_integral_store_location = (LinearLayout) findViewById(R.id.ll_sliver_integral_store_location);
        ll_sliver_integral_store_province = (LinearLayout) findViewById(R.id.ll_sliver_integral_store_province);
        ll_sliver_integral_store_city = (LinearLayout) findViewById(R.id.ll_sliver_integral_store_city);
        ll_sliver_integral_store_country = (LinearLayout) findViewById(R.id.ll_sliver_integral_store_country);

        //列表上面的位置布局
        ll_sliver_integral_store_location = (LinearLayout) findViewById(R.id
                .ll_sliver_integral_store_location);
        ll_sliver_integral_store_province = (LinearLayout) findViewById(R.id
                .ll_sliver_integral_store_province);
        ll_sliver_integral_store_city = (LinearLayout) findViewById(R.id
                .ll_sliver_integral_store_city);
        ll_sliver_integral_store_country = (LinearLayout) findViewById(R.id
                .ll_sliver_integral_store_country);
        relative = (RelativeLayout) findViewById(R.id.relative);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm = (TextView) findViewById(R.id.confirm);
        //确定  取消
        ll_recruitment_province_city_country_root = (LinearLayout) findViewById(R.id
                .ll_recruitment_province_city_country_root);
        initWheelView();
    }

    //初始化滚动选择器
    private void initWheelView() {
        wheelview_recruitment_province = (WheelView) findViewById(R.id
                .wheelview_recruitment_province);
        wheelview_recruitment_province.setWheelAdapter(new ArrayWheelAdapter(this));    //滚动选择器用的适配器
        wheelview_recruitment_province.setSkin(WheelView.Skin.Holo);
        if (provinceData != null && provinceData.size() > 0)
            wheelview_recruitment_province.setWheelData(provinceData);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        wheelview_recruitment_province.setStyle(style);

        wheelview_recruitment_city = (WheelView) findViewById(R.id.wheelview_recruitment_city);
        wheelview_recruitment_city.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelview_recruitment_city.setSkin(WheelView.Skin.Holo);
        List<String> lis = new ArrayList<>();
        try {
            lis = provinceDataHash.get(provinceData.get(wheelview_recruitment_province.getSelection()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lis != null && lis.size() >= 1)
            wheelview_recruitment_city.setWheelData(lis);
        wheelview_recruitment_city.setStyle(style);
        wheelview_recruitment_province.join(wheelview_recruitment_city);
        wheelview_recruitment_province.joinDatas(provinceDataHash);

        wheelview_recruitment_country = (WheelView) findViewById(R.id
                .wheelview_recruitment_country);
        wheelview_recruitment_country.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelview_recruitment_country.setSkin(WheelView.Skin.Holo);
        List<String> strings1 = new ArrayList<>();
        try {
            strings1 = cityDataHash.get(lis.get(wheelview_recruitment_city.getSelection()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strings1 != null && strings1.size() > 0)
            wheelview_recruitment_country.setWheelData(strings1);
        wheelview_recruitment_country.setStyle(style);
        wheelview_recruitment_city.join(wheelview_recruitment_country);
        wheelview_recruitment_city.joinDatas(cityDataHash);
    }

    //列表上面的定位功能
    private void initListener() {
        ll_sliver_integral_store_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classify_rlv.setVisibility(View.GONE);
                relative.setVisibility(View.VISIBLE);
                ll_recruitment_province_city_country_root.setVisibility(View.VISIBLE);
            }
        });
        //左上角返回按钮
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //分类筛选按钮
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classify_rlv.setVisibility(View.VISIBLE);
                relative.setVisibility(View.GONE);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);

                showClassifyRlv();
            }
        });

        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative.setVisibility(View.GONE);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);
            }
        });
        //确定
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative.setVisibility(View.GONE);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);
                mShiBean = mShengBeans.get
                        (wheelview_recruitment_province.getCurrentPosition()).getShi()
                        .get(wheelview_recruitment_city.getCurrentPosition());
                if (mShiBean.getXian() != null) {
                    mXianBean = mShiBean.getXian().get
                            (wheelview_recruitment_country.getCurrentPosition());
                    currentXian = mXianBean.getRegion_name();
                } else {
                    currentXian = "";
                }

                tv_sliver_integral_store_province.setText(provinceData.get
                        (wheelview_recruitment_province.getCurrentPosition()));   //省
                tv_sliver_integral_store_city.setText(mShiBean.getRegion_name());    //市
                tv_sliver_integral_store_country.setText(currentXian);  //区县

                getAddressDataFromServer();
            }
        });
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void getDataFromServer() {
        String url = ServerAddress.URL_JOIN_PARTNER_REQUEST_JOIN_LIST;  //银积分商城列表数据
        StringRequest questionRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                respMerchantDetail = gson.fromJson(response, RespMerchantList.class);
                if (respMerchantDetail.getCode() == 200) {
                    if (respMerchantDetail.getData() != null) {
                        adapter.getDataSource().clear();
                        adapter.getDataSource().addAll(respMerchantDetail.getData());
                        adapter.notifyDataSetChanged();
                    }
                } else if (respMerchantDetail.getCode() == 400) {
                    listView.removeAllViewsInLayout();
                    Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
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
                params.put("pageno", "1");
                params.put("pagesize", "100000000");
                params.put("type", 1 + "");
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(questionRequest);
    }

    //分类的列表
    private void showClassifyRlv() {
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMerchantsClassification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MerchantsClassification>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MerchantsClassification value) {
//                        if (value.getData() == null) return;
                        if (TextUtils.isEmpty(value.getData().toString())) return;
                        Log.e("-----宝贝评价", value.getData().toString());
                        try {
                            mBeanList = value.getData();
                            mAdapter.setList(mBeanList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mAdapter = new MyClassificationAdapter(SliverIntegralStoreActivity.this, mBeanList);
                        classify_rlv.setAdapter(mAdapter);
                        mAdapter.setItemClickListener(SliverIntegralStoreActivity.this);
                    }
                });
    }

    @Override
    public void onItemClick(int position, String id) {
        classify_rlv.setVisibility(View.GONE);
        getClassifyDataFromServer(id);
    }

    /**
     * 通过分类获得加盟商家列表
     * @param pId
     */
    private void getClassifyDataFromServer(final String pId) {
        String url = ServerAddress.URL_JOIN_PARTNER_REQUEST_JOIN_LIST;  //加盟商家分类列表数据
        StringRequest questionRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                respMerchantDetail = gson.fromJson(response, RespMerchantList.class);
                if (respMerchantDetail.getCode() == 200) {
                    if (respMerchantDetail.getData() != null) {
                        adapter.getDataSource().clear();
                        adapter.getDataSource().addAll(respMerchantDetail.getData());
                        adapter.notifyDataSetChanged();
                    }
                } else if (respMerchantDetail.getCode() == 400) {
                    listView.removeAllViewsInLayout();
                    Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
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
                params.put("type", 1 + "");
                params.put("garage_cid", pId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(questionRequest);
    }

    public class BaiduMapLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            province = location.getProvince();
            if (!TextUtils.isEmpty(province))
                tv_sliver_integral_store_province.setText(province);
            city = location.getCity();
            if (!TextUtils.isEmpty(city))
                tv_sliver_integral_store_city.setText(city);
            district = location.getDistrict();
            if (!TextUtils.isEmpty(district))
                tv_sliver_integral_store_country.setText(district);

            //定位
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            Log.e("======定位", mLatitude + "");
            Log.e("======定位", mLongitude + "");
            adapter.setAltitude(mLongitude);
            adapter.setLatitude(mLatitude);
            adapter.notifyDataSetChanged();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }
}
