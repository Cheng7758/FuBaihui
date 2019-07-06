package com.example.zhanghao.woaisiji.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.my.JsonBean;
import com.example.zhanghao.woaisiji.bean.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.widget.PickerView.PickerScrollView;
import com.example.zhanghao.woaisiji.widget.PickerView.Pickers;

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

public class RecruitmentActivity extends AppCompatActivity implements View.OnClickListener, PickerScrollView.onSelectListener {

    private TextView select_file;
    private TextView shop_classify;
    private TextView classify_label;
    private TextView location;
    private CheckBox checkbox_yuedu;
    private Button ruzhu_btn;
    private ImageView recruitment_back;
    private PickerScrollView pickerview;
    private List<ShopsRuzhuBean.DataBean.XianBean> mXianBeans;
    private List<ShopsRuzhuBean.DataBean.ShiBean> mShiBeans;
    private List<ShopsRuzhuBean.DataBean.ShengBean> mShengBeans;
    private List<ShopsRuzhuBean.DataBean.DpflBean> mDpflBeanList;
    private List<ShopsRuzhuBean.DataBean.FlbqBean> mFlbqBeanList;
    private TextView cancel;
    private TextView confirm;
    private int index;
    private RelativeLayout relative;
    private String name;
    private List<JsonBean> options1Items = new ArrayList<>();    //省份
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>(); //省份下面的市数据
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();  //市添加区
    private static boolean isLoaded = false;
    private List<JsonBean.CityBean> mCityBeans;
    private List<JsonBean.CityBean.County> mCountyBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);
        initView();
        initData();
    }
    private List<JsonBean> mJsonBeans;
    private void initData() {
        final String uid = WoAiSiJiApp.getUid();
        final String token = WoAiSiJiApp.token;

        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myserver.url)
                .addConverterFactory(GsonConverterFactory.create()) //添加json转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加RxJava 适配器
                .build();

        Myserver myserver = retrofit.create(Myserver.class);
        Observable<ShopsRuzhuBean> myOrderBean = myserver.getShopsRuzhuBean(params);
        myOrderBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsRuzhuBean>() {



                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopsRuzhuBean value) {
                        mDpflBeanList = value.getData().getDpfl();  //店铺分类
                        mFlbqBeanList = value.getData().getFlbq();  //分类标签
                        mShengBeans = value.getData().getSheng();   //省份
                        mShiBeans = value.getData().getShi();       //市区
                        mXianBeans = value.getData().getXian();     //县

                        mJsonBeans = new ArrayList<>();
                        mCityBeans = new ArrayList<>();
                        mCountyBeans = new ArrayList<>();
                        ArrayList<String> shiAr = new ArrayList<>();
                        ArrayList<String> xianAr = new ArrayList<>();
                        ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
                        for (int i = 0; i < mShengBeans.size(); i++) {
                            ShopsRuzhuBean.DataBean.ShengBean shengBean = mShengBeans.get(i);
                            for (int j = 0; j < mShiBeans.size(); j++) {
                                ShopsRuzhuBean.DataBean.ShiBean shiBean = mShiBeans.get(j);
                                if (shengBean.getRegion_id().equals(shiBean.getParent_id())){


                                    for (int k = 0; k < mXianBeans.size(); k++) {
                                        ShopsRuzhuBean.DataBean.XianBean xianBean = mXianBeans.get(k);
                                        if (shiBean.getRegion_id().equals(xianBean.getParent_id())){
                                            shiAr.add(mXianBeans.get(j).getRegion_name());
                                            mCountyBeans.add(new JsonBean.CityBean.County(xianBean.getRegion_name(),xianBean.getParent_id(),xianBean.getRegion_id(),xianBean.getRegion_type(),xianBean.getAgency_id()));
                                        }
                                    }
                                    mCityBeans.add(new JsonBean.CityBean(shiBean.getRegion_name(),shiBean.getParent_id(),shiBean.getRegion_id(),shiBean.getRegion_type(),shiBean.getAgency_id(), mCountyBeans));
                                }
                            }
                            options2Items.add(shiAr);
                            mJsonBeans.add(new JsonBean(shengBean.getRegion_name(),shengBean.getParent_id(),shengBean.getRegion_id(),shengBean.getRegion_type(),shengBean.getAgency_id(), mCityBeans));
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
        select_file = (TextView) findViewById(R.id.select_file);
        shop_classify = (TextView) findViewById(R.id.shop_classify);
        classify_label = (TextView) findViewById(R.id.classify_label);
        location = (TextView) findViewById(R.id.location);
        checkbox_yuedu = (CheckBox) findViewById(R.id.checkbox_yuedu);
        ruzhu_btn = (Button) findViewById(R.id.ruzhu_btn);
        recruitment_back = (ImageView) findViewById(R.id.recruitment_back);
        pickerview = (PickerScrollView) findViewById(R.id.pickerview);
        relative = (RelativeLayout) findViewById(R.id.relative);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm = (TextView) findViewById(R.id.confirm);

        ruzhu_btn.setOnClickListener(this);
        recruitment_back.setOnClickListener(this);
        shop_classify.setOnClickListener(this);
        classify_label.setOnClickListener(this);
        location.setOnClickListener(this);
        pickerview.setOnSelectListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recruitment_back:
                finish();
                break;
            case R.id.shop_classify:
                pickerviewShow(index = 1);
                break;
            case R.id.classify_label:
                pickerviewShow(index = 2);
                break;
            case R.id.location:
//                showPickerView();
                break;
            case R.id.cancel:
                pickerview.setVisibility(View.GONE);
                relative.setVisibility(View.GONE);
                break;
            case R.id.confirm:
                if (index == 1) {
                    shop_classify.setText(name);
                } else {
                    classify_label.setText(name);
                }
                pickerview.setVisibility(View.GONE);
                relative.setVisibility(View.GONE);
                break;
        }
    }

    // 弹出选择器
    /*private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = mJsonBeans.size() > 0 ?
                        mJsonBeans.get(options1).getName() : "";

                String opt2tx = mCityBeans.size() > 0
                        && mCityBeans.get(options1).size() > 0 ?
                        mCityBeans.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                Toast.makeText(RecruitmentActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        *//*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*//*
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }*/

    private void pickerviewShow(int pI) {
        ArrayList<Pickers> list = new ArrayList<>();
        if (pI == 1) {
            for (int i = 0; i < mDpflBeanList.size(); i++) {
                list.add(new Pickers(mDpflBeanList.get(i).getName() +
                        "让利" + mDpflBeanList.get(i).getBusiness() + "%"));
            }
        } else if (pI == 2) {
            for (int i = 0; i < mFlbqBeanList.size(); i++) {
                list.add(new Pickers(mFlbqBeanList.get(i).getName()));
            }
        }
        // 设置数据，默认选择第一条
        pickerview.setData(list);
        pickerview.setSelected(0);
        pickerview.setVisibility(View.VISIBLE);
        relative.setVisibility(View.VISIBLE);
    }

    // 滚动选择器选中事件
    @Override
    public void onSelect(Pickers pickers) {
        name = pickers.getShowConetnt();
    }

}
