package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.LoginInfosBean;
import com.example.zhanghao.woaisiji.bean.my.PersonalInfoBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.KeyPool;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.Util;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
@ContentView(R.layout.activity_personal_modify_nick)
public class PersonalModifyNickActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_title_bar_view_left_left)
    private ImageView iv_title_bar_view_left_left;
    @ViewInject(R.id.tv_title_bar_view_centre_title)
    private TextView tv_title_bar_view_centre_title;

    @ViewInject(R.id.et_personal_modify_nick_input_new_name)
    private EditText et_personal_modify_nick_input_new_name;
    @ViewInject(R.id.iv_personal_modify_nick_clean_content)
    private ImageView iv_personal_modify_nick_clean_content;
    @ViewInject(R.id.btn_personal_modify_nick_save)
    private Button btn_personal_modify_nick_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClickListener();
    }

    private void initClickListener() {
        iv_title_bar_view_left_left.setOnClickListener(this);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_personal_modify_nick_clean_content.setOnClickListener(this);
        btn_personal_modify_nick_save.setOnClickListener(this);
    }

    private void initView() {
        x.view().inject(this);
        tv_title_bar_view_centre_title.setText("修改昵称");
        /*et_personal_modify_nick_input_new_name.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                Log.e("---分分分", start + "======" + end);
                for (int i = start; i < end; i++) {
                    if (!Util.isMatchHanziOrNumberOrLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }, new InputFilter.LengthFilter(6)});*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.iv_personal_modify_nick_clean_content:
                et_personal_modify_nick_input_new_name.setText("");
                break;
            case R.id.btn_personal_modify_nick_save:
                if (TextUtils.isEmpty(et_personal_modify_nick_input_new_name.getText().toString())) {
                    Toast.makeText(this, "修改昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog();
                // 提交，保存到服务器
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_MODIFY_NICK,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dismissProgressDialog();
                                if (TextUtils.isEmpty(response))
                                    return;
                                Gson gson = new Gson();
                                RespNull respNull = gson.fromJson(response, RespNull.class);
                                if (respNull.getCode() == 200) {
                                    PersonalInfoBean currentUserInfo = gson.fromJson(PrefUtils.getString(PersonalModifyNickActivity.this, "personal_info", ""),
                                            PersonalInfoBean.class);
                                    currentUserInfo.setNickname(et_personal_modify_nick_input_new_name.getText().toString());
                                    String modifyNickNameStr = gson.toJson(currentUserInfo);
                                    PrefUtils.setString(PersonalModifyNickActivity.this, "personal_info", modifyNickNameStr);
                                    WoAiSiJiApp.setCurrentUserInfo(currentUserInfo);
                                    Toast.makeText(PersonalModifyNickActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent();
                                    intent.setAction(KeyPool.ACTION_MODIFY_PERSONAL_INFO);
                                    LocalBroadcastManager.getInstance(PersonalModifyNickActivity.this).sendBroadcast(intent);
                                    finish();
                                } else {
                                    if (!TextUtils.isEmpty(respNull.getMsg()))
                                        Toast.makeText(PersonalModifyNickActivity.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
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
                        Map<String, String> params = new HashMap<>();
                        params.put("uid", (WoAiSiJiApp.getUid()));
                        params.put("token", WoAiSiJiApp.token);
                        params.put("nickname", et_personal_modify_nick_input_new_name.getText().toString());
                        return params;
                    }
                };
                WoAiSiJiApp.mRequestQueue.add(stringRequest);
                break;
        }
    }
}
