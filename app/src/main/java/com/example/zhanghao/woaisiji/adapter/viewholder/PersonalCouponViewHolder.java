package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;

public class PersonalCouponViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_item_personal_coupon_money,tv_item_personal_coupon_condition_money,
            tv_item_personal_coupon_period_start_date,tv_item_personal_coupon_period_end_date,tv_item_personal_coupon_now_use;
    public PersonalCouponViewHolder(View itemView) {
        super(itemView);
        tv_item_personal_coupon_money = (TextView) itemView.findViewById(R.id.tv_item_personal_coupon_money);
        tv_item_personal_coupon_condition_money = (TextView) itemView.findViewById(R.id.tv_item_personal_coupon_condition_money);
        tv_item_personal_coupon_period_start_date = (TextView) itemView.findViewById(R.id.tv_item_personal_coupon_period_start_date);
        tv_item_personal_coupon_period_end_date = (TextView) itemView.findViewById(R.id.tv_item_personal_coupon_period_end_date);
        tv_item_personal_coupon_now_use = (TextView) itemView.findViewById(R.id.tv_item_personal_coupon_now_use);
    }
}
