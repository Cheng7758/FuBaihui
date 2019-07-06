package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhanghao.woaisiji.R;

public class FBHStoreDataDetailHolder extends BaseViewHolder {

    public ImageView iv_item_fbh_store_detail_shangpintu;
    public TextView tv_item_fbh_store_detail_introduce;
    public TextView tv_item_fbh_store_detail_price;
    public TextView tv_item_fbh_store_detail_payment;

    public FBHStoreDataDetailHolder(View view) {
        super(view);
        iv_item_fbh_store_detail_shangpintu = (ImageView) view.findViewById(R.id.iv_item_fbh_store_detail_shangpintu);
        tv_item_fbh_store_detail_introduce = (TextView) view.findViewById(R.id.tv_item_fbh_store_detail_introduce);
        tv_item_fbh_store_detail_price = (TextView) view.findViewById(R.id.tv_item_fbh_store_detail_price);
        tv_item_fbh_store_detail_payment = (TextView) view.findViewById(R.id.tv_item_fbh_store_detail_payment);
    }
}
