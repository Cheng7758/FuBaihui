package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.SliverIntegralStoreActivity;
import com.example.zhanghao.woaisiji.bean.MerchantsClassification;

import java.util.List;

/**
 * Created by Cheng on 2019/8/13.
 */

public class MyClassificationAdapter extends RecyclerView.Adapter<MyClassificationAdapter.ViewHolder> {
    private Context mContext;
    private List<MerchantsClassification.DataBean> mBeanList;
    private List<MerchantsClassification.DataBean> mList;
    private final LayoutInflater mInflater;

    public MyClassificationAdapter(Context pContext, List<MerchantsClassification.DataBean> pBeanList) {
        mContext = pContext;
        mBeanList = pBeanList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MerchantsClassification.DataBean> pList) {
        mList = pList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.classify_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.classify_name.setText(mBeanList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemClick(position, mBeanList.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList != null ? mBeanList.size() : 0;
    }

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int position, String id);
    }

    //声明这个接口变量
    private OnItemClickListener itemClick;

    //提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        itemClick = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView classify_name;

        public ViewHolder(View itemView) {
            super(itemView);
            classify_name = (TextView) itemView.findViewById(R.id.classify_name);
        }
    }
}
