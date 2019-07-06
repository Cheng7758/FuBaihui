package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.WuliuAdapter;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.WuliuBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
import com.example.zhanghao.woaisiji.view.SiJiWenDaListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/19.
 */
public class PersonalOrderFormDetailActivity extends Activity implements View.OnClickListener {


    private ListView lvDetailOrderForm;
    private String[] goodTitle;
    private String[] goodPrice;
    private String[] goodNum;
    private String[] goodCel;
    private String[] goodId;
    private String[] orderId;
    //    private String orderId;
    private String orderNum;
    private String time;
    private String nickName;
    private String mobile;
    private String place;
    private TextView tvOrderNum;
    private TextView tvOrderTime;
    private TextView tvOrderNickName;
    private TextView tvOrderMobile;
    private TextView tvOrderPlace;
    private String[] goodPic;
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int type;

    private SiJiWenDaListView listView;
    public List<WuliuBean.InfoBean.TracesBean> questionLists;
    private WuliuBean wuliuBean;
    private WuliuAdapter adapter;
    private boolean isFirst = true;
    private TextView tv_danhao;
    private TextView tv_gongsi,tv_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_order_form_detail);
        getData();

//        Log.d("ddddd", goodTitle[0]);
        initView();

        // 响应点击事件
        responseClickListener();

        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        listView = (SiJiWenDaListView) findViewById(R.id.lv_wuliu);
        initListView();
    }

    //listView的适配
    private void initListView() {

        questionLists = new ArrayList<>();
        adapter = new WuliuAdapter(PersonalOrderFormDetailActivity.this);
        // 获取服务器数据
        returnWuliu();
        tv_danhao = (TextView) findViewById(R.id.tv_danhao);
        tv_gongsi = (TextView) findViewById(R.id.tv_gongsi);

    }

    private void getData() {
/**
 *  intent.putExtra("ordernum",item.ordernum);
 intent.putExtra("time",item.time);
 intent.putExtra("nickname",item.nickname);
 intent.putExtra("mobile",item.mobile);
 intent.putExtra("place",item.place);
 */

        /**
         * 请求接口http://www.woaisiji.com/APP/Order/order_detail
         * 获取数据
         */

        type = getIntent().getFlags();
        /*orderId = getIntent().getStringExtra("order_id");*/
        orderNum = getIntent().getStringExtra("ordernum");
        time = getIntent().getStringExtra("time");
        nickName = getIntent().getStringExtra("nickname");
        mobile = getIntent().getStringExtra("mobile");
        place = getIntent().getStringExtra("place");

        orderId = getIntent().getStringArrayExtra("orderId");
//        Toast.makeText(PersonalOrderFormDetailActivity.this,orderId[0],Toast.LENGTH_SHORT).show();
        goodId = getIntent().getStringArrayExtra("good_id");
        goodTitle = getIntent().getStringArrayExtra("good_title");
        goodPrice = getIntent().getStringArrayExtra("good_price");
        goodNum = getIntent().getStringArrayExtra("good_num");
        goodCel = getIntent().getStringArrayExtra("good_cel");
        goodPic = getIntent().getStringArrayExtra("good_pic");

    }


    private void initView() {

        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);


        tvOrderNum = (TextView) findViewById(R.id.tv_order_num);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        tvOrderNickName = (TextView) findViewById(R.id.tv_order_nickname);
        tvOrderMobile = (TextView) findViewById(R.id.tv_order_mobile);
        tvOrderPlace = (TextView) findViewById(R.id.tv_order_place);
        tv_Title= (TextView) findViewById(R.id.tv_Title);

        lvDetailOrderForm = (ListView) findViewById(R.id.lv_detail_order_form);

    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
    }


    public void initData() {
//
        tvRegisterTitle.setText("订单详情");
        tvOrderNum.setText("订单编号:" + orderNum);
        tvOrderTime.setText("创建时间:" + TimeUtils.times(time));
        tvOrderNickName.setText("收件人:" + nickName);
        tvOrderMobile.setText(mobile);
        tvOrderPlace.setText("收货地址:" + place);

        lvDetailOrderForm.setAdapter(new OrderDetailAdapter());
        if (type == 1) {
            tv_Title.setText("司机商城");
        } else if (type == 2) {
            tv_Title.setText("分红商城");

        } else {
            tv_Title.setText("积分商城");

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
        }
    }

    private void returnWuliu() {
        StringRequest returnWuliuRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_WULIU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                wuliuBean = gson.fromJson(response, WuliuBean.class);
                if (wuliuBean.getCode() == 200) {
                    tv_danhao.setText(wuliuBean.getInfo().getLogisticCode());
                    tv_gongsi.setText(wuliuBean.getInfo().getShipperCode());
                    if (wuliuBean.getInfo() != null) {
                        for (WuliuBean.InfoBean.TracesBean bean : wuliuBean.getInfo().getTraces()) {
                            questionLists.add(bean);
                        }
//                        adapter.questionLists.clear();
                        adapter.carLists = questionLists;
                        if (isFirst) {
                            listView.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }

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
                params.put("ordernum", orderNum);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(returnWuliuRequest);
    }

    private void returnGood(final int i) {
        StringRequest returnGoodRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ORDER_FORM_RETURN_GOOD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AlterResultBean alterResult = gson.fromJson(response, AlterResultBean.class);
                Toast.makeText(PersonalOrderFormDetailActivity.this, alterResult.msg, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//
                params.put("id", orderId[i]);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(returnGoodRequest);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PersonalOrderFormDetail Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public class OrderDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodTitle.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(PersonalOrderFormDetailActivity.this, R.layout.item_order_form_detail, null);
                viewHolder = new ViewHolder();
                viewHolder.tvOrderDetailTitle = (TextView) view.findViewById(R.id.tv_order_detail_title);
                viewHolder.tvOrderDetailPrice = (TextView) view.findViewById(R.id.tv_order_detail_price);
                viewHolder.tvOrderDetailNum = (TextView) view.findViewById(R.id.tv_order_detail_num);
                viewHolder.tvOrderDetailGoodCel = (TextView) view.findViewById(R.id.tv_order_detail_goodcel);
                viewHolder.ivOrderDetailPicture = (ImageView) view.findViewById(R.id.iv_order_detail_picture);

                viewHolder.btnOrderFormAppraise = (Button) view.findViewById(R.id.btn_order_form_appraise);
                viewHolder.btnOrderFormApplyRefund = (Button) view.findViewById(R.id.btn_order_form_apply_refund);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tvOrderDetailTitle.setText(goodTitle[i]);
            viewHolder.tvOrderDetailPrice.setText("￥" + goodPrice[i]);
            viewHolder.tvOrderDetailNum.setText("×" + goodNum[i]);
            if ("0".equals(goodCel[i])) {
//                viewHolder.tvOrderDetailGoodCel.setText("核销码:未核销");
                viewHolder.tvOrderDetailGoodCel.setText("");
            } else {
                viewHolder.tvOrderDetailGoodCel.setText("核销码:" + goodCel[i]);
            }

            if (goodCel[i] == null) {
                viewHolder.tvOrderDetailGoodCel.setVisibility(View.GONE);
            }

            // 评价
            viewHolder.btnOrderFormAppraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonalOrderFormDetailActivity.this, AppraiseOrderActivity.class);
//                    intent.putExtra("uid", WoAiSiJiApp.uid);
                    intent.addFlags(type);
                    intent.putExtra("good_id", goodId[i]);
                    intent.putExtra("good_pic", goodPic[i]);
                    intent.putExtra("good_title", goodTitle[i]);
                    startActivity(intent);
                }
            });

            // 申请退货
            viewHolder.btnOrderFormApplyRefund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(PersonalOrderFormDetailActivity.this,orderId[i],Toast.LENGTH_SHORT).show();
                    final int pos = i;
                    //
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PersonalOrderFormDetailActivity.this);
                    builder.setMessage("确定退货吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            returnGood(pos);
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();

                }
            });

            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + goodPic[i], viewHolder.ivOrderDetailPicture);
            return view;
        }
    }


    static class ViewHolder {
        public TextView tvOrderDetailTitle;
        public TextView tvOrderDetailPrice;
        public TextView tvOrderDetailNum;
        public TextView tvOrderDetailGoodCel;
        public ImageView ivOrderDetailPicture;

        public Button btnOrderFormAppraise;
        public Button btnOrderFormApplyRefund;
    }


}
