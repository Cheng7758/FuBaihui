package com.hyphenate.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.ImageUrlBean;
import com.hyphenate.easeui.domain.MemberShipInfosBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzz on 2016/11/30.
 */

public class SetUserInfoUtils {

    private static String url = "http://www.woaisiji.com/APP/Member/info";
    private static String imgUrl = "http://www.woaisiji.com/APP/Public/get_img_path";
    private static RequestQueue requestQueue;
    private static MemberShipInfosBean memberShipInfosBean;
    private static Gson gson;

    public static void setUserInfo(final Context context , final String uid, final TextView tvName, final ImageView ivHeadPic){
        requestQueue = Volley.newRequestQueue(context);
        // 获取会员信息
        StringRequest getFriendsInfo = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gson =  new Gson();

                memberShipInfosBean =  gson.fromJson(response,MemberShipInfosBean.class);

//                Log.d("EaseConversation",uid+"-->"+memberShipInfosBean.info.headpic+"");
                if (memberShipInfosBean.code == 200){
                    if (tvName != null){
                        tvName.setText(memberShipInfosBean.info.nickname);
                    }


                    final String  headPic = memberShipInfosBean.info.headpic;
                    //                            Log.d("setUserInfoUtils",imageUrl.path);
//                            Glide.with(context).load("http://www.woaisiji.com"+imageUrl.path).into(ivHeadPic);
                    StringRequest getImgRequest = new StringRequest(Request.Method.POST, imgUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ImageUrlBean imageUrl = gson.fromJson(response,ImageUrlBean.class);
//                            ImageLoader.getInstance().displayImage("http://www.woaisiji.com"+imageUrl.path,ivHeadPic);
//                            Log.d("setUserInfoUtils",imageUrl.path);
                            Glide.with(context).load("http://www.woaisiji.com"+imageUrl.path).into(ivHeadPic);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_id", headPic);
                            return params;
                        }
                    };
                    requestQueue.add(getImgRequest);
                }
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };


        requestQueue.add(getFriendsInfo);
    }

}
