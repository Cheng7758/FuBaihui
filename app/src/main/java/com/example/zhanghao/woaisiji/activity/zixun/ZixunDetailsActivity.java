package com.example.zhanghao.woaisiji.activity.zixun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;

public class ZixunDetailsActivity extends AppCompatActivity {

    private ImageView zixun_details_back;
    private WebView zixun_webView;
    private String mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_details);
        Intent intent = getIntent();
        mContent = intent.getStringExtra("content");
        initView();
    }

    private void initView() {
        zixun_details_back = (ImageView) findViewById(R.id.zixun_details_back);
        zixun_webView = (WebView) findViewById(R.id.zixun_webView);

        zixun_details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        zixun_webView.getSettings().setJavaScriptEnabled(true);   //加上这一行网页为响应式的
        zixun_webView.getSettings().setDefaultFixedFontSize(15);
        zixun_webView.loadData(getHtmlData(mContent), "text/html; charset=UTF-8", null);
        zixun_webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
