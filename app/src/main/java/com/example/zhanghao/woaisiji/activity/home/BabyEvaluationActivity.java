package com.example.zhanghao.woaisiji.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.adapter.BabyEvaluationAdapter;
import com.example.zhanghao.woaisiji.bean.merchandise.MerchandiseDetails;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BabyEvaluationActivity extends AppCompatActivity implements View.OnClickListener {

    private String mId;
    private RecyclerView mRlv;
    private List<MerchandiseDetails.DataBean> mBeanList;
    private BabyEvaluationAdapter mAdapter;
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_evaluation);
        mId = getIntent().getStringExtra("id");
        initData();
        initView();
    }

    private void initData() {
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMerchandiseDetails(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MerchandiseDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MerchandiseDetails value) {
                        if (value.getData() == null) return;
                        Log.e("-----宝贝评价", value.getData().toString());
                        try {
                            mBeanList = value.getData();
                            mAdapter.setList(mBeanList);
                        } catch (Exception e) {
                            ToastUtils.showShort("占无评价");
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
        // 标题栏
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title.setText("宝贝评价");

        iv_title_bar_view_left_left.setOnClickListener(this);

        mRlv = (RecyclerView) findViewById(R.id.baby_evaluation_rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(BabyEvaluationActivity.this));
        mAdapter = new BabyEvaluationAdapter(BabyEvaluationActivity.this, mBeanList);
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
