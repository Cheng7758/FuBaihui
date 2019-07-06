package com.example.zhanghao.woaisiji;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.util.VersionInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class VersionActivity extends AppCompatActivity {
    private String currentversion;
    private TextView textview;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        textview = (TextView) findViewById(R.id.tv_packagetime);
        btn = (Button) findViewById(R.id.btn_submit_opinion);

        //返回
        ImageView imageView = (ImageView) findViewById(R.id.iv_register_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获取当前版本
        try {
            currentversion = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            TextView textView = (TextView) findViewById(R.id.tv_packagename);
            textView.setText("版本号："+currentversion);
//            Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //与服务器的版本号对比，若不同，提示有新版本，
        final String url = "http://123.56.236.200/APP/Index/version";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        final Versioninfo versioninfo = gson.fromJson(response, Versioninfo.class);

                        //"版本更新了！“，”版本号：4.0“，”下载链接“
//                        Toast.makeText(VersionActivity.this, versioninfo.getMsg(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(VersionActivity.this, versioninfo.getVersionname(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(VersionActivity.this, versioninfo.getApkurl(), Toast.LENGTH_SHORT).show();

                        if(!currentversion.equals(versioninfo.getVersionname())){
                            textview.setText(versioninfo.getMsg());
                            btn.setVisibility(View.VISIBLE);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //通过Intent打开系统浏览器访问网页
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(versioninfo.getApkurl())));
                                }
                            });
                        }else {
                            textview.setText("当前已是最新版本");
                        }
                    }
                });


        //在闪屏页后，主页面出来之后，才显示是否需要更新，即写在MainActivity中就行
    }
}
