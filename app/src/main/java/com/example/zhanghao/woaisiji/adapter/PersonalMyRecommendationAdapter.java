package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.adapter.viewholder.PersonalMyRecommendationViewHolder;
import com.example.zhanghao.woaisiji.resp.RespPersonalMyRecommendation;
import com.example.zhanghao.woaisiji.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class PersonalMyRecommendationAdapter extends RecyclerView.Adapter<PersonalMyRecommendationViewHolder> {
    private List<RespPersonalMyRecommendation.PersonalMyRecommendationBean> dataSource ;

    private Context context ;
    public PersonalMyRecommendationAdapter(Context context) {
        this.context = context;
        dataSource = new ArrayList<>();
    }

    public void setNewDataSource(List<RespPersonalMyRecommendation.PersonalMyRecommendationBean> newData ){
        if (newData!=null && newData.size()>0) {
            dataSource.clear();
            dataSource.addAll(newData);
            notifyDataSetChanged();
        }
    }
    @Override
    public PersonalMyRecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personal_my_recommendation, null, false);
        PersonalMyRecommendationViewHolder viewHolder = new PersonalMyRecommendationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonalMyRecommendationViewHolder holder, int position) {
        if (!TextUtils.isEmpty(dataSource.get(position).getBack1()) &&dataSource.get(position).getBack1().contains("成功") ){
            holder.tv_item_personal_my_recommendation_state.setText("成功推荐用户\""+dataSource.get(position).getNickname()+"\"");
        }else{
            holder.tv_item_personal_my_recommendation_state.setText("推荐用户\""+dataSource.get(position).getNickname()+"\"+失败");
        }

        holder.tv_item_personal_my_recommendation_time.setText(Util.getDateString(dataSource.get(position).getCtime()));
        holder.tv_item_personal_my_recommendation_get_gold.setText(dataSource.get(position).getScore());
        holder.tv_item_personal_my_recommendation_get_sliver.setText(dataSource.get(position).getSilver());
        holder.tv_item_personal_my_recommendation_get_banlance.setText(dataSource.get(position).getBalance());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
