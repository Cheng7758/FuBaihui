package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.bean.AppMallBean;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
public class LoveDriverAppMallActivity extends Activity implements PullToRefreshLayout.OnRefreshListener {
    private BaseListView lvLoveDriverAppMallList;
    private List<AppMallBean> appMallLists;
    private PullToRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovedriver_app_mall);
        lvLoveDriverAppMallList = (BaseListView) findViewById(R.id.lv_love_driver_app_mall_list);
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
        getDataFromServer();
        lvLoveDriverAppMallList.setAdapter(new LoveDriverAppMallAdapter());
    }

    private void getDataFromServer() {
        appMallLists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AppMallBean appMallBean = new AppMallBean();
            appMallBean.setAppMallName("视频播放");
            appMallBean.setAppMallIcon(R.drawable.demo01);
            appMallBean.setAppMallIvEnter(R.drawable.iv_enter);
            appMallBean.appMallClassifyList.add("在线视频");
            appMallBean.appMallClassifyList.add("直播");
            appMallBean.appMallClassifyList.add("真人视频");
            appMallLists.add(appMallBean);
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
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    class LoveDriverAppMallAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appMallLists.size();
        }

        @Override
        public AppMallBean getItem(int i) {
            return appMallLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(LoveDriverAppMallActivity.this, R.layout.lovedriver_appmall_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvAppMallName = (TextView) view.findViewById(R.id.tv_app_mall_name);
                viewHolder.tvAppMallClassify01 = (TextView) view.findViewById(R.id.tv_app_mall_classify01);
                viewHolder.tvAppMallClassify02 = (TextView) view.findViewById(R.id.tv_app_mall_classify02);
                viewHolder.tvAppMallClassify03 = (TextView) view.findViewById(R.id.tv_app_mall_classify03);
                viewHolder.ivAppMallIcon = (ImageView) view.findViewById(R.id.iv_app_mall_icon);
                viewHolder.ibAppMallEnter = (ImageButton) view.findViewById(R.id.ib_app_mall_enter);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            AppMallBean item = getItem(i);
            viewHolder.ivAppMallIcon.setImageResource(item.getAppMallIcon());
            viewHolder.tvAppMallName.setText(item.getAppMallName());
            viewHolder.tvAppMallClassify01.setText(item.appMallClassifyList.get(0));
            viewHolder.tvAppMallClassify02.setText(item.appMallClassifyList.get(1));
            viewHolder.tvAppMallClassify03.setText(item.appMallClassifyList.get(2));
            viewHolder.ibAppMallEnter.setImageResource(item.getAppMallIvEnter());
            return view;
        }
    }

    static class ViewHolder {
        public TextView tvAppMallName;
        public TextView tvAppMallClassify01;
        public TextView tvAppMallClassify02;
        public TextView tvAppMallClassify03;
        public ImageView ivAppMallIcon;
        public ImageButton ibAppMallEnter;
    }
}
