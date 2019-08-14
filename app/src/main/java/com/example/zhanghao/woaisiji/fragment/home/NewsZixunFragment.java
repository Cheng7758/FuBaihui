package com.example.zhanghao.woaisiji.fragment.home;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.zixun.NewsZixunAdapter;
import com.example.zhanghao.woaisiji.bean.zixun.NewsBean;
import com.example.zhanghao.woaisiji.fragment.BaseFragment;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsZixunFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    private XRecyclerView mXRlv;
    int page = 1;
    private List<NewsBean.ListBean> mBeanList;
    private NewsZixunAdapter mAdapter;

    public NewsZixunFragment() {
        // Required empty public constructor
    }

    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_zixun, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("p", page + "");
        NetManager.getNetManager().getMyService(Myserver.url)
                .getNewsBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean value) {
                        if (value.getList() == null) {
                            return;
                        }
                        mBeanList = value.getList();
                        mAdapter.setList(mBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View view) {
        mXRlv = (XRecyclerView) view.findViewById(R.id.zixun_xrlv);

        mBeanList = new ArrayList<>();
        mXRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewsZixunAdapter(getContext(), mBeanList);
        mXRlv.setAdapter(mAdapter);
        mXRlv.setLoadingListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                page = 1;
                initData();
                mXRlv.refreshComplete();
            }

        }, 2000);

    }

    @Override
    public void onLoadMore() {
        page++;
//        initData();
        mXRlv.refreshComplete();
    }

}
