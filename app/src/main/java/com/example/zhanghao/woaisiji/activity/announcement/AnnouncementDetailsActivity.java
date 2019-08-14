package com.example.zhanghao.woaisiji.activity.announcement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;

public class AnnouncementDetailsActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings settings;
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        String title = getIntent().getStringExtra("title");
        // 标题栏
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setText(title);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.announcement_webView);
        initData();
    }

    private void initData() {
        String id = getIntent().getStringExtra("id");
        if (id == null) {
            return;
        }
        String url = ServerAddress.SERVER_ROOT + "/Admin/Public/impublic/id/" + id + "/type/4";

        webView.loadUrl(url);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//加上这一行网页为响应式的
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        // 内容自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
            }
        });
    }
}
