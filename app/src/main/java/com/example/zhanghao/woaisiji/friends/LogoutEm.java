package com.example.zhanghao.woaisiji.friends;

import android.app.Activity;

import com.hyphenate.EMCallBack;

/**
 * Created by admin on 2016/9/30.
 */
public class LogoutEm {
    public Activity mActivity;
    public LogoutEm(Activity activity){
        mActivity = activity;
    }

    public static void logout(){
        DemoHelper.getInstance().logout(true,new EMCallBack() {

            @Override
            public void onSuccess() {
               /* runOnUiThread(new Runnable() {
                    public void run() {
                        // show login screen
                        ((PersonalFriendsActivity) getActivity()).finish();
//                        startActivity(new Intent(getActivity(), EmLoginActivity.class));

                    }
                });*/
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
}
