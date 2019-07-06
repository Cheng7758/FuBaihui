package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;

/**
 * Created by zzz on 2016/12/12.
 */

public class PaySuccessActivity extends Activity {

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private TextView tvGoodsNum;
    private TextView tvGoodsPrice;
    private Button btnBackMall;
    private String orderNum;
    private Double goodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        orderNum = getIntent().getStringExtra("order_num");
        goodPrice = getIntent().getDoubleExtra("good_price",0.00);

        initView();
    }

    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Activity activity:PublicActivityList.activityList){
                    activity.finish();
                }
            }
        });
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("支付成功");

        tvGoodsNum = (TextView) findViewById(R.id.tv_goods_num);
        tvGoodsNum.setText(orderNum);
        tvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        if (goodPrice == null){
            goodPrice = 0.00;
        }
        tvGoodsPrice.setText("￥"+goodPrice);

        btnBackMall = (Button) findViewById(R.id.btn_back_mall);
        btnBackMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Activity activity:PublicActivityList.activityList){
                    activity.finish();
                }
            }
        });
    }
}
