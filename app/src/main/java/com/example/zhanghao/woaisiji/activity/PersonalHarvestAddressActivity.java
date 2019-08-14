package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.zhanghao.woaisiji.bean.my.PersonalHarvestBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.resp.RespPersonalReceiveAddressList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonalHarvestAddressActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_personal_harvest_address_list;

    private List<PersonalHarvestBean> harvestAddressList;
    private Button btn_personal_harvest_address_add_address;
    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title, tv_personal_harvest_address_no_data;
    private boolean isClick = false;
    private PersonalHarvestAddressAdapter harvestAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_harvest_address);

        isClick = getIntent().getBooleanExtra("is_click", false);
        initView();
        initClickListener();
        // 请求服务器，获取收获地址
        obtainReceiveAddress();
    }

    private void initClickListener() {
        iv_title_bar_view_left_left.setOnClickListener(this);
        btn_personal_harvest_address_add_address.setOnClickListener(this);
    }

    private void initView() {
        // 标题栏
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);

        tv_personal_harvest_address_no_data = (TextView) findViewById(R.id.tv_personal_harvest_address_no_data);
        lv_personal_harvest_address_list = (ListView) findViewById(R.id.lv_personal_harvest_address_list);
        btn_personal_harvest_address_add_address = (Button) findViewById(R.id.btn_personal_harvest_address_add_address);

        harvestAddressList = new ArrayList<>();
        harvestAddressAdapter = new PersonalHarvestAddressAdapter();
        lv_personal_harvest_address_list.setAdapter(harvestAddressAdapter);

        if (isClick) {
            tv_title_bar_view_centre_title.setText("请选择收货地址");
        } else {
            tv_title_bar_view_centre_title.setText("管理收货地址");
        }
    }

    private void obtainReceiveAddress() {
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
                        tv_personal_harvest_address_no_data.setVisibility(View.GONE);
                        lv_personal_harvest_address_list.setVisibility(View.VISIBLE);
                        harvestAddressList.clear();
                        harvestAddressList.addAll(respPersonalReceiveAddressList.getList());
                        harvestAddressAdapter.notifyDataSetChanged();
                    } else {
                        tv_personal_harvest_address_no_data.setVisibility(View.VISIBLE);
                        lv_personal_harvest_address_list.setVisibility(View.GONE);
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

    private void deleteHarvestAddress(final String id, final int position) {
//        showProgressDialog();
        StringRequest deleteAddressRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_PERSONAL_RECEIVE_ADDRESS_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
                    harvestAddressList.remove(position);
                    harvestAddressAdapter.notifyDataSetChanged();
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
                params.put("id", id);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(deleteAddressRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.btn_personal_harvest_address_add_address:
                Intent intent = new Intent(PersonalHarvestAddressActivity.this, PersonalAddNewAddressActivity.class);
                startActivityForResult(intent, 706);
                break;
        }
    }


    class PersonalHarvestAddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return harvestAddressList.size();
        }

        @Override
        public PersonalHarvestBean getItem(int i) {
            return harvestAddressList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(PersonalHarvestAddressActivity.this, R.layout.item_personal_harvest_address_list, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_item_personal_harvest_address_name = (TextView) view.findViewById(R.id.tv_item_personal_harvest_address_name);
                viewHolder.tv_item_personal_harvest_address_phone = (TextView) view.findViewById(R.id.tv_item_personal_harvest_address_phone);
                viewHolder.tv_item_personal_harvest_address_detail_address = (TextView) view.findViewById(R.id.tv_item_personal_harvest_address_detail_address);
                viewHolder.tv_item_personal_harvest_address_edit_address = (TextView) view.findViewById(R.id.tv_item_personal_harvest_address_edit_address);
                viewHolder.tv_item_personal_harvest_address_delete_address = (TextView) view.findViewById(R.id.tv_item_personal_harvest_address_delete_address);
                viewHolder.ck_item_personal_harvest_address_selected_default = (CheckBox) view.findViewById(R.id.ck_item_personal_harvest_address_selected_default);
                viewHolder.ll_item_personal_harvest_address_root = (LinearLayout) view.findViewById(R.id.ll_item_personal_harvest_address_root);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final PersonalHarvestBean item = getItem(position);
            viewHolder.tv_item_personal_harvest_address_name.setText(item.getNew_nickname());
            viewHolder.tv_item_personal_harvest_address_phone.setText(item.getNew_mobile());
            viewHolder.tv_item_personal_harvest_address_detail_address.setText(item.getNew_place());
            //删除
            viewHolder.tv_item_personal_harvest_address_delete_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteHarvestAddress(item.getId(), position);
                }
            });
            //编辑
            viewHolder.tv_item_personal_harvest_address_edit_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PersonalHarvestAddressActivity.this, PersonalAddNewAddressActivity.class);
                    String name = viewHolder.tv_item_personal_harvest_address_name.getText().toString();
                    String phone = viewHolder.tv_item_personal_harvest_address_phone.getText().toString();
                    String address  = viewHolder.tv_item_personal_harvest_address_detail_address.getText().toString();
                    intent.putExtra("name",name);
                    intent.putExtra("phone",phone);
                    intent.putExtra("address ",address );
                    startActivityForResult(intent, 706);
                }
            });
            if (!TextUtils.isEmpty(item.getIs_default()) && "1".equals(item.getIs_default())) {
                viewHolder.ck_item_personal_harvest_address_selected_default.setChecked(true);
            } else {
                viewHolder.ck_item_personal_harvest_address_selected_default.setChecked(false);
            }
            viewHolder.ck_item_personal_harvest_address_selected_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        setDefaultAddress(item.getId());
                }
            });
            viewHolder.ll_item_personal_harvest_address_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isClick) {
                        Intent intent = new Intent();
                        intent.putExtra("PersonalHarvestBean", item);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            return view;
        }
    }

    /**
     * 设置默认收货地址
     */
    private void setDefaultAddress(final String addressId) {
//        showProgressDialog();
        StringRequest setDefaultRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_PERSONAL_RECEIVE_ADDRESS_SET_DEFAULT_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespNull respNull = gson.fromJson(response, RespNull.class);
                if (respNull.getCode() == 200) {
                    obtainReceiveAddress();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dismissProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("id", addressId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(setDefaultRequest);
    }

    public class ViewHolder {
        public LinearLayout ll_item_personal_harvest_address_root;
        public TextView tv_item_personal_harvest_address_name, tv_item_personal_harvest_address_phone,
                tv_item_personal_harvest_address_detail_address;
        public TextView tv_item_personal_harvest_address_delete_address, tv_item_personal_harvest_address_edit_address;
        public CheckBox ck_item_personal_harvest_address_selected_default;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 706) {
            obtainReceiveAddress();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
