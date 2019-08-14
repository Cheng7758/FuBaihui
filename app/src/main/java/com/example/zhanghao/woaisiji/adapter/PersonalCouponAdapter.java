package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.adapter.viewholder.PersonalCouponViewHolder;
import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;
import com.example.zhanghao.woaisiji.resp.RespPersonalCoupon;
import com.example.zhanghao.woaisiji.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class PersonalCouponAdapter extends RecyclerView.Adapter<PersonalCouponViewHolder> {
    private List<PersonalCouponBean> dataSource;

    private Context context;
    ItemListener<PersonalCouponBean> listener;

    public void setListener(ItemListener<PersonalCouponBean> listener) {
        this.listener = listener;
    }

    public PersonalCouponAdapter(Context context) {
        this.context = context;
        dataSource = new ArrayList<>();
    }

    public void setNewDataSource(List<PersonalCouponBean> newData) {
        if (newData != null && newData.size() > 0) {
            dataSource.clear();
            dataSource.addAll(newData);
        }
    }

    @Override
    public PersonalCouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personal_coupon, null, false);
        PersonalCouponViewHolder viewHolder = new PersonalCouponViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PersonalCouponViewHolder holder, int position) {
        final PersonalCouponBean personalCouponBean = dataSource.get(position);
        holder.tv_item_personal_coupon_money.setText(setMoney(personalCouponBean.getMoney()));
        Log.e("---money", setMoney(personalCouponBean.getMoney()) + "");
        holder.tv_item_personal_coupon_condition_money.setText("满" + personalCouponBean.getMoney_condition() + "可用");
        holder.tv_item_personal_coupon_period_start_date.setText(DateUtil.getTime_YyyyMmdd(personalCouponBean.getUse_start_time()));
        holder.tv_item_personal_coupon_period_end_date.setText(DateUtil.getTime_YyyyMmdd(personalCouponBean.getUse_end_time()));
        holder.tv_item_personal_coupon_now_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(holder.itemView,personalCouponBean);
            }
        });
    }

    private SpannableStringBuilder setMoney(String conditionMoney) {
        String protocolText = "￥" + conditionMoney;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(protocolText);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(25, true), 0, 1,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public int getItemCount() {
        if (dataSource!=null)
        {
            return dataSource.size();
        }
        return 0;
    }

    public interface ItemListener<T>{
        void onItemClick(View itemView, T t);
    }
}
