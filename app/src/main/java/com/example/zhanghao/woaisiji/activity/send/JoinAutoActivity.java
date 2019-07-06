package com.example.zhanghao.woaisiji.activity.send;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.JoinCarAdapter;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespMerchantList;
import com.example.zhanghao.woaisiji.view.SiJiWenDaListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


public class JoinAutoActivity extends BaseActivity {
    private SiJiWenDaListView listView;
    private RespMerchantList respMerchantDetail;
    private JoinCarAdapter adapter;
    private String lat ,log ;
    // 定位相关
    LocationClient mLocClient;
    //Title
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private String keywords, category, order, screen;
    private String searchWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_auto);
        ButterKnife.inject(this);
        searchWord = getIntent().getStringExtra("SearchWord");
        getDataFromServer();
        initView();
        initLocation();
        initListener();

    }

    private void initListener() {
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initLocation() {
        mLocClient = new LocationClient(this);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(JoinAutoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(JoinAutoActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(JoinAutoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            mLocClient.setLocOption(option);
            mLocClient.start();
            getLngAndLat(this);
        }
    }

    private void getDataFromServer() {
        String url = !TextUtils.isEmpty(searchWord)?ServerAddress.URL_JOIN_PARTNER_SEARCH_DATA:ServerAddress.URL_JOIN_PARTNER_REQUEST_JOIN_LIST;
        StringRequest questionRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                    Toast.makeText(getApplicationContext(), "暂无数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (!TextUtils.isEmpty(searchWord))
                    params.put("keyword", searchWord);
                else{
                    params.put("pageno","1");
                    params.put("pagesize","200");
                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(questionRequest);
    }

    private void initView() {
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("加盟伙伴");
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);

        listView = (SiJiWenDaListView) findViewById(R.id.lv_join_auto_us_show_data);
        adapter = new JoinCarAdapter(JoinAutoActivity.this);
        listView.setAdapter(adapter);
    }
    /**
     * 获取经纬度
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public String getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        lat = String.valueOf(latitude);
        log = String.valueOf(longitude);

        return longitude + "," + latitude;
    }
    //从网络获取经纬度
    @SuppressLint("MissingPermission")
    public String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return longitude + "," + latitude;
    }
    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }
        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }
        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };
}
