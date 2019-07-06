package com.example.zhanghao.woaisiji.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.my.MyOrderDetailActivity;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.List;

/**
 * Created by Cheng on 2019/7/1.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderBean.DataBean> mList;
    private int mIndex;
    private LayoutInflater mInflater;

    public MyOrderAdapter(Context pContext, List<OrderBean.DataBean> pBeanList, int pIndex) {
        mContext = pContext;
        mList = pBeanList;
        mIndex = pIndex;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_order_show, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final OrderBean.DataBean bean = mList.get(position);
        holder.store_name.setText(bean.getStore_name());
        holder.status_m.setText(bean.getStatus_m());
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + bean.getGoods_img())
                .into(holder.goods_img);
        holder.goods_name.setText(bean.getGoods_name());
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + bean.getSymbol()).into(holder.symbol);
        holder.goods_price.setText(bean.getGoods_price());
        holder.goods_num.setText("×" + bean.getGoods_num());
        holder.pay_price.setText("共" + bean.getGoods_num() + "件商品，合计：￥" + bean.getPay_price() + "(含运费￥0.00)");
        if (bean.getStatus_o() == 0) {
            holder.cancel.setText("取消订单");
            holder.confirmation.setText("付款");
        } else if (bean.getStatus_o() == 1) {
            holder.cancel.setText("申请退款");
            holder.confirmation.setVisibility(View.GONE);
        } else if (bean.getStatus_o() == 2) {
            holder.cancel.setText("查看物流");
            holder.confirmation.setText("确认收货");
        } else if (bean.getStatus_o() == 3) {
            holder.cancel.setText("查看物流");
            holder.confirmation.setText("评价");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyOrderDetailActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView store_name;
        private TextView status_m;
        private ImageView goods_img;
        private TextView goods_name;
        private ImageView symbol;
        private TextView goods_price;
        private TextView goods_num;
        private TextView pay_price;
        private TextView cancel;
        private TextView confirmation;

        public ViewHolder(View itemView) {
            super(itemView);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            status_m = (TextView) itemView.findViewById(R.id.status_m);
            goods_img = (ImageView) itemView.findViewById(R.id.goods_img);
            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            symbol = (ImageView) itemView.findViewById(R.id.symbol);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            goods_num = (TextView) itemView.findViewById(R.id.goods_num);
            pay_price = (TextView) itemView.findViewById(R.id.pay_price);

            cancel = (TextView) itemView.findViewById(R.id.cancel);
            confirmation = (TextView) itemView.findViewById(R.id.confirmation);
        }
    }

}
