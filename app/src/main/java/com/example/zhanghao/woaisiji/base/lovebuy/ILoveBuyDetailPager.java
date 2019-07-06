package com.example.zhanghao.woaisiji.base.lovebuy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.bean.ImageUrlBean;
import com.example.zhanghao.woaisiji.bean.LoveBuyBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/4.
 */
public class ILoveBuyDetailPager extends BasePagerDetail {

    private ListView lvLoveBuy;
    private int time = 1;
    private LoveBuyBean loveBuyBean;
    private int type =1;

    private List<LoveBuyBean.DataBean> loveBuyDataList;
    private TextView tvLaveTime;



    public ILoveBuyDetailPager(Activity activity,int time) {
        super(activity);
        this.time = time;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_love_buy_detail,null);
        lvLoveBuy = (ListView) view.findViewById(R.id.lv_love_buy);
        tvLaveTime = (TextView) view.findViewById(R.id.tv_lave_time);
        return view;
    }

    @Override
    public void initData() {
        loveBuyDataList = new ArrayList<>();
        // 获取服务器数据
        getDadaFromServer();

    }

    private void getDadaFromServer() {

        StringRequest loveBuyRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_LOVE_BUY_MALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transServerData(response);
                if (listener != null){
                    List<String> data = new ArrayList<>();
                    data.add(String.valueOf(loveBuyBean.time));
                    data.add(String.valueOf(loveBuyBean.t10));
                    data.add(String.valueOf(loveBuyBean.t11));
                    data.add(String.valueOf(loveBuyBean.t12));
                    data.add(String.valueOf(loveBuyBean.t13));
                    listener.sendData(data);
                }

                if (loveBuyBean.data != null){
                    for (LoveBuyBean.DataBean bean : loveBuyBean.data){
                        loveBuyDataList.add(bean);
                    }
                    // 设置适配器
                    lvLoveBuy.setAdapter(new LoveBuyAdapter());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("qg_time", String.valueOf(time));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(loveBuyRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        loveBuyBean = gson.fromJson(response,LoveBuyBean.class);
    }


    class LoveBuyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return loveBuyDataList.size();
        }

        @Override
        public LoveBuyBean.DataBean getItem(int i) {
            return loveBuyDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null){
                view = View.inflate(mActivity,R.layout.item_love_buy_list,null);
                viewHolder = new ViewHolder();
                viewHolder.ivItemIconPic = (ImageView) view.findViewById(R.id.iv_item_icon_pic);
                viewHolder.tvItemTitle = (TextView) view.findViewById(R.id.tv_item_title);
                viewHolder.tvItemPrice = (TextView) view.findViewById(R.id.tv_item_price);
                viewHolder.btnItemLoveBuy = (Button) view.findViewById(R.id.btn_item_love_buy);
                viewHolder.tvItemProgress = (TextView) view.findViewById(R.id.tv_item_progress);
                viewHolder.tvItemNum = (TextView) view.findViewById(R.id.tv_item_num);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final LoveBuyBean.DataBean item = getItem(i);
            viewHolder.tvItemTitle.setText(item.title);
            viewHolder.tvItemPrice.setText("￥"+item.price);
            viewHolder.tvItemNum.setText("已抢"+item.people+"件");

            StringRequest pictureRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_IMAGE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    ImageUrlBean imageUrl = gson.fromJson(response, ImageUrlBean.class);
                    ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+imageUrl.path,viewHolder.ivItemIconPic);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("img_id",item.cover.get(0));
                    return params;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(pictureRequest);

//            Log.d("dddddddd",timeDiffrent(String.valueOf(loveBuyBean.time),String.valueOf(loveBuyBean.t10)));

//            boolean isBegin = false;
//            viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_bg);
            if (time == 1){
                if (loveBuyBean.time>=loveBuyBean.t10 && loveBuyBean.time <=loveBuyBean.t11){
//                    isBegin = true;
                    viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_bg);
//                    viewHolder.btnItemLoveBuy.setTextColor();
                    viewHolder.btnItemLoveBuy.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mActivity, ProductDetailActivity2.class);
//                    String Id = item.id
                            intent.putExtra("id", item.id);
                            intent.putExtra("type", type);
                            mActivity.startActivity(intent);
                        }
                    });
                }else {
//                    isBegin = false;
                }
            }else {
//                viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_white_bg);
            }
            if (time == 2){
                if (loveBuyBean.time>=loveBuyBean.t11 && loveBuyBean.time <= loveBuyBean.t12){
                    viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_bg);
                    viewHolder.btnItemLoveBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mActivity, ProductDetailActivity2.class);
//                    String Id = item.id
                            intent.putExtra("id", item.id);
                            intent.putExtra("type", type);
                            mActivity.startActivity(intent);
                        }
                    });
//                    isBegin = true;
                }else {
//                    isBegin = false;
                }
            }else {
//                viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_white_bg);
            }
            if (time == 3){
                if (loveBuyBean.time >= loveBuyBean.t12 && loveBuyBean.time <= loveBuyBean.t13){
                    viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_bg);
                    viewHolder.btnItemLoveBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mActivity, ProductDetailActivity2.class);
//                    String Id = item.id
                            intent.putExtra("id", item.id);
                            intent.putExtra("type", type);
                            mActivity.startActivity(intent);
                        }
                    });
//                    isBegin = true;
                }else {
//                    isBegin = false;
                }
            }else {
//                viewHolder.btnItemLoveBuy.setBackgroundResource(R.drawable.btn_shape_white_bg);
            }
//            if (isBegin){
//                viewHolder.btnItemLoveBuy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(mActivity, ProductDetailActivity2.class);
////                    String Id = item.id
//                        intent.putExtra("id", item.id);
//                        intent.addFlags(type);
//                        mActivity.startActivity(intent);
//                    }
//                });
//            }

            return view;
        }
    }
    static class  ViewHolder{
        public ImageView ivItemIconPic;
        public TextView tvItemTitle;
        public TextView tvItemPrice;
        public TextView tvItemProgress;
        public Button btnItemLoveBuy;
        public TextView tvItemNum;
    }

    private static final long ONE_MINUTE = 60000L;
    public  String timeDiffrent(String time1, String time2) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        int i = Integer.parseInt(time2);
        String times = sdf.format(new Date(i * 1000L));
        Date date = null;
        try {
            date = sdf.parse(times);
            long delta = new Date().getTime() - date.getTime();
//            Log.d("dddddddd",""+toSeconds(delta));
            if (delta < 1L * ONE_MINUTE) {
                long seconds = toSeconds(delta);
                return (seconds <= 0 ? 1 : seconds) +"";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""+toSeconds(new Date().getTime() - date.getTime());
//        return "还没到抢购时间";
//        return toSeconds(delta);
    }

    private long toSeconds(long date) {
        return date / 1000L;
    }


    private OnSendDataListener listener;
    public void setOnSendDataListener(OnSendDataListener listener){
        this.listener = listener;
    }
    // 回调接口
    public interface OnSendDataListener{
        public void sendData(List<String> data);
    }

}
