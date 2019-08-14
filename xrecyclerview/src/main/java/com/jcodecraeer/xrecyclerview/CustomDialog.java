package com.jcodecraeer.xrecyclerview;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;


/**
 * 自定义对话框
 *
 * @author Sun
 */
public class CustomDialog {
    /**
     * 加载中
     */
    public static Dialog LineDialog(final Context context,int style,String content) {
        final Dialog curDialog = new Dialog(context, R.style.dialog);
        curDialog.setContentView(R.layout.dialog_common_loading_indicator);
        SimpleViewSwitcher mProgressBar = (SimpleViewSwitcher) curDialog.findViewById(R.id.load_progressbar);
        TextView mContent = (TextView) curDialog.findViewById(R.id.loading_info);
        if (content != null){
            mContent.setText(content);
        } else {
            mContent.setVisibility(View.GONE);
        }
        if(style == ProgressStyle.SysProgress){
            mProgressBar.setView(new ProgressBar(context, null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(context);
            progressView.setIndicatorColor(ContextCompat.getColor(context,R.color.red_theme));
            progressView.setIndicatorId(style);
            mProgressBar.setView(progressView);
        }
        curDialog.setCanceledOnTouchOutside(true);

        return curDialog;
    }

}
