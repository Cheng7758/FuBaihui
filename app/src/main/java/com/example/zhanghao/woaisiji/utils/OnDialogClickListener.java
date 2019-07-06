package com.example.zhanghao.woaisiji.utils;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 反暴力点击监听（Click） ，可取代OnClickListener
 */
public abstract class OnDialogClickListener implements OnClickListener {
	/**当前监听绑定的dialog**/
	private Dialog dialog;

	/**
	 * View被点击后调用
	 * 
	 * @author 张晓晨
	 * @param v
	 *            被点击的View
	 */
	public abstract void onDialogClick(View v, Dialog dialog);

	@Override
	public void onClick(View v) {
		if (!BtnLock.isFastDoubleClick()) {
			onDialogClick(v, getDialog());
		}
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

}
