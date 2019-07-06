package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.CollectionBean;
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
 * Created by admin on 2016/9/18.
 */
public class PersonalCollectionActivity extends Activity implements PullToRefreshLayout.OnRefreshListener,View.OnClickListener {
    private static final int TYPE_COLLECTION = 1;
    private static final int TYPE_ADD_CAR = 2;
    private static final int TYPE_DRIVER_MALL = 1;
    private static final int TYPE_CONVERT_MALL = 2;
    private int type = 1;
    private boolean isFirst = true;
    private static int pager = 0;
    private CollectionBean collectionResult;
    private PullToRefreshLayout refreshView;
    private BaseListView lvPersonalCollectionList;
    private List<CollectionBean.CollectionList> collectionList;
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
//    private Button btnRegisterMore;
    private AlterResultBean resultBean;
    private TextView tvPersonalCollectionAllNum;
    private Button btnOrderFormDriveMall;
    private Button btnOrderFormConvertMall;
    private LinearLayout llPersonalOrderForm;
    private CollectionListAdapter collectionListAdapter;
    private String className;
    private String url = ServerAddress.URL_COLLECTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_collection);
        className = getIntent().getStringExtra("class_name");
        //
        initView();
        if ("1".equals(className)){
            url = ServerAddress.URL_COLLECTION;
            tvRegisterTitle.setText("我的收藏");
        }else {
            url = ServerAddress.URL_BROWS_HISTORY;
            tvRegisterTitle.setText("浏览历史");
        }
        refreshView.setOnRefreshListener(this);
        collectionList = new ArrayList<>();
        collectionListAdapter = new CollectionListAdapter();
        getDataFromServer(type);
    }


    private void initView() {
        //
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ivRegisterBack.setOnClickListener(this);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);

        tvPersonalCollectionAllNum = (TextView) findViewById(R.id.tv_personal_collection_all_num);
//        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
//        btnRegisterMore.setVisibility(View.GONE);
//        btnRegisterMore.setBackgroundResource(R.drawable.iv_more);

        llPersonalOrderForm = (LinearLayout) findViewById(R.id.ll_personal_order_form);
        btnOrderFormDriveMall = (Button) findViewById(R.id.btn_order_form_drive_mall);
        btnOrderFormDriveMall.setOnClickListener(this);
        btnOrderFormConvertMall = (Button) findViewById(R.id.btn_order_form_convert_mall);
        btnOrderFormConvertMall.setOnClickListener(this);


        refreshView = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lvPersonalCollectionList = (BaseListView) findViewById(R.id.lv_personal_collection_list);
    }

    private void getDataFromServer(final int type) {

        StringRequest collectionRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("activity_collection", response);
                transServerData(response,TYPE_COLLECTION);
                if (collectionResult.code == 200) {
                    if (collectionResult.list != null){
//                        Log.d("activity_collection", collectionResult.list.get(0).toString());

                        for (int i=0;i<collectionResult.list.size();i++){
                            if (collectionResult.list.get(i).data != null){
                                collectionList.add(collectionResult.list.get(i));
                            }

                        }
                        String number = "全部宝贝("+collectionList.size()+")";
                        tvPersonalCollectionAllNum.setText(number);
                    }

                    if (isFirst){
                        isFirst = false;
                        lvPersonalCollectionList.setAdapter(collectionListAdapter);
                    }else {
                        collectionListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", String.valueOf(type));
                params.put("p", String.valueOf(pager));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(collectionRequest);
    }

    private void addGoodsToCar(final String gId, final String userId, final String goodNum) {
        StringRequest addCarRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ADD_GOOD_TO_CAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response,TYPE_ADD_CAR);
                Toast.makeText(PersonalCollectionActivity.this,resultBean.msg,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("g_id",gId);
                params.put("user_id",userId);
                params.put("num",goodNum);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addCarRequest);
    }

    private void transServerData(String response,int type) {
        Gson gson = new Gson();
        switch (type){
            case TYPE_COLLECTION:
                collectionResult = gson.fromJson(response, CollectionBean.class);
                break;
            case TYPE_ADD_CAR:
                resultBean = gson.fromJson(response,AlterResultBean.class);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.btn_order_form_drive_mall:
                if (type != TYPE_DRIVER_MALL){
                    type = TYPE_DRIVER_MALL;
                    pager = 0;
                    collectionList.clear();
                    llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_default);
                    getDataFromServer(type);
                }
                break;
            case R.id.btn_order_form_convert_mall:
                if (type != TYPE_CONVERT_MALL){
                    type = TYPE_CONVERT_MALL;
                    pager = 0;
                    collectionList.clear();
                    llPersonalOrderForm.setBackgroundResource(R.drawable.btn_mall_bg_selected);
                    getDataFromServer(type);
                }
                break;
        }
    }

    class CollectionListAdapter extends BaseAdapter{
        private DisplayImageOptions options;
        public CollectionListAdapter(){
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return collectionList.size();
        }

        @Override
        public CollectionBean.CollectionList getItem(int i) {
            return collectionList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null){
                view = View.inflate(PersonalCollectionActivity.this,R.layout.collection_list_item,null);
                viewHolder = new ViewHolder();
                viewHolder.ivCollection = (ImageView) view.findViewById(R.id.iv_collection);
                viewHolder.tvCollectionTitle = (TextView) view.findViewById(R.id.tv_collection_title);
                viewHolder.tvCollectionSilver = (TextView) view.findViewById(R.id.tv_collection_silver);
                viewHolder.tvCollectionPrice = (TextView) view.findViewById(R.id.tv_collection_price);
                viewHolder.btnCollectionAddCart = (Button) view.findViewById(R.id.btn_collection_add_cart);

                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final CollectionBean.CollectionList item = getItem(i);
            if (item.data != null){
                viewHolder.tvCollectionTitle.setText(item.data.get(0).title);
                viewHolder.tvCollectionSilver.setText("送"+item.data.get(0).f_silver+"银A币");
                viewHolder.tvCollectionPrice.setText("￥ "+item.data.get(0).price);
                if (item.data.get(0).cover!=null){
                    ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+item.data.get(0).cover.get(0),viewHolder.ivCollection,options,null);
                }
            }
            viewHolder.btnCollectionAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
//                    if (type == 1) {
                        intent = new Intent(PersonalCollectionActivity.this, ProductDetailActivity2.class);
//                    } else {
//                        intent = new Intent(PersonalCollectionActivity.this, ExchangeProductdetail.class);
//                    }
                    intent.putExtra("id", item.data.get(0).id);
                    intent.putExtra("type", type);
                    startActivity(intent);
//                    addGoodsToCar(item.g_id,item.user_id,item.data.get(0).number);
//                    Toast.makeText(PersonalCollectionActivity.this,"成功加入购物车",Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }



    static class ViewHolder{
        public TextView tvCollectionTitle;
        public TextView tvCollectionSilver;
        public TextView tvCollectionPrice;
        public Button btnCollectionAddCart;
        public ImageView ivCollection;
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
                pager++;
                getDataFromServer(type);
                // 千万别忘了告诉控件加载完毕了哦！
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
}
