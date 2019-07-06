package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
import com.example.zhanghao.woaisiji.utils.PrefUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zzz on 2016/12/14.
 */

public class WebViewLotteryActivity extends Activity {
    private static final String APP_CACHE_DIRNAME = "/webcache";
    private ListView lvComment;
    private String commentId;
    private String commentType;
    private String webUrl;
    private View webView;
    private WebView wvDetailContent;
    private int count;
    private boolean isHaveNum;
    private Long endStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_header);
        webUrl = "http://www.woaisiji.com/index/choujiang1";


        initView();

        // 根据时间戳初始化，抽奖次数
            initLotteryCount();

//        PrefUtils.setInt(WebViewLotteryActivity.this, "lotteryNum", 0);
        count = PrefUtils.getInt(WebViewLotteryActivity.this, "lotteryNum", 0);


        initData();


//        webUrl = ServerAddress.URL_HEALTH_DETAIL + "/id/" + commentId + "/type/" + commentType;
//        wvDetailContent = (WebView) findViewById(R.id.wv_detail_content);

      /*  wvDetailContent.loadUrl(webUrl);
        wvDetailContent.getSettings().setJavaScriptEnabled(true);
        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/


    }

    private void initLotteryCount() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 59);

        Date begin = beginTime.getTime();
        Date end = endTime.getTime();

        Date currentDate = new Date();
        isHaveNum = PrefUtils.getBoolean(WebViewLotteryActivity.this, "is_have_num", false);
        endStamp = PrefUtils.getLong(WebViewLotteryActivity.this,"end_stamp", (long) 0);
        if (currentDate.getTime() > endStamp){
            Log.d("WebViewLottery", TimeUtils.times(currentDate.getTime()));
            PrefUtils.setInt(WebViewLotteryActivity.this,"lotteryNum",0);
            PrefUtils.setLong(WebViewLotteryActivity.this,"end_stamp",end.getTime());
        }
        /*if (!isHaveNum){
            PrefUtils.setInt(WebViewLotteryActivity.this,"lotteryNum",0);
            PrefUtils.setBoolean(WebViewLotteryActivity.this,"is_have_num",true);
        }
        if (currentDate.getTime() > begin.getTime()) {

        }
*/

    }

    private void initData() {
        wvDetailContent.loadUrl(webUrl);
//        wvDetailContent.getSettings().setJavaScriptEnabled(true);
        wvDetailContent.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvDetailContent.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式

        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        //设置数据库缓存路径
        wvDetailContent.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        wvDetailContent.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        wvDetailContent.getSettings().setAppCacheEnabled(true);

//        webSettings.setJavaScriptEnabled(true);
        WebSettings webSettings = wvDetailContent.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放


        wvDetailContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                count++;
                PrefUtils.setInt(WebViewLotteryActivity.this, "lotteryNum", count);


               /* Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                Date m6 = c.getTime();*/
//                    String.valueOf()
//                Log.d("WebViewLottery", TimeUtils.times(m6.getTime()));
//                Toast.makeText(WebViewLotteryActivity.this, "" + m6.getTime(), Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);
            }
        });
        wvDetailContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (count >= 4) {
                    return true;
                } else {
                    return false;
                }

            }
        });


         /*wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });*/
    }

    @Override
    protected void onRestart() {
        count = PrefUtils.getInt(WebViewLotteryActivity.this, "lotteryNum", 0);
//        initData();
    }

    private void initView() {
        wvDetailContent = (WebView) findViewById(R.id.wv_detail_content);
    }

}
