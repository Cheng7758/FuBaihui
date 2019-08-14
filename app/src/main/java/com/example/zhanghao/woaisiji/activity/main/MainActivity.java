package com.example.zhanghao.woaisiji.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.PersonalWalletActivity;
import com.example.zhanghao.woaisiji.activity.home.ZxingDetailsActivity;
import com.example.zhanghao.woaisiji.activity.login.LoginActivity;
import com.example.zhanghao.woaisiji.activity.merchant.MerchantPayActivity;
import com.example.zhanghao.woaisiji.bean.Update;
import com.example.zhanghao.woaisiji.fragment.BaseFragment;
import com.example.zhanghao.woaisiji.fragment.DriverReviewFragment;
import com.example.zhanghao.woaisiji.fragment.HomePageFragment;
import com.example.zhanghao.woaisiji.fragment.MyFragment;
import com.example.zhanghao.woaisiji.fragment.ShoppingCarFragment;
import com.example.zhanghao.woaisiji.fragment.home.CommentFragment;
import com.example.zhanghao.woaisiji.fragment.home.NewsZixunFragment;
import com.example.zhanghao.woaisiji.friends.Constant;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.friends.ui.GroupsActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.KeyPool;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.example.zhanghao.woaisiji.viewpage.NoScrollViewPager;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.loveplusplus.update.UpdateChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private android.app.AlertDialog.Builder conflictBuilder;//下线通知框
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    public boolean isConflict = false;
    private boolean isCurrentAccountRemoved = false;
    protected static final String TAG = "MainActivity";
    private int currentTabIndex;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    private Update update;

    private HashMap<Integer, BaseFragment> mSavedFragment = new HashMap<Integer, BaseFragment>();

    private NoScrollViewPager vp_main_switch_function;
    private RadioButton rb_home_page, rb_driver_dynamic, rb_shopping_car, rb_personal_setting,
            rb_news_information;
    private MainFragmentAdapter pagerAdapter;
    private long touchTime = 0;
    private int flags = 1;
    private SharedPreferences mSharedPreferences;
    private CommentFragment commentFragment;
    private ShoppingCarFragment shoppingCarFragment;

    @Override
    public void onBackPressed() {
        // 模板自动生成的，大概是说如果左侧抽屉栏被打开，按返回键的时候关闭抽屉栏而不是退出程序
        long currentTime = System.currentTimeMillis();
        if (currentTime - touchTime > 2000) {
            Snackbar.make(vp_main_switch_function, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//实例化控件
        initData();//网络请求数据，即页面显示
        initListener();//初始化监听
        /*if (!TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
            qianbao();
        }*/
        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
        if (WoAiSiJiApp.isUpdateTip == false) {
            UpdateChecker.checkForDialog(MainActivity.this);
            WoAiSiJiApp.isUpdateTip = true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
//        update();//检测版本更新
        registerBroadcastReceiver();
    }

    private void qianbao() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespPersonalWallet respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(MainActivity.this, "yue", respPersonalWallet);
                } else {
                    if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        Toast.makeText(MainActivity.this, respPersonalWallet.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    private void initData() {
        mSavedFragment.put(0, new HomePageFragment());
//        mSavedFragment.put(1, new DriverReviewFragment());
        commentFragment = new CommentFragment();
//        mSavedFragment.put(3, new ShoppingCarFragment());
        mSavedFragment.put(1, commentFragment);
        mSavedFragment.put(2, new NewsZixunFragment());
        shoppingCarFragment = new ShoppingCarFragment();
        mSavedFragment.put(3, shoppingCarFragment);
        mSavedFragment.put(4, new MyFragment());
    }

    //控件初始化
    private void initView() {
        pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager());

        vp_main_switch_function = (NoScrollViewPager) findViewById(R.id.vp_main_switch_function);
        vp_main_switch_function.setAdapter(pagerAdapter);

        rb_home_page = (RadioButton) findViewById(R.id.rb_home_page);
        rb_driver_dynamic = (RadioButton) findViewById(R.id.rb_driver_dynamic);
        rb_shopping_car = (RadioButton) findViewById(R.id.rb_shopping_car);
        rb_personal_setting = (RadioButton) findViewById(R.id.rb_personal_setting);
        rb_news_information = (RadioButton) findViewById(R.id.rb_news_information);
    }

    private void initListener() {
        rb_home_page.setOnClickListener(this);
        rb_driver_dynamic.setOnClickListener(this);
        rb_shopping_car.setOnClickListener(this);
        rb_personal_setting.setOnClickListener(this);
        rb_news_information.setOnClickListener(this);
    }

    private void update() {
        try {
            final String currentversion = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            StringRequest questionRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_UPDATE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    transServerData(response);
                    if (update.getCode() == 200) {
                        if (update.getInfo() != null) {
                            String versioninfo = update.getInfo().getVersion_name();
                            if (!currentversion.equals(versioninfo)) {
                                //对话框提示有新版本，更新与否，点击更新跳转链接，不更新关闭
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("有新版本！");
                                builder.setMessage("是否更新？");
                                builder.setPositiveButton("去更新", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                                Toast.makeText(MainActivity.this, "gengxin", Toast.LENGTH_SHORT).show();
                                        //通过Intent打开系统浏览器访问网页
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(update.getInfo().getApk_url())));
                                    }
                                });
                                builder.setNegativeButton("不更新", null);
                                builder.show();
                            }
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
                    params.put("app_id", "1");
                    params.put("did", "0ebb87ed6c3355a2a45825dd0873ccff");
                    params.put("version_name", currentversion);
                    return params;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(questionRequest);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        update = gson.fromJson(response, Update.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        setIntent(intent);
//        intent = getIntent();
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        } else if (!TextUtils.isEmpty(intent.getStringExtra(KeyPool.ACTION_LOGINOUT)) &&
                KeyPool.ACTION_LOGINOUT.equals(intent.getStringExtra(KeyPool.ACTION_LOGINOUT))) {
            vp_main_switch_function.setCurrentItem(0, true);
            rb_home_page.setChecked(true);
        } else if (!TextUtils.isEmpty(intent.getStringExtra("GetintoType"))) {
            vp_main_switch_function.setCurrentItem(3, true);    //购物车fragment
            rb_shopping_car.setChecked(true);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
            rb_home_page.setChecked(true);
            vp_main_switch_function.setCurrentItem(1, false);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_page://首页
                vp_main_switch_function.setCurrentItem(0, false);
                break;
            case R.id.rb_driver_dynamic://服务点评
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    rb_home_page.setChecked(true);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));//登录页面
                } else {
                    vp_main_switch_function.setCurrentItem(1, false);
                }
                break;
            case R.id.rb_news_information://新闻资讯
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    rb_home_page.setChecked(true);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    vp_main_switch_function.setCurrentItem(2, false);
                }
                break;
            case R.id.rb_shopping_car://购物车
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    rb_home_page.setChecked(true);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    vp_main_switch_function.setCurrentItem(3, false);
                }
                break;
            case R.id.rb_personal_setting://我的
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    rb_home_page.setChecked(true);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    vp_main_switch_function.setCurrentItem(4, false);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!isConflict && !isCurrentAccountRemoved) {
//            if (currentTabIndex==0){
//                ((HomePageFragment)mSavedFragment.get(currentTabIndex)).updateUnreadLabel();
//            }
//        }
        //存储token
//        Log.e("-----token1",WoAiSiJiApp.token.toString());
//        mSharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString("token",WoAiSiJiApp.token);
//        editor.commit();//这是将数据提交

        if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
            vp_main_switch_function.setCurrentItem(0, false);
        }
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        if (commentFragment != null) {
            commentFragment.loadData(1);
        }
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();
    }

    EMMessageListener messageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            ((HomePageFragment) mSavedFragment.get(0)).refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                /*if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    RedPacketUtil.receiveRedPacketAckMessage(message);
                }*/
            }
            //end of red packet code
            ((HomePageFragment) getCurrentFragment(currentTabIndex)).refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Fragment currentFragment = getCurrentFragment(currentTabIndex);
                if (currentFragment instanceof HomePageFragment)
                    ((HomePageFragment) currentFragment).updateUnreadLabel();
                if (currentTabIndex == 0) {
                    ((HomePageFragment) mSavedFragment.get(currentTabIndex)).refreshUIWithMessageCurrentTabIndex0();
                } else if (currentTabIndex == 1) {
                    ((HomePageFragment) getCurrentFragment(currentTabIndex - 1)).refreshUIWithMessageCurrentTabIndex1();
                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onContactAgreed(String username) {
        }

        @Override
        public void onContactRefused(String username) {
        }
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    /**
     * 移除及下线------------------------------------------------------------------------------------------------
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        WoAiSiJiApp.setUid(null);
                        PrefUtils.setString(MainActivity.this, "uid", "");
                        WoAiSiJiApp.memberShipInfos = null;
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }
        }
    }

    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        WoAiSiJiApp.setUid(null);
                        PrefUtils.setString(MainActivity.this, "uid", "");
                        WoAiSiJiApp.memberShipInfos = null;
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    public Fragment getCurrentFragment(int position) {
        BaseFragment currentFragment = null;
        if (position >= 0) {
            currentTabIndex = position;
            currentFragment = mSavedFragment.get(position);
        }
        return currentFragment;
    }

    public class MainFragmentAdapter extends FragmentPagerAdapter {

        public MainFragmentAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCurrentFragment(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 111)
            shoppingCarFragment.onActivityResult(requestCode,resultCode,data);
        //扫码结果
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                //扫码失败
                Toast.makeText(MainActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
            } else {
                String result = intentResult.getContents();//返回值
                Log.e("----扫码结果", result.toString());
                //textView.setText("扫码结果：" + result);
                if (result.contains("http")) {
                    Intent intent = new Intent(MainActivity.this, ZxingDetailsActivity.class);
                    intent.putExtra("details", result);
                    startActivity(intent);
                } else if (result.contains("id")) {
                    Intent intent = new Intent(MainActivity.this, MerchantPayActivity.class);
                    intent.putExtra("store_id", result);
                    startActivity(intent);
                }
            }
        }
    }
}


