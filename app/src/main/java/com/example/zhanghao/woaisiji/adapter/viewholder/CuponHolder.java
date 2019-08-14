package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhanghao.woaisiji.R;

public class CuponHolder extends BaseViewHolder {


    public TextView name;
    public TextView manjian;
    public TextView integral;

    public CuponHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        manjian = (TextView) view.findViewById(R.id.manjian);
        integral = (TextView) view.findViewById(R.id.integral);
    }
}
