package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.OrderPreviewAdapter;
import com.example.zhanghao.woaisiji.bean.my.PersonalHarvestBean;
import com.example.zhanghao.woaisiji.bean.order.OrderBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespAddOrder;
import com.example.zhanghao.woaisiji.resp.RespAddOrderSuccess;
import com.example.zhanghao.woaisiji.resp.RespPersonalReceiveAddressList;
import com.google.gson.Gson;
import com.example.network.utils.MGson;
import com.jcodecraeer.xrecyclerview.gold.UserManager;
import com.example.network.utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 */
public class OrderPreviewActivity extends BaseActivity {

    private Intent intent;
    private PersonalHarvestBean addressBean = null;
    private String[] cardIdList;
    private List<String> couponList = new ArrayList<>();
    private int currentType = 0;//0-正品   3-金积分    4-银积分

    /**
     * TitleBar
     */
    private RelativeLayout rl_title_bar_view_root;
    private TextView tv_title_bar_view_centre_title;
    private ImageView iv_title_bar_view_left_left;

    private LinearLayout ll_order_preview_consignee_info;
    private TextView tv_order_preview_consignee_name, tv_order_preview_consignee_phone, tv_order_preview_consignee_address;
    private ExpandableListView expandList_order_preview_consignee_bug_good;
    private TextView tv_order_preview_good_total, tv_order_preview_consignee_submit;

    private OrderPreviewAdapter orderPreviewAdapter;
    private List<OrderBean> orderBeanList;
    private int mPrice;
    private boolean isSilver;
    ArrayList<String> couponIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);
        intent = getIntent();
        cardIdList = intent.getStringArrayExtra("cardIdList");
        String coupon_id = intent.getStringExtra("coupon_id");
        try {
            couponIdList = MGson.from(coupon_id, new ArrayList<String>().getClass());
        }catch (Exception e){

        }
        currentType = intent.getIntExtra("type", 0);
        isSilver = intent.getBooleanExtra("isSilver",false);
//        Log.i("qlb","Order - cardIdList->"+cardIdList);
//        Log.i("qlb","Order - coupon_id->"+coupon_id);
//        Log.i("qlb","Order - couponIdList->"+couponIdList);
//        Log.i("qlb","Order - currentType->"+currentType);
//        Log.i("qlb","Order - isSilver->"+isSilver);
        intView();
        initListener();
        getOrderDataFromServer();
        //设置默认地址
        defaultAddress();
    }

    private void intView() {
        rl_title_bar_view_root = (RelativeLayout) findViewById(R.id.rl_title_bar_view_root);
        rl_title_bar_view_root.setBackgroundColor(ContextCompat.getColor(OrderPreviewActivity.this, R.color.white));
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setImageResource(R.drawable.back);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setTextColor(ContextCompat.getColor(OrderPreviewActivity.this, R.color.black));
        tv_title_bar_view_centre_title.setText("确认订单");


        ll_order_preview_consignee_info = (LinearLayout) findViewById(R.id.ll_order_preview_consignee_info);
        tv_order_preview_consignee_name = (TextView) findViewById(R.id.tv_order_preview_consignee_name);
        tv_order_preview_consignee_phone = (TextView) findViewById(R.id.tv_order_preview_consignee_phone);
        tv_order_preview_consignee_address = (TextView) findViewById(R.id.tv_order_preview_consignee_address);
        expandList_order_preview_consignee_bug_good = (ExpandableListView) findViewById(R.id.expandList_order_preview_consignee_bug_good);
        tv_order_preview_good_total = (TextView) findViewById(R.id.tv_order_preview_good_total);
        tv_order_preview_consignee_submit = (TextView) findViewById(R.id.tv_order_preview_consignee_submit);

        orderBeanList = new ArrayList<>();
        orderPreviewAdapter = new OrderPreviewAdapter(orderBeanList, OrderPreviewActivity.this,isSilver);
        expandList_order_preview_consignee_bug_good.setAdapter(orderPreviewAdapter);
    }

    /**
     * 获取商品数据
     */
    private void getOrderDataFromServer() {
        showProgressDialog();
        RequestParams entity = new RequestParams(ServerAddress.URL_ORDER_PREVIEW_ACQUIRE_ODER_LIST_DATA);
        entity.addBodyParameter("uid", WoAiSiJiApp.getUid());
        entity.addBodyParameter("pay_type",currentType+ "");
        entity.addBodyParameter("token", WoAiSiJiApp.token);
        for (String i : cardIdList) {
            entity.addBodyParameter("cart_id[]", i);//1
        }
        for (String i : couponList) {
            entity.addBodyParameter("coupon", i);//1
        }
//        Log.i("qlb-response-list_data>","商品列表参数："+new Gson().toJson(entity));
        //现在是2个了
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(result))
                    return;
                Gson gson = new Gson();
                RespAddOrder respAddOrder = gson.fromJson(result, RespAddOrder.class);
                if (respAddOrder.getCode() == 200) {
                    if (respAddOrder.getData().size() > 0) {
                        int total = 0;
                        for (int i = 0; i < respAddOrder.getData().size(); i++) {
//                            Log.i("qlb-response-list_data>","商品列表返回："+respAddOrder.getData().get(i).getStore_total_price());
//                            Log.i("qlb-response-list_data>","商品列表返回："+respAddOrder.getData().get(i).getStore_name());
                            if (!TextUtils.isEmpty(respAddOrder.getData().get(i).getStore_total_price()))
                                total += Integer.valueOf(respAddOrder.getData().get(i).getStore_total_price());
                        }
                        tv_order_preview_good_total.setText("￥" + total);
                        mPrice = total;
                        orderBeanList.clear();
                        orderBeanList.addAll(respAddOrder.getData());
                        orderPreviewAdapter.notifyDataSetChanged();
                        for (int i = 0; i < orderPreviewAdapter.getGroupCount(); i++) {
                            expandList_order_preview_consignee_bug_good.expandGroup(i);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissProgressDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissProgressDialog();
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }
    private void defaultAddress() {
        StringRequest obtainGoodsAddressRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_PERSONAL_RECEIVE_ADDRESS_LIST_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespPersonalReceiveAddressList respPersonalReceiveAddressList =
                        gson.fromJson(response, RespPersonalReceiveAddressList.class);
                if (respPersonalReceiveAddressList.getCode() == 200) {
                    if (respPersonalReceiveAddressList.getList() != null && respPersonalReceiveAddressList.getList().size() > 0) {

                        for (PersonalHarvestBean personalHarvestBean : respPersonalReceiveAddressList.getList()  ){

                            if (!TextUtils.isEmpty(personalHarvestBean.getIs_default()) && "1".equals(personalHarvestBean.getIs_default())) {
                                addressBean = personalHarvestBean;
                                tv_order_preview_consignee_name.setText("收件人：" + personalHarvestBean.getNew_nickname());
                                tv_order_preview_consignee_phone.setText(personalHarvestBean.getNew_mobile());
                                tv_order_preview_consignee_address.setText("收货地址：" + personalHarvestBean.getNew_place());

                                return;
                            }


                        }
//                        tv_personal_harvest_address_no_data.setVisibility(View.GONE);
//                        lv_personal_harvest_address_list.setVisibility(View.VISIBLE);
//                        harvestAddressList.clear();
//                        harvestAddressList.addAll(respPersonalReceiveAddressList.getList());
//                        harvestAddressAdapter.notifyDataSetChanged();
//                    } else {
//                        tv_personal_harvest_address_no_data.setVisibility(View.VISIBLE);
//                        lv_personal_harvest_address_list.setVisibility(View.GONE);
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
                params.put("uid", WoAiSiJiApp.getUid());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(obtainGoodsAddressRequest);
    }
    private void initListener() {
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {//后退按钮
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //换地址
        ll_order_preview_consignee_info.setOnClickListener(new View.OnClickListener() {//收货人和收货地址，跳转到管理收货地址
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(OrderPreviewActivity.this,
                        PersonalHarvestAddressActivity.class).putExtra("is_click", true), 705);
            }
        });
        //提交订单
        tv_order_preview_consignee_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressBean == null) {
                    Toast.makeText(OrderPreviewActivity.this, "请选择收货地址!", Toast.LENGTH_SHORT).show();
                } else {
                    couponList.clear();
                    for (int i = 0; i < orderBeanList.size(); i++) {
                        if (orderBeanList.get(i).getCoupon_list() != null && orderBeanList.get(i).getCoupon_list().size() > 0) {
                            for (int j = 0; j < orderBeanList.get(i).getCoupon_list().size(); j++) {
                                couponList.add(orderBeanList.get(i).getCoupon_list().get(j).getId());
                            }
                        } else
                            continue;
                    }
                    submitOrder();
                }
            }
        });
    }

    /**
     * 提交订单
     */
    private strictfp void submitOrder() {
        double totalPrice = 0.0;
        for (OrderBean orderBean : orderBeanList) {
            String store_total_price = orderBean.getStore_total_price();
            totalPrice += Double.valueOf(StringUtils.defaultStr(store_total_price,"0.00"));
        }
        double count = Double.parseDouble(StringUtils.defaultStr(isSilver ? UserManager.silver : UserManager.gold,"0.0"));
        if (totalPrice > count){
            String s = isSilver ? "银积分" : "金积分";
            ToastUtils.showShort("余额不足，请充值" + s);
//            return;
        }

        StringRequest oderListDataUrl = new StringRequest(Request.Method.POST,
                ServerAddress.URL_ORDER_PREVIEW_SUBMIT_ODER_LIST_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                Log.d("requestParams", "onSuccess: " + response);
                RespAddOrderSuccess respAddOrderSuccess = gson.fromJson(response, RespAddOrderSuccess.class);
                Log.i("qlb-response-list_data>","参数："+response.toString());
                if (respAddOrderSuccess.getCode() == 200) {
                    Intent intent = new Intent(OrderPreviewActivity.this, PaymentMainActivity.class);
                    intent.putExtra("price",String.valueOf(mPrice));
                    intent.putExtra("orderID",respAddOrderSuccess.getId().getId());
                    intent.putExtra("orderNumber",respAddOrderSuccess.getId().getOrdernum());
                    intent.putExtra("merge","1");
                    startActivity(intent);
                    finish();
                    return;
                }
                if (TextUtils.isEmpty(respAddOrderSuccess.getMsg()))
                    Toast.makeText(OrderPreviewActivity.this, "订单生成失败!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(OrderPreviewActivity.this, respAddOrderSuccess.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());//
//                params.put("pay_type", isSilver ? "4" : "0");//1
                params.put("pay_type", currentType + "");//1
                params.put("beizhu", "");//1

                params.put("token", WoAiSiJiApp.token);//1
                params.put("plcid", addressBean.getId());//1
                String ins = "";
                for (String i : cardIdList) {
                    ins+=i+",";
                }
                params.put("cart_id[]", ins);//1
                String ins2 = "";
                for (String i : couponIdList) {
                    ins2+=i+",";
                }
                params.put("coupon", ins2);//1
//                Log.i("qlb-response-list_data>","返回："+params);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(oderListDataUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 705) {
            if (data != null) {
                addressBean = (PersonalHarvestBean) data.getSerializableExtra("PersonalHarvestBean");
                if (addressBean != null) {
                    tv_order_preview_consignee_name.setText("收件人：" + addressBean.getNew_nickname());
                    tv_order_preview_consignee_phone.setText(addressBean.getNew_mobile());
                    tv_order_preview_consignee_address.setText("收货地址：" + addressBean.getNew_place());
                }
            }
        }
    }
}
