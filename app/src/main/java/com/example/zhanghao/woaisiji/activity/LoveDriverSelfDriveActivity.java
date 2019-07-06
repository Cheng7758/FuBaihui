package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
public class LoveDriverSelfDriveActivity extends Activity implements PullToRefreshLayout.OnRefreshListener {
    private BaseListView lvLoveDriverConsultList;
    private List<DriveTravelBean.TravelList> travelLists;
    private PullToRefreshLayout refreshView;
    private Button btnRelease;
    private Button btnReleased;
    private Button btnBack;
    private TextView tvLoveDriverTitle;
    // 分页加载数据
    private static int pager = 1;
    private DriveTravelBean driveTravel;
    // 区分是司机健康还是司机资讯,还是自驾游
    private String commentType = "" + 4;
    private DriverTravelListAdapter driverTravelListAdapter;
    private boolean isFirst = true;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_driver_self_drive);
        lvLoveDriverConsultList = (BaseListView) findViewById(R.id.lv_love_driver_self_drive_list);
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
        btnRelease = (Button) findViewById(R.id.btn_release);
        btnReleased = (Button) findViewById(R.id.btn_released);
        tvLoveDriverTitle = (TextView) findViewById(R.id.love_driver_title);
       /* btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvLoveDriverTitle.setText("司机自驾游");
        travelLists = new ArrayList<>();
        driverTravelListAdapter = new DriverTravelListAdapter();
        getDataFromServer();

        btnRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())){
                    Toast.makeText(LoveDriverSelfDriveActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(LoveDriverSelfDriveActivity.this, SelfDriveReleaseActivity.class));
                }

            }
        });
        btnReleased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())){
                    Toast.makeText(LoveDriverSelfDriveActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(LoveDriverSelfDriveActivity.this, SelfDriveAlreadyReleaseActivity.class));
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pager = 1;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pager = 1;
                getDataFromServer();
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
                            lvLoveDriverConsultList.setAdapter(driverTravelListAdapter);
                        }else {
                            driverTravelListAdapter.notifyDataSetChanged();
                        }

//                        lvLoveDriverConsultList.setAdapter(new DriverTravelListAdapter());
                        lvLoveDriverConsultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // 跳转到webView
                                Intent intent = new Intent(LoveDriverSelfDriveActivity.this, WebViewActivity.class);
                                intent.putExtra("id", travelLists.get(i).id);
                                intent.putExtra("type", commentType);
                                startActivity(intent);
                            }
                        });
                    }else {
                        Toast.makeText(LoveDriverSelfDriveActivity.this,"数据已经加载完了...",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoveDriverSelfDriveActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p", "" + pager);
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
                view = View.inflate(LoveDriverSelfDriveActivity.this, R.layout.lovedriver_selfdrive_list_item, null);
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
