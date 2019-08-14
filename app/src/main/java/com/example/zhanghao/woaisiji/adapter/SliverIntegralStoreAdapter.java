package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.SliverIntegralStoreDetail;
import com.example.zhanghao.woaisiji.activity.send.JoinDetailsActivity;
import com.example.zhanghao.woaisiji.bean.merchant.MerchantInfoDetailBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.CircleCornerTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliverIntegralStoreAdapter extends BaseAdapter {
    private List<MerchantInfoDetailBean> carLists;
    private Context context;
    private double mLatitude;
    private double mAltitude;
    private static final double EARTH_RADIUS = 6378.137;

    public void setLatitude(double pLatitude) {
        mLatitude = pLatitude;
    }

    public void setAltitude(double pAltitude) {
        mAltitude = pAltitude;
    }

    public SliverIntegralStoreAdapter(Context context) {
        this.context = context;
        carLists = new ArrayList<>();
    }

    public List<MerchantInfoDetailBean> getDataSource() {
        return carLists;
    }

    @Override
    public int getCount() {
        return carLists.size();
    }

    @Override
    public MerchantInfoDetailBean getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.car_service_item, null);
            viewHolder.img_car = (ImageView) convertView.findViewById(R.id.img_car);
            viewHolder.tx_name = (TextView) convertView.findViewById(R.id.tx_name);
            viewHolder.tx_content = (TextView) convertView.findViewById(R.id.tx_content);
            viewHolder.tx_address_detail = (TextView) convertView.findViewById(R.id.tx_address_detail);
            viewHolder.tx_m = (TextView) convertView.findViewById(R.id.tx_m);
            viewHolder.ll_car_service = (LinearLayout) convertView.findViewById(R.id.ll_car_service);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MerchantInfoDetailBean item = getItem(position);
        String url = item.getLogo();
        CircleCornerTransform circleCornerTransform = new CircleCornerTransform(context);
        circleCornerTransform.setRoundWidthPercentage(0.1f);
        Picasso.with(context).load(url).transform(circleCornerTransform)
                .error(R.drawable.ic_fubaihui).into(viewHolder.img_car);
        viewHolder.tx_name.setText(item.getName());
//        viewHolder.tx_content.setText(item.getContent());
        /*if (!TextUtils.isEmpty(item.getJuli())) {
            viewHolder.tx_m.setText(item.getJuli());
        }*/
        double latitude = 0.00;
        double longitude = 0.00;
        if (!TextUtils.isEmpty(item.getLatitude()) && !TextUtils.isEmpty(item.getLongitude())) {
            latitude = Double.parseDouble(item.getLatitude());
            longitude = Double.parseDouble(item.getLongitude());
        }
        if (mLatitude == 0.00 || mAltitude == 0.00 || latitude == 0.00 || longitude == 0.00) {
            viewHolder.tx_m.setText("未知距离");
        } else {
            double distance = getDistance(mLatitude, mAltitude, latitude, longitude);
            viewHolder.tx_m.setText(distance + "km");
        }
        viewHolder.ll_car_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SliverIntegralStoreDetail.class);
                intent.putExtra("IntentSliverDetailCommodityID", item.getId());
                intent.putExtra("latitude", mLatitude+"");
                intent.putExtra("altitude", mAltitude+"");
                intent.putExtra("type","1");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        public ImageView img_car;
        public TextView tx_name;
        public TextView tx_content;
        public TextView tx_address_detail;
        public TextView tx_m;
        public LinearLayout ll_car_service;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 返回单位是:千米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        //有小数的情况;注意这里的10000d中的“d”
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;//单位：米
//        s = Math.round(s/10d) /100d   ;//单位：千米 保留两位小数
        s = Math.round(s / 100d) / 10d;//单位：千米 保留一位小数
        return s;
    }
}
