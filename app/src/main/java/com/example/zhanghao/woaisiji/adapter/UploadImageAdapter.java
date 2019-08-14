package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;

import java.util.List;

public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public UploadImageAdapter(Context pContext, List<String> pList) {
        mContext = pContext;
        mList = pList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.upload_image_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (mList.size() == 0) {
            holder.mImageView.setImageResource(R.drawable.icon_addpic_focused);
        } else {
            if (position != mList.size()) {
                Glide.with(mContext).load(mList.get(position)).into(holder.mImageView);
            } else {
                holder.mImageView.setImageResource(R.drawable.icon_addpic_focused);
            }
        }
        if (position == mList.size()) {
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener pOnItemClickListener) {
        mOnItemClickListener = pOnItemClickListener;
    }
}
