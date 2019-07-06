package com.example.zhanghao.woaisiji.view;

import android.content.Context;
import android.telecom.TelecomManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by admin on 2016/9/13.
 */
public class VerticalSlidePager extends ViewGroup {
    private Scroller mScroller;
    private Context mContext;
    public VerticalSlidePager(Context context) {
        super(context);
    }

    public VerticalSlidePager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mScroller=new Scroller(context);
        Toast.makeText(mContext,"aaaaaaaaaa",Toast.LENGTH_SHORT).show();
    }

    public VerticalSlidePager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int totalHeight=0;
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View childView=getChildAt(i);

            //          int measureHeight=childView.getMeasuredHeight();
            //          int measureWidth=childView.getMeasuredWidth();

            childView.layout(left, totalHeight, right, totalHeight+bottom);

            Log.d("VerticalSlidePager",""+totalHeight);
            Toast.makeText(mContext,""+totalHeight,Toast.LENGTH_SHORT).show();
            totalHeight += bottom;
        }
    }

    private VelocityTracker mVelocityTracker;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);


        int count=getChildCount();
        for(int i=0;i<count;i++){
            getChildAt(i).measure(width, height);
        }
        setMeasuredDimension(width, height);
    }

    private int mLastMotionY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocityTracker==null){
            mVelocityTracker=VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int action=event.getAction();

        float y=event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMotionY=(int) y;

                Log.d("montion", ""+getScrollY());
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY=(int) (mLastMotionY-y);
                scrollBy(0,deltaY);
                //mScroller.startScroll(0, getScrollY(), 0, deltaY);
                invalidate();

                mLastMotionY=(int) y;
                break;
            case MotionEvent.ACTION_UP:
                if(mVelocityTracker!=null){
                    mVelocityTracker.recycle();
                    mVelocityTracker=null;
                }

                if(getScrollY()<0){
                    mScroller.startScroll(0, -400, 0, 400);
                }else if(getScrollY()>(getHeight()*(getChildCount()-1))){
                    View lastView=getChildAt(getChildCount()-1);

                    mScroller.startScroll(0,lastView.getTop()+300, 0, -300);
                }else{
                    int position=getScrollY()/getHeight();
                    int mod=getScrollY()%getHeight();


                    if(mod>getHeight()/3){
                        View positionView=getChildAt(position+1);
                        mScroller.startScroll(0, positionView.getTop()-300, 0, +300);
                    }else{
                        View positionView=getChildAt(position);
                        mScroller.startScroll(0, positionView.getTop()+300, 0, -300);
                    }


                }
                invalidate();
                break;
//      case MotionEvent.ACTION_MASK:
//          if(getScrollY()<0){
//              mScroller.startScroll(0, 0, 0, 0);
//          }else if(getScrollY()>(getHeight()*(getChildCount()-1)){
//          }
//          invalidate();
//          break;
        }

        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
        }else{

        }
    }
}
