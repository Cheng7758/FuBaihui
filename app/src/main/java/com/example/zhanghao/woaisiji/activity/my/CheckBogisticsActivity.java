package com.example.zhanghao.woaisiji.activity.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.adapter.my.MyOrderWuLiuAdapter;
import com.example.zhanghao.woaisiji.bean.my.CheckBogisticsBean;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CheckBogisticsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView wuliu_back;
    private ImageView wuliu_img;
    private TextView tv_status;
    private TextView tv_kuaidi;
    private TextView tv_kefu_phone;
    private TextView tv_genzhong;
    private XRecyclerView wuliu_rlv;
    private List<CheckBogisticsBean.DataBean> mBeanList;
    private MyOrderWuLiuAdapter mAdapter;
    private String mNu;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bogistics);
        mId = getIntent().getStringExtra("id");
        mNu = getIntent().getStringExtra("nu");
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mId);
        params.put("nu", mNu);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getCheckBogisticsBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckBogisticsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckBogisticsBean value) {
                        Log.e("----物流信息", value.getData().toString());
                        mBeanList = value.getData();
                        wuliu_rlv.setLayoutManager(new LinearLayoutManager(CheckBogisticsActivity.this));
                        mAdapter = new MyOrderWuLiuAdapter(CheckBogisticsActivity.this, mBeanList);
                        wuliu_rlv.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        wuliu_back = (ImageView) findViewById(R.id.wuliu_back);
        wuliu_img = (ImageView) findViewById(R.id.wuliu_img);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_kuaidi = (TextView) findViewById(R.id.tv_kuaidi);
        tv_kefu_phone = (TextView) findViewById(R.id.tv_kefu_phone);
        tv_genzhong = (TextView) findViewById(R.id.tv_genzhong);
        wuliu_rlv = (XRecyclerView) findViewById(R.id.wuliu_rlv);

        wuliu_back.setOnClickListener(this);
        tv_kuaidi.setText("物流单号：" + mNu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wuliu_back:
                finish();
                break;
        }
    }
}
