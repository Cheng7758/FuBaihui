package com.example.zhanghao.woaisiji.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;

import java.util.List;

/**
 * Created by admin on 2016/9/7.
 */
public class DialogHarvestInfo extends Dialog {

    private int position;
    private Context context;

    private TextView tvDialogTitle;
    private EditText editDailogContent;
    private Button btnDialogCancel;
    private Button btnDialogCertain;

    private String[] strTitle = {"请输入收件人姓名","请输入收件人电话","请输入邮政编码",
            "个人独白","职务","电话",
            "品牌","地址","城市",
            "民族"

    };

    /*protected DialogHarvestInfo(@NonNull Context context) {
        super(context);
    }*/
    public DialogHarvestInfo(Context context, int position) {
        super(context);
        this.context = context;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;

//        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        setContentView(R.layout.dialog_harvest_info);
        initView();

        initTitle();
        responseClickLister();

    }
    private void initView() {
        tvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        editDailogContent = (EditText) findViewById(R.id.edit_dialog_content);
        btnDialogCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        btnDialogCertain = (Button) findViewById(R.id.btn_dialog_certain);
    }

    private void initTitle() {
        if (position>0){
            tvDialogTitle.setText(strTitle[position-1]);
        }

        /*switch (position){
            case 1:
                tvDialogTitle.setText(strTitle[0]);
                break;
            case 2:
                tvDialogTitle.setText(strTitle[1]);
                break;
            case 3:
                tvDialogTitle.setText(strTitle[2]);
                break;
        }*/
    }

    private void responseClickLister() {
        btnDialogCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    String data = editDailogContent.getText().toString();
                    listener.sendData(data);
                }
                dismiss();
            }
        });
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    private SendDataListener listener;
    public void setSendDataListener(SendDataListener listener){
        this.listener = listener;
    }
    public interface SendDataListener{
        public void sendData(String data);
    }
}
