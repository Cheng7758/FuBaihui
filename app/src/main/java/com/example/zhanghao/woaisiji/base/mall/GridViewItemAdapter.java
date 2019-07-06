package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.DriverShoppingBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.AbViewUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by admin on 2016/10/29.
 */
public class GridViewItemAdapter extends BaseAdapter {

    private Activity mActivity;
    public List<DriverShoppingBean.ListBean> mGoodLists;

    public GridViewItemAdapter(Activity activity) {
        mActivity = activity;
//        this.mGoodLists = mGoodsLists;
    }

    @Override
    public int getCount() {
        return mGoodLists.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.recommend_itme, null);
            hoder = new viewHoder();
//            AbViewUtil.scaleContentView((ViewGroup) convertView);
            hoder.introduce = (TextView) convertView.findViewById(R.id.introduce);
            hoder.payment = (TextView) convertView.findViewById(R.id.payment);
            hoder.price = (TextView) convertView.findViewById(R.id.price);
            hoder.iv_shangpintu = (ImageView) convertView.findViewById(R.id.iv_shangpintu);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
            Object tag = hoder.iv_shangpintu.getTag(0);
            if (tag != null && tag instanceof ImageRequest) {
                ((ImageRequest) tag).cancel();
            }
            hoder.iv_shangpintu.setImageResource(R.drawable.weixianshi);
        }
        hoder.iv_shangpintu.setImageResource(R.drawable.weixianshi);
        hoder.payment.setText(mGoodLists.get(position).getPeople() + "人付款");
        hoder.introduce.setText(mGoodLists.get(position).getTitle());
        hoder.price.setText("￥" +mGoodLists.get(position).getPrice());
        final String url = ServerAddress.SERVER_ROOT + mGoodLists.get(position).getCover().get(0);
        ImageLoader.getInstance().displayImage(url, hoder.iv_shangpintu);
        return convertView;
    }

    class viewHoder {
        //内容
        TextView introduce;
        //图片
        ImageView iv_shangpintu;
        //价格
        TextView price;
        //付款
        TextView payment;
    }
}
