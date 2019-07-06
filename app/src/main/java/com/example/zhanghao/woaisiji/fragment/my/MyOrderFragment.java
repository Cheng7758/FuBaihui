package com.example.zhanghao.woaisiji.fragment.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.my.MyOrderActivity;
import com.example.zhanghao.woaisiji.activity.my.MyOrderDetailActivity;
import com.example.zhanghao.woaisiji.adapter.my.MyOrderAdapter;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.utils.http.NetManager;

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

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyOrderFragment extends Fragment {

    private RecyclerView order_rlv;
    private int mIndex;
    private MyOrderAdapter mAdapter;

    public MyOrderFragment(int pI) {
        mIndex = pI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;
        Map<String, String> params = new HashMap<>();
        params.put("select_t", mIndex + "");
        params.put("uid", uid);
        params.put("token", token);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getMyOrderBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderBean value) {
                        Log.e("--订单", value.getData().toString());
                        final List<OrderBean.DataBean> beanList = value.getData();
                        order_rlv.setLayoutManager(new LinearLayoutManager(getContext()));
                        mAdapter = new MyOrderAdapter(getContext(), beanList, mIndex);
                        order_rlv.setAdapter(mAdapter);
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
        order_rlv = (RecyclerView) view.findViewById(R.id.order_rlv);
    }

}
