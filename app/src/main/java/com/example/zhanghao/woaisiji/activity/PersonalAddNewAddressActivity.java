package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.zhanghao.woaisiji.bean.AddHarvestResultBean;
import com.example.zhanghao.woaisiji.bean.CommentConfig;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.CommonUtils;
import com.example.zhanghao.woaisiji.view.DialogHarvestInfo;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/18.
 */
public class PersonalAddNewAddressActivity extends Activity implements View.OnClickListener {
    private static final int INFO_NAME = 1;
    private static final int INFO_MOBILE = 2;
    private static final int INFO_CODES = 3;
    private ImageButton ibLocationRegion;
    private ImageButton ibLocationStreet;

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private Button btnRegisterMore;

    private TextView tvHarvestUserName;
    private TextView tvHarvestMobile;
    private TextView tvHarvestCodes;


    private LinearLayout llHarvestUserName;
    private LinearLayout llHarvestMobile;
    private LinearLayout llHarvestLocation;
    private LinearLayout llHarvestStreet;
    private EditText editHarvestDetailAddress;
    private AddHarvestResultBean addHarvestResult;
    private String addUserName = "";
    private String addUserMobile = "";
    private String addUserCodes = "";
    private String addUserAddress = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add_new_address);

        initView();
        initData();
        // 响应点击事件
        responseClickListener();


    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
        btnRegisterMore.setOnClickListener(this);

        ibLocationRegion.setOnClickListener(this);
        ibLocationStreet.setOnClickListener(this);

        llHarvestUserName.setOnClickListener(this);
        llHarvestMobile.setOnClickListener(this);
        llHarvestLocation.setOnClickListener(this);
//        llHarvestStreet.setOnClickListener(this);
    }

    private void initData() {
        tvRegisterTitle.setText("添加新地址");
        btnRegisterMore.setText("保存");

        tvHarvestUserName.setText("");
        tvHarvestMobile.setText("");
        tvHarvestCodes.setText("");
        editHarvestDetailAddress.setText("");
        editHarvestDetailAddress.setHint("详细地址");

    }

    private void initView() {
        // 标题栏
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
//        btnRegisterMore.setBackground(null);

        // 收件人、电话，邮编
        tvHarvestUserName = (TextView) findViewById(R.id.tv_harvest_username);
        tvHarvestMobile = (TextView) findViewById(R.id.tv_harvest_mobile);
        tvHarvestCodes = (TextView) findViewById(R.id.tv_harvest_codes);


        llHarvestUserName = (LinearLayout) findViewById(R.id.ll_harvest_username);
        llHarvestMobile = (LinearLayout) findViewById(R.id.ll_harvest_mobile);
        llHarvestLocation = (LinearLayout) findViewById(R.id.ll_harvest_codes);
//        llHarvestStreet = (LinearLayout) findViewById(R.id.ll_harvest_street);
        editHarvestDetailAddress = (EditText) findViewById(R.id.edit_harvest_detail_address);
        ibLocationRegion = (ImageButton) findViewById(R.id.ib_location_codes);
        ibLocationStreet = (ImageButton) findViewById(R.id.ib_location_street);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.btn_register_more:
                addUserAddress = editHarvestDetailAddress.getText().toString();
                if (TextUtils.isEmpty(addUserName)){
                    Toast.makeText(PersonalAddNewAddressActivity.this,"收件人姓名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(addUserMobile)){
                    Toast.makeText(PersonalAddNewAddressActivity.this,"收件人电话不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(addUserAddress)){
                    Toast.makeText(PersonalAddNewAddressActivity.this,"收件人地址不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    // 点击保存，提交到服务器
                    saveDataToServer();
                    finish();
                }

                break;
            case R.id.ll_harvest_username:
                showHarvestInfoDialog(INFO_NAME);
                break;
            case R.id.ll_harvest_mobile:
                showHarvestInfoDialog(INFO_MOBILE);
                break;
            case R.id.ib_location_codes:
            case R.id.ll_harvest_codes:
                showHarvestInfoDialog(INFO_CODES);
//                startActivity(new Intent(PersonalAddNewAddressActivity.this, PersonalSelectRegionActivity.class));
                break;
//            case R.id.ib_location_street:
//            case R.id.ll_harvest_street:
//                startActivity(new Intent(PersonalAddNewAddressActivity.this, PersonalSelectStreetActivity.class));
//                break;
        }
    }

    private void saveDataToServer() {
        final StringRequest addHarvestRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ADD_HARVEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (addHarvestResult.code == 200){
//                    Toast.makeText(PersonalAddNewAddressActivity.this,addHarvestResult.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("new_nickname",addUserName);
                params.put("new_place",addUserAddress);
                params.put("new_mobile",addUserMobile);
                params.put("new_code",addUserCodes);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addHarvestRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        addHarvestResult = gson.fromJson(response, AddHarvestResultBean.class);
    }

    private void showHarvestInfoDialog(final int infos) {


        DialogHarvestInfo harvestDialog = new DialogHarvestInfo(PersonalAddNewAddressActivity.this, infos);
        harvestDialog.show();
        harvestDialog.setSendDataListener(new DialogHarvestInfo.SendDataListener() {
            @Override
            public void sendData(String data) {
                switch (infos) {
                    case INFO_NAME:
                        tvHarvestUserName.setText(data);
                        addUserName = data;
                        break;
                    case INFO_MOBILE:
                        tvHarvestMobile.setText(data);
                        addUserMobile = data;
                        break;
                    case INFO_CODES:
                        tvHarvestCodes.setText(data);
                        addUserCodes = data;
                        break;
                }
            }
        });
    }

}
