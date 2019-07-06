package com.example.zhanghao.woaisiji.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.PersonalFriendsMessageActivity;
import com.example.zhanghao.woaisiji.friends.ui.AddContactActivity;

/**
 * Created by admin on 2016/10/8.
 */
public class DialogLoveDriver extends Dialog{
    private Context context;
    public DialogLoveDriver(Context context) {
        super(context);
        this.context = context;
//        this.position = position;
        setCanceledOnTouchOutside(true);
    }

    public DialogLoveDriver(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogLoveDriver(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.TOP;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        setContentView(R.layout.dialog_love_driver_social);
        Button btnAddNewFriend = (Button) findViewById(R.id.btn_add_new_friend);
        Button btnNesMessage = (Button) findViewById(R.id.btn_new_message);
        btnAddNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddContactActivity.class));
                dismiss();
            }
        });
        btnNesMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PersonalFriendsMessageActivity.class));
                dismiss();
            }
        });
    }
}
