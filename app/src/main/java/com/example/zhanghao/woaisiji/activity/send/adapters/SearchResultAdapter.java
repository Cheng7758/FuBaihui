package com.example.zhanghao.woaisiji.activity.send.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.activity.SliverIntegralStoreDetail;
import com.example.zhanghao.woaisiji.activity.send.searchModel.SearchResult;
import com.example.zhanghao.woaisiji.bean.merchant.SearchDataBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.CircleCornerTransform;
import com.squareup.picasso.Picasso;

/**
 * Author:      Lee Yeung
 * Create Date: 2019/7/20
 * Description:
 */
public class SearchResultAdapter extends CommonAdapter<SearchResult.DataBean.ItemData> {
    private final LayoutInflater mLayoutInflater;

    public SearchResultAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public int getItemViewType(int position) {
        final SearchResult.DataBean.ItemData model = getItem(position);
        if (model != null) {
            if (model instanceof SearchResult.DataBean.StoreBean) {
                return 0;
            } else if (model instanceof SearchResult.DataBean.GoodsBean) {
                return 1;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public CommonViewHolder createViewHolder(SearchResult.DataBean.ItemData model) {
        if (model instanceof SearchResult.DataBean.StoreBean) {
            Log.e("-----storeBean", "----1----");
            return new StoreViewHolder(mLayoutInflater.inflate(R.layout.car_service_item, null, false));
        } else if (model instanceof SearchResult.DataBean.GoodsBean) {
            Log.e("-----storeBean", "----2----");
            return new GoodsViewHolder(mLayoutInflater.inflate(R.layout.commodity_item, null, false));
        }
        return null;
    }

    class StoreViewHolder extends CommonViewHolder {
        public ImageView img_car;
        public TextView tx_name;
        public TextView tx_content;
        public TextView tx_address_detail;
        public TextView tx_m;
        public LinearLayout ll_car_service;

        public StoreViewHolder(View itemView) {
            super(itemView);
            img_car = (ImageView) itemView.findViewById(R.id.img_car);
            tx_name = (TextView) itemView.findViewById(R.id.tx_name);
            tx_content = (TextView) itemView.findViewById(R.id.tx_content);
            tx_address_detail = (TextView) itemView.findViewById(R.id.tx_address_detail);
            tx_m = (TextView) itemView.findViewById(R.id.tx_m);
            ll_car_service = (LinearLayout) itemView.findViewById(R.id.ll_car_service);
        }

        @Override
        public void update(SearchResult.DataBean.ItemData model) {
            final SearchResult.DataBean.StoreBean storeBean = (SearchResult.DataBean.StoreBean) model;
            Log.e("-----storeBean", storeBean.toString());
            CircleCornerTransform circleCornerTransform = new CircleCornerTransform(mLayoutInflater.getContext());
            circleCornerTransform.setRoundWidthPercentage(0.1f);
            Picasso.with(mLayoutInflater.getContext()).load(ServerAddress.SERVER_ROOT + storeBean.getLogo())
                    .transform(circleCornerTransform)
                    .error(R.drawable.ic_fubaihui).into(img_car);
            tx_name.setText(storeBean.getName());
//        viewHolder.tx_content.setText(item.getContent());
            ll_car_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mLayoutInflater.getContext(), SliverIntegralStoreDetail.class);
//                intent.putExtra("IntentJoinToDetailDataId", item.getId());
                    intent.putExtra("IntentSliverDetailCommodityID", storeBean.getId());
                    ActivityUtils.startActivity(intent);
                    Toast.makeText(mLayoutInflater.getContext(), "商家店铺", Toast.LENGTH_SHORT).show();
                }
            });
            if (!TextUtils.isEmpty(storeBean.getJuli()+"")) {
                tx_m.setText(storeBean.getJuli()+"");
            }
        }
    }

    class GoodsViewHolder extends CommonViewHolder {
        public ImageView img_car;
        public TextView tx_name;
        public TextView tx_content;
        public LinearLayout ll_commodity_service;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            img_car = (ImageView) itemView.findViewById(R.id.img_car);
            tx_name = (TextView) itemView.findViewById(R.id.tx_name);
            tx_content = (TextView) itemView.findViewById(R.id.tx_content);
            ll_commodity_service = (LinearLayout) itemView.findViewById(R.id.ll_commodity_service);
        }

        @Override
        public void update(SearchResult.DataBean.ItemData model) {
            final SearchResult.DataBean.GoodsBean goodsBean = (SearchResult.DataBean.GoodsBean) model;
            Log.e("-----goodsBean", goodsBean.toString());
            CircleCornerTransform circleCornerTransform = new CircleCornerTransform(mLayoutInflater.getContext());
            circleCornerTransform.setRoundWidthPercentage(0.1f);
            Picasso.with(mLayoutInflater.getContext()).load(ServerAddress.SERVER_ROOT + goodsBean.getCover())
                    .transform(circleCornerTransform)
                    .error(R.drawable.ic_fubaihui).into(img_car);
            tx_name.setText(goodsBean.getTitle());
            ll_commodity_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("点击商品");
                    Intent intent = new Intent(mLayoutInflater.getContext(), ProductDetailActivity2.class);
                    intent.putExtra("id", goodsBean.getId());
                    ActivityUtils.startActivity(intent);
                }
            });
        }
    }
}
