package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.send.JoinAutoDetailsActivity;
import com.example.zhanghao.woaisiji.activity.send.JoinDetailsActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespMerchantList;
import com.example.zhanghao.woaisiji.tools.CircleCornerTransform;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JoinCarAdapter extends BaseAdapter {
    private List<RespMerchantList.MerchantInfoDetailBean> carLists;
    private Context context;
    public JoinCarAdapter(Context context) {
        this.context=context;
        carLists = new ArrayList<>();
    }

    public List<RespMerchantList.MerchantInfoDetailBean> getDataSource(){
        return carLists ;
    }

    @Override
    public int getCount() {
        return carLists.size();
    }

    @Override
    public RespMerchantList.MerchantInfoDetailBean getItem(int position) {
        return carLists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
       ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.car_service_item,null);
            viewHolder.img_car= (ImageView) convertView.findViewById(R.id.img_car);
            viewHolder.tx_name= (TextView) convertView.findViewById(R.id.tx_name);
            viewHolder.tx_content= (TextView) convertView.findViewById(R.id.tx_content);
            viewHolder.tx_address_detail= (TextView) convertView.findViewById(R.id.tx_address_detail);
            viewHolder.tx_m= (TextView) convertView.findViewById(R.id.tx_m);
            viewHolder.ll_car_service = (LinearLayout) convertView.findViewById(R.id.ll_car_service);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final RespMerchantList.MerchantInfoDetailBean item = getItem(position);
        String url = ServerAddress.SERVER_ROOT+item.getLogo() ;
        CircleCornerTransform circleCornerTransform = new CircleCornerTransform(context);
        circleCornerTransform.setRoundWidthPercentage(0.1f);
        Picasso.with(context).load(url).transform(circleCornerTransform)
                .error(R.drawable.icon_loading).into(viewHolder.img_car);
        viewHolder.tx_name.setText(item.getName());
//        viewHolder.tx_content.setText(item.getContent());
        if (!TextUtils.isEmpty(item.getJuli())) {
            viewHolder.tx_m.setText(item.getJuli());
        }
        viewHolder.ll_car_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JoinDetailsActivity.class);
                intent.putExtra("IntentJoinToDetailDataId",item.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    static class ViewHolder{
        public ImageView img_car;
        public TextView tx_name;
        public TextView tx_content;
        public TextView tx_address_detail;
        public TextView tx_m;
        public LinearLayout ll_car_service;
    }
}
