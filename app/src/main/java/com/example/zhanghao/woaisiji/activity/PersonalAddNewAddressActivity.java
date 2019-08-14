package com.example.zhanghao.woaisiji.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.view.DialogHarvestInfo;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加收货地址
 */
public class PersonalAddNewAddressActivity extends BaseActivity implements View.OnClickListener {
    private static final int INFO_NAME = 1;
    private static final int INFO_MOBILE = 2;
    private static final int INFO_CODES = 3;

    private String addUserName = "";
    private String addUserMobile = "";
    private String addUserCodes = "";
    private String addUserAddress = "";
    /**
     * TitleBar
     */
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_right_right_introduction, tv_title_bar_view_centre_title;
    private LinearLayout ll_personal_add_new_address_consignee, ll_personal_add_new_address_phone
            /*ll_personal_add_new_address_postcode*/;
    private TextView tv_personal_add_new_address_consignee, tv_personal_add_new_address_phone
            /*tv_personal_add_new_address_postcode*/;
    private EditText edit_personal_add_new_address_input_detail_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add_new_address);
        initView();
    }

    private void initView() {
        // 标题栏
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("添加收货地址");
        tv_title_bar_view_right_right_introduction = (TextView) findViewById(R.id.tv_title_bar_view_right_right_introduction);
        tv_title_bar_view_right_right_introduction.setText("保存");
        tv_title_bar_view_right_right_introduction.setVisibility(View.VISIBLE);
        tv_title_bar_view_right_right_introduction.setOnClickListener(this);

        // 收件人、电话，邮编
        tv_personal_add_new_address_consignee = (TextView) findViewById(R.id.tv_personal_add_new_address_consignee);
        tv_personal_add_new_address_phone = (TextView) findViewById(R.id.tv_personal_add_new_address_phone);
//        tv_personal_add_new_address_postcode = (TextView) findViewById(R.id.tv_personal_add_new_address_postcode);

        ll_personal_add_new_address_consignee = (LinearLayout) findViewById(R.id.ll_personal_add_new_address_consignee);
        ll_personal_add_new_address_consignee.setOnClickListener(this);
        ll_personal_add_new_address_phone = (LinearLayout) findViewById(R.id.ll_personal_add_new_address_phone);
        ll_personal_add_new_address_phone.setOnClickListener(this);
//        ll_personal_add_new_address_postcode = (LinearLayout) findViewById(R.id.ll_personal_add_new_address_postcode);
//        ll_personal_add_new_address_postcode.setOnClickListener(this);

        edit_personal_add_new_address_input_detail_address = (EditText) findViewById(R.id.edit_personal_add_new_address_input_detail_address);
        //修改收货地址
        tv_personal_add_new_address_consignee.setText(getIntent().getStringExtra("name"));
        tv_personal_add_new_address_phone.setText(getIntent().getStringExtra("phone"));
        edit_personal_add_new_address_input_detail_address.setText(getIntent().getStringExtra("address"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.tv_title_bar_view_right_right_introduction:
                addUserAddress = edit_personal_add_new_address_input_detail_address.getText().toString();
                if (TextUtils.isEmpty(addUserName)) {
                    Toast.makeText(PersonalAddNewAddressActivity.this, "收件人姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(addUserMobile)) {
                    Toast.makeText(PersonalAddNewAddressActivity.this, "收件人电话不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(addUserAddress)) {
                    Toast.makeText(PersonalAddNewAddressActivity.this, "收件人地址不能为空", Toast.LENGTH_SHORT).show();
                } /*else if (TextUtils.isEmpty(addUserCodes)) {
                    Toast.makeText(PersonalAddNewAddressActivity.this, "邮编不能为空", Toast.LENGTH_SHORT).show();
                }*/ else {
                    saveDataToServer();
                }
                break;
            case R.id.ll_personal_add_new_address_consignee:
                showHarvestInfoDialog(INFO_NAME);
                break;
            case R.id.ll_personal_add_new_address_phone:
                showHarvestInfoDialog(INFO_MOBILE);
                break;
            /*case R.id.ll_personal_add_new_address_postcode:
                showHarvestInfoDialog(INFO_CODES);
                break;*/
//            case R.id.ib_location_street:
//            case R.id.ll_harvest_street:
//                startActivity(new Intent(PersonalAddNewAddressActivity.this, PersonalSelectStreetActivity.class));
//                break;
        }
    }

    /**
     * 保存新的收货地址
     */
    private void saveDataToServer() {
        showProgressDialog();
        final StringRequest addHarvestRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_PERSONAL_RECEIVE_ADDRESS_ADD_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (!TextUtils.isEmpty(respNull.getMsg()))
                    Toast.makeText(PersonalAddNewAddressActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
                if (respNull.getCode() == 200) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mapString = new HashMap<>();
                mapString.put("uid", WoAiSiJiApp.getUid());
                mapString.put("new_code", addUserCodes);
                mapString.put("new_mobile", addUserMobile);
                mapString.put("new_place", addUserAddress);
                mapString.put("new_nickname", addUserName);
                return mapString;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addHarvestRequest);
    }

    private void showHarvestInfoDialog(final int infos) {
        DialogHarvestInfo harvestDialog = new DialogHarvestInfo(PersonalAddNewAddressActivity.this, infos);
        harvestDialog.show();
        harvestDialog.setSendDataListener(new DialogHarvestInfo.SendDataListener() {
            @Override
            public void sendData(String data) {
                switch (infos) {
                    case INFO_NAME:
                        tv_personal_add_new_address_consignee.setText(data);
                        addUserName = data;
                        break;
                    case INFO_MOBILE:
                        tv_personal_add_new_address_phone.setText(data);
                        addUserMobile = data;
                        break;
                    /*case INFO_CODES:
                        tv_personal_add_new_address_postcode.setText(data);
                        addUserCodes = data;
                        break;*/
                }
            }
        });
    }

}
