package com.example.zhanghao.woaisiji.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/*import com.yiw.circledemo.MyApplication;
import com.yiw.circledemo.R;
import com.yiw.circledemo.bean.FavortItem;
import com.yiw.circledemo.spannable.CircleMovementMethod;
import com.yiw.circledemo.spannable.SpannableClickable;*/

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.FavortItem;
import com.example.zhanghao.woaisiji.spannable.CircleMovementMethod;
import com.example.zhanghao.woaisiji.spannable.SpannableClickable;

import java.util.List;

/**
 * Created by yiwei on 16/7/9.
 */
public class PraiseListView extends TextView {


    private int itemColor;
    private int itemSelectorColor;
//    private List<FavortItem> datas;
    private String favorNum = null;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PraiseListView(Context context) {
        super(context);
    }

    public PraiseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public PraiseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色             /*praise_item_default*/
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.colorAccent));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        }finally {
            typedArray.recycle();
        }
    }

   /* public List<FavortItem> getDatas() {
        return datas;
    }
    public void setDatas(List<FavortItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }*/
    public void setDatas(String favorNum) {
//        this.datas = datas;
        this.favorNum = favorNum;
        notifyDataSetChanged();
    }


    public void notifyDataSetChanged(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (!("0".equals(favorNum))){
            //添加点赞图标
            builder.append(setImageSpan());

            SpannableString subjectSpanText = new SpannableString("有"+favorNum+"人觉得很赞");
            builder.append(subjectSpanText);
        }
        /*if(datas != null && datas.size() > 0){

            FavortItem item = null;
            for (int i=0; i<datas.size(); i++){
                item = datas.get(i);
                if(item != null){
                    builder.append(setClickableSpan(item.getUser().getName(), i));
                    if(i != datas.size()-1){
                        builder.append(", ");
                    }
                }
            }

        }*/

        setText(builder);
        setMovementMethod(new CircleMovementMethod(itemSelectorColor));
    }


    private SpannableString setImageSpan(){
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(WoAiSiJiApp.getContext(), R.drawable.icon_praise, DynamicDrawableSpan.ALIGN_BASELINE),
                0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, final int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor){
                                    @Override
                                    public void onClick(View widget) {
                                        if(onItemClickListener!=null){
                                            onItemClickListener.onClick(position);
                                        }
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }


    public static interface OnItemClickListener{
        public void onClick(int position);
    }
}
