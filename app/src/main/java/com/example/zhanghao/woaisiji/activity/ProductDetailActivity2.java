package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.example.zhanghao.woaisiji.bean.AddShoppingCartBean;
import com.example.zhanghao.woaisiji.bean.my.ShopsDetailBean;
import com.example.zhanghao.woaisiji.fragment.TuWenDetails;
import com.example.zhanghao.woaisiji.fragment.WenZiDetails2;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.example.zhanghao.woaisiji.view.DragLayout;
import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.zhanghao.woaisiji.WoAiSiJiApp.getContext;

public class ProductDetailActivity2 extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProductDetailActivity2";
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private TextView tv_product_detail_store, tv_product_detail_kefu, tv_product_detail_collection,
            tv_product_detail_add_shopping_car, tv_product_detail_check_shopping_car;
    private TuWenDetails tuWenDetails;
    private WenZiDetails2 wenZiDetails;
    private DragLayout draglayout;
    private String num = "1";
    private String productId;

    // 商品详情url
    private String url = ServerAddress.URL_DETAILS;
    // 商品添加收藏url
    private String collectionUrl = ServerAddress.URL_ZHENGPINCOLLECTION;
    // 商品添加购物车url
    private String cartUrl = ServerAddress.URL_ADDSHOPPINGCART;
    private int type;
    private WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail_layout);
        productId = getIntent().getStringExtra("id");
//        type = getIntent().getIntExtra("type", 0);
        Log.e("--------productId", "request: " + productId);
//        setUrl();
        initView();
        initData();
    }

    private void setUrl() {
        switch (type) {
            case 0:  // 正品商城
                url = ServerAddress.URL_DETAILS;
                collectionUrl = ServerAddress.URL_ZHENGPINCOLLECTION;
                cartUrl = ServerAddress.URL_ADDSHOPPINGCART;
                break;
            case 1: // 银币商城
                url = ServerAddress.URL_EXCHANGE_DETAILS;
                collectionUrl = ServerAddress.URL_DUIHUANCOLLECTION;
                cartUrl = ServerAddress.URL_DUIHUANADDSHOPPINGCART;
                break;
            case 3:  // 养护连锁
                url = ServerAddress.URL_CURING_DETAILS;
                collectionUrl = ServerAddress.URL_YANGHUCOLLECTION;
                cartUrl = ServerAddress.URL_YANGHUSHOPPINGCART;
                break;
        }
    }

    private void initData() {
        NetManager.getNetManager().getMyService(Myserver.url)
                .getShopsDetailBean(Integer.valueOf(productId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopsDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ShopsDetailBean value) {
                        Log.d(TAG, value.getData() + "");
//                        getSupportFragmentManager().beginTransaction().add(R.id.wenzi_details, wenZiDetails)
//                                .add(R.id.tuwen_details, tuWenDetails).commit();
                        if (value.getData() == null) {
                            Toast.makeText(ProductDetailActivity2.this, "商品已下架", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        WebSettings settings = mWeb.getSettings();
                        settings.setJavaScriptEnabled(true);
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        settings.setDefaultFixedFontSize(15);
                        mWeb.loadData(getHtmlData(value.getData().getContent()), "text/html; charset=UTF-8", null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("----------onError", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*StringRequest stringRequest=new StringRequest(Request.Method.GET, ServerAddress.URL_FBH_COMMODITY_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespFBHCommodityDetails detailsbean = gson.fromJson(response, RespFBHCommodityDetails.class);
                if (detailsbean.getCode() == 200) {
                    wenZiDetails = new WenZiDetails2(ProductDetailActivity2.this,detailsbean.getData());
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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailActivity2.this,"获取服务器数据失败",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new ArrayMap<>();
                map.put("id", productId);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);*/
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private void initView() {
        //后退
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("福百惠商城");
        //店铺
        tv_product_detail_store = (TextView) findViewById(R.id.tv_product_detail_store);
        //客服
        tv_product_detail_kefu = (TextView) findViewById(R.id.tv_product_detail_kefu);
        //收藏
        tv_product_detail_collection = (TextView) findViewById(R.id.tv_product_detail_collection);
        //加入购物车
        tv_product_detail_add_shopping_car = (TextView) findViewById(R.id.tv_product_detail_add_shopping_car);
        //查看购物车
        tv_product_detail_check_shopping_car = (TextView) findViewById(R.id.tv_product_detail_check_shopping_car);
        mWeb = (WebView) findViewById(R.id.web);

        iv_title_bar_view_left_left.setOnClickListener(this);
        tv_product_detail_add_shopping_car.setOnClickListener(this);
        tv_product_detail_kefu.setOnClickListener(this);
        tv_product_detail_collection.setOnClickListener(this);
        tv_product_detail_check_shopping_car.setOnClickListener(this);
        tv_product_detail_store.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //后退点击事件
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            //客服点击事件
            case R.id.tv_product_detail_kefu:
                break;
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
                    addShoppingRequest();
                }
                break;
            //查看购物车
            case R.id.tv_product_detail_check_shopping_car:
                if (TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
                    startActivity(new Intent(ProductDetailActivity2.this, LoginActivity.class));
                } else {
                    Intent intent = new Intent(ProductDetailActivity2.this, ShoppingCart.class);
                    if (type == 2) {
                        intent.putExtra("Getinto", type);
                    } else {
                        intent.putExtra("Getinto", 1);
                    }
                    startActivity(intent);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_COLLECTION_PRODUCTION, new Response.Listener<String>() {
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_COMMODITY_ADD_SHOPPING_CAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AddShoppingCartBean addShoppingCartBean = gson.fromJson(response, AddShoppingCartBean.class);
                if (addShoppingCartBean == null) {
                    Toast.makeText(getContext(), "服务器维护", Toast.LENGTH_SHORT).show();
                }
                if (addShoppingCartBean.getCode() == 200) {
                    Toast.makeText(ProductDetailActivity2.this, addShoppingCartBean.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (addShoppingCartBean.getCode() == 400) {
                    Toast.makeText(ProductDetailActivity2.this, addShoppingCartBean.getMsg(), Toast.LENGTH_SHORT).show();
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

