package com.example.zhanghao.woaisiji.activity.send.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019/7/20
 * Description:
 */
public abstract class CommonAdapter<E> extends BaseAdapter {
    private final List<E> data = new ArrayList<>();
    private int lastViewType;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public E getItem(int position) {
        return position >= 0 && data != null && position < data.size() ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = null;

        if (convertView != null) {
            viewHolder = (CommonViewHolder) convertView.getTag(convertView.getId());
        }

        boolean needCheck = false;
        if (getViewTypeCount() > 1) {
            int currentViewType = getItemViewType(position);
            if (currentViewType != lastViewType) {
                needCheck = true;
                lastViewType = currentViewType;
            }
        }

        if (convertView == null || needCheck) {
            viewHolder = createViewHolder(getItem(position));
            convertView = viewHolder.itemView;
            convertView.setTag(convertView.getId(), viewHolder);
        }

        if (viewHolder != null) {
            viewHolder.update(getItem(position));
        }

        return convertView;
    }

    public abstract CommonViewHolder createViewHolder(E model);

    public void addData(List<E> data) {
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public abstract class CommonViewHolder {
        public View itemView;

        private CommonViewHolder() {
        }

        public CommonViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public abstract void update(E model);
    }
}
