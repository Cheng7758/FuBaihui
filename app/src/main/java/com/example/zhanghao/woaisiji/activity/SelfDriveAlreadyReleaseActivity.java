package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.DriveTravelBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/21.
 */
public class SelfDriveAlreadyReleaseActivity extends Activity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{

    private Button btnBack;
    private TextView tvLoveDriverTitle;
    private PullToRefreshLayout refreshView;
    private BaseListView blvLoveDriverSelfDriverList;

    // 分页加载数据
    private static int pager = 1;
    private DriveTravelBean driveTravel;
    private String commentType = "" + 4;
    private List<DriveTravelBean.TravelList> travelLists;
    private DriverTravelListAdapter driverTravelListAdapter;
    private boolean isFirst = true;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfdrive_already_release);
        initView();
        responseClickListener();
        travelLists = new ArrayList<>();
        driverTravelListAdapter = new DriverTravelListAdapter();
        getDataFromServer();
    }


    private void initView() {
//        btnBack = (Button) findViewById(R.id.btn_back);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvLoveDriverTitle = (TextView) findViewById(R.id.love_driver_title);
        tvLoveDriverTitle.setText("已发布");
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        blvLoveDriverSelfDriverList = (BaseListView) findViewById(R.id.lv_love_driver_self_drive_list);
    }

    private void responseClickListener() {
        ivBack.setOnClickListener(this);
        refreshView.setOnRefreshListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件刷新完毕了哦！
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pager = pager+1;
                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pager = 1;
    }


    private void getDataFromServer() {

        // 从网络获取数据
        StringRequest travelDataRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVE_TRAVEL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (driveTravel.code == 200) {
//                    Toast.makeText(LoveDriverSelfDriveActivity.this,"请求成功"+driveTravel.code,Toast.LENGTH_SHORT).show();
                    if (driveTravel.list != null) {
                        for (int i = 0; i < driveTravel.list.size(); i++) {
                            travelLists.add(driveTravel.list.get(i));
                        }

                        if (isFirst){
                            isFirst = false;
                            blvLoveDriverSelfDriverList.setAdapter(driverTravelListAdapter);
                        }else {
                            driverTravelListAdapter.notifyDataSetChanged();
                        }

//                        lvLoveDriverConsultList.setAdapter(new DriverTravelListAdapter());
                        blvLoveDriverSelfDriverList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // 跳转到webView
                                Intent intent = new Intent(SelfDriveAlreadyReleaseActivity.this, WebViewActivity.class);
                                intent.putExtra("id", travelLists.get(i).id);
                                intent.putExtra("type", commentType);
                                startActivity(intent);
                            }
                        });
                    }else {
                        Toast.makeText(SelfDriveAlreadyReleaseActivity.this,"数据已经加载完了...",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelfDriveAlreadyReleaseActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("p", String.valueOf(pager));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(travelDataRequest);

    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        driveTravel = gson.fromJson(response, DriveTravelBean.class);
    }

    class DriverTravelListAdapter extends BaseAdapter {
        private DisplayImageOptions options;
        public DriverTravelListAdapter(){
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return travelLists.size();
        }

        @Override
        public DriveTravelBean.TravelList getItem(int i) {
            return travelLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(SelfDriveAlreadyReleaseActivity.this, R.layout.lovedriver_selfdrive_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvTravelTitle = (TextView) view.findViewById(R.id.tv_travel_title);
                viewHolder.tvTravelNickName = (TextView) view.findViewById(R.id.tv_travel_nick_name);
                viewHolder.tvTravelRead = (TextView) view.findViewById(R.id.tv_travel_read);
                viewHolder.tvTravelPraise = (TextView) view.findViewById(R.id.tv_travel_praise);
                viewHolder.ivTravelDefault = (ImageView) view.findViewById(R.id.iv_travel_default);
                viewHolder.ivTravelHeadPic = (ImageView) view.findViewById(R.id.iv_travel_headpic);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            DriveTravelBean.TravelList item = getItem(i);
            viewHolder.tvTravelTitle.setText(item.title);
            viewHolder.tvTravelNickName.setText(item.nickname);
            viewHolder.tvTravelRead.setText(item.view_num);
            viewHolder.tvTravelPraise.setText(item.top);
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.headpic,viewHolder.ivTravelHeadPic,options,null);
            if (item.picture !=null){
                ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+item.picture.get(0),viewHolder.ivTravelDefault,options,null);
            }
//

            return view;
        }
    }

    static class ViewHolder {
        public TextView tvTravelTitle;
        public TextView tvTravelNickName;
        public TextView tvTravelRead;
        public TextView tvTravelPraise;
        public ImageView ivTravelDefault;
        private ImageView ivTravelHeadPic;
    }
}
