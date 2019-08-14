package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.adapter.viewholder.FBHStoreDataDetailHolder;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliverIntegralStoreDataDetailAdapter extends BaseQuickAdapter<RespCommodityList.CommodityDataDetail, FBHStoreDataDetailHolder> {

    private Activity context;

    public SliverIntegralStoreDataDetailAdapter(Activity activity, List<RespCommodityList.CommodityDataDetail> data) {
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity2.class);
                intent.putExtra("id",item.getId());
                context.startActivity(intent);
            }
        });
    }

    //定义接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //声明这个接口变量
    private OnItemClickListener itemClick;
    //提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        itemClick = itemClickListener;
    }
}
