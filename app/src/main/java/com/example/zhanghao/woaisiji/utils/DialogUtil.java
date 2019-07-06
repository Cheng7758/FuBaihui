package com.example.zhanghao.woaisiji.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;

import com.example.zhanghao.woaisiji.view.CustomDialog;

public class DialogUtil {

    /**
     * progressDialog 黑色
     */
    public static final int PROGDIALOG_STYLE_BLACK = 4;

    /** 滚动条dialog **/
    private static Dialog progDialog = null;

    public static Dialog newProgInstance(Context ctx, int taskId,
                                         boolean cancelAble, DialogInterface.OnDismissListener onDismissListener) {
        return newInstance(ctx, taskId, cancelAble, null, null, null, null,
                null, null, onDismissListener);
    }
    public static Dialog newInstance(Context ctx, int style,
                                     boolean cancelAble, String title, String msg, View view,
                                     String leftBtnStr, String rightBtnStr,
                                     OnDialogClickListener onClickListener,
                                     DialogInterface.OnDismissListener onDismissListener) {
        Dialog resultDialog = null;
        switch (style) {
            case PROGDIALOG_STYLE_BLACK:
                progDialog = createProgressDialog(ctx, style, msg,
                        onDismissListener);
                resultDialog = progDialog;
                break;

            default:
                break;
        }
        return resultDialog;
    }
    /**
     * 创建ProgressDialog
     *
     * @return
     */
    public static Dialog createProgressDialog(Context ctx, int taskId,
                                              String msg, DialogInterface.OnDismissListener onDismissListener) {
        CustomDialog cus = new CustomDialog(ctx, taskId, msg);
        cus.setCanceledOnTouchOutside(false);
        cus.setCancelable(false);
        if (onDismissListener != null) {
            cus.setOnDismissListener(onDismissListener);
        }
        cus.setOwnerActivity((Activity) ctx);
        return cus;
    }


    /**
     * 显示进度条dialog
     *
     * @author 张晓晨
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showProgressDialog() {
        if (progDialog != null) {
            Activity activity=progDialog.getOwnerActivity();
            if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
            if (progDialog.isShowing()){
                progDialog.dismiss();
            }
            progDialog.show();
        }
    }
    /**
     * 取消进度条dialog
     *
     * @author 张晓晨
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void cancelProgressDialog() {
        if (progDialog != null && progDialog.isShowing()) {
            Activity activity=progDialog.getOwnerActivity();
            if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
            progDialog.dismiss();
        }
    }
}
