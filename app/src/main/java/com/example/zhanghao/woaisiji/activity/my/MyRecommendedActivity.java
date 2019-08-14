package com.example.zhanghao.woaisiji.activity.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.my.MyRecommendedAdapter;
import com.example.zhanghao.woaisiji.bean.my.MyRecommendedBean;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyRecommendedActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;
    private RecyclerView mRlv;
    private List<MyRecommendedBean.DataBean> mBeanList;
    private MyRecommendedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommended);
        initData();
        initView();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", WoAiSiJiApp.getUid());
        params.put("token", WoAiSiJiApp.token);
        params.put("page", 1 + "");
        params.put("row_num", 100000000 + "");
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMyRecommendedBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRecommendedBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyRecommendedBean value) {
                        if (value.getData() == null) return;
                        try {
                            mBeanList = value.getData();
                            mAdapter.setList(mBeanList);
                        } catch (Exception e) {
                            ToastUtils.showShort("占无推荐人");
                            e.printStackTrace();
                        }
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
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("我的推荐");
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);

        mRlv = (RecyclerView) findViewById(R.id.my_recommended_rlv);
        mBeanList = new ArrayList<>();
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecommendedAdapter(this, mBeanList);
        mRlv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
        }
    }
}
