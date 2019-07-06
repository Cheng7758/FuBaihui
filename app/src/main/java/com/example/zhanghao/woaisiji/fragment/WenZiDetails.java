package com.example.zhanghao.woaisiji.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.CommentActivity;
import com.example.zhanghao.woaisiji.adapter.ImageAdapter;
import com.example.zhanghao.woaisiji.bean.DetailsBean;
import com.example.zhanghao.woaisiji.bean.ProductPictureBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.AmountView;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghao on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class WenZiDetails extends Fragment implements ViewPager.OnPageChangeListener {
    //储存请求地址
    public static final String ID = "id";
    private List<String> PictureUrl = new ArrayList<>();
    private DetailsBean detailsBean;
    //图片返回值
    private String cover = "";
    private ImageAdapter imageAdapter;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ImageView[] imageViews;
    private ImageView img;
    private Context mContext;
    private SendDataActivityListener listener;
    private TextView jiujiage;

    @SuppressLint("ValidFragment")
    public WenZiDetails(Context context, DetailsBean detailsBean) {
        this.detailsBean = detailsBean;
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.productdetail_xiangqing_layout, null);
        //灰色间隔
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout_mall_product_detail_dots);
        //viewpager左右滑动的图片
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_mall_product_detail);
        //商品名称
        TextView shangpinjieshao = (TextView) view.findViewById(R.id.tv_shangpinjieshao);
        //商品价格数字
        TextView jiage = (TextView) view.findViewById(R.id.tv_jiage);
        //金a币银a币黄色文本
        TextView diya = (TextView) view.findViewById(R.id.tv_diya);
        //送金A币
        TextView jinbi = (TextView) view.findViewById(R.id.tv_jinAbi);
        //送银A币
        TextView yinbi = (TextView) view.findViewById(R.id.tv_yinBbi);
        //免运费
        TextView yunfei = (TextView) view.findViewById(R.id.tv_yunfei);
        //宝贝评价的数量
        TextView pingjiaTotal = (TextView) view.findViewById(R.id.tv_pingjiaTotal);
        //评价昵称
        TextView nickname = (TextView) view.findViewById(R.id.te_nickname);
        //评论
        TextView pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
        //全部评论
        ImageView quanbupinglun = (ImageView) view.findViewById(R.id.iv_quanbupinglun);
        //旧价格，价格下面，划横线的那个
        jiujiage = (TextView) view.findViewById(R.id.tv_jiujiage);
        //库存剩余量
        TextView Stock = (TextView) view.findViewById(R.id.stock);

        for (int i = 0; i < detailsBean.getInfo().getCover().size(); i++) {
            cover = cover + detailsBean.getInfo().getCover().get(i) + ",";
        }
        //数量加减的指示器
        AmountView amountView = (AmountView) view.findViewById(R.id.amount_view);


//       ------------------------------------------------------------------------------------------------------------
        PictureRequest();//请求图片

        //以下都是设置参数
        jiage.setText("￥" + Double.valueOf(detailsBean.getInfo().getPrice()));
        jiujiage.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字被划掉效果
        jiujiage.setText("价格:￥" + Double.valueOf(detailsBean.getInfo().getPrice_sc()));
        if (detailsBean.getInfo().getCarrmb().equals("0")) {
            yunfei.setText("免运费");
        } else {
            yunfei.setText(detailsBean.getInfo().getCarrmb() + "元");
        }
        shangpinjieshao.setText(detailsBean.getInfo().getTitle());
        jinbi.setText("送金币" + detailsBean.getInfo().getF_sorts());
        yinbi.setText("送银币" + detailsBean.getInfo().getF_silver());
        diya.setText("银币可抵" + detailsBean.getInfo().getMaxchit());
        //判断评论是否存在，不写if没有评论的商品就会包空指针
        if (detailsBean.getPllist() != null) {
            //日期
            TextView riqi = (TextView) view.findViewById(R.id.tv_riqi);
            //评价头像
            RoundImageView portrait = (RoundImageView) view.findViewById(R.id.iv_pingjiaportrait);
            pinglun.setText(detailsBean.getPllist().get(0).getContent());
            nickname.setText(detailsBean.getPllist().get(0).getUser_name());
            pingjiaTotal.setText("(" + detailsBean.getPlcount() + ")");
            String url = ServerAddress.SERVER_ROOT + detailsBean.getPllist().get(0).getStarts();
            ImageLoader.getInstance().displayImage(url, portrait);
            riqi.setText(times(detailsBean.getPllist().get(0).getCtime()));

            quanbupinglun.setOnClickListener(new View.OnClickListener() {
                String id = detailsBean.getInfo().getId();

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra(ID, id);
                    mContext.startActivity(intent);
                }
            });
        } else {
            pinglun.setText("");
            nickname.setText("");
            pingjiaTotal.setText("(" + "0" + ")");

            TextView riqi = (TextView) view.findViewById(R.id.tv_riqi);
            riqi.setVisibility(View.GONE);
            RoundImageView portrait = (RoundImageView) view.findViewById(R.id.iv_pingjiaportrait);
            portrait.setVisibility(View.GONE);
            quanbupinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "亲没有更多的评论了", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Stock.setText("库存剩余量" + detailsBean.getInfo().getNumber());
        int kucun = Integer.parseInt(detailsBean.getInfo().getNumber());
        //  listener.sendData("1");
        amountView.setGoods_storage(kucun);
        amountView.setListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                Log.d("amountView", "" + amount);
                String num = String.valueOf(amount);
                if (listener != null) {
                    listener.sendData(num);
                }
            }
        });
        return view;
    }

    public void setSendDataActivity(SendDataActivityListener listener) {
        this.listener = listener;
    }

    public interface SendDataActivityListener {
        public void sendData(String data);
    }

    /**
     * 请求图片
     */
    private void PictureRequest() {
        PictureUrl.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_PRODUCTPICTURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ProductPictureBean productPicture = gson.fromJson(response, ProductPictureBean.class);
                if (productPicture == null) {
                    Toast.makeText(getContext(), "服务器维护", Toast.LENGTH_SHORT).show();
                }
                if (productPicture.getCode() == 200) {
                    PictureUrl.addAll(productPicture.getList());
                    if (imageAdapter != null) {
                        imageAdapter.notifyDataSetChanged();
                    }


//                    ImageView imageView = new ImageView(mActivity);
//                    ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
//                    layoutParams.width =  ViewPager.LayoutParams.MATCH_PARENT;
//                    layoutParams.height =   ViewPager.LayoutParams.MATCH_PARENT;
//
//                    imageView.setLayoutParams(layoutParams);
//                    imageView.setPadding(0,3,0,0);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    imageViews = new ImageView[PictureUrl.size()];
                    LinearLayout.LayoutParams layoutParams = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 0);

                    for (int i = 0; i < PictureUrl.size(); i++) {
                        img = new ImageView(getActivity());
                        imageViews[i] = img;
                        if (i == 0) {
                            imageViews[i].setBackgroundResource(R.drawable.dot_focus);
                        } else {
                            imageViews[i].setBackgroundResource(R.drawable.dot_normal);
                        }
                        img.setLayoutParams(layoutParams);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);//图片的布局
                        linearLayout.addView(imageViews[i]);

                    }
                    imageAdapter = new ImageAdapter(PictureUrl, getActivity());//viewpager滑动图片的布局

                    viewPager.setAdapter(imageAdapter);
                    viewPager.setOnPageChangeListener(WenZiDetails.this);
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("cover", cover);
                Log.d("cover", "" + cover);


                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < imageViews.length; i++) {

            if (position == i) {
                imageViews[position].setBackgroundResource(R.drawable.dot_focus);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.dot_normal);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
}
