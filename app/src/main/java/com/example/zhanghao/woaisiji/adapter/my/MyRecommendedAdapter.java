package com.example.zhanghao.woaisiji.adapter.my;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.my.MyRecommendedActivity;
import com.example.zhanghao.woaisiji.bean.my.MyRecommendedBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.DatasUtil;
import com.example.zhanghao.woaisiji.utils.DateUtil;

import java.util.List;

/**
 * Created by Cheng on 2019/8/4.
 */

public class MyRecommendedAdapter extends RecyclerView.Adapter<MyRecommendedAdapter.ViewHolder> {
    private MyRecommendedActivity mActivity;
    private List<MyRecommendedBean.DataBean> mList;
    private LayoutInflater mInflater;

    public MyRecommendedAdapter(MyRecommendedActivity pActivity, List<MyRecommendedBean.DataBean> pBeanList) {
        mActivity = pActivity;
        mList = pBeanList;
        mInflater = LayoutInflater.from(mActivity);
    }

    public void setList(List<MyRecommendedBean.DataBean> pList) {
        mList = pList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_recommended_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyRecommendedBean.DataBean dataBean = mList.get(position);
        if (!TextUtils.isEmpty(dataBean.getHeadpic())) {
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getHeadpic()).into(holder.my_tuijian_headpic);
        } else {
            holder.my_tuijian_headpic.setImageResource(R.drawable.ic_fubaihui);
        }
        holder.my_tuijian_nickname.setText(dataBean.getNickname());
        holder.my_tuijian_reg_time.setText(dataBean.getReg_time());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView my_tuijian_headpic;
        private TextView my_tuijian_nickname, my_tuijian_reg_time;

        public ViewHolder(View itemView) {
            super(itemView);
            my_tuijian_headpic = (ImageView) itemView.findViewById(R.id.my_tuijian_headpic);
            my_tuijian_nickname = (TextView) itemView.findViewById(R.id.my_tuijian_nickname);
            my_tuijian_reg_time = (TextView) itemView.findViewById(R.id.my_tuijian_reg_time);
        }
    }
}
