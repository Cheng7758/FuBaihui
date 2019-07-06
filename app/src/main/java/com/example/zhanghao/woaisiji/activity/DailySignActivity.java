package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.login.LoginActivity;
import com.example.zhanghao.woaisiji.bean.DailySignResponseBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.serverdata.FriendsListData;
import com.example.zhanghao.woaisiji.utils.DpTransPx;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/29.
 */
public class DailySignActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rlDailySignClick;
    private TextView tvDailySignIcon01;
    private TextView tvDailySignIcon02;
    private TextView tvDailySignIcon03;
    private TextView tvDailySignIcon04;
    private TextView tvDailySignIcon05;
    private TextView tvDailySignIcon06;
    private TextView tvDailySignIcon07;
    private ImageView ivDailySignIcon01;
    private ImageView ivDailySignIcon02;
    private ImageView ivDailySignIcon03;
    private ImageView ivDailySignIcon04;
    private ImageView ivDailySignIcon05;
    private ImageView ivDailySignIcon06;
    private ImageView ivDailySignIcon07;


    // 获取系统时间
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private long timeStamp;

    private final static int TYPE_ALREADY_SIGN = 1;
    private final static int TYPE_SIGN = 0;
    private DailySignResponseBean dailySignBean;
    private List<String> dateList;
    private int[] isSign = new int[40];
    private int[] sevenDate = new int[10];
    private Button btnBack;
    private SimpleDateFormat sdf;
    private Calendar calendar;
    private ImageView ivBack;
    private WebView wvWeatherReveal;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sign);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        timeStamp = calendar.getTimeInMillis();

        // 初始化布局
        initView();

        // 响应点击事件
        responseClickListener();

        dateList = new ArrayList<>();

        // 初始化时间
        initDate();

        // 为相应控件设置数据
        initData();

        // 展示天气
        initWeather();


    }

    private void initWeather() {
//        String url = "http://weather.html5.qq.com/";
//        String url = "http://m.baidu.com/from=2001a/s?word=%E5%A4%A9%E6%B0%94&ts=8296479&t_kt=0&ie=utf-8&rsv_iqid=9264249347020784832&rsv_t=5c71BpVYihM8ErjU9aJIOhHooEkDlkodtSeK1fiESxpRiyrBtVu%252BYTJyPps&sa=ib&rsv_pq=9264249347020784832&rsv_sug4=11478&ss=111&inputT=9388";
        String url = "https://m.baidu.com/s?word=%E7%99%BE%E5%BA%A6%E5%A4%A9%E6%B0%94&ref=www_colorful&st=111041&tn=iphone&from=1014285c";


        wvWeatherReveal.loadUrl(url);
        wvWeatherReveal.getSettings().setJavaScriptEnabled(true);
        wvWeatherReveal.setScrollContainer(false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , DpTransPx.Dp2Px(DailySignActivity.this, 543));
        wvWeatherReveal.setLayoutParams(params);
        wvWeatherReveal.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
/*
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }*/
        });

        wvWeatherReveal.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }

        });
    }

    private void initView() {

//        btnBack = (Button) findViewById(R.id.btn_back);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        TextView tvLoveDriverTitle = (TextView) findViewById(R.id.love_driver_title);
        tvLoveDriverTitle.setText("我的签到");

        // 天气webview
        wvWeatherReveal = (WebView) findViewById(R.id.wv_weather_reveal);
//        scrollView = (ScrollView) findViewById(R.id.scroll_view);


        rlDailySignClick = (RelativeLayout) findViewById(R.id.rl_daily_sign_click);

        tvDailySignIcon01 = (TextView) findViewById(R.id.tv_daily_sign_icon01);
        ivDailySignIcon01 = (ImageView) findViewById(R.id.iv_daily_sign_icon01);

        tvDailySignIcon02 = (TextView) findViewById(R.id.tv_daily_sign_icon02);
        ivDailySignIcon02 = (ImageView) findViewById(R.id.iv_daily_sign_icon02);

        tvDailySignIcon03 = (TextView) findViewById(R.id.tv_daily_sign_icon03);
        ivDailySignIcon03 = (ImageView) findViewById(R.id.iv_daily_sign_icon03);

        tvDailySignIcon04 = (TextView) findViewById(R.id.tv_daily_sign_icon04);
        ivDailySignIcon04 = (ImageView) findViewById(R.id.iv_daily_sign_icon04);

        tvDailySignIcon05 = (TextView) findViewById(R.id.tv_daily_sign_icon05);
        ivDailySignIcon05 = (ImageView) findViewById(R.id.iv_daily_sign_icon05);

        tvDailySignIcon06 = (TextView) findViewById(R.id.tv_daily_sign_icon06);
        ivDailySignIcon06 = (ImageView) findViewById(R.id.iv_daily_sign_icon06);

        tvDailySignIcon07 = (TextView) findViewById(R.id.tv_daily_sign_icon07);
        ivDailySignIcon07 = (ImageView) findViewById(R.id.iv_daily_sign_icon07);

    }

    private void responseClickListener() {
        rlDailySignClick.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void initDate() {
//        Log.d("今天", "" + calendar);

        // 获取前后三天的日期
        for (int i = -3; i < 4; i++) {
            long temp = timeStamp + i * 24 * 3600 * 1000;
            String ss = sdf.format(temp);
            int tempMonth = Integer.parseInt(ss.split("-")[1]);
            int tempDay = Integer.parseInt(ss.split("-")[2]);
            sevenDate[i + 3] = tempDay;
            String tempDate = tempMonth + "." + tempDay;
            dateList.add(tempDate);
        }


    }

    private void initData() {

        tvDailySignIcon01.setText(dateList.get(0));
        tvDailySignIcon02.setText(dateList.get(1));
        tvDailySignIcon03.setText(dateList.get(2));
        tvDailySignIcon04.setText(dateList.get(3));
        tvDailySignIcon05.setText(dateList.get(4));
        tvDailySignIcon06.setText(dateList.get(5));
        tvDailySignIcon07.setText(dateList.get(6));


        StringRequest dailySignRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ALREADY_SIGN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("activity_daily", response);
                transServerData(response, TYPE_ALREADY_SIGN);

                setImageVisible();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(dailySignRequest);
    }

    private void setImageVisible() {
        if (isSign[sevenDate[0]] == 1) {
            ivDailySignIcon01.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.already_daily_sign_mark);
            drawable.setBounds(0, 0, 60, 60);
            tvDailySignIcon01.setCompoundDrawables(null, drawable, null, null);
        } else {
            ivDailySignIcon01.setVisibility(View.INVISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.daily_sign_mark);
            drawable.setBounds(0, 0, 50, 50);
            tvDailySignIcon01.setCompoundDrawables(null, drawable, null, null);
        }
        if (isSign[sevenDate[1]] == 1) {
            ivDailySignIcon02.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.already_daily_sign_mark);
            drawable.setBounds(0, 0, 60, 60);
            tvDailySignIcon02.setCompoundDrawables(null, drawable, null, null);
        } else {
            ivDailySignIcon02.setVisibility(View.INVISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.daily_sign_mark);
            drawable.setBounds(0, 0, 50, 50);
            tvDailySignIcon02.setCompoundDrawables(null, drawable, null, null);
        }
        if (isSign[sevenDate[2]] == 1) {
            ivDailySignIcon03.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.already_daily_sign_mark);
            drawable.setBounds(0, 0, 60, 60);
            tvDailySignIcon03.setCompoundDrawables(null, drawable, null, null);
        } else {
            ivDailySignIcon03.setVisibility(View.INVISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.daily_sign_mark);
            drawable.setBounds(0, 0, 50, 50);
            tvDailySignIcon03.setCompoundDrawables(null, drawable, null, null);
        }
        if (isSign[sevenDate[3]] == 1) {
            ivDailySignIcon04.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.already_daily_sign_mark);
            drawable.setBounds(0, 0, 60, 60);
            tvDailySignIcon04.setCompoundDrawables(null, drawable, null, null);
        } else {
            ivDailySignIcon04.setVisibility(View.INVISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.daily_sign_mark);
            drawable.setBounds(0, 0, 50, 50);
            tvDailySignIcon04.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_daily_sign_click:
                if (TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
                    submitDailySign();
                } else {
                    startActivity(new Intent(DailySignActivity.this, LoginActivity.class));
                }

                break;
        }
    }

    private void submitDailySign() {
        StringRequest submitSignRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_SIGN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_SIGN);

                ivDailySignIcon04.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(R.drawable.already_daily_sign_mark);
                drawable.setBounds(0, 0, 60, 60);
                tvDailySignIcon04.setCompoundDrawables(null, drawable, null, null);
                Toast.makeText(DailySignActivity.this, dailySignBean.msg, Toast.LENGTH_SHORT).show();

                // 更新用户信息
                FriendsListData friendsListData = new FriendsListData();
                friendsListData.obtainDataFromServer();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(submitSignRequest);
    }

    private void transServerData(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case TYPE_SIGN:
                dailySignBean = gson.fromJson(response, DailySignResponseBean.class);
                break;
            case TYPE_ALREADY_SIGN:
                String num = "";
                String num02 = "";
                for (int i = 2; i < response.split(":").length; i++) {
                    num = num + "," + response.split(":")[i];
                }
                for (int i = 1; i < num.split(",").length; i = i + 2) {
                    num02 = num02 + "}" + num.split(",")[i];
                }
                int k = 1;
                for (int i = 1; i < num02.split("\\}").length - 1; i++) {
                    String tempData = num02.split("\\}")[i];
                    if (tempData != "") {
                        if (tempData.contains("1") || tempData.contains("2") || tempData.contains("3")
                                || tempData.contains("4") || tempData.contains("5") || tempData.contains("6")
                                || tempData.contains("7") || tempData.contains("8") || tempData.contains("9")) {
                            isSign[k] = 1;
                        } else {
                            isSign[k] = 2;
                        }
                        k++;
                    }
                }
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        timeStamp = calendar.getTimeInMillis();
//        initDate();
        initData();
    }
}
