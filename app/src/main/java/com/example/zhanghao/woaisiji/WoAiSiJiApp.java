package com.example.zhanghao.woaisiji;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.network.manager.NetManager;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.bean.my.PersonalInfoBean;
import com.example.zhanghao.woaisiji.bean.my.PersonalWalletBean;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalWallet;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.SharedPrefrenceUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.http.Myserver;
import com.hyphenate.easeui.utils.SetUserInfoUtils;
import com.jcodecraeer.xrecyclerview.gold.UserManager;
import com.loveplusplus.update.AppUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//import android.support.multidex.MultiDex;

//import com.easemob.redpacketsdk.RedPacket;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class WoAiSiJiApp extends Application {
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "CircleDemo" + File.separator + "Images"
            + File.separator;
    private static Context context;
    // RequestQueue请求队列对象
    public static RequestQueue mRequestQueue;

    public static int APPLICATION_SHOP_TYPE ;

    //Token
    public static String token = null;
    //用户信息
    private static PersonalInfoBean currentUserInfo = null;
    public static PersonalInfoBean getCurrentUserInfo() {
        String temp = PrefUtils.getString(getContext(), "personal_info", "");
        if (currentUserInfo==null && !TextUtils.isEmpty(temp)) {
            Gson gon = new Gson();
            currentUserInfo = gon.fromJson(PrefUtils.getString(getContext(), "personal_info", ""),PersonalInfoBean.class);
        }
        return currentUserInfo;
    }

    public static void setCurrentUserInfo(PersonalInfoBean currentUserInfo) {
        WoAiSiJiApp.currentUserInfo = currentUserInfo;
    }

    //用户UID
    private static String uid = null;
    public static String getUid() {
        if (uid==null)
            uid = PrefUtils.getString(getContext(),"uid","");
        return uid;
    }
    public static void setUid(String currentUid) {
//        if (!TextUtils.isEmpty(currentUid)){
            uid = currentUid ;
//        }
        if (currentUid == null)
            return;
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        com.hyphenate.easeui.http.NetManager.getNetManager().getMyService(Myserver.url)
                .getFriendsBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SetUserInfoUtils.Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SetUserInfoUtils.Bean value) {
                        Log.e("-----username",value.toString());

                        if (value.code == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(value.toString());
                                jsonObject = jsonObject.getJSONObject("data");
                                jsonObject = jsonObject.getJSONObject(uid);

                                    String nickname = jsonObject.getString("nickname");

                                String headpic = jsonObject.getString("headpic");
                                UserManager.myId = uid;
                                UserManager.myName = nickname;
                                UserManager.myPic = "http://wasj.zhangtongdongli.com" +headpic;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            qianbao();
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
    private static void qianbao() {
        StringRequest registerRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_MY_PERSONAL_INFO_MY_WALLET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                RespPersonalWallet respPersonalWallet = null;
                try {
                    respPersonalWallet = gson.fromJson(response, RespPersonalWallet.class);
                }catch (Exception e){

                }if (respPersonalWallet == null)
                    return;
                SharedPrefrenceUtils.remove(ActivityUtils.getTopActivity(), "yue");
                if (respPersonalWallet.getCode() == 200) {
                    SharedPrefrenceUtils.putObject(ActivityUtils.getTopActivity(), "yue", respPersonalWallet);
                    PersonalWalletBean data = respPersonalWallet.getData();
                    UserManager.silver  = data.getSilver();
                    UserManager.balance = data.getBalance();
                    UserManager.gold  = data.getScore();
                    UserManager.storeGold = data.getStore_score();
                } else {
                    if (!TextUtils.isEmpty(respPersonalWallet.getMsg()))
                        ToastUtils.showShort(respPersonalWallet.getMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityUtils.getTopActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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
    // 会员信息的对象设置为全局变量
    public static MemberShipInfosBean memberShipInfos;
    // 好友列表设置为全局变量
    public static Map<String,EaseUser> m;
    // 记录是否已经初始化
    private boolean isInit = false;

    private static Context mContext;


    private static WoAiSiJiApp instance;
    // login user name
    public final String PREF_USERNAME = "username";
    public static String currentUserNick = "";
    // 是否已经提示更新
    public static boolean isUpdateTip = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                Log.e("AppCrash", "onCrash: ", e);
            }
        });
        //初始化上下文
        context = getApplicationContext();
        //获取主线程id
//        mainThreadId = android.os.Process.myTid();
        memberShipInfos = new MemberShipInfosBean();
        m = new Hashtable<>();

        initImageLoader();

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        // xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        initImageLoader();

        // 初始化环信SDK
        initEasemob();

        instance = this;
        //初始化全局上下文
        mContext = this;

        //init demo helper
        DemoHelper.getInstance().init(context);
        //red packet code : 初始化红包上下文，开启日志输出开关
       /* RedPacket.getInstance().initContext(context);
        RedPacket.getInstance().setDebugMode(true);*/
        //end of red packet code

        initBugly();    //初始化腾讯bug管理平台

        //初始化网络模块
        NetManager.init(new NetManager.Builder(ServerAddress.URL_SERVER));
    }

    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugly() {
        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
        */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(AppUtils.getVersionCode(getApplicationContext())));   //App的版本
        strategy.setAppPackageName(AppUtils.getVersionName(getApplicationContext()));   //App的包名
        strategy.setAppReportDelay(20000);                          //Bugly会在启动20s后联网同步数据

        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*/
        CrashReport.initCrashReport(getApplicationContext(), "f7f4f6698b", true ,strategy);
    }

    //获取全局上下文
    public static Context getGlobalApplication(){
        return mContext;
    }

    public static WoAiSiJiApp getInstance() {
        return instance;
    }

   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    public static Context getContext(){
        return context;
    }

    public static void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
//        ImageLoader.getInstance().init(config);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }

    private void initEasemob() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }
        /**
         * SDK初始化的一些配置
         * 关于 EMOptions 可以参考官方的 API 文档
         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
         */
        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("guaju");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，TODO 这个暂时有bug，上层收不到发送回执
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
        options.setRequireServerAck(true);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(context, options);

        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(false);

        // 设置初始化已经完成
        isInit = true;
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }
}
