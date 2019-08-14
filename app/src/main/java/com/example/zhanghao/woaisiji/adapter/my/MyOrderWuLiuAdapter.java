package com.example.zhanghao.woaisiji.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.my.CheckBogisticsBean;

import java.util.List;

/**
 * Created by Cheng on 2019/7/15.
 */

public class MyOrderWuLiuAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<CheckBogisticsBean.DataBean> mBeanList;

    public MyOrderWuLiuAdapter(Context context, List<CheckBogisticsBean.DataBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.wuliu_item, null, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder) holder;
        CheckBogisticsBean.DataBean dataBean = mBeanList.get(position);
        holder1.tx_name.setText(dataBean.getContext());
        holder1.tx_time.setText(dataBean.getTime());
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tx_name;
        private TextView tx_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            tx_name = (TextView) itemView.findViewById(R.id.tx_name);
            tx_time = (TextView) itemView.findViewById(R.id.tx_time);
        }
    }

}
