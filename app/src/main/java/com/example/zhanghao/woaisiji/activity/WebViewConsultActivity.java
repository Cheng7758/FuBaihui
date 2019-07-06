package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.ArrayList;

/**
 * Created by admin on 2016/10/19.
 */
public class WebViewConsultActivity extends Activity {

    private ListView lvComment;
    private String commentId;
    private String commentType;
    private String webUrl;
    private View webView;
    private WebView wvDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_header);
        commentId = getIntent().getStringExtra("id");
        commentType = getIntent().getStringExtra("type");


        webUrl = ServerAddress.URL_HEALTH_DETAIL + "/id/" + commentId + "/type/" + commentType;

        wvDetailContent = (WebView) findViewById(R.id.wv_detail_content);
        wvDetailContent.loadUrl(webUrl);
        wvDetailContent.getSettings().setJavaScriptEnabled(true);
        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }
}
