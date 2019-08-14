package com.example.zhanghao.woaisiji.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.bean.my.MyCollectionBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.List;

/**
 * Created by Cheng on 2019/6/30.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> {
    private Context mContext;
    private List<MyCollectionBean.DataBean> mList;
    private LayoutInflater mInflater;

    public MyCollectionAdapter(Context pContext, List<MyCollectionBean.DataBean> pList) {
        mContext = pContext;
        mList = pList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MyCollectionBean.DataBean> pList) {
        mList = pList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.collection_show, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(ServerAddress.SERVER_ROOT + mList.get(position).getImg())
                .into(holder.collection_img);
        holder.collection_title.setText(mList.get(position).getTitle());
        holder.price.setText("￥" + mList.get(position).getPrice());
        holder.sorts.setText(mList.get(position).getSorts());
        holder.silver.setText(mList.get(position).getSilver());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity2.class);
                intent.putExtra("id", mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClick != null) {
                    itemLongClick.onItemLongClick(position, mList.get(position).getId());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //定义接口
    public interface OnItemLongClickListener {
        void onItemLongClick(int position, String goods_id);
    }

    //声明这个接口变量
    private OnItemLongClickListener itemLongClick;

    //提供set方法
    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        itemLongClick = itemLongClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView collection_img;
        private TextView collection_title;
        private TextView price;
        private TextView sorts;
        private TextView silver;
        private TextView see_details;

        public ViewHolder(View itemView) {
            super(itemView);
            collection_img = (ImageView) itemView.findViewById(R.id.collection_img);
            collection_title = (TextView) itemView.findViewById(R.id.collection_title);
            price = (TextView) itemView.findViewById(R.id.price);
            sorts = (TextView) itemView.findViewById(R.id.sorts);
            silver = (TextView) itemView.findViewById(R.id.silver);
            see_details = (TextView) itemView.findViewById(R.id.see_details);
        }
    }

}
