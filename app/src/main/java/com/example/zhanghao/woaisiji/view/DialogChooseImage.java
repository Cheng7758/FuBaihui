package com.example.zhanghao.woaisiji.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;


/**
 * Created by admin on 2016/8/31.
 */
public class DialogChooseImage extends Dialog {

    private String title;
    private TextView tv_dialog_title;
    public DialogChooseImage(Context context) {
        super(context, R.style.dialog_logout);
        setCanceledOnTouchOutside(true);
    }public DialogChooseImage(Context context,String title) {
        super(context, R.style.dialog_logout);
        this.title = title;
        setCanceledOnTouchOutside(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = window.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(params);//设置宽度

        setContentView(R.layout.dialog_choose_image);
        findViewById(R.id.dialog_btn_cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        if (!TextUtils.isEmpty(title))
            tv_dialog_title.setText(title);
    }
    public void setClickCameraListener(View.OnClickListener listener) {
        findViewById(R.id.dialog_btn_camera).setOnClickListener(listener);
    }

    public void setClickGalleryListener(View.OnClickListener listener) {
        findViewById(R.id.dialog_btn_gallery).setOnClickListener(listener);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = window.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(params);//设置宽度
    }
}

