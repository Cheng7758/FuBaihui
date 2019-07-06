package com.example.zhanghao.woaisiji.activity.send;

import android.view.View;

public interface MyItemClickListener {
    void onItemClick(View view, int postion, String string);

    void onItemClick(View view, int postion, String string, int code);
}
