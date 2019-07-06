package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.zhanghao.woaisiji.R;

public class AdvertisementWebViewActivity extends AppCompatActivity {
    private boolean hasInited = false;
    private WebView webView;
    private WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_web_view);

        webView = (WebView) findViewById(R.id.wb_advertisement);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        if (content == null){
            return ;
        }

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 内容自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

         /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        settings.setDomStorageEnabled(true);
        webView.loadDataWithBaseURL(null,addStr(content),"text/html", "utf-8",null);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
//        }
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    public String addStr(String content){
        String result = "http://www.woaisiji.com/Uploads";
        System.out.println(content);
        String[] list = content.split("/Uploads");
        StringBuffer sb =new StringBuffer();
        sb.append(list[0]);
        for (int i=1;i<list.length;i++){
            sb.append(result).append(list[i]);
        }
        Log.d("sb-->",sb.toString());
        return sb.toString();
    }
    }


