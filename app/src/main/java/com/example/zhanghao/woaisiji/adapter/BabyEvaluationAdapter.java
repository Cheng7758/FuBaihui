package com.example.zhanghao.woaisiji.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.home.BabyEvaluationActivity;
import com.example.zhanghao.woaisiji.bean.merchandise.MerchandiseDetails;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.DateUtil;

import java.util.List;

/**
 * Created by Cheng on 2019/7/31.
 */

public class BabyEvaluationAdapter extends RecyclerView.Adapter<BabyEvaluationAdapter.ViewHolder> {
    private BabyEvaluationActivity mActivity;
    private List<MerchandiseDetails.DataBean> mBeanList;
    private List<MerchandiseDetails.DataBean> mList;
    private LayoutInflater mInflater;

    public BabyEvaluationAdapter(BabyEvaluationActivity pActivity, List<MerchandiseDetails.DataBean> pBeanList) {
        mActivity = pActivity;
        mBeanList = pBeanList;
        mInflater = mActivity.getLayoutInflater();
    }

    public void setList(List<MerchandiseDetails.DataBean> pList) {
        mList = pList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.baby_evaluation_show, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MerchandiseDetails.DataBean dataBean = mBeanList.get(position);
        holder.evaluate_name.setText(dataBean.getUser_name());
        String time_yyyyMmdd = DateUtil.getTime_YyyyMmdd(dataBean.getCtime());
        holder.evaluate_ctime.setText(time_yyyyMmdd);
        if (dataBean.getContent() != null) {
            holder.evaluate_content.setText(dataBean.getContent());
        } else {
            holder.evaluate_content.setText("无评价内容");
        }
        if (dataBean.getImg().size() == 1) {
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(0))
                    .into(holder.evaluate_img1);
        } else if (dataBean.getImg().size() == 2) {
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(0))
                    .into(holder.evaluate_img1);
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(1))
                    .into(holder.evaluate_img2);
        } else if (dataBean.getImg().size() == 3) {
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(0))
                    .into(holder.evaluate_img1);
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(1))
                    .into(holder.evaluate_img2);
            Glide.with(mActivity).load(ServerAddress.SERVER_ROOT + dataBean.getImg().get(2))
                    .into(holder.evaluate_img3);
        }
    }

    @Override
    public int getItemCount() {
        return mBeanList != null ? mBeanList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView evaluate_name, evaluate_ctime, evaluate_content;
        private ImageView evaluate_img1, evaluate_img2, evaluate_img3;

        public ViewHolder(View itemView) {
            super(itemView);
            evaluate_name = (TextView) itemView.findViewById(R.id.evaluate_name);
            evaluate_ctime = (TextView) itemView.findViewById(R.id.evaluate_ctime);
            evaluate_content = (TextView) itemView.findViewById(R.id.evaluate_content);
            evaluate_img1 = (ImageView) itemView.findViewById(R.id.evaluate_img1);
            evaluate_img2 = (ImageView) itemView.findViewById(R.id.evaluate_img2);
            evaluate_img3 = (ImageView) itemView.findViewById(R.id.evaluate_img3);
        }
    }
}
