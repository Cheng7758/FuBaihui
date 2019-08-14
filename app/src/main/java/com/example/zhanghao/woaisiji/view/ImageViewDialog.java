package com.example.zhanghao.woaisiji.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.mvp.contract.CircleContract;
import com.example.zhanghao.woaisiji.utils.DpTransPx;
import com.example.zhanghao.woaisiji.utils.Util;
import com.example.zhanghao.woaisiji.wxapi.share.AlertShareUtils;
import com.example.zhanghao.woaisiji.wxapi.share.ShareHelper;
import com.example.zhanghao.woaisiji.wxapi.share.ShereFactory;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义请求滚动条
 */
public class ImageViewDialog extends Dialog implements View.OnClickListener{


	private Activity context;
	private ImageView iv_dialog_show_picture;
	private Button btn_dialog_show_sharebtn;
	public String sharetext;
	private Bitmap currentDialogPicture = null;

	public ImageViewDialog(Activity context,Bitmap bitmap) {
		super(context, R.style.dialogNoDim);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		currentDialogPicture = bitmap ;
		initCustomDialog();
	}
	public void initCustomDialog() {
		setContentView(R.layout.dialog_image_view);
		android.view.WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.height = DpTransPx.Dp2Px(context,390);
		params.width = DpTransPx.Dp2Px(context,300);
		getWindow().setAttributes(params);
		setCanceledOnTouchOutside(true);
		iv_dialog_show_picture = (ImageView) findViewById(R.id.iv_dialog_show_picture);
		btn_dialog_show_sharebtn = (Button) findViewById(R.id.btn_dialog_show_sharebtn);

		if (currentDialogPicture!=null) {
			iv_dialog_show_picture.setImageBitmap(currentDialogPicture);
			btn_dialog_show_sharebtn.setVisibility(View.VISIBLE);
			btn_dialog_show_sharebtn.setOnClickListener(this);
		}
	}

	@Override
	public void show() {
		super.show();
		if (btn_dialog_show_sharebtn != null&&sharetext != null)
			btn_dialog_show_sharebtn.setText("分享"+sharetext);
	}

	@Override
	public void onClick(View v) {

		AlertShareUtils.showShareAlert(new ShereFactory("",currentDialogPicture,context));

	}
}
