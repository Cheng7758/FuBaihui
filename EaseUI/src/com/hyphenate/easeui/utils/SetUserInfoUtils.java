package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.network.utils.MGson;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.MemberShipInfosBean;
import com.hyphenate.easeui.http.Myserver;
import com.hyphenate.easeui.http.NetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zzz on 2016/11/30.
 */

public class SetUserInfoUtils {

    private static String url = "http://www.woaisiji.com/APP/Member/info";
    private static String imgUrl = "http://www.woaisiji.com/APP/Public/get_img_path";
    private static RequestQueue requestQueue;
    private static MemberShipInfosBean memberShipInfosBean;
    private static Gson gson;

    public static void setUserInfo(final Context context, final String uid, final TextView tvName, final ImageView ivHeadPic) {
        /*requestQueue = Volley.newRequestQueue(context);
        // 获取会员信息
        StringRequest getFriendsInfo = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
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
                            Log.d("imageLoads+__", "onResponse: ");
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
        requestQueue.add(getFriendsInfo);*/

        SharedPreferences sharedPreferences = context.getSharedPreferences("token", 0);
        String token = sharedPreferences.getString("token", "");
        Log.e("-----token2", token.toString());
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getFriendsBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean value) {
                        Log.e("-----username", value.toString());

                        if (value.code == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(value.toString());
                                jsonObject = jsonObject.getJSONObject("data");
                                jsonObject = jsonObject.getJSONObject(uid);

                                if (tvName != null) {
                                    String nickname = jsonObject.getString("nickname");
                                    tvName.setText(nickname);
                                }

                                String headpic = jsonObject.getString("headpic");
                                if (ivHeadPic != null)
                                    Glide.with(context.getApplicationContext()).load("http://wasj.zhangtongdongli.com" + headpic).into(ivHeadPic);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override   //请求成功
                    public void onComplete() {

                    }
                });
    }

    public static class Bean {
        public int code;
        public String msg;
        public Object data;

        public String getNickName(String uid) {
            return get(uid, "nickname");
        }

        public String getPic(String uid) {
            return get(uid, "headpic");
        }

        public String getUid(String uid) {
            return get(uid, "uid");
        }

        private String get(String uid, String key) {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(data)).getJSONObject(uid);
                return jsonObject.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public String toString() {
            return MGson.toJson(this);
        }
    }
}
