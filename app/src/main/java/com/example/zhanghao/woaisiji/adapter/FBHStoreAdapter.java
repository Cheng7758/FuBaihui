package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.DriverMall;
import com.example.zhanghao.woaisiji.bean.fbh.FBHStoreCategory;
import com.example.zhanghao.woaisiji.resp.RespFBHStoreCategory;

import java.util.List;

public class FBHStoreAdapter extends BaseAdapter {
    TextView textView;
    private Context mContext;
    private List<FBHStoreCategory> mListData;
    // private int selectPos;
    private int selectPos = -1;

    public void setSelectPos(int pos) {
        this.selectPos = pos;
        notifyDataSetInvalidated();
    }

    public FBHStoreAdapter(Context context, List<FBHStoreCategory> listData) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fbh_store_adapter, viewGroup, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.boundData(convertView, mListData, position);
        return convertView;
    }

    class ViewHolder {
        TextView tv_item_fbh_store_title;

        public void boundData(View convertView, List<FBHStoreCategory> mListData, int position) {
            tv_item_fbh_store_title = (TextView) convertView.findViewById(R.id.tv_item_fbh_store_title);
            tv_item_fbh_store_title.setText(mListData.get(position).getTitle());
            if (selectPos == position) {
                tv_item_fbh_store_title.setBackgroundColor(Color.WHITE);
                tv_item_fbh_store_title.setTextColor(Color.parseColor("#DF8807"));
            } else {
                tv_item_fbh_store_title.setBackgroundColor(Color.parseColor("#f4f4f4"));
                tv_item_fbh_store_title.setTextColor(Color.BLACK);
            }
        }
    }
}
