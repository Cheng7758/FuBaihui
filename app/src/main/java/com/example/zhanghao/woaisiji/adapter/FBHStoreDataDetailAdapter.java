package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.adapter.viewholder.FBHStoreDataDetailHolder;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FBHStoreDataDetailAdapter extends BaseQuickAdapter<RespCommodityList.CommodityDataDetail, FBHStoreDataDetailHolder> {

    private Activity context;

    public FBHStoreDataDetailAdapter(Activity activity, List<RespCommodityList.CommodityDataDetail> data) {
        super(R.layout.item_fbh_store_detail, data);
        this.context = activity;
    }
    @Override
    protected void convert(FBHStoreDataDetailHolder helper, RespCommodityList.CommodityDataDetail item) {
        initContentView(helper, item);
    }
    private void initContentView(final FBHStoreDataDetailHolder viewHolder, final RespCommodityList.CommodityDataDetail item) {

        viewHolder.tv_item_fbh_store_detail_payment.setText(item.getPeople() + "人付款");
        viewHolder.tv_item_fbh_store_detail_introduce.setText(item.getTitle());
        viewHolder.tv_item_fbh_store_detail_price.setText("￥" +item.getPrice());
        Picasso.with(context).load(item.getCover()).error(R.drawable.weixianshi).into(viewHolder.iv_item_fbh_store_detail_shangpintu);
    }
}
