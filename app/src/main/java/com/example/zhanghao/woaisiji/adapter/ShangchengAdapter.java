package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.DriverMall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2016/8/11.
 */
public class ShangchengAdapter extends BaseAdapter {
    TextView textView;
    private final Context mContext;
    private final List<DriverMall.ListBean> mListData;
   // private int selectPos;
    private   int selectPos=-1;
    public void setSelectPos(int pos){
        this.selectPos = pos;
        notifyDataSetInvalidated();
    }
    public ShangchengAdapter(Context context, List<DriverMall.ListBean> listData) {
        mContext = context;
        mListData = listData;

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.shangcheng_itme,viewGroup,false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.boundData(convertView,mListData,position);
        return convertView;
    }

    class ViewHolder{
        TextView shop;
        public void boundData(View convertView, List<DriverMall.ListBean> mListData, int position) {
            shop = (TextView) convertView.findViewById(R.id.cell_tv);
            shop.setText(mListData.get(position).getTitle());
            if(selectPos==position){
                shop.setBackgroundColor(Color.WHITE);
                shop.setTextColor(Color.parseColor("#DF8807"));
            } else {
                shop.setBackgroundColor(Color.parseColor("#f4f4f4"));
                shop.setTextColor(Color.BLACK);
            }
        }
    }
}
