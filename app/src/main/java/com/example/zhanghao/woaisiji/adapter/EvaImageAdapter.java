package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;

import java.util.List;

public class EvaImageAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mEntityList;

    public EvaImageAdapter(Context context, List<String> entityList) {
        mContext = context;
        mEntityList = entityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String entity = mEntityList.get(position);
        DemoViewHolder viewHolder = ((DemoViewHolder) holder);
        Glide.with(mContext).load(entity).into(viewHolder.iv);
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    private class DemoViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public DemoViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }


}
