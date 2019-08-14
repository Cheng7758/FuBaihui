package com.example.zhanghao.woaisiji.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.blankj.utilcode.util.ActivityUtils;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.RecruitmentActivity;
import com.example.zhanghao.woaisiji.activity.SliverIntegralStoreActivity;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.example.zhanghao.woaisiji.utils.http.VolleyMultipartRequest;
import com.google.gson.Gson;
import com.example.network.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FunctionUtils {

    /**
     * 进入银积分商城
     */
    public static void requestSilverIntegral(final Activity toActivity) {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;
        final Map<String, String> params = new HashMap<>();
        params.put("uid", StringUtils.defaultStr(uid,""));
        params.put("token", StringUtils.defaultStr(token,""));
        NetManager.getNetManager().getMyService(Myserver.url)
                .getShopsRuzhuBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsRuzhuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ((BaseActivity)(ActivityUtils.getTopActivity())).showProgressDialog();
                    }

                    @Override
                    public void onNext(ShopsRuzhuBean value) {
                        DialogUtil.cancelProgressDialog();
                        Gson gson = new Gson();
                        String geographicInfo = gson.toJson(value.getData().getSsx());
                        String dpfl = gson.toJson(value.getData().getDpfl());
                        String flbq = gson.toJson(value.getData().getFlbq());
                        if (TextUtils.isEmpty(PrefUtils.getString(toActivity, "GeographicInfo", "")))
                            PrefUtils.setString(toActivity, "GeographicInfo", geographicInfo);
                        PrefUtils.setString(toActivity, "RecruitDpfl", dpfl);
                        PrefUtils.setString(toActivity, "RecruitFlbq", flbq);
                        ActivityUtils.startActivity(new Intent(toActivity, SliverIntegralStoreActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.cancelProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 进入加盟商家
     */
    public static void requestFranchisee(final Activity toActivity) {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;
        final Map<String, String> params = new HashMap<>();
        params.put("uid", StringUtils.defaultStr(uid,""));
        params.put("token", StringUtils.defaultStr(token,""));
        NetManager.getNetManager().getMyService(Myserver.url)
                .getShopsRuzhuBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsRuzhuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ((BaseActivity)(ActivityUtils.getTopActivity())).showProgressDialog();
                    }

                    @Override
                    public void onNext(ShopsRuzhuBean value) {
                        DialogUtil.cancelProgressDialog();
                        Gson gson = new Gson();
                        String geographicInfo = gson.toJson(value.getData().getSsx());
                        String dpfl = gson.toJson(value.getData().getDpfl());
                        String flbq = gson.toJson(value.getData().getFlbq());
                        if (TextUtils.isEmpty(PrefUtils.getString(toActivity, "GeographicInfo", "")))
                            PrefUtils.setString(toActivity, "GeographicInfo", geographicInfo);
                        PrefUtils.setString(toActivity, "RecruitDpfl", dpfl);
                        PrefUtils.setString(toActivity, "RecruitFlbq", flbq);
                        ActivityUtils.startActivity(new Intent(toActivity, JoinAutoActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.cancelProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 进入商家入驻页面的请求
     */
    public static void requestRecruitment(final Activity toActivity) {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;
        final Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getShopsRuzhuBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsRuzhuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ShopsRuzhuBean value) {
                        DialogUtil.cancelProgressDialog();
                        Gson gson = new Gson();
                        String geographicInfo = gson.toJson(value.getData().getSsx());
                        String dpfl = gson.toJson(value.getData().getDpfl());
                        String flbq = gson.toJson(value.getData().getFlbq());
                        if (TextUtils.isEmpty(PrefUtils.getString(toActivity, "GeographicInfo", "")))
                            PrefUtils.setString(toActivity, "GeographicInfo", geographicInfo);
                        PrefUtils.setString(toActivity, "RecruitDpfl", dpfl);
                        PrefUtils.setString(toActivity, "RecruitFlbq", flbq);
                        ActivityUtils.startActivity(new Intent(toActivity, RecruitmentActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 上传单张图片
     *
     * @param listener
     * @param errorListener
     * @param file
     * @param paramsStates
     */
    public static void uploadServiceImg(Response.Listener<String> listener, Response.ErrorListener errorListener, File file, String paramsStates) {
        Map<String, String> params = new HashMap<String, String>();
        if (TextUtils.isEmpty(paramsStates)) {
            params.put("status", paramsStates);//传入1的时候返回图片的路径，如果不传参数返回图片的id
        }
        VolleyMultipartRequest request = new VolleyMultipartRequest(ServerAddress.URL_UPLOAD_PICTURES,
                listener, errorListener, "img[]", file, params); //注意这个key必须是f_file[],后面的[]不能少
        WoAiSiJiApp.mRequestQueue.add(request);
    }

    /**
     * 上传多张图片
     *
     * @param listener
     * @param errorListener
     * @param files
     * @param paramsStates
     */
    public static void uploadServiceImgGroup(Response.Listener<String> listener, Response.ErrorListener errorListener,
                                             List<File> files, String paramsStates) {
        Map<String, String> params = new HashMap<String, String>();
        if (TextUtils.isEmpty(paramsStates)) {
            params.put("status", paramsStates);//传入1的时候返回图片的路径，如果不传参数返回图片的id
        }
        VolleyMultipartRequest request = new VolleyMultipartRequest(ServerAddress.URL_UPLOAD_PICTURES,
                listener, errorListener, "img[]", files, params);
        WoAiSiJiApp.mRequestQueue.add(request);
    }


    /**
     * 上传头像
     *
     * @param listener
     * @param errorListener
     * @param file
     * @param paramsMap
     */
    public static void updateHeadPicServer(Response.Listener<String> listener, Response.ErrorListener errorListener,
                                           File file, Map<String, String> paramsMap) {
        if (!paramsMap.isEmpty()) {
            VolleyMultipartRequest request = new VolleyMultipartRequest(ServerAddress.URL_MY_PERSONAL_INFO_MODIFY_HEAD_PORTRAIT,
                    listener, errorListener, "file", file, paramsMap); //注意这个key必须是f_file[],后面的[]不能少
            WoAiSiJiApp.mRequestQueue.add(request);
        }
    }

    /**
     * 支付宝  微信   结尾回调
     *
     * @param payType
     * @param paymentOrderID
     * @param responseListener
     * @param errorListener
     */

    public static void paymentEndCallBack(String payType, final String paymentOrderID, Response.Listener responseListener, Response.ErrorListener errorListener) {
        String url = "1".equals(payType) ? ServerAddress.URL_ALI_PAY_CALLBACK : ServerAddress.URL_WX_PAY_CALLBACK;
        StringRequest payRequest = new StringRequest(Request.Method.POST, url,
                responseListener, errorListener) {
            // 携带参数
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ordernum", paymentOrderID);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(payRequest);
    }
}
