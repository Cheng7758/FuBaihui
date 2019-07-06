package com.example.zhanghao.woaisiji.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.adapter.my.MyCollectionAdapter;
import com.example.zhanghao.woaisiji.bean.my.MyCollectionBean;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCollectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView collection_back;
    private RecyclerView rlv;
    private List<MyCollectionBean.DataBean> mBeanList;
    private MyCollectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initView();
        initData();
    }

    private void initView() {
        collection_back = (ImageView) findViewById(R.id.collection_back);
        rlv = (RecyclerView) findViewById(R.id.collection_rlv);

        collection_back.setOnClickListener(this);

        mBeanList = new ArrayList<>();
        rlv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new MyCollectionAdapter(this, mBeanList);
        rlv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void initData() {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;

        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        Log.e("---", uid + "--------------" + token);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMyCollectionBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyCollectionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyCollectionBean value) {
                        mBeanList = value.getData();
                        mAdapter.setList(mBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("--onError--", e.toString());
                    }

                    @Override
                    public void onComplete() {}
                });
    }

}
