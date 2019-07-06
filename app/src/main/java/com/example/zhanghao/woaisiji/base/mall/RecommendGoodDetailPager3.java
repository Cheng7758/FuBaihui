package com.example.zhanghao.woaisiji.base.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.example.zhanghao.woaisiji.activity.ProductDetailActivity2;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.bean.RecommendBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.view.MyGridView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/29.
 */
public class RecommendGoodDetailPager3 extends BasePagerDetail implements PullToRefreshLayout.OnRefreshListener{
    private int type = 1;
    public final static String ID = "id";
    private MyGridView gridView;
    List<RecommendBean.ListBean> mRecommendLists;
    private int pager = 1;
    private PullToRefreshLayout refreshView;
    private RecommendAdapter recommendAdapter;
    private boolean isFirst = true;
    private boolean isCache = false;
    private String url = ServerAddress.URL_RECOMMEND;
    private RecommendBean recommend;

    public RecommendGoodDetailPager3(Activity activity, int type) {
        super(activity);
        this.type = type;
        /**
         *  type=1  正品商城
         *  type=2  银币商城
         *  type=3  养护连锁
         */
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.shoppingmall_gridview, null);
        gridView = (MyGridView) view.findViewById(R.id.gv_view);
        refreshView = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        refreshView.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void initData() {
        String recommendCache = "";
        switch (type){
            case 1:
                url = ServerAddress.URL_RECOMMEND;
                recommendCache = PrefUtils.getString(mActivity,"recommendCache01","");
                break;
            case 2:
                url = ServerAddress.URL_EXCHANGE_RECOMMEND;
                recommendCache = PrefUtils.getString(mActivity,"recommendCache02","");
                break;
            case 3:
                url = ServerAddress.URL_CURING_RECOMMEND;
                recommendCache = PrefUtils.getString(mActivity,"recommendCache03","");
                break;
        }
        recommendAdapter = new RecommendAdapter();
        mRecommendLists = new ArrayList<>();

        /*if (!TextUtils.isEmpty(recommendCache)){
            transServerData(recommendCache);
            for (int i = 0; i < recommend.getList().size(); i++) {
                RecommendBean.ListBean listBean = recommend.getList().get(i);
                mRecommendLists.add(listBean);//这是分类？
            }
            gridView.setAdapter(recommendAdapter);
            isFirst = false;
            isCache = true;
        }*/
        getDataFromServer();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kucun = mRecommendLists.get(position).getNumber();
                if (kucun.equals("0")) {
                    Toast.makeText(mActivity, "没有库存喽哟！ 亲～", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mActivity, ProductDetailActivity2.class);
                    String Id = mRecommendLists.get(position).getId();
                    intent.putExtra(ID, Id);
                    intent.putExtra("type", type);
                    mActivity.startActivity(intent);

                }
            }
        });
    }

    private void getDataFromServer() {

        final StringRequest recommendRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //请求网络数据成功

            @Override
            public void onResponse(String response) {


                transServerData(response);
                if (recommend.getCode() == 200) {
                    if (recommend.getList() != null){
                        for (int i = 0; i < recommend.getList().size(); i++) {
                            RecommendBean.ListBean listBean = recommend.getList().get(i);
                            mRecommendLists.add(listBean);//这是分类？
                        }

                        // 数据缓存
                        if (!isCache){
                            switch (type){
                                case 1:
                                    PrefUtils.setString(mActivity,"recommendCache01",response);
                                    break;
                                case 2:
                                    PrefUtils.setString(mActivity,"recommendCache02",response);
                                    break;
                                case 3:
                                    PrefUtils.setString(mActivity,"recommendCache03",response);
                                    break;
                            }
                            isCache = true;
                        }


                        if (isFirst){
                            gridView.setAdapter(recommendAdapter);
                            isFirst = false;
                        }else {
                            recommendAdapter.notifyDataSetChanged();
                        }


                    }else {
                        Toast.makeText(mActivity, "没有更多数据！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            //请求网络数据失败
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, " 推荐页未获取到服务器数据", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p", String.valueOf(pager));
                return params;
            }
        };
        //添加网络请求队列
        WoAiSiJiApp.mRequestQueue.add(recommendRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        recommend = gson.fromJson(response, RecommendBean.class);
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
        }.sendEmptyMessageDelayed(0, 2000);
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
        }.sendEmptyMessageDelayed(0, 2000);
    }


    public class RecommendAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mRecommendLists.size();
        }

        @Override
        public RecommendBean.ListBean getItem(int i) {
            return mRecommendLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHoder viewHolder = null;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.recommend_itme, null);
                viewHolder = new ViewHoder();
//                AbViewUtil.scaleContentView((ViewGroup) view);
                viewHolder.introduce = (TextView) view.findViewById(R.id.introduce);
                viewHolder.payment = (TextView) view.findViewById(R.id.payment);
                viewHolder.price = (TextView) view.findViewById(R.id.price);
                viewHolder.iv_shangpintu = (ImageView) view.findViewById(R.id.iv_shangpintu);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHoder) view.getTag();
            }
            RecommendBean.ListBean item = getItem(i);
            viewHolder.introduce.setText(item.getTitle());
            viewHolder.payment.setText(item.getPeople() + "人付款");
            viewHolder.price.setText("￥" + item.getPrice());
            if (item.cover != null){
                ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.cover.get(0), viewHolder.iv_shangpintu);
            }else {
//                Toast.makeText(mActivity,"错错错",Toast.LENGTH_SHORT).show();
            }
//            Glide.with(context).load("http://www.woaisiji.com"+imageUrl.path).into(ivHeadPic);

            return view;
        }
    }

    static class ViewHoder {
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
