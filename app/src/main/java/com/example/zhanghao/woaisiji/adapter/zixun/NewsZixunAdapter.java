package com.example.zhanghao.woaisiji.adapter.zixun;

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
import com.example.zhanghao.woaisiji.activity.zixun.ZixunDetailsActivity;
import com.example.zhanghao.woaisiji.bean.zixun.NewsBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.List;

/**
 * Created by Cheng on 2019/7/8.
 */

public class NewsZixunAdapter extends RecyclerView.Adapter<NewsZixunAdapter.ViewHolder> {
    private Context mContext;
    private List<NewsBean.ListBean> mBeanList;
    private LayoutInflater mInflater;
    private List<NewsBean.ListBean> mAdd;

    public NewsZixunAdapter(Context pContext, List<NewsBean.ListBean> pBeanList) {
        mContext = pContext;
        mBeanList = pBeanList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.news_zixun_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.news_zixun_title.setText(mBeanList.get(position).getTitle());
        holder.news_zixun_type_present.setText(mBeanList.get(position).getType() + "\t" +
                mBeanList.get(position).getPresent());
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + mBeanList.get(position).getPicture())
                .into(holder.news_zixun_picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZixunDetailsActivity.class);
                intent.putExtra("content",mBeanList.get(position).getContent());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void setList(List<NewsBean.ListBean> pList) {
        mBeanList = pList;
        notifyDataSetChanged();
    }

    public void setAdd(List<NewsBean.ListBean> pAdd) {
        this.mBeanList.addAll(pAdd);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView news_zixun_title;
        private TextView news_zixun_type_present;
        private ImageView news_zixun_picture;

        public ViewHolder(View itemView) {
            super(itemView);
            news_zixun_title = (TextView) itemView.findViewById(R.id.news_zixun_title);
            news_zixun_type_present = (TextView) itemView.findViewById(R.id.news_zixun_type_present);
            news_zixun_picture = (ImageView) itemView.findViewById(R.id.news_zixun_picture);
        }
    }
}
