package com.example.zhanghao.woaisiji.activity.send;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.zhanghao.woaisiji.activity.OrderPreviewActivity;
import com.example.zhanghao.woaisiji.activity.PaymentMethodActivity;
import com.example.zhanghao.woaisiji.bean.GoldBuyBean;
import com.example.zhanghao.woaisiji.bean.SendGold;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.friends.ui.ChatFragment;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.serverdata.ObtainUserInfo;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.MemberShipInfosBean;
import com.hyphenate.easeui.ui.EaseChatFragment;

import java.util.HashMap;
import java.util.Map;

public class SendGoldActivity extends AppCompatActivity {
    private ImageView registerBack;
    private TextView tvRegisterTitle, tv_touid;
    private Button btn_gold;
    private String uid, userid, num, name;
    private EditText edt;
    private final static int REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gold);
        initView();
        btn_gold = (Button) findViewById(R.id.btn_gold);
        edt = (EditText) findViewById(R.id.gold_num);
        edt.addTextChangedListener(new OnTextChangeListener());
        btn_gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendGoldActivity.this);
                builder.setTitle("确认付款");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         goldBuy();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();

            }
        });
        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * EditText输入变化事件监听器
     */
    class OnTextChangeListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            //Editable s  可变字符串  表示输入后的字符串
            String numString = s.toString();
            num = numString;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    private void goldBuy() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GLOD_SEND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                SendGold sendGold = gson.fromJson(response, SendGold.class);
                if (sendGold.getCode() == 200) {
                    Intent intent = new Intent();
                    Log.d("123", num+uid+userid);
                    intent.setClass(SendGoldActivity.this, ChatActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("goldStr", num+"个金币");
                    bundle2.putString("uid", uid);
                    bundle2.putString("toChatUsername", userid);
                    bundle2.putString("nameTitle2", name);
                    bundle2.putString("nameTitle", "金币转让");
                    intent.putExtra("bundle2", bundle2);
                    setResult(0, intent);
                    SendGoldActivity.this.finish();
                    //                    intent.putExtra("goldStr", num);
//                    intent.putExtra("uid", uid);
//                    intent.putExtra("toChatUsername", userid);
//                    intent.putExtra("nameTitle2", name);
//                    intent.putExtra("nameTitle", "银币转让");

//                    EMMessage message = EMMessage.createTxtSendMessage("转账", toChatUsername);
//                    message.setAttribute("nameTitle","银币转让" );
//                    message.setAttribute("nameTitle2", "转给" + name);
//                    message.setAttribute("goldStr", "1个金币");
//                    message.setAttribute("records", true);
                    //startActivity(intent);
                } else {
                    Log.d("商品详情获取服务器废了", "" + response);
                }
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                params.put("user_pid", userid);
                params.put("num", num);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }


    private void initView() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        userid = intent.getStringExtra("uesr_pid");
        name = intent.getStringExtra("name");
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("金币转让");
        registerBack = (ImageView) findViewById(R.id.iv_register_back);
        tv_touid = (TextView) findViewById(R.id.tv_touid);
        tv_touid.setText(name);

    }
}
