package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.uploadhead.PersonalDataActivity;
import com.example.zhanghao.woaisiji.bean.ImageUrlBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.CircleCornerTransform;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
//import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by admin on 2016/8/13.
 */
public class PersonalActivity extends Activity implements View.OnClickListener {
    private ImageButton ibEnter;
    private ImageButton ibPersonalSetting;
    private ImageButton ibPersonalWallet;
    private RelativeLayout rlUserHead;
//    public MemberShipInfosBean memberShipInfo;
    public ImageUrlBean imageUrl;

    private TextView tvUserNick;
    private TextView tvUserPhone;
    private ImageView ivHead;

    private LinearLayout llMyFriends;
    private LinearLayout llCollection;
    private LinearLayout llMyWallet;
    private LinearLayout llMyOrderForm;
    private LinearLayout llSetting;
    private String imgUrl;
    private ImageView ivRegisterBack;
    private Button btnLottery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();
        // 响应点击事件
        responseClickListener();

        // 从服务器获取会员信息
        obtainDataFromServer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        obtainImgUrlFromServer();
    }

    private void obtainImgUrlFromServer() {
        StringRequest imageRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerImageUrl(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if("".equals(WoAiSiJiApp.memberShipInfos.info.headpic)){
                    params.put("img_id", "");
                }else {
                    params.put("img_id", WoAiSiJiApp.memberShipInfos.info.headpic);
                }

                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(imageRequest);
    }

    public void addDataForInterface() {

        tvUserNick.setText(WoAiSiJiApp.memberShipInfos.info.nickname);
        tvUserPhone.setText(WoAiSiJiApp.memberShipInfos.info.username);


        // 请求服务器图片
        imgUrl = ServerAddress.SERVER_ROOT + imageUrl.path;
//        Log.d("activity-pic",imgUrl);
        // 头像加载
        ImageLoader.getInstance().displayImage(imgUrl,ivHead);
        Picasso.with(this).load(imgUrl).transform(new CircleCornerTransform(this))
                .error(R.drawable.icon_loading).into(ivHead);

    }


    public void obtainDataFromServer() {
        // 从服务器获取图片路径
        obtainImgUrlFromServer();
    }

    public void transServerImageUrl(String response) {
        Gson gson = new Gson();
        imageUrl = gson.fromJson(response, ImageUrlBean.class);
        // 为交互页面填充数据
        addDataForInterface();
    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
        ibEnter.setOnClickListener(this);
        ibPersonalSetting.setOnClickListener(this);
        ibPersonalWallet.setOnClickListener(this);
        rlUserHead.setOnClickListener(this);
        llMyFriends.setOnClickListener(this);

        llCollection.setOnClickListener(this);
        llMyWallet.setOnClickListener(this);
        llMyOrderForm.setOnClickListener(this);
        llSetting.setOnClickListener(this);

        btnLottery.setOnClickListener(this);
    }

    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);


        rlUserHead = (RelativeLayout) findViewById(R.id.rl_user_head);
        TextView tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("我");
        ibEnter = (ImageButton) findViewById(R.id.ib_enter);
        ibPersonalSetting = (ImageButton) findViewById(R.id.ib_personal_setting);
        ibPersonalWallet = (ImageButton) findViewById(R.id.ib_personal_wallet);

        tvUserNick = (TextView) findViewById(R.id.tv_user_nick);
        tvUserPhone = (TextView) findViewById(R.id.tv_user_phone);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        llMyFriends = (LinearLayout) findViewById(R.id.ll_my_friends);
        llCollection = (LinearLayout) findViewById(R.id.ll_collection);
        llMyWallet = (LinearLayout) findViewById(R.id.ll_my_wallet);
        llMyOrderForm = (LinearLayout) findViewById(R.id.ll_my_order_form);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting);

        btnLottery = (Button) findViewById(R.id.btn_lottery);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back://返回按钮
                finish();
                break;
            case R.id.ib_enter:
            case R.id.rl_user_head://我爱司机个人资料部分
                Intent intent = new Intent(PersonalActivity.this, PersonalDataActivity.class);
                intent.putExtra("imgUrl",imgUrl);
                startActivity(intent);
                break;
            case R.id.ll_my_friends://我的好友
                startActivity(new Intent(PersonalActivity.this, LoveDriverFriendsActivity.class));
                break;
            case R.id.ll_collection://收藏
                Intent collectionIntent = new Intent(PersonalActivity.this,PersonalCollectionActivity.class);
                collectionIntent.putExtra("class_name","1");
                startActivity(collectionIntent);
                break;
            case R.id.ll_my_order_form://我的订单
                startActivity(new Intent(PersonalActivity.this,PersonalOrderFormActivity.class));
                break;
            case R.id.ib_personal_setting:
            case R.id.ll_setting://设置
                startActivity(new Intent(PersonalActivity.this, PersonalSettingActivity.class));
                break;
            case R.id.ib_personal_wallet:
            case R.id.ll_my_wallet://我的钱包
                startActivity(new Intent(PersonalActivity.this, PersonalWalletActivity.class));
                break;

            case R.id.btn_lottery://抽奖
                startActivity(new Intent(PersonalActivity.this,WebViewLotteryActivity.class));
                break;
        }
    }
}
