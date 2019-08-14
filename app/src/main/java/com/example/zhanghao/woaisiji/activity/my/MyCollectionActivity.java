package com.example.zhanghao.woaisiji.activity.my;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.my.MyCollectionAdapter;
import com.example.zhanghao.woaisiji.bean.my.CancelCollectionBean;
import com.example.zhanghao.woaisiji.bean.my.MyCollectionBean;
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

public class MyCollectionActivity extends AppCompatActivity implements View.OnClickListener,
        MyCollectionAdapter.OnItemLongClickListener{
    private ImageView collection_back;
    private RecyclerView rlv;
    private List<MyCollectionBean.DataBean> mBeanList;
    private MyCollectionAdapter mAdapter;
    private String mUid;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        mUid = WoAiSiJiApp.getUid();
        mToken = WoAiSiJiApp.token;
        Log.e("---", mUid + "--------------" + mToken);
        initView();
        initData();
    }

    private void initView() {
        collection_back = (ImageView) findViewById(R.id.collection_back);
        rlv = (RecyclerView) findViewById(R.id.collection_rlv);

        collection_back.setOnClickListener(this);

        mBeanList = new ArrayList<>();
        rlv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyCollectionAdapter(this, mBeanList);
        rlv.setAdapter(mAdapter);
        mAdapter.setItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", mUid);
        params.put("token", mToken);
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
                        Log.e("-------收藏列表", value.getData().toString());
                        if (value.getData() == null){
                            Toast.makeText(MyCollectionActivity.this, "亲~ 还未收藏过商品哦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mBeanList = value.getData();
                        mAdapter.setList(mBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("--onError--", e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onItemLongClick(final int position, final String goods_id) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否删除当前收藏的商品")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mBeanList.remove(position);
                        Log.e("-----删除后",mBeanList.toString());
                        mAdapter.notifyDataSetChanged();
                        deleteItem(position, goods_id);   //删除收藏的数据
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyCollectionActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void deleteItem(final int position, String goods_id) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", mUid);
        params.put("token", mToken);
        params.put("goods_id", goods_id);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getCancelCollectionBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancelCollectionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CancelCollectionBean value) {
                        Toast.makeText(MyCollectionActivity.this, value.getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
