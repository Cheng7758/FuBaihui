package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.GoodsAddressBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/18.
 */
public class PersonalHarvestAddressActivity extends Activity implements View.OnClickListener {
    private ListView lvHarvestAddressList;

    private List<GoodsAddressBean.ObtainGoods> harvestAddressList;
    private GoodsAddressBean goodsAddress;

    private Button btnAddHarvestAddress;

    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private Button btnRegisterMore;
    private String isClick = null;
    private HarvestAddressAdapter harvestAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_harvest_address);

        // 获取intent传递过来的参数，控制listview的item是否有点击事件
        // intent.putExtra("is_click","click");
        isClick = getIntent().getStringExtra("is_click");

        initView();
        initData();
        // 响应点击事件
        responseClickListener();

        initListData();

    }

    private void responseClickListener() {
        ivRegisterBack.setOnClickListener(this);
        btnRegisterMore.setOnClickListener(this);
        btnAddHarvestAddress.setOnClickListener(this);
    }

    private void initData() {
        tvRegisterTitle.setText("管理收货地址");
    }

    private void initView() {
        // 标题栏
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
        btnRegisterMore.setVisibility(View.GONE);

        lvHarvestAddressList = (ListView) findViewById(R.id.lv_harvest_address_list);
        btnAddHarvestAddress = (Button) findViewById(R.id.btn_add_harvest_address);
    }

    private void initListData() {
        harvestAddressList = new ArrayList<>();
        harvestAddressAdapter = new HarvestAddressAdapter();
        // 请求服务器，获取收获地址
        obtainGoodsAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtainGoodsAddress();
    }

    private void obtainGoodsAddress() {
        StringRequest obtainGoodsAddressRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_GOODS_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (goodsAddress.code == 200) {
                    harvestAddressList = goodsAddress.list;
                    lvHarvestAddressList.setAdapter(harvestAddressAdapter);
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
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(obtainGoodsAddressRequest);
    }

    private void deleteHarvestAddress(final String id, final int position) {
        StringRequest deleteAddressRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DELETE_HARVEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("aaa", response);
                harvestAddressList.remove(position);
                harvestAddressAdapter.notifyDataSetChanged();
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
                params.put("id", id);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(deleteAddressRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        goodsAddress = gson.fromJson(response, GoodsAddressBean.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.btn_add_harvest_address:
                startActivity(new Intent(PersonalHarvestAddressActivity.this, PersonalAddNewAddressActivity.class));
                break;
        }
    }


    class HarvestAddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return harvestAddressList.size();
        }

        @Override
        public GoodsAddressBean.ObtainGoods getItem(int i) {
            return harvestAddressList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(PersonalHarvestAddressActivity.this, R.layout.personal_harvest_address_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvHarvestAddressName = (TextView) view.findViewById(R.id.tv_harvest_address_name);
                viewHolder.tvHarvestAddressPhone = (TextView) view.findViewById(R.id.tv_harvest_address_phone);
                viewHolder.tvHarvestDetailAddress = (TextView) view.findViewById(R.id.tv_harvest_detail_address);
                viewHolder.btnDeleteAddress = (Button) view.findViewById(R.id.btn_delete_address);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final GoodsAddressBean.ObtainGoods item = getItem(position);
            viewHolder.tvHarvestAddressName.setText(item.new_nickname);
            viewHolder.tvHarvestAddressPhone.setText(item.new_mobile);
            viewHolder.tvHarvestDetailAddress.setText(item.new_place);
            view.findViewById(R.id.ll_harvest_click).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("click".equals(isClick)) {
                        Intent intent = new Intent(PersonalHarvestAddressActivity.this, OrderPreviewActivity.class);
                        intent.putExtra("nickname", item.new_nickname);
                        intent.putExtra("mobile", item.new_mobile);
                        intent.putExtra("place", item.new_place);
                        intent.putExtra("addressId", item.id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            viewHolder.btnDeleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteHarvestAddress(item.id, position);
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        public TextView tvHarvestAddressName, tvHarvestAddressPhone, tvHarvestDetailAddress;
        public Button btnDeleteAddress;
        public Button btnSelectedDefault, btnEditAddress;
    }

    public SelectAddressLitener listener;

    public void setSelectAddressLitener(SelectAddressLitener listener) {
        this.listener = listener;
    }

    public interface SelectAddressLitener {
        public void setAddress(String name, String phone, String address);
    }

}
