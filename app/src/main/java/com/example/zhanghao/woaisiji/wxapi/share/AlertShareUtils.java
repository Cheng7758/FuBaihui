package com.example.zhanghao.woaisiji.wxapi.share;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.example.zhanghao.woaisiji.R;


public class AlertShareUtils {
    public static  void showShareAlert(final ShereFactory shereFactory) {
        if (shereFactory.getImgUrl()==null||shereFactory.getImgUrl().equals("")||shereFactory.getUrl()==null||shereFactory.getUrl().equals("")||shereFactory.getDesc()==null||shereFactory.getTitle() == null
                ){
        if (shereFactory.getImgBitmap() == null) {
            return;
        }
        }

        final Dialog dialog = new Dialog(shereFactory.getContext());
        int divierId = shereFactory.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);

        View divider = dialog.findViewById(divierId);
        if (divider != null)
            divider.setBackgroundColor(Color.parseColor("#00000000"));

        dialog.setContentView(R.layout.alert_share);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShereFactory mshereFactory = shereFactory;

                switch (v.getId()){

//                    case R.id.alertshare_weiboshare:
//                        mshereFactory.setAction(ShereFactory.ShereType.WEIBO);
//                        ShareHelper.facotry(mshereFactory).Share();
//                        dialog.dismiss();
//                        break;
                    case R.id.alertshare_weixinhaoyou:
                        mshereFactory.setAction(ShereFactory.ShereType.WEIXIN);
                        ShareHelper.facotry(mshereFactory).Share();
                        dialog.dismiss();
                        break;
                    case R.id.alertshare_weixinpengyou:
                        mshereFactory.setAction(ShereFactory.ShereType.WEIXINZONE);
                        ShareHelper.facotry(mshereFactory).Share();
                        dialog.dismiss();
                        break;
                     default:
//                         ToastUtil.toastText("分享失败！");
                         break;



                }
            }
        };
        dialog.getWindow().findViewById(R.id.alertshare_weixinhaoyou).setOnClickListener(v);
        dialog.getWindow().findViewById(R.id.alertshare_weixinpengyou).setOnClickListener(v);
//        dialog.getWindow().findViewById(R.id.alertshare_weiboshare).setOnClickListener(v);

        dialog.show();

    }
}
