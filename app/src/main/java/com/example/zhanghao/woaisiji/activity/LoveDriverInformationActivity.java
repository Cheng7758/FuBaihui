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
import com.example.zhanghao.woaisiji.bean.DriverInformationBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
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
 * Created by admin on 2016/8/19.
 */
public class LoveDriverInformationActivity extends Activity implements PullToRefreshLayout.OnRefreshListener {
    private BaseListView lvLoveDriverConsultList;
    // 司机资讯列表
    private List<DriverInformationBean.InformationList> informationList;
    private PullToRefreshLayout refreshView;
    private Button btnBack;
    private TextView tvLoveDriverTitle;
    // 区分是司机健康还是司机资讯
    private String commentType = "" + 5;
    private DriverInformationBean driverInformation;
    private static int pager = 1;
    private boolean isFirst = true;
    private ConsultListAdapter consultListAdapter;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovedriver_consult);
        initView();

        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
       /* healthBeanList = new ArrayList<>();
        healthBeanList = new ArrayList<>();*/
        informationList = new ArrayList<>();
        consultListAdapter = new ConsultListAdapter();
        getDataFromServer();

    }

    private void initView() {
//        btnBack = (Button) findViewById(R.id.btn_back);


        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvLoveDriverTitle = (TextView) findViewById(R.id.love_driver_title);

        tvLoveDriverTitle.setText("司机资讯");

        lvLoveDriverConsultList = (BaseListView) findViewById(R.id.lv_love_driver_consult_list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pager = 1;
    }

    private void getDataFromServer() {

        StringRequest driverInformationRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVE_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (driverInformation.code == 200) {
//                    if ("health".equals(typeName)) {
                    if (driverInformation.list != null){
                        for (int i = 0; i < driverInformation.list.size(); i++) {
//                        healthBeanList.add(driverHealth.list.get(i));
                            informationList.add(driverInformation.list.get(i));
                        }

                        if (isFirst){
                            isFirst = false;
                            lvLoveDriverConsultList.setAdapter(consultListAdapter);
                        }else {
                            consultListAdapter.notifyDataSetChanged();
                        }

//                        lvLoveDriverConsultList.setAdapter(new ConsultListAdapter());
                        lvLoveDriverConsultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // 跳转到webView
                                Intent intent = new Intent(LoveDriverInformationActivity.this, WebViewConsultActivity.class);
                                intent.putExtra("id", informationList.get(i).id);
                                intent.putExtra("type", commentType);
                                startActivity(intent);
                            }
                        });
                    }else {
                        Toast.makeText(LoveDriverInformationActivity.this,"数据已经加载完了...",Toast.LENGTH_SHORT).show();
                    }

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
                params.put("p", String.valueOf(pager));
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(driverInformationRequest);


    }

    private void transServerData(String response) {
        Gson gson = new Gson();
//        driverHealth = gson.fromJson(response, DriverHealthBean.class);
//        driverInformation = gson.fromJson(response,DriverInformationBean.class);
        driverInformation = gson.fromJson(response,DriverInformationBean.class);
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
                pager = pager +1;
                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    class ConsultListAdapter extends BaseAdapter {
        private DisplayImageOptions options;

        public ConsultListAdapter() {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        @Override
        public DriverInformationBean.InformationList getItem(int i) {
            return informationList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(LoveDriverInformationActivity.this, R.layout.lovedriver_consult_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvConsultTitle = (TextView) view.findViewById(R.id.tv_consult_title);
                viewHolder.tvConsultDate = (TextView) view.findViewById(R.id.tv_consult_date);
                viewHolder.tvConsultRead = (TextView) view.findViewById(R.id.tv_consult_read);
//                viewHolder.tvConsultPraise = (TextView) view.findViewById(R.id.tv_consult_praise);
                viewHolder.ivConsultDefault = (ImageView) view.findViewById(R.id.iv_consult_default);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
//            DriverHealthBean.DriverHealthList item = getItem(i);
            DriverInformationBean.InformationList item = getItem(i);
            viewHolder.tvConsultTitle.setText(item.title);
            viewHolder.tvConsultDate.setText(TimeUtils.times(item.update_time));
            viewHolder.tvConsultRead.setVisibility(View.GONE);
//            viewHolder.tvConsultRead.setText(item.view_num);
//            viewHolder.tvConsultPraise.setText(Integer.toString(item.getPraises()));
            if (item.picture != null){
                viewHolder.ivConsultDefault.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.picture.get(0)
                        , viewHolder.ivConsultDefault, options, null);
            }else {
                viewHolder.ivConsultDefault.setVisibility(View.GONE);
            }

            return view;
        }
    }

    static class ViewHolder {
        public TextView tvConsultTitle;
        public TextView tvConsultDate;
        public TextView tvConsultRead;
        //        public TextView tvConsultPraise;
        public ImageView ivConsultDefault;
    }
}
