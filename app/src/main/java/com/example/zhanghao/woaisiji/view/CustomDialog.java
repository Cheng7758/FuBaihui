package com.example.zhanghao.woaisiji.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.utils.Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义请求滚动条
 */
public class CustomDialog extends Dialog {
	private ClipDrawable clipDrawable;
	private Animation anima;
	private Context context;
	private int style;
	private String msg;

	private ImageView iv_dialog_view_custom_loading;

	private Timer timer;
	private TimerTask tm;

	public CustomDialog(Context context, int style, String msg) {
		super(context, R.style.dialogNoDim);
		this.style = style;
		this.msg = msg;
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setLayout(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
		initCustomDialog(style, "请稍候...");
	}

//	private Runnable r = new Runnable() {
//
//		public void run() {
//			anim.start();
//
//		}
//	};

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			clipDrawable.setLevel(msg.what);
		};
	};

	@Override
	public void show() {
		Window window = getWindow();
		if (window == null || window.isActive())
			return;
		super.show();
		setLoadingRotate();

	}

	public void initCustomDialog(int style, String message) {
		setContentView(R.layout.dialog_view_progress_custom);
		android.view.WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.height = Util.getRealHeight(context);
		params.width = Util.getRealWidth(context);
		getWindow().setAttributes(params);

		iv_dialog_view_custom_loading = (ImageView) findViewById(R.id.iv_dialog_view_custom_loading);
	}


	/**
	 * 旋转动画
	 */
	private void setLoadingRotate(){
		iv_dialog_view_custom_loading.setVisibility(View.VISIBLE);
		RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		LinearInterpolator lin = new LinearInterpolator();
		rotate.setInterpolator(lin);//设置匀速效果
		rotate.setDuration(750);//设置动画速度
		rotate.setRepeatCount(-1);//设置重复次数
//		rotate.setFillAfter(false);//动画执行完后是否停留在执行完的状态
		rotate.setStartOffset(0);//执行前的等待时间
		iv_dialog_view_custom_loading.setAnimation(rotate);
	}

	
	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		iv_dialog_view_custom_loading.clearAnimation();
	}
}
