package com.example.zhanghao.woaisiji.serverdata;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.ImageUrlBean;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.bean.MyFriendsListBean;
import com.example.zhanghao.woaisiji.bean.friend.FriendsListBean;
import com.example.zhanghao.woaisiji.friends.Constant;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2016/10/14.
 */
public class FriendsListData {
    //    private List<FriendsListBean.DataBean> mBeanList;
    // 加载头像路径
    private static final int TYPE_IMAGE = 1;
    // 加载好友列表信息
    private static final int TYPE_FRIENDS = 2;
    // 加载用户信息
    private static final int TYPE_GET_INFO = 3;
    //    public MyFriendsListBean myFriendsList;
    public FriendsListBean myFriendsList;
    private MemberShipInfosBean memberShipInfos;
    private ImageUrlBean imageUrl;

    //===========================回调接口，用于返回数据============================================
    public SendDataListener listener;

    public void setSendDataListener(SendDataListener listener) {
        this.listener = listener;
    }

    public interface SendDataListener {
        public void sendData(Map<String, EaseUser> userList);

        public void sendStringData(String data);
    }
//===============================Gson解析数据==============================================================

    private void transServerData(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case TYPE_FRIENDS:
//                myFriendsList = gson.fromJson(response, MyFriendsListBean.class);
                myFriendsList = gson.fromJson(response, FriendsListBean.class);
                break;
            case TYPE_IMAGE:
                imageUrl = gson.fromJson(response, ImageUrlBean.class);
                break;
            case TYPE_GET_INFO:
                memberShipInfos = gson.fromJson(response, MemberShipInfosBean.class);
                break;
        }
    }

    // 加载好友列表
    public void obtainFriendsListFromServer() {
        StringRequest getFriendsRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_FRIEND_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_FRIENDS);
                if (myFriendsList.getCode() == 200 && myFriendsList.getData() != null) {
                    Log.e("-----myFriendsList", myFriendsList.getData().toString());
//                    List<EaseUser> userList = new ArrayList<EaseUser>();
                    Map<String, EaseUser> userList = new Hashtable<>();
                    for (FriendsListBean.DataBean bean : myFriendsList.getData()) {
                        EaseUser user = new EaseUser(bean.getAdd_who());
//                        user.setNick(bean.getNickname());
                        user.setNickname(bean.getNickname());

                        //ServerAddress是uri类
                        user.setAvatar(ServerAddress.SERVER_ROOT + bean.getHeadpic());
//                        user.setAvatar(bean.uid);
                        if (bean.getAdd_who().equals(Constant.NEW_FRIENDS_USERNAME) || bean.getAdd_who()
                                .equals(Constant.GROUP_USERNAME)
                                || bean.getAdd_who().equals(Constant.CHAT_ROOM) || bean.getAdd_who().equals(Constant.CHAT_ROBOT)) {
                            user.setInitialLetter("");
                        } else {
                            EaseCommonUtils.setUserInitialLetter(user);
                        }
                        userList.put(bean.getAdd_who(), user);
                    }
                    if (listener != null) {
                        listener.sendData(userList);
                    }
                }
                /*if (myFriendsList.code == 200 && myFriendsList.list != null) {
//                    List<EaseUser> userList = new ArrayList<EaseUser>();
                    Map<String, EaseUser> userList = new Hashtable<>();
                    for (MyFriendsListBean.ListBean bean : myFriendsList.list) {
                        EaseUser user = new EaseUser(bean.uid);
                        user.setNick(bean.id);
                        user.setNickname(bean.nickname);

                        //ServerAddress是uri类
                        user.setAvatar(ServerAddress.SERVER_ROOT + bean.headpic);
//                        user.setAvatar(bean.uid);
                        if (bean.uid.equals(Constant.NEW_FRIENDS_USERNAME) || bean.uid.equals(Constant.GROUP_USERNAME)
                                || bean.uid.equals(Constant.CHAT_ROOM) || bean.uid.equals(Constant.CHAT_ROBOT)) {
                            user.setInitialLetter("");
                        } else {
                            EaseCommonUtils.setUserInitialLetter(user);
                        }
                        userList.put(bean.uid, user);
                    }
                    if (listener != null) {
                        listener.sendData(userList);
                    }
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FriendsListData", "请求失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("token", WoAiSiJiApp.token);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(getFriendsRequest);
    }

    // 加载头像
    public void getHeadPictureUrl() {
        StringRequest getImgUrlRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        transServerData(response, TYPE_IMAGE);
                        if (imageUrl.code == 200) {
                            if (listener != null) {
                                listener.sendStringData(ServerAddress.SERVER_ROOT + imageUrl.path);
                            }
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
//                if (!TextUtils.isEmpty(WoAiSiJiApp.memberShipInfos.info.headpic)){
//                    params.put("img_id", WoAiSiJiApp.memberShipInfos.info.headpic);
//                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(getImgUrlRequest);
    }

    // 获取用户信息
    public void obtainDataFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MEMBER_INFO,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 解析服务器返回的数据
                transServerData(response, TYPE_GET_INFO);
                if (memberShipInfos.code == 200) {
                    WoAiSiJiApp.memberShipInfos = memberShipInfos;
                    Log.d("-----请求成功", memberShipInfos.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("请求失败", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /*// 加载好友列表
    public void obtainFriendsListFromServer() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", WoAiSiJiApp.getUid());
        params.put("token", WoAiSiJiApp.token);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getFriendsListBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendsListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FriendsListBean value) {
                        if (TextUtils.isEmpty(value.toString())) return;
                        if (value.getCode() == 200) {
                            if (value.getData() != null && value.getData().size() > 0) {
                                mBeanList = value.getData();

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("-----onError", "" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/

}
