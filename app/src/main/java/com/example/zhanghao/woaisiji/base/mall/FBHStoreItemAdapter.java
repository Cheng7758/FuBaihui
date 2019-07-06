package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespCommodityList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/29.
 */
public class FBHStoreItemAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<RespCommodityList.CommodityDataDetail> mGoodLists = new ArrayList<>();

    public FBHStoreItemAdapter(Activity activity) {
        mActivity = activity;
    }
    public void setNewDataSource(List<RespCommodityList.CommodityDataDetail> dataSource){
        mGoodLists.clear();
        if (dataSource==null){
            notifyDataSetChanged();
            return;
        }
        mGoodLists.addAll(dataSource);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGoodLists==null?0:mGoodLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final viewHoder hoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_fbh_store_detail, null);
            hoder = new viewHoder();
            hoder.tv_item_fbh_store_detail_introduce = (TextView) convertView.findViewById(R.id.tv_item_fbh_store_detail_introduce);
            hoder.tv_item_fbh_store_detail_payment = (TextView) convertView.findViewById(R.id.tv_item_fbh_store_detail_payment);
            hoder.tv_item_fbh_store_detail_price = (TextView) convertView.findViewById(R.id.tv_item_fbh_store_detail_price);
            hoder.iv_item_fbh_store_detail_shangpintu = (ImageView) convertView.findViewById(R.id.iv_item_fbh_store_detail_shangpintu);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
            Object tag = hoder.iv_item_fbh_store_detail_shangpintu.getTag(0);
            if (tag != null && tag instanceof ImageRequest) {
                ((ImageRequest) tag).cancel();
            }
            hoder.iv_item_fbh_store_detail_shangpintu.setImageResource(R.drawable.weixianshi);
        }
        hoder.iv_item_fbh_store_detail_shangpintu.setImageResource(R.drawable.weixianshi);
        hoder.tv_item_fbh_store_detail_payment.setText(mGoodLists.get(position).getPeople() + "人付款");
        hoder.tv_item_fbh_store_detail_introduce.setText(mGoodLists.get(position).getTitle());
        hoder.tv_item_fbh_store_detail_price.setText("￥" +mGoodLists.get(position).getPrice());
        final String url = ServerAddress.SERVER_ROOT + mGoodLists.get(position).getCover() ;
        ImageLoader.getInstance().displayImage(url, hoder.iv_item_fbh_store_detail_shangpintu);
        return convertView;
    }

    class viewHoder {
        //内容
        TextView tv_item_fbh_store_detail_introduce;
        //图片
        ImageView iv_item_fbh_store_detail_shangpintu;
        //价格
        TextView tv_item_fbh_store_detail_price;
        //付款
        TextView tv_item_fbh_store_detail_payment;
    }
}
