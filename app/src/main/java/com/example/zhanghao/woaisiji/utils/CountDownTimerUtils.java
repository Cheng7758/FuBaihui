package com.example.zhanghao.woaisiji.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;


/**
 * Created by admin on 2016/9/1.
 */
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setEnabled(false); // 设置不可点击
        mTextView.setText("重新发送"+millisUntilFinished/1000);
        // 设置按钮为灰色，这时按钮是不能点击的
        mTextView.setTextColor(Color.parseColor("#c0c0c0"));

        /**
         * 超链接 URLSpan
         * 文字背景颜色 BackgroundColorSpan
         * 文字颜色 ForegroundColorSpan
         * 字体大小 AbsoluteSizeSpan
         * 粗体、斜体 StyleSpan
         * 删除线 StrikethroughSpan
         * 下划线 UnderlineSpan
         * 图片 ImageSpan
         * http://blog.csdn.net/ah200614435/article/details/7914459
         */
        // 获取按钮上的文字
//        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.GRAY);
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */
        // 将倒计时时段设置为灰色
//        spannableString.setSpan(span,0,2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setEnabled(true);
        mTextView.setTextColor(Color.parseColor("#2b9734"));
    }
}
