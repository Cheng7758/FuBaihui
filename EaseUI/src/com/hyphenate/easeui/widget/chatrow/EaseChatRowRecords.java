package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.exceptions.HyphenateException;

public class EaseChatRowRecords extends EaseChatRow {
    private ImageView imageView;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_job;
    private View ll_gold;
    public EaseChatRowRecords(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }



    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        tv_name = (TextView) findViewById(R.id.tv_name_r);
        tv_phone = (TextView) findViewById(R.id.tv_phone_r);
        tv_job = (TextView) findViewById(R.id.tv_job_r);

    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        try {
            //tv_job.setText(message.getStringAttribute("nameTitle"));
            tv_name.setText(message.getStringAttribute("nameTitle2"));
            tv_phone.setText(message.getStringAttribute("goldStr"));
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onInflateView() {
        try {
            if(message.getStringAttribute("nameTitle").equals("金币转让")){
                inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                        R.layout.ease_row_received_records : R.layout.ease_row_sent_records,this);
            }else  if(message.getStringAttribute("nameTitle").equals("银币转让")) {
                inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                        R.layout.ease_row_received_records_s : R.layout.ease_row_sent_records_s,this);
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }


    }
    @Override
    protected void onBubbleClick() {

    }
}
