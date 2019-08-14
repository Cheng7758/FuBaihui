package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.example.zhanghao.woaisiji.activity.login.LoginActivity;
import com.example.zhanghao.woaisiji.activity.main.MainActivity;
import com.example.zhanghao.woaisiji.bean.AddShoppingCartBean;
import com.example.zhanghao.woaisiji.bean.DetailsBean;
import com.example.zhanghao.woaisiji.fragment.TuWenDetails;
import com.example.zhanghao.woaisiji.fragment.WenZiDetails2;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespBusinessDetails;
import com.example.zhanghao.woaisiji.resp.RespFBHCommodityDetails;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.view.DragLayout;
import com.google.gson.Gson;

import java.util.Map;

import static com.example.zhanghao.woaisiji.WoAiSiJiApp.getContext;

public class ProductDetailActivity2 extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private TextView /*tv_product_detail_store, tv_product_detail_kefu,*/ tv_product_detail_collection,
            tv_product_detail_add_shopping_car, tv_product_detail_check_shopping_car;
    private TuWenDetails tuWenDetails;
    private WenZiDetails2 wenZiDetails;
    private DragLayout draglayout;
    private String num = "1";
    private String productId;

    private int type;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail_layout);
        type = getIntent().getIntExtra("type", 1);  //获取的参数没有传递过来的时候，返回defaultValue这个值
        mTitle = getIntent().getStringExtra("title");

        initView();
        initData();
        //服务器请求
        request();
    }

    private void request() {
        productId = getIntent().getStringExtra("id");
        Log.e("------detail", productId + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerAddress.URL_FBH_COMMODITY_DETAIL
                + productId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespFBHCommodityDetails detailsbean = gson.fromJson(response, RespFBHCommodityDetails.class);
                if (detailsbean.getCode() == 200) {
                    wenZiDetails = new WenZiDetails2(ProductDetailActivity2.this, detailsbean.getData(), type);
                    wenZiDetails.setSendDataActivity(new WenZiDetails2.SendDataActivityListener() {
                        @Override
                        public void sendData(String data) {
                            num = data;
                        }
                    });
                    tuWenDetails = new TuWenDetails(detailsbean.getData());
                    getSupportFragmentManager().beginTransaction().add(R.id.wenzi_details, wenZiDetails)
                            .add(R.id.tuwen_details, tuWenDetails).commit();
                    DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
                        @Override
                        public void onDragNext() {
                            tuWenDetails.initView();
                        }
                    };
                    draglayout = (DragLayout) findViewById(R.id.draglayout);
                    draglayout.setNextPageListener(nextIntf);
                } else {
                    Toast.makeText(ProductDetailActivity2.this, "商品已下架", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailActivity2.this, "库存不足", Toast.LENGTH_SHORT).show();
            }
        });
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void initView() {
        //后退
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        if (!TextUtils.isEmpty(mTitle)) {
            tv_title_bar_view_centre_title.setText(mTitle);
        }else {
            tv_title_bar_view_centre_title.setText("福百惠商城");
        }
        /*//店铺
        tv_product_detail_store = (TextView) findViewById(R.id.tv_product_detail_store);
        //客服
        tv_product_detail_kefu = (TextView) findViewById(R.id.tv_product_detail_kefu);*/
        //收藏
        tv_product_detail_collection = (TextView) findViewById(R.id.tv_product_detail_collection);
        //加入购物车
        tv_product_detail_add_shopping_car = (TextView) findViewById(R.id.tv_product_detail_add_shopping_car);
        //查看购物车
        tv_product_detail_check_shopping_car = (TextView) findViewById(R.id.tv_product_detail_check_shopping_car);
    }

    private void initData() {
        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_product_detail_add_shopping_car.setOnClickListener(this);
        /*tv_product_detail_kefu.setOnClickListener(this);
        tv_product_detail_store.setOnClickListener(this);*/
        tv_product_detail_collection.setOnClickListener(this);
        tv_product_detail_check_shopping_car.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //后退点击事件
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            //客服点击事件
           /* case R.id.tv_product_detail_kefu:
                break;*/
            //收藏点击事件
            case R.id.tv_product_detail_collection:
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    startActivity(new Intent(ProductDetailActivity2.this, LoginActivity.class));
                } else
                    collectionProductRequest();
                break;
            //加入购物车点击事件
            case R.id.tv_product_detail_add_shopping_car:
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    startActivity(new Intent(ProductDetailActivity2.this, LoginActivity.class));
                } else {
                    showProgressDialog();   //加载
                    addShoppingRequest();   //加入购物车
                }
                break;
            //查看购物车
            case R.id.tv_product_detail_check_shopping_car:
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    startActivity(new Intent(ProductDetailActivity2.this, LoginActivity.class));
                } else {
                    Intent intent = new Intent(ProductDetailActivity2.this, MainActivity.class);
                    if (type == 1) {
                        intent.putExtra("GetintoType", String.valueOf(type));
                    } else {
                        intent.putExtra("GetintoType", String.valueOf("2"));
                    }
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 收藏产品
     */
    private void collectionProductRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_COLLECTION_PRODUCTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                Toast.makeText(ProductDetailActivity2.this, respNull.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("uid", (WoAiSiJiApp.getUid()));
                map.put("goods_id", productId);
                map.put("token", WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /**
     * 加入购物车
     */
    public void addShoppingRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_COMMODITY_ADD_SHOPPING_CAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        Gson gson = new Gson();
                        AddShoppingCartBean addShoppingCartBean = gson.fromJson(response, AddShoppingCartBean.class);
                        if (addShoppingCartBean == null) {
                            Toast.makeText(getContext(), "服务器维护", Toast.LENGTH_SHORT).show();
                        }
                        if (addShoppingCartBean.getCode() == 200) {
                            Toast.makeText(ProductDetailActivity2.this,
                                    addShoppingCartBean.getMsg(), Toast.LENGTH_SHORT).show();
                        } else if (addShoppingCartBean.getCode() == 400) {
                            Toast.makeText(ProductDetailActivity2.this,
                                    addShoppingCartBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new ArrayMap<>();
                map.put("uid", (WoAiSiJiApp.getUid()));
                map.put("goods_id", productId);
                map.put("number", num);
                map.put("token", WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

}

