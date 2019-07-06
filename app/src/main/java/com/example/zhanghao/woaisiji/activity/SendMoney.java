package com.example.zhanghao.woaisiji.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.Versioninfo;
import com.example.zhanghao.woaisiji.activity.main.MainActivity;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class SendMoney extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView tv_register_title;
    private ImageView iv_register_back;
    private RadioGroup rg_send_select;
    private RadioButton rb_send_gold, rb_send_silver;
    private EditText et_send_person, et_send_number;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        initView();
        initData();
    }

    private void initData() {
        tv_register_title.setText("转赠余额");
        iv_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rg_send_select.setOnCheckedChangeListener(this);
    }

    private void initView() {
        tv_register_title = (TextView) findViewById(R.id.tv_register_title);
        iv_register_back = (ImageView) findViewById(R.id.iv_register_back);
        rg_send_select = (RadioGroup) findViewById(R.id.rg_send_select);
        rb_send_gold = (RadioButton) findViewById(R.id.rb_send_gold);
        rb_send_silver = (RadioButton) findViewById(R.id.rb_send_silver);
        et_send_person = (EditText) findViewById(R.id.et_send_person);
        et_send_number = (EditText) findViewById(R.id.et_send_number);
        btn_send = (Button) findViewById(R.id.btn_send);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.rb_send_gold ://送金币
                sendGold();//送金币
                break;
            case R.id.rb_send_silver://送银币
                sendSilver();//送银币
                break;
        }
    }

    private void sendSilver() {

    }

    private void sendGold() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //若选了送金币就获取那会获取到空值，应该在点button的那个事件获取
//                final String who = et_send_person.getText().toString();//转给谁
//                final String number = et_send_number.getText().toString();//金额数量
//
//                //去服务器送金币
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        final String url = "http://123.56.236.200/APP/Index/send";
//
//                        OkHttpUtils
//                                .get()
//                                .url(url)
//                                .addParams("myId", String.valueOf(getMyId()))//我的id
//                                .addParams("whoId", String.valueOf(getIdByName(who)))//别人的id
//                                .addParams("style", String.valueOf(0))//转金币还是银币,0代表金币
//                                .addParams("number", number)//数量
//                                .build()
//                                .execute(new StringCallback() {
//                                    @Override
//                                    public void onError(Request request, Exception e) {
//                                        Toast.makeText(SendMoney.this, "转赠失败", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Toast.makeText(SendMoney.this, "转赠成功", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
//                }).start();
            }
        });
    }

    private int getMyId() {
        return 0;
    }

    private int getIdByName(String who) {
        //输入姓名从服务器获取用户id，能不能直接传姓名，不传id，免得又多写方法
        return 0;
    }
}
