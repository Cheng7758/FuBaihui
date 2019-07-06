package com.example.zhanghao.woaisiji.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/6.
 */
public class DialogWheelView extends Dialog {
    private int position;
    private Context context;
    private WheelView myWheelView;
    private Button btnConfirm;
    private DatePicker myDateBirthDay;
    private List<String> selectorList;
    private String[] sex = new String[]{"保密","男", "女"};
    private String[] marry = {"未婚","已婚","保密"};
    private String[] age = {"18岁以下","18-22岁","23-25岁","26-28岁","29-35岁","35岁以上"};

    public DialogWheelView(Context context, int position) {
        super(context, R.style.dialog_logout);
        this.context = context;
        this.position = position;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        setContentView(R.layout.dialog_wheelview_sex);
        myWheelView = (WheelView) findViewById(R.id.my_wheel_view);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        myDateBirthDay = (DatePicker) findViewById(R.id.my_date_birthday);
        selectorList = new ArrayList<>();
        switch (position) {

            case 1:   // 选择性别
//                setContentView(R.layout.dialog_wheelview_sex);
                myWheelView.setVisibility(View.VISIBLE);
                myDateBirthDay.setVisibility(View.GONE);
                selectorList.clear();
                for (int i =0;i<sex.length;i++){
                    selectorList.add(sex[i]);
                }
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(0);

                break;
            case 2:
                myDateBirthDay.setVisibility(View.VISIBLE);
                myWheelView.setVisibility(View.GONE);
                break;
            case 3:
                myDateBirthDay.setVisibility(View.GONE);
                myWheelView.setVisibility(View.VISIBLE);
                selectorList.clear();
                for (int i =0;i<sex.length;i++){
                    selectorList.add(marry[i]);
                }
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(0);
                break;
            case 4:
                myDateBirthDay.setVisibility(View.GONE);
                myWheelView.setVisibility(View.VISIBLE);
                selectorList.clear();
                selectorList.add("3000以下");
                selectorList.add("3000-5000");
                selectorList.add("5000-8000");
                selectorList.add("8000-10000");
                selectorList.add("10000-20000");
                selectorList.add("20000以上");
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(0);
                break;
            case 5:
                myDateBirthDay.setVisibility(View.GONE);
                myWheelView.setVisibility(View.VISIBLE);
                selectorList.clear();
                selectorList.add("初中");
                selectorList.add("高中");
                selectorList.add("大专/中专");
                selectorList.add("本科");
                selectorList.add("硕士");
                selectorList.add("博士");
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(0);
                break;


            //===========司机爱情选择  性别和年龄  ========================
            case 6:
                myWheelView.setVisibility(View.VISIBLE);
                myDateBirthDay.setVisibility(View.GONE);
                selectorList.clear();
                selectorList.add("不限");
                for (int i =1;i<sex.length;i++){
                    selectorList.add(sex[i]);
                }
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(1);
                break;
            case 7:
                myDateBirthDay.setVisibility(View.GONE);
                myWheelView.setVisibility(View.VISIBLE);
                selectorList.clear();
               for (String str:age){
                   selectorList.add(str);
               }
                myWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
                myWheelView.setWheelSize(3);
                myWheelView.setSkin(WheelView.Skin.Holo);
                myWheelView.setWheelData(selectorList);
                myWheelView.setSelection(0);
                break;
            //=============================================================

        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 1:
                    case 3:
                    case 6:
                    case 7:
                        listener.sendData(String.valueOf(myWheelView.getCurrentPosition()));
                        break;
                    case 4:
                    case 5:

                        if (listener != null) {
                            listener.sendData(selectorList.get(myWheelView.getCurrentPosition()));
                        }
                        break;
                    case 2:
                        if (listener != null){
                            String birthDay = String.format("%04d-%02d-%02d",myDateBirthDay.getYear(),myDateBirthDay.getMonth()+1,myDateBirthDay.getDayOfMonth());
                            listener.sendData(birthDay);
                        }
                        break;

                }

                dismiss();
            }
        });
    }

    private SendDataToActivityListener listener = null;

    public void setSendDataToActivityListener(SendDataToActivityListener listener) {
        this.listener = listener;
    }


    public interface SendDataToActivityListener {
        public void sendData(String data);
    }
}
