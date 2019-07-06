package com.example.zhanghao.woaisiji.activity.send;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.zhanghao.woaisiji.R;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



/**
 * 位置详情
 */
public class JoinCarLocationActivity extends AppCompatActivity {

    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";

    private TextView tvTitle ;
    private TextView tvNavigation ;
    private ImageView registerBack;

    private TextureMapView mv_join_car_location;
    private BaiduMap mBaiduMap;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private String currentLat,currentLog;
    private float mCurrentAccracy;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;

    private Marker marker;
    private MarkerOptions markerOptions;
    private double[] merchantPosition ;//当前商户的位置


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this.getApplicationContext());
        setContentView(R.layout.activity_join_car_location);
        String longitude= getIntent().getStringExtra("longitude");
        String latitude = getIntent().getStringExtra("latitude");
        merchantPosition = map_tx2bd(Double.valueOf(latitude), Double.valueOf(longitude));


        initView();
        initMapView();
    }

    private void initMapView() {
        mBaiduMap = mv_join_car_location.getMap();

        // 位置
        LatLng latLng = new LatLng(merchantPosition[0],merchantPosition[1]);
        //准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_gps);
        //准备 marker option 添加 marker 使用
        markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
        MarkerOptions overlayOptions = new MarkerOptions().position(latLng)
                .icon(bitmap).zIndex(5);
        marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
        //获取添加的 marker 这样便于后续的操作
        marker = (Marker) mBaiduMap.addOverlay(markerOptions);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng);
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(builder.build());
        mBaiduMap.animateMapStatus(u);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(JoinCarLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(JoinCarLocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(JoinCarLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

    private void initView() {
        registerBack = (ImageView) findViewById(R.id.iv_register_back);
        tvTitle = (TextView) findViewById(R.id.tv_register_title);
        tvNavigation = (TextView) findViewById(R.id.tv_join_car_location_leader);
        mv_join_car_location = (TextureMapView) findViewById(R.id.mv_join_car_location);
        tvTitle.setText("位置详情");

        tvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNavigationMap();
            }
        });

        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 展示导航地图
     */
    private void showNavigationMap() {
        final String[] items = { "百度地图","高德地图"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(JoinCarLocationActivity.this);
        dialog.setTitle("选择地图");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){//百度地图
                    startBDNavi();
                }else{//高德地图
                    startGaoNavi();
                }
            }
        });
        dialog.show();
    }
    //开启百度导航
    public void startBDNavi() {
        if (isAvilible(JoinCarLocationActivity.this, BAIDU_PACKAGE_NAME)) {
            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(
                    "baidumap://map/direction?origin=我的位置&destination="+merchantPosition[0]+","+
                            merchantPosition[1]));
            intent.setPackage("com.baidu.BaiduMap");
            startActivity(intent); //启动调用
        } else {
            Toast.makeText(JoinCarLocationActivity.this,"您尚未安装百度或地图版本过低",Toast.LENGTH_SHORT).show();
        }
    }
    //高德地图,起点就是定位点
    public void startGaoNavi() {
        if (isAvilible(JoinCarLocationActivity.this, GAODE_PACKAGE_NAME)) {
            double[] positionTransform = map_baidu2gd(merchantPosition[0],merchantPosition[1]);
            LatLng ll = new LatLng(positionTransform[1],positionTransform[0]);
            Intent intent = null;
            try {
                intent = Intent.getIntent("androidamap://navi?sourceApplication=GDYH" +
                        "&poiname=我的目的地&lat=" + ll.latitude + "&lon=" + ll.longitude + "&dev=0");
                startActivity(intent);
            } catch (URISyntaxException e) {
                Toast.makeText(JoinCarLocationActivity.this,"您尚未安装高德地图或地图版本过低",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(JoinCarLocationActivity.this,"您尚未安装高德地图或地图版本过低",Toast.LENGTH_SHORT).show();
        }
    }
    //验证各种导航地图是否安装
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 获取经纬度
     *
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
        currentLat = String.valueOf(latitude);
        currentLog = String.valueOf(longitude);
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

    /**
     * 坐标转换，腾讯地图转换成百度地图坐标
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：经度,纬度
     */
    public double[] map_tx2bd(double lat, double lon){
//        double bd_lat;
//        double bd_lon;
//        double x_pi=3.14159265358979324;
//        double x = lon, y = lat;
//        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
//        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
//        bd_lon = z * Math.cos(theta) + 0.0065;
//        bd_lat = z * Math.sin(theta) + 0.006;
        LatLng sourceLatLng = new LatLng(lat,lon);
        CoordinateConverter coordinateConverter = new CoordinateConverter();
        coordinateConverter = coordinateConverter.from(CoordinateConverter.CoordType.COMMON).coord(sourceLatLng);
        LatLng desCoord= coordinateConverter.convert();
        double[] result = new double[2];
        result[0] = desCoord.latitude;
        result[1] = desCoord.longitude;
        return result;
    }

    /**
     * 坐标转换，百度地图转换成高德地图坐标
     * @param bd_lat 百度纬度
     * @param bd_lon 百度经度
     * @return 返回结果：经度,纬度
     */
    public double[] map_baidu2gd(double bd_lat, double bd_lon){
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_join_car_location == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng llCurrent = new LatLng(mCurrentLat,mCurrentLon);
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.tar。get(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                List<LatLng> mLatLngList = new ArrayList<>();
                mLatLngList.add(llCurrent);
                mLatLngList.add(new LatLng(merchantPosition[0],merchantPosition[1]));
                zoomToSpan(mLatLngList);

                tvNavigation.setVisibility(View.VISIBLE);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 计算缩放比
     * @param mLatLngList
     */
    public void zoomToSpan(List<LatLng> mLatLngList) {
        if (mLatLngList.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : mLatLngList) {
                builder.include(latLng);
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    /**
     * 定位监听器
     */
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


    @Override
    protected void onPause() {
        mv_join_car_location.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mv_join_car_location.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mv_join_car_location.onDestroy();
        mv_join_car_location = null;
        super.onDestroy();
    }
}
