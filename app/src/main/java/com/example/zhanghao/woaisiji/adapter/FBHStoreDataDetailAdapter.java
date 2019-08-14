package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.activity.SliverIntegralStoreDetail;
import com.example.zhanghao.woaisiji.adapter.viewholder.FBHStoreDataDetailHolder;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FBHStoreDataDetailAdapter extends BaseQuickAdapter<RespCommodityList.CommodityDataDetail,
        FBHStoreDataDetailHolder> {

    private Activity context;
    private int mType;

    public FBHStoreDataDetailAdapter(Activity activity, List<RespCommodityList.CommodityDataDetail> data, int pFromType) {
        super(R.layout.item_fbh_store_detail, data);
        this.context = activity;
        mType = pFromType;
    }

    @Override
    protected void convert(FBHStoreDataDetailHolder helper, RespCommodityList.CommodityDataDetail item) {
        initContentView(helper, item);
    }

    private void initContentView(final FBHStoreDataDetailHolder viewHolder, final RespCommodityList.CommodityDataDetail item) {
        viewHolder.tv_item_fbh_store_detail_payment.setText(item.getPeople() + "人付款");
        viewHolder.tv_item_fbh_store_detail_introduce.setText(item.getTitle());
        if (mType == 0) {
            viewHolder.tv_item_fbh_store_detail_price.setText("￥" + item.getPrice());
        } else if (mType == 1) {
            viewHolder.tv_item_fbh_store_detail_price.setText("银积分:" + item.getSilver());
        }
        Picasso.with(context).load(item.getCover()).error(R.drawable.weixianshi).
                into(viewHolder.iv_item_fbh_store_detail_shangpintu);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity2.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("type", mType);
                intent.putExtra("title", item.getTitle());
                context.startActivity(intent);
            }
        });
    }

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //声明这个接口变量
    private OnItemClickListener itemClick;

    //提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        itemClick = itemClickListener;
    }
}
