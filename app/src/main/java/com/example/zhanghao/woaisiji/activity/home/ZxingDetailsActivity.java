package com.example.zhanghao.woaisiji.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zhanghao.woaisiji.R;

public class ZxingDetailsActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_details);
        mUrl = getIntent().getStringExtra("details");
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.zxing_webView);

        mWebView.loadUrl(mUrl);
        mWebView.getSettings().setJavaScriptEnabled(true);   //加上这一行网页为响应式的
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
            }
        });
    }
}
