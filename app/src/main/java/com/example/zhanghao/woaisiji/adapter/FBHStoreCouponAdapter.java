package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;

import java.util.List;

public class FBHStoreCouponAdapter extends BaseQuickAdapter<PersonalCouponBean, FBHStoreCouponAdapter.FBHStoreCouponHolder> {

    private Activity context;

    public FBHStoreCouponAdapter(Activity activity, List<PersonalCouponBean> data) {
        super(R.layout.item_fbh_coupon_adapter, data);
        this.context = activity;
    }
    @Override
    protected void convert(FBHStoreCouponHolder helper, PersonalCouponBean item) {
        initContentView(helper, item);
    }
    private void initContentView(final FBHStoreCouponHolder viewHolder, final PersonalCouponBean item) {
        viewHolder.tv_item_fbh_coupon_name.setText("满"+ item.getMoney_condition()+"减"+item.getMoney());
            viewHolder.tv_item_fbh_coupon_prices.setText(item.getSilver()+"银积分");
//        viewHolder.tv_item_fbh_store_detail_payment.setText(item.getPeople() + "人付款");
//        viewHolder.tv_item_fbh_store_detail_introduce.setText(item.getTitle());
//        viewHolder.tv_item_fbh_store_detail_price.setText("￥" +item.getPrice());
//        Picasso.with(context).load(item.getCover()).error(R.drawable.weixianshi).into(viewHolder.iv_item_fbh_store_detail_shangpintu);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ProductDetailActivity2.class);
//                intent.putExtra("id",item.get());
//                context.startActivity(intent);
                itemClick.onItemClick(item);
            }
        });
    }

    //定义接口
    public interface OnItemClickListener{
        void onItemClick(PersonalCouponBean item);
    }
    //声明这个接口变量
    private OnItemClickListener itemClick;
    //提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        itemClick = itemClickListener;
    }
    public class FBHStoreCouponHolder extends BaseViewHolder {

//        public ImageView iv_item_fbh_store_detail_shangpintu;
        public TextView tv_item_fbh_coupon_name;
        public TextView tv_item_fbh_coupon_prices;
//        public TextView tv_item_fbh_store_detail_payment;

        public FBHStoreCouponHolder(View view) {
            super(view);
//            iv_item_fbh_store_detail_shangpintu = (ImageView) view.findViewById(R.id.iv_item_fbh_);
//            tv_item_fbh_store_detail_introduce = (TextView) view.findViewById(R.id.tv_item_fbh_store_detail_introduce);
            tv_item_fbh_coupon_name = (TextView) view.findViewById(R.id.tv_item_fbh_coupon_name);
            tv_item_fbh_coupon_prices = (TextView) view.findViewById(R.id.tv_item_fbh_coupon_prices);
        }
    }
}
