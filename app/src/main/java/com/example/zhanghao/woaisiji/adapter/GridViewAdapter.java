package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.ImageUrlBean;
import com.example.zhanghao.woaisiji.bean.SearchProductBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzz on 2016/11/25.
 */

public class GridViewAdapter extends BaseAdapter {

    public List<SearchProductBean.ListBean> mSearchProductList;
    private Activity mActivity;
    private ImageUrlBean imageUrl;

    public GridViewAdapter(Activity mActivity){
        this.mActivity = mActivity;
        mSearchProductList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mSearchProductList.size();
    }

    @Override
    public SearchProductBean.ListBean getItem(int i) {
        return mSearchProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if (view == null){
            view = View.inflate(mActivity, R.layout.recommend_itme,null);
            viewHoder = new ViewHoder();
//            AbViewUtil.scaleContentView((ViewGroup) convertView);
            viewHoder.introduce = (TextView) view.findViewById(R.id.introduce);
            viewHoder.payment = (TextView) view.findViewById(R.id.payment);
            viewHoder.price = (TextView) view.findViewById(R.id.price);
            viewHoder.iv_shangpintu = (ImageView) view.findViewById(R.id.iv_shangpintu);
            view.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) view.getTag();
        }

        SearchProductBean.ListBean item = getItem(i);

        viewHoder.iv_shangpintu.setImageResource(R.drawable.weixianshi);
        viewHoder.payment.setText(item.getPeople() + "人付款");
        viewHoder.introduce.setText(item.getTitle());
        viewHoder.price.setText("￥" +item.getPrice());
        getPictureUrl(item.getCover().split(",")[0],viewHoder.iv_shangpintu);
//        ImageLoader.getInstance().displayImage(url, viewHoder.iv_shangpintu);

        return view;
    }


    static class ViewHoder {
        //内容
        TextView introduce;
        //图片
        ImageView iv_shangpintu;
        //价格
        TextView price;
        //付款
        TextView payment;
    }


    public void getPictureUrl(final String cover, final ImageView iv_shangpintu){

        StringRequest getImgUrlRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (imageUrl.code == 200) {

//                 ServerAddress.SERVER_ROOT + imageUrl.path
                    ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + imageUrl.path, iv_shangpintu);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("img_id", cover);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(getImgUrlRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        imageUrl = gson.fromJson(response, ImageUrlBean.class);

    }
}
