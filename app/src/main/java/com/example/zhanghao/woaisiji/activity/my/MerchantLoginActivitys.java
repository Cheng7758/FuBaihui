package com.example.zhanghao.woaisiji.activity.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;

public class MerchantLoginActivitys extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login_activitys);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.merchant_login_webview);

        mWebView.loadUrl(ServerAddress.SERVER_ROOT + "/Admin/Public/login.html");
        mWebView.getSettings().setJavaScriptEnabled(true);   //加上这一行网页为响应式的
        // 内容自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
            }
        });
    }
}
