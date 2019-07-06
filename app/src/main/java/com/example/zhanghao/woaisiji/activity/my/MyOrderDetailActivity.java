package com.example.zhanghao.woaisiji.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.my.ShopsDetails;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyOrderDetailActivity";
    private String mId;
    private String mGoods_id;
    private ImageView my_order_back;
    private TextView username;
    private TextView phone_number;
    private TextView shipping_address;
    private TextView order_sn;
    private TextView add_time;
    private ImageView goods_img;
    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_num;
    private TextView remark;
    private TextView total_price;
    private TextView discount;
    private ShopsDetails.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        getData();
        initView();
        initData();
    }

    private void initData() {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;
        Log.d(TAG, "uid: " + uid);
        Log.d(TAG, "token: " + token);
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        params.put("id", mId);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMyOrderDetailBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopsDetails value) {
                        if (value.getData() == null) {
                            Toast.makeText(MyOrderDetailActivity.this, "商品已下架", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("---orderDetail", value.getData().toString());
                        mData = value.getData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getData() {
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mGoods_id = intent.getStringExtra("goods_id");
        Log.d(TAG, "getData: " + mId);
    }

    private void initView() {
        my_order_back = (ImageView) findViewById(R.id.my_order_back);
        username = (TextView) findViewById(R.id.username);
        phone_number = (TextView) findViewById(R.id.phone_number);
        shipping_address = (TextView) findViewById(R.id.shipping_address);
        order_sn = (TextView) findViewById(R.id.order_sn);
        add_time = (TextView) findViewById(R.id.add_time);
        goods_img = (ImageView) findViewById(R.id.goods_img);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_num = (TextView) findViewById(R.id.goods_num);
        remark = (TextView) findViewById(R.id.remark);
        total_price = (TextView) findViewById(R.id.total_price);
        discount = (TextView) findViewById(R.id.discount);

        my_order_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_order_back:
                finish();
                break;
        }
    }
}
