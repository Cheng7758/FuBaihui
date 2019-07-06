package com.example.zhanghao.woaisiji.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.utils.PhoneJudgeUtils;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
//import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by admin on 2016/8/13.
 */
public class RegisterOneActivity extends Activity implements View.OnClickListener {
    private Button btnNext;
    private EditText editRegisterPhoneNumber;
    private ImageView registerBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicActivityList.activityList.add(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_one);

        initView();
        // 响应点击事件
        responseClickListener();

        // 判断手机号格式是否正确
        editRegisterPhoneNumber.addTextChangedListener(new TextWatcher() {
            private CharSequence mTemp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTemp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phoneNumber = mTemp.toString();

                if ((null != phoneNumber) && PhoneJudgeUtils.isPhone(phoneNumber)) {
                    btnNext.setBackgroundResource(R.drawable.btn_register_selected);
                    btnNext.setEnabled(true);
                    btnNext.setClickable(true);
                    btnNext.setOnClickListener(RegisterOneActivity.this);

                } else {
                    btnNext.setBackgroundResource(R.drawable.btn_register_default);
                    btnNext.setClickable(false);
                    btnNext.setEnabled(false);
                }
            }
        });

    }

    private void responseClickListener() {
        registerBack.setOnClickListener(this);
    }

    private void initView() {
        editRegisterPhoneNumber = (EditText) findViewById(R.id.edit_register_phone_number);
        registerBack = (ImageView) findViewById(R.id.iv_register_back);

        btnNext = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next://下一步
                Intent intent = new Intent(RegisterOneActivity.this, RegisterTwoActivity.class);
                intent.putExtra("phone",editRegisterPhoneNumber.getText().toString());
                startActivity(intent);
                break;
            case R.id.iv_register_back://返回按钮
                finish();
                break;
        }
    }

}
