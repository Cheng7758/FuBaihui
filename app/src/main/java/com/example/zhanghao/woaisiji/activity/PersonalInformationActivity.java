package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.AgeAndConstellation;
import com.example.zhanghao.woaisiji.view.DialogWheelView;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalInformationActivity extends Activity implements View.OnClickListener {
    private static final int INFO_SEX = 1;
    private static final int INFO_BIRTH = 2;
    private static final int INFO_MARRY = 3;
    private static final int INFO_INCOME = 4;
    private static final int INFO_EDU = 5;
    private static final int TYPE_01 = 1;
    private static final int TYPE_02 = 2;
    private ImageButton ibPersonalBirthDay;
    private ImageButton ibPersonalMarry;
    private ImageButton ibPersonalLocation;
    private ImageButton ibPersonalHomeTown;
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;

    private LinearLayout llInformationSex;
    private LinearLayout llInformationMobile;
    private LinearLayout llInformationBirthDay;
    private LinearLayout llInformationMarriage;
    private LinearLayout llInformationMonIncome;
    private LinearLayout llInformationEducation;
    private LinearLayout llInformationLocation;
    private LinearLayout llInformationHomeTown;

    private Button btnRegisterMore;
    private TextView tvInfoSex;
    private TextView tvInfoBirthDay;

    private TextView tvInfoAge;
    private TextView tvInfoConstellation;
    private TextView tvInfoMobile;
    private TextView tvInfoMarry;
    private TextView tvInfoMonIncome;
    private TextView tvInfoEducation;


    private String[] sex = new String[]{"保密", "男", "女"};
    private String[] marry = {"未婚", "已婚", "保密"};

    private AlterResultBean alterResult;

    // 对更改的信息进行临时保存
    String alterSex = "";
    String alterBirthDay = "";
    String alterMarry = "";
    String alterIncome = "";
    String alterEdu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        initView();
        initData();

        // 响应点击事件
        responseClickListener();

    }

    private void initData() {
        tvRegisterTitle.setText("编辑资料");
        btnRegisterMore.setText("确定");
        tvInfoSex.setText(sex[Integer.parseInt(WoAiSiJiApp.memberShipInfos.info.sex)]);
        tvInfoBirthDay.setText(WoAiSiJiApp.memberShipInfos.info.birthday);

        // 设置年龄和星座
        setAgeAndConstellation(WoAiSiJiApp.memberShipInfos.info.birthday);

        tvInfoMobile.setText(WoAiSiJiApp.memberShipInfos.info.mobile);
        tvInfoMarry.setText(marry[Integer.parseInt(WoAiSiJiApp.memberShipInfos.info.marriage)]);
        tvInfoMonIncome.setText(WoAiSiJiApp.memberShipInfos.info.offer);
        tvInfoEducation.setText(WoAiSiJiApp.memberShipInfos.info.school);
    }

    private void setAgeAndConstellation(String birthDay) {
        // 计算年龄和星座
        int age = 0;
        try {
            age = AgeAndConstellation.getAge(birthDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String constellation = "星座";
        try {
            constellation = AgeAndConstellation.getConstellation(birthDay);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvInfoAge.setText("" + age);
        tvInfoConstellation.setText(constellation);
    }


    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);

        tvInfoSex = (TextView) findViewById(R.id.tv_info_sex);
        tvInfoAge = (TextView) findViewById(R.id.tv_info_age);
        tvInfoConstellation = (TextView) findViewById(R.id.tv_info_constellation);
        tvInfoMobile = (TextView) findViewById(R.id.tv_info_mobile);
        tvInfoMarry = (TextView) findViewById(R.id.tv_info_marry);
        tvInfoMonIncome = (TextView) findViewById(R.id.tv_info_mon_income);
        tvInfoEducation = (TextView) findViewById(R.id.tv_info_education);
        tvInfoBirthDay = (TextView) findViewById(R.id.tv_info_birth_day);


//        findViewById(R.id.ll_information_age);
//        findViewById(R.id.ll_information_constellation);
        llInformationSex = (LinearLayout) findViewById(R.id.ll_information_sex);
        llInformationMobile = (LinearLayout) findViewById(R.id.ll_information_mobile);
        llInformationBirthDay = (LinearLayout) findViewById(R.id.ll_information_birthday);
        llInformationMarriage = (LinearLayout) findViewById(R.id.ll_information_marriage);
        llInformationMonIncome = (LinearLayout) findViewById(R.id.ll_information_mon_income);
        llInformationEducation = (LinearLayout) findViewById(R.id.ll_information_education);
        llInformationLocation = (LinearLayout) findViewById(R.id.ll_information_location);
        llInformationHomeTown = (LinearLayout) findViewById(R.id.ll_information_hometown);


        ibPersonalBirthDay = (ImageButton) findViewById(R.id.ib_personal_birthday);
        ibPersonalMarry = (ImageButton) findViewById(R.id.ib_personal_marry);
        ibPersonalLocation = (ImageButton) findViewById(R.id.ib_personal_location);
        ibPersonalHomeTown = (ImageButton) findViewById(R.id.ib_personal_hometown);
    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
        ibPersonalBirthDay.setOnClickListener(this);
        ibPersonalMarry.setOnClickListener(this);
        ibPersonalLocation.setOnClickListener(this);
        ibPersonalHomeTown.setOnClickListener(this);

        llInformationSex.setOnClickListener(this);
        llInformationMobile.setOnClickListener(this);
        llInformationBirthDay.setOnClickListener(this);
        llInformationMarriage.setOnClickListener(this);
        llInformationMonIncome.setOnClickListener(this);
        llInformationEducation.setOnClickListener(this);
        llInformationLocation.setOnClickListener(this);
        llInformationHomeTown.setOnClickListener(this);

        btnRegisterMore.setOnClickListener(this);
    }

    private void saveDataToServer(final int type) {
        String url ="";
        if (type == 1){
            url = ServerAddress.URL_UPDATE_INFO;
        }else {
            url = ServerAddress.URL_UPDATE_INFO_TWO;
        }
        StringRequest saveInfoRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);

//                Toast.makeText(PersonalInformationActivity.this, alterResult.msg, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonalInformationActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                if (type == 1){
                    params.put("sex", WoAiSiJiApp.memberShipInfos.info.sex);
                    params.put("birthday", WoAiSiJiApp.memberShipInfos.info.birthday);
                }else {
                    params.put("marriage", WoAiSiJiApp.memberShipInfos.info.marriage);
                    params.put("offer", WoAiSiJiApp.memberShipInfos.info.offer);
                    params.put("school", WoAiSiJiApp.memberShipInfos.info.school);
                }
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(saveInfoRequest);
    }

    public void transServerData(String response) {
        Gson gson = new Gson();
        alterResult = gson.fromJson(response, AlterResultBean.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.btn_register_more:
                boolean isAlter01 = false;
                boolean isAlter02 = false;
                if (!TextUtils.isEmpty(alterSex)) {
                    WoAiSiJiApp.memberShipInfos.info.sex = alterSex;
                    isAlter01 = true;
                }
                if (!TextUtils.isEmpty(alterBirthDay)) {
                    WoAiSiJiApp.memberShipInfos.info.birthday = alterBirthDay;
                    isAlter01 = true;
                }
                if (!TextUtils.isEmpty(alterMarry)) {
                    WoAiSiJiApp.memberShipInfos.info.marriage = alterMarry;
                    isAlter02 = true;
                }
                if (!TextUtils.isEmpty(alterIncome)) {
                    WoAiSiJiApp.memberShipInfos.info.offer = alterIncome;
                    isAlter02 = true;
                }
                if (!TextUtils.isEmpty(alterEdu)) {
                    WoAiSiJiApp.memberShipInfos.info.school = alterEdu;
                    isAlter02 = true;
                }
                if(isAlter01){
                    // 向服务器提交数据
                    saveDataToServer(TYPE_01);
                }
                if(isAlter02){
                    // 向服务器提交数据
                    saveDataToServer(TYPE_02);
                }
                finish();

                break;

            case R.id.ll_information_sex:
//                Toast.makeText(PersonalInformationActivity.this,"选择性别",Toast.LENGTH_SHORT).show();
                showWheelViewDialog(INFO_SEX);
                break;
            case R.id.ll_information_mobile:
                break;
            case R.id.ll_information_birthday:
            case R.id.ib_personal_birthday:
                showWheelViewDialog(INFO_BIRTH);
                break;
            case R.id.ll_information_marriage:
            case R.id.ib_personal_marry:
                showWheelViewDialog(INFO_MARRY);
                break;
            case R.id.ll_information_mon_income:
                showWheelViewDialog(INFO_INCOME);
                break;
            case R.id.ll_information_education:
                showWheelViewDialog(INFO_EDU);
                break;
//            case R.id.ll_information_location:
//            case R.id.ib_personal_location:
//                startActivity(new Intent(PersonalInformationActivity.this, PersonalAddressActivity.class));
//                break;
//            case R.id.ll_information_hometown:
//            case R.id.ib_personal_hometown:
//                startActivity(new Intent(PersonalInformationActivity.this, PersonalAddressActivity.class));
//                break;
        }
    }


    private void showWheelViewDialog(final int infos) {
        DialogWheelView dialogWheelView = new DialogWheelView(PersonalInformationActivity.this, infos);
        dialogWheelView.show();
        dialogWheelView.setSendDataToActivityListener(new DialogWheelView.SendDataToActivityListener() {
            @Override
            public void sendData(String data) {
                switch (infos) {
                    case INFO_SEX:
                        alterSex = data;
                        tvInfoSex.setText(sex[Integer.parseInt(data)]);
                        break;
                    case INFO_BIRTH:
                        alterBirthDay = data;
                        tvInfoBirthDay.setText(data);
                        setAgeAndConstellation(data);
                        break;
                    case INFO_MARRY:
                        alterMarry = data;
                        tvInfoMarry.setText(marry[Integer.parseInt(data)]);
                        break;
                    case INFO_INCOME:
                        alterIncome = data;
                        tvInfoMonIncome.setText(data);
                        break;
                    case INFO_EDU:
                        alterEdu = data;
                        tvInfoEducation.setText(data);
                        break;
                }

            }
        });
    }

}
