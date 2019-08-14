package com.example.zhanghao.woaisiji.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.LoveDriverFriendsActivity;
import com.example.zhanghao.woaisiji.activity.PersonalCouponActivity;
import com.example.zhanghao.woaisiji.activity.PersonalHarvestAddressActivity;
import com.example.zhanghao.woaisiji.activity.PersonalMyRecommendationActivity;
import com.example.zhanghao.woaisiji.activity.PersonalRecommendCodeActivity;
import com.example.zhanghao.woaisiji.activity.PersonalSettingActivity;
import com.example.zhanghao.woaisiji.activity.PersonalWalletActivity;
import com.example.zhanghao.woaisiji.activity.my.MerchantLoginActivitys;
import com.example.zhanghao.woaisiji.activity.my.MyCollectionActivity;
import com.example.zhanghao.woaisiji.activity.my.MyOrderActivity;
import com.example.zhanghao.woaisiji.activity.my.MyRecommendedActivity;
import com.example.zhanghao.woaisiji.bean.ImageUrlBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.CircleTransform;
import com.example.zhanghao.woaisiji.utils.DialogUtil;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.KeyPool;
import com.squareup.picasso.Picasso;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rl_home_my_page_top_user_head;
    public ImageUrlBean imageUrl;

    private TextView tvUserNick;
    private TextView tvUserPhone;
    private ImageView iv_view_my_fragment_head;

    //2019 6.13
    private RelativeLayout rl_home_my_page_mid_coupon, rl_home_my_page_mid_setting,
            rl_home_my_page_mid_kefu, rl_my_page_mid_referral_code, rl_home_my_page_mid_recommend,
            rl_my_page_mid_shipping_address, rl_my_page_mid_recruitment,rl_my_page_mid_merchant_login,
            rl_my_page_mid_merchant_phone;
    private LinearLayout ll_my_page_bottom_my_friends, ll_my_page_bottom_my_collection,
            ll_my_page_bottom_my_money_package, ll_my_page_bottom_my_cargo;
    private BroadcastReceiver modifyBroadcastReceiver;

    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_page_my_page, container, false);
        initView(view);
        // 响应点击事件
        responseClickListener();
        setPersonalInfoFromServer();

        //局部通知管理器
        modifyBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!TextUtils.isEmpty(intent.getAction()) && KeyPool.ACTION_MODIFY_PERSONAL_INFO.equals(intent.getAction())) {
                    setUserInfo();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KeyPool.ACTION_MODIFY_PERSONAL_INFO);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(modifyBroadcastReceiver, intentFilter);
        return view;
    }

    /**
     * 获取用户信息
     */
    public void setPersonalInfoFromServer() {
        if (WoAiSiJiApp.getCurrentUserInfo() != null) {
            setUserInfo();
        }
    }

    private void setUserInfo() {
        tvUserNick.setText(WoAiSiJiApp.getCurrentUserInfo().getNickname());
        tvUserPhone.setText(WoAiSiJiApp.getCurrentUserInfo().getUsername());
        Picasso.with(getActivity()).load(ServerAddress.SERVER_ROOT + WoAiSiJiApp.getCurrentUserInfo().getPic())
                .transform(new CircleTransform(getActivity()))
                .placeholder(R.drawable.ic_fubaihui)
                .error(R.drawable.ic_fubaihui)
                .into(iv_view_my_fragment_head);
    }

    private void responseClickListener() {
        rl_home_my_page_top_user_head.setOnClickListener(this);
        rl_home_my_page_mid_coupon.setOnClickListener(this);
        rl_home_my_page_mid_setting.setOnClickListener(this);
        rl_home_my_page_mid_kefu.setOnClickListener(this);
        rl_home_my_page_mid_recommend.setOnClickListener(this);
        rl_my_page_mid_referral_code.setOnClickListener(this);
        rl_my_page_mid_shipping_address.setOnClickListener(this);
        rl_my_page_mid_recruitment.setOnClickListener(this);
        rl_my_page_mid_merchant_login.setOnClickListener(this);
        rl_my_page_mid_merchant_phone.setOnClickListener(this);

        ll_my_page_bottom_my_collection.setOnClickListener(this);
        ll_my_page_bottom_my_money_package.setOnClickListener(this);
        ll_my_page_bottom_my_cargo.setOnClickListener(this);
        ll_my_page_bottom_my_friends.setOnClickListener(this);
    }

    private void initView(View rootView) {
        TextView titleBarCentre = (TextView) rootView.findViewById(R.id.tv_title_bar_view_centre_title);
        titleBarCentre.setText("个人中心");
        rl_home_my_page_top_user_head = (RelativeLayout) rootView.findViewById(R.id.rl_home_my_page_top_user_head);

        tvUserNick = (TextView) rootView.findViewById(R.id.tv_user_nick);
        tvUserPhone = (TextView) rootView.findViewById(R.id.tv_user_phone);
        iv_view_my_fragment_head = (ImageView) rootView.findViewById(R.id.iv_view_my_fragment_head);

        rl_home_my_page_mid_coupon = (RelativeLayout) rootView.findViewById(R.id.rl_home_my_page_mid_coupon);
        rl_home_my_page_mid_setting = (RelativeLayout) rootView.findViewById(R.id.rl_home_my_page_mid_setting);
        rl_home_my_page_mid_kefu = (RelativeLayout) rootView.findViewById(R.id.rl_home_my_page_mid_kefu);
        rl_home_my_page_mid_recommend = (RelativeLayout) rootView.findViewById(R.id.rl_home_my_page_mid_recommend);
        rl_my_page_mid_referral_code = (RelativeLayout) rootView.findViewById(R.id.rl_my_page_mid_referral_code);
        rl_my_page_mid_shipping_address = (RelativeLayout) rootView.findViewById(R.id.rl_my_page_mid_shipping_address);
        rl_my_page_mid_recruitment = (RelativeLayout) rootView.findViewById(R.id.rl_my_page_mid_recruitment);
        rl_my_page_mid_merchant_login = (RelativeLayout) rootView.findViewById(R.id.rl_my_page_mid_merchant_login);
        rl_my_page_mid_merchant_phone = (RelativeLayout) rootView.findViewById(R.id.rl_my_page_mid_merchant_phone);

        ll_my_page_bottom_my_friends = (LinearLayout) rootView.findViewById(R.id.ll_my_page_bottom_my_friends);
        ll_my_page_bottom_my_collection = (LinearLayout) rootView.findViewById(R.id.ll_my_page_bottom_my_collection);
        ll_my_page_bottom_my_money_package = (LinearLayout) rootView.findViewById(R.id.ll_my_page_bottom_my_money_package);
        ll_my_page_bottom_my_cargo = (LinearLayout) rootView.findViewById(R.id.ll_my_page_bottom_my_cargo);
    }
    long oldtime = 0;
    @Override
    public void onClick(View view) {
        if (System.currentTimeMillis()-oldtime<1000){
            return;
        }
        oldtime =  System.currentTimeMillis();

        switch (view.getId()) {
            case R.id.rl_home_my_page_mid_coupon://优惠券
                Intent intent = new Intent(getActivity(), PersonalCouponActivity.class);
//                intent.putExtra("store_id","1");
                startActivity(intent);
                break;
            case R.id.rl_home_my_page_mid_recommend://我的推荐
//                startActivity(new Intent(getActivity(), PersonalMyRecommendationActivity.class));
                startActivity(new Intent(getActivity(), MyRecommendedActivity.class));
                break;
            case R.id.rl_my_page_mid_referral_code://推荐码
                startActivity(new Intent(getActivity(), PersonalRecommendCodeActivity.class));
                break;
            case R.id.rl_my_page_mid_shipping_address://收货地址
                startActivity(new Intent(getActivity(), PersonalHarvestAddressActivity.class));
                break;
            case R.id.rl_my_page_mid_recruitment://商家入驻
//                startActivity(new Intent(getActivity(), RecruitmentActivity.class));
                DialogUtil.showProgressDialog();
                FunctionUtils.requestRecruitment(getActivity());
                break;
            case R.id.rl_my_page_mid_merchant_login://商家登录
                startActivity(new Intent(getActivity(), MerchantLoginActivitys.class));
                break;
            case R.id.rl_my_page_mid_merchant_phone://平台热线
                    Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18210521289"));
                    startActivity(intent2);
                break;
            case R.id.ll_my_page_bottom_my_friends://我的好友
                startActivity(new Intent(getActivity(), LoveDriverFriendsActivity.class));
                break;
            case R.id.ll_my_page_bottom_my_collection://收藏
                //Intent collectionIntent = new Intent(getActivity(),PersonalCollectionActivity.class);
                Intent collectionIntent = new Intent(getActivity(), MyCollectionActivity.class);
                collectionIntent.putExtra("class_name", "1");
                startActivity(collectionIntent);
                break;
            case R.id.ll_my_page_bottom_my_money_package://我的钱包
                startActivity(new Intent(getActivity(), PersonalWalletActivity.class));
                break;
            case R.id.ll_my_page_bottom_my_cargo://我的订单
//                startActivity(new Intent(getActivity(), PersonalOrderFormActivity.class));
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.rl_home_my_page_mid_setting://设置
            case R.id.rl_home_my_page_top_user_head://我爱司机个人资料部分
                startActivity(new Intent(getActivity(), PersonalSettingActivity.class));
                break;
        }
    }
}
