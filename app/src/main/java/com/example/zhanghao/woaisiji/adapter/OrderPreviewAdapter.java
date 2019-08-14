package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.order.OrderBean;
import com.example.zhanghao.woaisiji.bean.order.OrderGoodsBean;
import com.example.zhanghao.woaisiji.resp.RespAddOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderPreviewAdapter extends BaseExpandableListAdapter {

    private List<OrderBean> groups;
    private Context context;
    private boolean isSilver;
    /**
     * 构造函数
     *  @param groups  组元素列表
     * @param context
     * @param isSilver
     */
    public OrderPreviewAdapter(List<OrderBean> groups, Context context, boolean isSilver) {
        this.groups = groups;
        this.isSilver = isSilver;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getGoods().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_preview_goup_title, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        OrderBean group = (OrderBean) getGroup(groupPosition);
        gholder.tv_order_preview_group_store_name.setText(group.getStore_name());
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_preview, null);
            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();
        }

        OrderGoodsBean goodsInfo = (OrderGoodsBean) getChild(groupPosition, childPosition);

        if (goodsInfo != null) {
            cholder.tv_item_order_preview_good_title.setText(goodsInfo.getTitle());
            String price = goodsInfo.getPrice();
            cholder.tv_item_order_preview_good_price.setText("￥ " + price + "");
            cholder.tv_item_order_preview_good_num.setText("X " + goodsInfo.getCart_num());
            Picasso.with(context).load(goodsInfo.getCover()).error(R.drawable.ic_fubaihui)
                    .placeholder(R.drawable.ic_fubaihui).into(cholder.iv_item_order_preview_good_picture);
            notifyDataSetChanged();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 组元素绑定器
     */
    static class GroupViewHolder {
        TextView tv_order_preview_group_store_name;

        GroupViewHolder(View view) {
            tv_order_preview_group_store_name = (TextView) view.findViewById(R.id.tv_order_preview_group_store_name);
        }
    }

    /**
     * 子元素绑定器
     */
    static class ChildViewHolder {
        //价格
        public TextView tv_item_order_preview_good_price;
        //优惠券
        public TextView tv_item_order_preview_good_coupon;
        //商品名
        public TextView tv_item_order_preview_good_title;
        //商品图片
        public ImageView iv_item_order_preview_good_picture;
        //当前商品数量
        public TextView tv_item_order_preview_good_num;

        ChildViewHolder(View convertView) {
            tv_item_order_preview_good_price = (TextView) convertView.findViewById(R.id.tv_item_order_preview_good_price);
            tv_item_order_preview_good_coupon = (TextView) convertView.findViewById(R.id.tv_item_order_preview_good_coupon);
            tv_item_order_preview_good_title = (TextView) convertView.findViewById(R.id.tv_item_order_preview_good_title);
            iv_item_order_preview_good_picture = (ImageView) convertView.findViewById(R.id.iv_item_order_preview_good_picture);
            tv_item_order_preview_good_num = (TextView) convertView.findViewById(R.id.tv_item_order_preview_good_num);
        }
    }
}
