package com.example.zhanghao.woaisiji.activity.send;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespMerchantDetail;
import com.example.zhanghao.woaisiji.resp.RespMerchantList;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class JoinDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private TextView tv_join_details_commodity_introduction,tv_join_details_commodity_name;
    private ImageView iv_join_details_top_picture;
    private MapView mv_join_details_commodity_position;

    private RespMerchantList.MerchantInfoDetailBean detailData;
    private String commodityId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_join_details);

        initData();
        initView();
    }

    private void initData() {
        Intent getIntent = getIntent();
        if (getIntent!=null)
            commodityId = getIntent.getStringExtra("IntentJoinToDetailDataId");
        if (!TextUtils.isEmpty(commodityId)){
            StringRequest questionRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_COMMODITY_DETAIL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    RespMerchantDetail respMerchantDetail = gson.fromJson(response, RespMerchantDetail.class);
                    if (respMerchantDetail.getCode() == 200) {
                        detailData = respMerchantDetail.getData();
                        setViewValue();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id",commodityId);
                    return params;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(questionRequest);
        }
    }

    private void initView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("商家详情");

        iv_join_details_top_picture = (ImageView) findViewById(R.id.iv_join_details_top_picture);
        iv_join_details_top_picture.setOnClickListener(this);
        tv_join_details_commodity_introduction = (TextView) findViewById(R.id.tv_join_details_commodity_introduction);
        tv_join_details_commodity_name = (TextView) findViewById(R.id.tv_join_details_commodity_name);
        mv_join_details_commodity_position = (MapView) findViewById(R.id.mv_join_details_commodity_position);
    }

    private void setViewValue (){
        if (!TextUtils.isEmpty(detailData.getName()))
            tv_join_details_commodity_name.setText(detailData.getName());
        if (!TextUtils.isEmpty(detailData.getLogo()))
            Glide.with(this).load(detailData.getLogo()).error(R.drawable.icon_loading).into(iv_join_details_top_picture);

        if (!TextUtils.isEmpty(detailData.getContent())){
            tv_join_details_commodity_introduction.setText(detailData.getContent());
        }

        if (!TextUtils.isEmpty(detailData.getLatitude()) && !TextUtils.isEmpty(detailData.getLatitude())) {
            // 设置地图类型
            BaiduMap mBaiduMap = mv_join_details_commodity_position.getMap();
            // 普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            // 地图标注
            LatLng point = new LatLng(Double.valueOf(detailData.getLatitude()), Double.valueOf(detailData.getLongitude()));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_gps);
            OverlayOptions overlayOptions = new MarkerOptions().position(point).icon(bitmapDescriptor);
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(point).zoom(18)//图层大小
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
            mBaiduMap.addOverlay(overlayOptions);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.iv_title_bar_view_left_left){
            finish();
        }else if (view.getId() == R.id.iv_join_details_top_picture){
            Intent intent = new Intent(JoinDetailsActivity.this,JoinAutoDetailsActivity.class);
            intent.putExtra("img_car",detailData.getLogo());
            intent.putExtra("tx_name",detailData.getName());
            intent.putExtra("tx_address_detail",detailData.getName());
            if (!TextUtils.isEmpty(detailData.getJuli())){
                intent.putExtra("tx_m",detailData.getJuli());
            }
            intent.putExtra("tx_content",detailData.getContent());
            intent.putExtra("call",detailData.getPhone());
            intent.putExtra("longitude",detailData.getLongitude());
            intent.putExtra("latitude",detailData.getLatitude());
            startActivity(intent);
        }
    }
}
