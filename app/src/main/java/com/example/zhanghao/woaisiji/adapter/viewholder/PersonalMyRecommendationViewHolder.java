package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;

public class PersonalMyRecommendationViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_item_personal_my_recommendation_state,tv_item_personal_my_recommendation_time,
            tv_item_personal_my_recommendation_get_gold,tv_item_personal_my_recommendation_get_sliver,
            tv_item_personal_my_recommendation_get_banlance;

    public PersonalMyRecommendationViewHolder(View itemView) {
        super(itemView);
        tv_item_personal_my_recommendation_state = (TextView) itemView.findViewById(R.id.tv_item_personal_my_recommendation_state);
        tv_item_personal_my_recommendation_time = (TextView) itemView.findViewById(R.id.tv_item_personal_my_recommendation_time);
        tv_item_personal_my_recommendation_get_gold = (TextView) itemView.findViewById(R.id.tv_item_personal_my_recommendation_get_gold);
        tv_item_personal_my_recommendation_get_sliver = (TextView) itemView.findViewById(R.id.tv_item_personal_my_recommendation_get_sliver);
        tv_item_personal_my_recommendation_get_banlance = (TextView) itemView.findViewById(R.id.tv_item_personal_my_recommendation_get_banlance);
    }
}
