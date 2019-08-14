package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.PersonDetailInfoBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.DialogHarvestInfo;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/29.
 */
public class PersonalInfoDetailActivity extends BaseActivity implements View.OnClickListener{

    private String userId;
    private PersonDetailInfoBean detailInfoBean;

    private Map<String,String> personaInfos;

    private ProgressDialog progressDialog;

    private LinearLayout[] linearLayouts;
    private int linearLayoutId[] = new int[]{R.id.ll_detail_info_left01,
            R.id.ll_detail_info_left02,R.id.ll_detail_info_left03,R.id.ll_detail_info_left04,
            R.id.ll_detail_info_left05,R.id.ll_detail_info_left06,R.id.ll_detail_info_left07
    };
    private TextView[] textViewLefts;
    private int tvLeftId[] = new int[]{R.id.tv_detail_info_left01,R.id.tv_detail_info_left02,R.id.tv_detail_info_left03,
            R.id.tv_detail_info_left04,R.id.tv_detail_info_left05,R.id.tv_detail_info_left06,
            R.id.tv_detail_info_left07
    };
    private TextView[] textViewRights;
    private int tvRightId[] = new int[]{R.id.tv_detail_info_right01,R.id.tv_detail_info_right02,R.id.tv_detail_info_right03,
            R.id.tv_detail_info_right04,R.id.tv_detail_info_right05,R.id.tv_detail_info_right06,
            R.id.tv_detail_info_right07
    };
    private ImageView[] imageViews;
    private int imageViewId[] = new int[]{R.id.iv_detail_info_edit01,R.id.iv_detail_info_edit02,R.id.iv_detail_info_edit03,
            R.id.iv_detail_info_edit04,R.id.iv_detail_info_edit05,R.id.iv_detail_info_edit06,R.id.iv_detail_info_edit07
    };

    private String[] tvLeftValue = {"个人独白","职务","电话",
            "品牌","地址","城市",
            "民族"
    };

    /*,"逛街购物","宗教信仰",
            "作息时间","交际圈","最大消费",
            "月薪","购房","购车",
            "经济观念","籍贯","民族",
            "接受异地恋","何时结婚","愿与对方同住",
            "父母情况","兄弟姐妹"*/

    private String[] tvRightValue = new String[8];
    private Button[] btnInfoDetailClick;
    private RoundImageView rivInfoDetail;
    private Button btnDetailInfoBack;
    private TextView tvDetailInfoNickName;
    private boolean isSave = false;
    //    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_detail);//个人信息界面

        userId = ((WoAiSiJiApp)getApplication()).getUid();

        // 初始化布局
        initView();

        // 获取服务器用户信息
        getDataFromServer();
    }

    private void initView() {
        btnDetailInfoBack = (Button) findViewById(R.id.btn_detail_info_back);
        btnDetailInfoBack.setOnClickListener(this);
        tvDetailInfoNickName = (TextView) findViewById(R.id.tv_detail_info_nickname);

        textViewLefts = new TextView[8];
        textViewRights = new TextView[8];
        imageViews = new ImageView[8];
        linearLayouts = new LinearLayout[8];
        btnInfoDetailClick = new Button[2];
        for (int i=0;i<tvLeftValue.length;i++){
            textViewLefts[i] = (TextView) findViewById(tvLeftId[i]);
            textViewLefts[i].setText(tvLeftValue[i]);

            textViewRights[i] = (TextView) findViewById(tvRightId[i]);
            textViewRights[i].setText(tvRightValue[i]);

            imageViews[i] = (ImageView) findViewById(imageViewId[i]);
            linearLayouts[i] = (LinearLayout) findViewById(linearLayoutId[i]);
        }
        btnInfoDetailClick[0] = (Button) findViewById(R.id.btn_info_detail_greet);
        btnInfoDetailClick[1] = (Button) findViewById(R.id.btn_info_detail_add_friend);
        if (userId.equals(WoAiSiJiApp.getUid())){
            btnInfoDetailClick[0].setText("编辑资料");
            btnInfoDetailClick[1].setText("个性背景");
        }
        rivInfoDetail = (RoundImageView) findViewById(R.id.riv_info_detail);
        for (int i = 0; i<2;i++){
            btnInfoDetailClick[i].setOnClickListener(this);
        }
    }


    private void getDataFromServer() {
        StringRequest detailInfoRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PERSONAL_DETAIL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (detailInfoBean.code == 200){
                    ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+detailInfoBean.info.headpic,rivInfoDetail);
                    // 数据请求成功
                    setData();
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
                params.put("uid",userId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(detailInfoRequest);
    }



    private void transServerData(String response) {
        Gson gson = new Gson();
        detailInfoBean = gson.fromJson(response,PersonDetailInfoBean.class);
    }

    private void setData() {
        tvDetailInfoNickName.setText(detailInfoBean.info.nickname);

        /*if (detailInfoBean.info.signature != null){
            tvRightValue[0] = (String) detailInfoBean.info.signature;
        }*/
//        Toast.makeText(PersonalInfoDetailActivity.this, detailInfoBean.info.present,Toast.LENGTH_SHORT).show();
        if (detailInfoBean.info.present != null){
            tvRightValue[0] = detailInfoBean.info.present;
        }

        if (detailInfoBean.info.detail != null){
            if (detailInfoBean.info.detail.smoke != null){
                tvRightValue[1] = detailInfoBean.info.detail.smoke;
            }

            if (detailInfoBean.info.detail.liqueur != null){
                tvRightValue[3] = detailInfoBean.info.detail.liqueur;
            }

            if (detailInfoBean.info.detail.is_purchase != null){
                tvRightValue[4] = detailInfoBean.info.detail.is_purchase;
            }

            if (detailInfoBean.info.detail.jg_city != null){
                tvRightValue[5] = detailInfoBean.info.detail.jg_city;
            }

            if (detailInfoBean.info.detail.nation != null){
                tvRightValue[6] = detailInfoBean.info.detail.nation;
            }

        }
        for (int i=0;i<tvRightId.length;i++){
//            textViewRights[i] = (TextView) findViewById(tvRightId[i]);
            textViewRights[i].setText(tvRightValue[i]);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_detail_info_back:
                finish();
                break;
            case R.id.btn_info_detail_greet:
                if (userId.equals(WoAiSiJiApp.getUid())){
                    if (isSave){
//                        Toast.makeText(PersonalInfoDetailActivity.this,"保存资料",Toast.LENGTH_SHORT).show();
                        for (int i=0;i<imageViewId.length;i++){
                            imageViews[i].setVisibility(View.GONE);
                            linearLayouts[i].setEnabled(false);
                            linearLayouts[i].setClickable(false);
                        }
                        btnInfoDetailClick[0].setText("编辑资料");
                        isSave = false;

                        // 保存数据到服务器
                        saveInfoToServer();

                    }else {
//                        Toast.makeText(PersonalInfoDetailActivity.this,"编辑资料",Toast.LENGTH_SHORT).show();
                        for (int i=0;i<imageViewId.length;i++){
                            if (i!=2){
                                imageViews[i].setVisibility(View.VISIBLE);
                                linearLayouts[i].setEnabled(true);
                                linearLayouts[i].setClickable(true);
                                final int pos = i;

                                linearLayouts[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DialogHarvestInfo harvestDialog = new DialogHarvestInfo(PersonalInfoDetailActivity.this, pos+4);
                                        harvestDialog.show();
                                        harvestDialog.setSendDataListener(new DialogHarvestInfo.SendDataListener() {
                                            @Override
                                            public void sendData(String data) {
                                                tvRightValue[pos] = data;
                                                textViewRights[pos].setText(data);
                                            }
                                        });
                                    }
                                });
                            }

                        }
                        btnInfoDetailClick[0].setText("完成");
                        isSave = true;
                    }

                }else {
                    String username  = detailInfoBean.info.uid;
//                    String username  = detailInfoBean.info.name;
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(PersonalInfoDetailActivity.this, ChatActivity.class).putExtra("userId", username));
                }

                break;
            case R.id.btn_info_detail_add_friend:
                if (userId.equals(WoAiSiJiApp.getUid())){
                    Toast.makeText(PersonalInfoDetailActivity.this,"个性背景",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog = new ProgressDialog(this);
                    String stri = getResources().getString(R.string.Is_sending_a_request);
                    progressDialog.setMessage(stri);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    new Thread(new Runnable() {
                        public void run() {

                            try {
                                //demo use a hardcode reason here, you need let user to input if you like
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(detailInfoBean.info.uid, s);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        String s1 = getResources().getString(R.string.send_successful);
                                        Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (final Exception e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                        Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }

                break;
        }
    }

    private void saveInfoToServer() {
        StringRequest saveInfoRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_USER_EDIT_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AlterResultBean alterResult = gson.fromJson(response,AlterResultBean.class);
                Toast.makeText(PersonalInfoDetailActivity.this,alterResult.msg, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                String[] keys = {"present","smoke","mobile","liqueur","is_purchase","jg_city","nation"};
                for (int i=0;i<keys.length;i++){
                    if (i != 2){
                        if (tvRightValue[i] != null){
                            params.put(keys[i],tvRightValue[i]);
                        }else {
                            params.put(keys[i],"");
                        }
                    }
                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(saveInfoRequest);
    }
}
