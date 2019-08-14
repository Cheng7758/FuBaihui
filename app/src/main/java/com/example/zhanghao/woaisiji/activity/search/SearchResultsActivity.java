package com.example.zhanghao.woaisiji.activity.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.search.SearchVipBean;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchResultsActivity extends Activity implements PullToRefreshLayout.OnRefreshListener{

    private static final int INFO_SEARCH = 0;
    private static final int ADDRESS_INFO = 1;
    private int pager = 1;
    private boolean isFirst = true;
    private BaseListView blvSocialSearchResult;
    private PullToRefreshLayout refreshView;
    private List<SearchVipBean.DataBean> mSearchVipBeanList;
    private MemberInfoListAdapter memberInfoListAdapter;
    private ImageView ivSearchResultBack;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        keyword = getIntent().getStringExtra("keyword");
        setContentView(R.layout.activity_search_result);
        initView();
        mSearchVipBeanList = new ArrayList<>();
        memberInfoListAdapter = new MemberInfoListAdapter();
        getDataFromServer();
    }

    private void initView() {
        ivSearchResultBack = (ImageView) findViewById(R.id.iv_search_result_back);
        ivSearchResultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        blvSocialSearchResult = (BaseListView) findViewById(R.id.lv_social_search_result);
        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
    }

    private void getDataFromServer() {
        // 获取服务器数据
        Map<String, String> params = new HashMap<>();
        params.put("uid", WoAiSiJiApp.getUid());
        params.put("token", WoAiSiJiApp.token);
        params.put("keyword", keyword);
        params.put("page", String.valueOf(pager));
        NetManager.getNetManager().getMyService(Myserver.url)
                .getSearchVipBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchVipBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchVipBean value) {
                        Log.e("-----search", value.getData().toString());
                        if (value.getCode() == 200) {
                            if (value.getData() != null) {
                                mSearchVipBeanList = value.getData();
                                if (isFirst) {
                                    isFirst = false;
                                    blvSocialSearchResult.setAdapter(memberInfoListAdapter);
                                } else {
                                    memberInfoListAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(SearchResultsActivity.this, "数据已经加载完了!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                pager = pager + 1;
                getDataFromServer();
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    class MemberInfoListAdapter extends BaseAdapter {
        private DisplayImageOptions options;

        public MemberInfoListAdapter() {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return mSearchVipBeanList.size();
        }

        @Override
        public SearchVipBean.DataBean getItem(int i) {
            return mSearchVipBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(SearchResultsActivity.this, R.layout.social_member_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.llSocialMemberItem = (LinearLayout) view.findViewById(R.id.ll_social_member_item);
                viewHolder.ivSocialMemberHead = (ImageView) view.findViewById(R.id.iv_social_member_head);
                viewHolder.tvSocialMemberNickName = (TextView) view.findViewById(R.id.tv_social_member_nick_name);
                viewHolder.tvSocialMemberbasicInfo = (TextView) view.findViewById(R.id.tv_social_member_basic_info);
                viewHolder.tvSocialMemberPush = (TextView) view.findViewById(R.id.tv_social_member_push);
                viewHolder.tvSocialMemberDistance = (TextView) view.findViewById(R.id.tv_social_member_distance);
                viewHolder.tvSocialMemberItemFate = (TextView) view.findViewById(R.id.tv_social_member_item_fate);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final SearchVipBean.DataBean item = getItem(i);

            if (item.getNickname().length() == 11) {
                viewHolder.tvSocialMemberNickName.setText("1**********");
            } else {
                viewHolder.tvSocialMemberNickName.setText(item.getNickname());
            }
            /*if (item.prov != null) {
                StringRequest addressInfoRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVER_MEMBER_ADDRESS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        transServerData(response, ADDRESS_INFO);
                        if (memberAddress.code == 200) {

                            String basicInfo = item.age + "|" + memberAddress.address;
                            if (item.detail != null) {
                                if (item.detail.monthly_salary != null) {
                                    basicInfo = basicInfo + "|" + item.detail.monthly_salary;
                                }
                            }
//                            Log.d("activity_info",basicInfo);
                            viewHolder.tvSocialMemberbasicInfo.setText(basicInfo);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchResultsActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("prov", item.prov);
                        if (item.city != null) {
                            params.put("city", item.city);
                        }
                        return params;
                    }
                };
                WoAiSiJiApp.mRequestQueue.add(addressInfoRequest);
            } else {
                String basicInfo = item.age;
                if (item.detail != null) {
                    if (item.detail.monthly_salary != null) {
                        basicInfo = basicInfo + "|" + item.detail.monthly_salary;
                    }
                }
                viewHolder.tvSocialMemberbasicInfo.setText(basicInfo);
            }*/
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.getHeadpic(),
                    viewHolder.ivSocialMemberHead, options, null);
            /*viewHolder.llSocialMemberItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchResultActivity.this, PersonalInfoDetailActivity.class);
                    intent.putExtra("uid", item.uid);
                    startActivity(intent);
                }
            });*/
            viewHolder.tvSocialMemberItemFate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = item.getUid();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(SearchResultsActivity.this, ChatActivity.class)
                            .putExtra("userId", username));
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        public LinearLayout llSocialMemberItem;
        public ImageView ivSocialMemberHead;
        public TextView tvSocialMemberNickName;
        public TextView tvSocialMemberbasicInfo;
        public TextView tvSocialMemberPush;
        public TextView tvSocialMemberDistance;
        public TextView tvSocialMemberItemFate;
    }

}
