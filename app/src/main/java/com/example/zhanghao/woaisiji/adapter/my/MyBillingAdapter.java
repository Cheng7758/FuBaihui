package com.example.zhanghao.woaisiji.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.billing.BillingDetailsBean;
import com.example.zhanghao.woaisiji.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cheng on 2019/7/16.
 */

public class MyBillingAdapter extends RecyclerView.Adapter<MyBillingAdapter.ViewHolder> {
    private List<BillingDetailsBean.DataBean> mBeanList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public MyBillingAdapter(Context pContext) {
        mContext = pContext;
        mInflater = LayoutInflater.from(mContext.getApplicationContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.billing_details_show, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(mBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeanList != null ? mBeanList.size() : 0;
    }

    public void addData(List<BillingDetailsBean.DataBean> list) {
        if (list != null && !list.isEmpty()) {
            mBeanList.clear();
            mBeanList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView transfer_accounts;
        private TextView silver_integral;
        private TextView balance;
        private TextView gold_integral;
        private TextView merchant_integral;
        private TextView billing_time;

        public ViewHolder(View itemView) {
            super(itemView);
            transfer_accounts = (TextView) itemView.findViewById(R.id.transfer_accounts);
            silver_integral = (TextView) itemView.findViewById(R.id.silver_integral);
            balance = (TextView) itemView.findViewById(R.id.balance);
            gold_integral = (TextView) itemView.findViewById(R.id.gold_integral);
            merchant_integral = (TextView) itemView.findViewById(R.id.merchant_integral);
            billing_time = (TextView) itemView.findViewById(R.id.billing_time);
        }

        public void update(BillingDetailsBean.DataBean dataBean) {
            Log.e("---账单查询---", dataBean.toString());
            if (dataBean.getStatus().equals("0")) { //转出
                transfer_accounts.setText("转出：" + dataBean.getBack1());
            } else if (dataBean.getStatus().equals("1")) {
                transfer_accounts.setText("转入：" + dataBean.getBack1());
            }
            silver_integral.setText("银积分：" + dataBean.getSilver());
            balance.setText("余额：" + dataBean.getBalance());
            gold_integral.setText("金积分：" + dataBean.getScore());
            merchant_integral.setText("商家金积分：" + dataBean.getStore_score());
            String timeYMD = DateUtil.getTime_YyyyMmdd(dataBean.getCtime());
            billing_time.setText(timeYMD);
        }
    }
}
