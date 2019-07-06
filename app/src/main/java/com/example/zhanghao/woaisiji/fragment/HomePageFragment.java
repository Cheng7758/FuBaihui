package com.example.zhanghao.woaisiji.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dreamlive.upmarqueeview.UPMarqueeView;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.AdvertisementWebViewActivity;
import com.example.zhanghao.woaisiji.activity.SliverIntegralShangCheng;
import com.example.zhanghao.woaisiji.activity.FBHStoreActivity;
import com.example.zhanghao.woaisiji.activity.ILoveShopActivity;
import com.example.zhanghao.woaisiji.activity.LoveDriverSociallyActivity;
import com.example.zhanghao.woaisiji.activity.PersonalFriendsMessageActivity;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.activity.QuestionAnswerActivity;
import com.example.zhanghao.woaisiji.activity.YangHuLianSuo;
import com.example.zhanghao.woaisiji.activity.login.LoginActivity;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoActivity;
import com.example.zhanghao.woaisiji.adapter.HomePagerBanner;
import com.example.zhanghao.woaisiji.add.ImageViewAdapter;
import com.example.zhanghao.woaisiji.bean.Update;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.friends.ui.ContactListFragment;
import com.example.zhanghao.woaisiji.friends.ui.ConversationListFragment;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespGlobalSlideShow;
import com.example.zhanghao.woaisiji.resp.RespHomePagerJXGG;
import com.example.zhanghao.woaisiji.tools.CircleCornerTransform;
import com.example.zhanghao.woaisiji.utils.DensityUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.loveplusplus.update.UpdateChecker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.zhanghao.woaisiji.global.ServerAddress.URL_GLOBAL_SLIDE_SHOW;
import static com.example.zhanghao.woaisiji.global.ServerAddress.URL_HOME_PAGE_JINGXUAN_GONGGAO;

public class HomePageFragment extends BaseFragment implements View.OnClickListener{

    private TextView unreadLabel;
    private android.app.AlertDialog.Builder accountRemovedBuilder;


    protected static final String TAG = "MainActivity";
    List<View> views = new ArrayList<>();
    private int currentItem = 0; // 当前图片的索引号

    private ConversationListFragment conversationListFragment;
    private ContactListFragment contactListFragment;

    /**
     * 精选
     */
    private List<RespHomePagerJXGG.JingXuan> BottomGoods = new ArrayList<>();

    /**
     * 司机公告
     */
    private List<RespHomePagerJXGG.GongGao> noticeListData = new ArrayList<>();
    private EditText et_home_page_search;

    private ViewPager viewPager;//顶部轮播和中部轮播
    private HomePagerBanner TopCarouselAdapter, BodyCarouselAdapter;//顶部轮播适配器和中部轮播适配器
    //顶部轮播数据源
    private List<RespGlobalSlideShow.GlobalSlideShow> TopCarousel = new ArrayList<>();
    //中部轮播数据源
    private List<RespGlobalSlideShow.GlobalSlideShow> BodyCarousel = new ArrayList<>();

    private LinearLayout topLayout;
    private UPMarqueeView upview1;

    private LinearLayout ll_home_page_fbh_store,ll_home_page_silver_store,ll_home_page_search_vip,
            ll_home_page_join_us;

    private ImageView[] imageViews;
    private LinearLayout bodyLayout;
    private ImageView banner;
    private ImageView[] goodImgs = new ImageView[4];
    private ImageView imageButton;
    private ImageView ivProductSearch;
    private LinearLayout ll_home_page_message;
    private Update update;

    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_page_home_page, container,false);
        constructionMethod(view);
        return view;
    }

    private void constructionMethod(View rootView) {
        //init views 消息数量
        unreadLabel = (TextView) rootView.findViewById(R.id.unread_msg_number);
        initView(rootView);//实例化控件
        initData();//网络请求数据，即页面显示
        initListener();//初始化监听
        //轮播的实现，顶部轮播和中部轮播
        TopCarouselAdapter = new HomePagerBanner(getActivity(), TopCarousel);
        viewPager.setAdapter(TopCarouselAdapter);

        BodyCarouselAdapter = new HomePagerBanner(getActivity(), BodyCarousel);

        if (WoAiSiJiApp.isUpdateTip == false) {
            UpdateChecker.checkForDialog(getActivity());
            WoAiSiJiApp.isUpdateTip = true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Log.d("TTTT", "弹出提示");
            }
        }
    }

    private void initData() {
        //APP顶部轮播
        HomePagerAdvertisement(URL_GLOBAL_SLIDE_SHOW);
        //司机公告&&精选
        HomePagerAdvertisement(URL_HOME_PAGE_JINGXUAN_GONGGAO);
    }

    //控件初始化
    private void initView(View rootView) {
        //八个选项
        ll_home_page_fbh_store = (LinearLayout) rootView.findViewById(R.id.ll_home_page_fbh_store);//司机商城
        ll_home_page_silver_store = (LinearLayout) rootView.findViewById(R.id.ll_home_page_silver_store);//银币商城
        ll_home_page_search_vip = (LinearLayout) rootView.findViewById(R.id.ll_home_page_search_vip);//搜索会员
        ll_home_page_join_us = (LinearLayout) rootView.findViewById(R.id.ll_home_page_join_us);//加入我们

        imageButton = (ImageView) rootView.findViewById(R.id.imageButton);//左上角扫一扫按钮
        et_home_page_search = (EditText) rootView.findViewById(R.id.et_home_page_search);//搜索框
        ivProductSearch = (ImageView) rootView.findViewById(R.id.iv_product_search);//白色搜索按钮
        ll_home_page_message = (LinearLayout) rootView.findViewById(R.id.ll_home_page_message);//消息
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_shouyegundong);//顶部轮播
        topLayout = (LinearLayout) rootView.findViewById(R.id.topLayout);

        banner = (ImageView) rootView.findViewById(R.id.app_body_banner);//条幅
        upview1 = (UPMarqueeView) rootView.findViewById(R.id.upview1);//上下轮播，司机公告右边

        //司机精选下面6张图片
        goodImgs[0] = (ImageView)rootView.findViewById(R.id.iv_goods1);
        goodImgs[1] = (ImageView) rootView.findViewById(R.id.iv_goods2);
        goodImgs[2] = (ImageView) rootView.findViewById(R.id.iv_goods3);
        goodImgs[3] = (ImageView) rootView.findViewById(R.id.iv_goods4);
    }


    private void initListener() {
        ll_home_page_fbh_store.setOnClickListener(this);
        ll_home_page_silver_store.setOnClickListener(this);
        ll_home_page_search_vip.setOnClickListener(this);
        ll_home_page_join_us.setOnClickListener(this);

        banner.setOnClickListener(this);

        // 扫一扫监听事件
        imageButton.setOnClickListener(this);
        // 搜索监听事件
        ivProductSearch.setOnClickListener(this);
        // 好友消息事件
        ll_home_page_message.setOnClickListener(this);

        et_home_page_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                    searchFunction();
                }
                return handled;
            }
        });
    }

    //轮播效果的实现
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    currentItem = (currentItem + 1) % TopCarousel.size();
                    viewPager.setCurrentItem(currentItem);
                    break;
            }
        }
    };

    private void startAd() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
        //scheduledExecutorService.scheduleAtFixedRate(new ScrollTask02(), 1, 3, TimeUnit.SECONDS);
    }

    /**
     * 轮播图自动滑动
     */
    private class ScrollTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendEmptyMessage(msg.what);
            }
        }
    }

    //点击事件-----------------------------------------------------------------------------------------------------
    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_product_search://搜索按钮
                // 商品搜多功能执行
                searchFunction();
                break;
            case R.id.ll_home_page_message://消息
                // 进入好友消息列表
                startActivity(new Intent(getActivity(), PersonalFriendsMessageActivity.class));
                break;
            case R.id.app_body_banner://中部条幅
                startActivity(new Intent(getActivity(), ILoveShopActivity.class));
                break;
            //福百惠商城
            case R.id.ll_home_page_fbh_store:
                Intent intent = new Intent(getActivity(), FBHStoreActivity.class);
                intent.putExtra("fromType",0);
                startActivity(intent);
                break;
//            case R.id.duihuanshangcheng://银币商城
            case R.id.ll_home_page_silver_store:
//                startActivity(new Intent(getActivity(), SliverIntegralShangCheng.class));
                break;
//            case R.id.aisiji://搜索会员
            case R.id.ll_home_page_search_vip://搜索会员
                if (!TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
                    //直接跳转到这里
                    startActivity(new Intent(getActivity(), LoveDriverSociallyActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
//            case R.id.iv_daily_sign://加盟
            case R.id.ll_home_page_join_us://加盟
                if (!TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
                    if (Build.VERSION.SDK_INT >= 23) {
                        int checkPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
                        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            Log.d("TTTT", "弹出提示");
                        }

                    }
                    startActivity(new Intent(getActivity(), JoinAutoActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.yanghuliansuo://养护连锁
                startActivity(new Intent(getActivity(), YangHuLianSuo.class));
                break;
            case R.id.sijiwenda://司机问答
                startActivity(new Intent(getActivity(), QuestionAnswerActivity.class));
                break;
            case R.id.sijikefu://司机客服
                if (TextUtils.isEmpty((WoAiSiJiApp.getUid()))){
                    String username = "l8888";
                    if (WoAiSiJiApp.getUid().equals(username)) {
                        // 客服ID
                        Toast.makeText(getActivity(), "自己不能与自己聊天", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                    }
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.imageButton://汽车扫描
                // 扫一扫功能执行
//                startActivity(new Intent(getContext(),CaptureActivity.class));
                /*new IntentIntegrator(this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                        //.setPrompt("请对准二维码")// 设置提示语
                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                        .initiateScan();// 初始化扫码*/
                break;
        }
    }

    /**
     * 搜索功能
     */
    private void searchFunction() {
        String keyWords = et_home_page_search.getText().toString();
        if (TextUtils.isEmpty(keyWords)){
            Toast.makeText(getActivity(),"关键字不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        et_home_page_search.setText("");
        startActivity(new Intent(getActivity(), JoinAutoActivity.class)
                .putExtra("SearchWord", keyWords).addFlags(1));
    }

    //调用这个方法后需要判断是否是0，是0的话执行  refreshUIWithMessageCurrentTabIndex0()方法
    public void refreshUIWithMessage() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
            }
        });
    }

    public void refreshUIWithMessageCurrentTabIndex0(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        });
    }
    public void refreshUIWithMessageCurrentTabIndex1(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(contactListFragment != null) {
                    contactListFragment.refresh();
                }
            }
        });
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get unread message count
     *消息数量
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for(EMConversation conversation: EMClient.getInstance().chatManager().getAllConversations().values()){
            if(conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal-chatroomUnreadMsgCount;
    }
    /**
     * 网络请求
     */
    private List<ImageView> mImageViewList;

    public void HomePagerAdvertisement(final String url) {
        int method = (!TextUtils.isEmpty(url)&&(URL_GLOBAL_SLIDE_SHOW).equals(url))?Request.Method.GET:Request.Method.POST;
        //网络请求
        StringRequest HomePagerAdvertisementRequest = new StringRequest(method,url, new Response.Listener<String>() {
            //请求网络数据成功
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                //APP顶部轮播
                if (url.equals(URL_GLOBAL_SLIDE_SHOW)){
                    mImageViewList = new ArrayList<>();
                    RespGlobalSlideShow slideShow =
                            gson.fromJson(response, RespGlobalSlideShow.class);//解析成对象
                    if (slideShow.getCode()!=200)
                        return;
                    for (RespGlobalSlideShow.GlobalSlideShow bean : slideShow.getData()) {
                        TopCarousel.add(bean);//数据源添加bean
                        final ImageView imageView = new ImageView(getActivity());
                        Glide.with(getActivity())
                                .load(ServerAddress.SERVER_ROOT + bean.getImg())
                                .into(new SimpleTarget<GlideDrawable>() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                        imageView.setBackground(resource);
                                    }
                                });
                        mImageViewList.add(imageView);
                    }
                    initImageView();
                    initViewPager();
                }else if (url.equals(URL_HOME_PAGE_JINGXUAN_GONGGAO)){
                    RespHomePagerJXGG homePagerJXGG =
                            gson.fromJson(response, RespHomePagerJXGG.class);//解析成对象
                    if (homePagerJXGG.getCode()!=200)
                        //司机公告
                        noticeListData.clear();
                    views.clear();
                    if (homePagerJXGG.getData().getGonggao()!=null && homePagerJXGG.getData().getGonggao().size()>0)
                        noticeListData.addAll(homePagerJXGG.getData().getGonggao());
                    setView(noticeListData);//中有点击事件
                    upview1.setViews(views);
                    //APP司机精选下面六张图片
                    BottomGoods.clear();
                    for (RespHomePagerJXGG.JingXuan bodyflash : homePagerJXGG.getData().getJingxuan()) {
                        BottomGoods.add(bodyflash);
                    }
                    for (int i = 0; i < BottomGoods.size(); i++) {
                        if ( TextUtils.isEmpty(BottomGoods.get(i).getPath()) || i >= 4) {
                            break;
                        }
                        if (i==3 &&i==2){
                            Picasso.with(getActivity())
                                    .load(ServerAddress.SERVER_ROOT + BottomGoods.get(i).getPath())
                                    .error(R.drawable.weixianshi)
                                    .transform(new CircleCornerTransform(getActivity()))
                                    .placeholder(R.drawable.weixianshi)
                                    .into(goodImgs[i]);
                        }else
                            Picasso.with(getActivity())
                                    .load(ServerAddress.SERVER_ROOT + BottomGoods.get(i).getPath())
                                    .error(R.drawable.weixianshi)
                                    .placeholder(R.drawable.weixianshi)
                                    .into(goodImgs[i]);
                        final int count = i;
                        goodImgs[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //六张点击跳到图文详情
                                Intent intent = new Intent(getActivity(), ProductDetailActivity2.class);
                                intent.putExtra("id", BottomGoods.get(count).getId());
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
            //请求网络失败
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //添加网络请求队列
        WoAiSiJiApp.mRequestQueue.add(HomePagerAdvertisementRequest);

    }


    private void setView(final List<RespHomePagerJXGG.GongGao> notice) {

        for (int i = 0; i < notice.size(); i++) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.itme_upmarquee, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            //进行对控件赋值
            tv1.setText(notice.get(i).getTitle().toString());
            moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
        upview1.setOnItemClickListener(new UPMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getActivity(), AdvertisementWebViewActivity.class);
                intent.putExtra("id", notice.get(position).getNid());
                intent.putExtra("content", notice.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

//  -顶部轮播viewpager的显示-----------------------------------------------------------------
    private void initViewPager() {
        viewPager.setAdapter(new ImageViewAdapter(getActivity(), mImageViewList));
        final int length = mImageViewList.size();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        if (getActivity()==null)
                            return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (position % length == length - 1) {
                                    viewPager.setCurrentItem((position + 1) % length, false);
                                } else {
                                    viewPager.setCurrentItem((position + 1) % length);
                                }
                            }
                        });
                    }
                }).start();

                mImageViewList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //顶部轮播点击进广告网站
                        Intent intent = new Intent(getActivity(), AdvertisementWebViewActivity.class);
                        intent.putExtra("content", TopCarousel.get(position).getTzurl());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mImageViewList.size()) - 1;

        //用来触发监听器
        viewPager.setCurrentItem(index);
    }

    private void initImageView() {
        for (int i = 0; i < mImageViewList.size(); i++) {
            // 初始化小圆点
            ImageView point = new ImageView(getActivity());
            point.setImageResource(R.drawable.shape_point_gray);
            // 初始化参数布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                // 从第二个设置左边距
                params.leftMargin = DensityUtils.dip2px(10, getActivity());
            }
            point.setLayoutParams(params);  // 设置布局参数
        }
    }
}
