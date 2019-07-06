package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.zhanghao.woaisiji.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalMarryActivity extends Activity {
    private WheelView wheelView;
    private List<String> marryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_marry);

        wheelView = (WheelView) findViewById(R.id.my_wheelview);
        marryList.add("已婚");
        marryList.add("未婚");
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelView.setSkin(WheelView.Skin.Holo);
        wheelView.setWheelData(marryList);
    }
}
