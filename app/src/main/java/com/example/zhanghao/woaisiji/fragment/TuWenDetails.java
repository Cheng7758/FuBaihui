package com.example.zhanghao.woaisiji.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.DetailsBean;
import com.example.zhanghao.woaisiji.bean.fbh.FBHBusinessDetails;
import com.example.zhanghao.woaisiji.resp.RespBusinessDetails;
import com.example.zhanghao.woaisiji.resp.RespFBHCommodityDetails;


/**
 * Created by zhanghao on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class TuWenDetails extends Fragment {
    private View progressBar;
    private WebView webView;
    private boolean hasInited = false;
    private WebSettings settings;
    private FBHBusinessDetails detailsbean;

    public TuWenDetails(FBHBusinessDetails detailsbean) {
        this.detailsbean = detailsbean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.productdetail_list_layout, null);
        webView = (WebView) view.findViewById(R.id.wb_tuwenxiangqing);
        progressBar = view.findViewById(R.id.progressbar);

        return view;
    }

    public void initView() {
//        String url=detailsbean.getContent();
        String id = detailsbean.getId();

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        /*settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        //settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //webView.loadData(url,"text/html", "utf-8");
        webView.loadDataWithBaseURL(null,addStr(url),"text/html", "utf-8",null);*/
        webView.loadUrl("http://wasj.zhangtongdongli.com/Admin/Public/impublic/id/" + id + "/type/2");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String string) {
                view.loadUrl(string);
                //返回true， 立即跳转，返回false,打开网页有延时
                return true;
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    public String addStr(String content) {
        String result = "http://www.woaisiji.com/Uploads";
        String[] list = content.split("/Uploads");
        StringBuffer sb = new StringBuffer();
        sb.append(list[0]);
        for (int i = 1; i < list.length; i++) {
            sb.append(result).append(list[i]);
        }
        Log.d("sb-->", sb.toString());
        return sb.toString();
    }
}
