package com.example.zhanghao.woaisiji.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.friends.util.Utils;
import com.squareup.picasso.Transformation;

/**
 * 圆角代码
 */
public class CircleCornerTransform implements Transformation {

    private Context mContext;

    public CircleCornerTransform(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int widthLight = source.getWidth();
        int heightLight = source.getHeight();

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        //先绘制颜色作为背景
        canvas.drawColor(ContextCompat.getColor(mContext, R.color.transparent));
        Paint paintBitmap = new Paint();
        paintBitmap.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));
        if (roundCorner > 0f)
            canvas.drawRoundRect(rectF, roundCorner, roundCorner, paintBitmap);
        else if (roundCornerPercentage > 0f)
            canvas.drawRoundRect(rectF, widthLight * roundCornerPercentage, widthLight * roundCornerPercentage, paintBitmap);
        else
            canvas.drawRoundRect(rectF, widthLight / 20, widthLight / 20, paintBitmap);

        paintBitmap.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paintBitmap);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "roundcorner";
    }

    private float roundCorner = 0f;

    /**
     * 直接设置数字
     *
     * @param roundCornerDp
     */
    public void setRoundCorner(float roundCornerDp) {
        if (roundCornerDp > 0.0f) {
            roundCornerDp = Utils.dp2px(mContext, roundCornerDp);
            this.roundCorner = roundCornerDp;
        }
    }

    /**
     * 设置为宽度的百分比
     *
     * @param roundCornerDp
     */
    private float roundCornerPercentage = 0f;

    public void setRoundWidthPercentage(float roundCornerPer) {
        if (roundCornerPer > 0.0f) {
            this.roundCornerPercentage = roundCornerPer;
        }
    }
}
