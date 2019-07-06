package com.example.zhanghao.woaisiji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2016/9/12.
 */
public class OptionCircleView extends View {


    public OptionCircleView(Context context) {
        super(context);
        init(null, 0);
    }

    public OptionCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public OptionCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int verticalCenter    =  getHeight() / 2;
        int horizontalCenter  =  getWidth() / 2;
        int circleRadius      = 100;
        Paint paint = new Paint();
        //paint.setAntiAlias(false);
        paint.setColor(Color.RED);
       // canvas.drawCircle( horizontalCenter, verticalCenter-250, circleRadius, paint);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
//        canvas.drawCircle( 102, 102, circleRadius, paint);
        for (int i=0;i<8;i++){
            canvas.drawCircle(horizontalCenter,verticalCenter,circleRadius,paint);
            circleRadius += 30;
        }

    }
}
