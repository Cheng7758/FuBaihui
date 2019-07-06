package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.MapGpsBean;
import com.example.zhanghao.woaisiji.bean.WuliuBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.ArrayList;
import java.util.List;

public class WuliuAdapter extends BaseAdapter {
    public List<WuliuBean.InfoBean.TracesBean> carLists;
    private WuliuBean wuliuBean;
    private Context context;

    public WuliuAdapter(Context context) {
        this.context = context;
        carLists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return carLists.size();
    }

    @Override
    public WuliuBean.InfoBean.TracesBean getItem(int position) {
        return carLists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.wuliu_item, null);
            viewHolder.tx_name = (TextView) convertView.findViewById(R.id.tx_name);
            viewHolder.tx_time = (TextView) convertView.findViewById(R.id.tx_time);
            viewHolder.ll_car_service = (LinearLayout) convertView.findViewById(R.id.ll_car_service);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final WuliuBean.InfoBean.TracesBean item = getItem(position);

        viewHolder.tx_name.setText(item.getAcceptStation());
        viewHolder.tx_time.setText(item.getAcceptTime());


        wuliuBean = new WuliuBean();


        viewHolder.ll_car_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

    static class ViewHolder {

        public TextView tx_name;
        public TextView tx_time;
        public LinearLayout ll_car_service;
    }
}
