package com.example.zhanghao.woaisiji.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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
import com.example.zhanghao.woaisiji.activity.OrderPreviewActivity;
import com.example.zhanghao.woaisiji.adapter.ShoppingCarAdapter;
import com.example.zhanghao.woaisiji.bean.my.PersonalCouponBean;
import com.example.zhanghao.woaisiji.bean.shoppingcar.ShoppingCarGoodsInfo;
import com.example.zhanghao.woaisiji.bean.shoppingcar.ShoppingCarStoreInfo;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespShoppingCarList;
import com.google.gson.Gson;
import com.example.network.utils.MGson;
import com.example.network.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCarFragment extends BaseFragment implements ShoppingCarAdapter.CheckInterface,
        ShoppingCarAdapter.ModifyCountInterface {

    private Button btn_view_page_shopping_car_zhengping_mall, btn_view_page_shopping_car_sliver_mall,
            btn_view_page_shopping_joina_the_business;
    //btn_view_page_shopping_car_gold_mall;
    private TextView tv_view_page_shopping_car_editor;
    private ExpandableListView expandList_view_page_shopping_car_list_data;
    //    private CheckBox ck_view_page_shopping_car_all_choose;
    private TextView tv_view_page_shopping_car_count_money, tv_view_page_shopping_car_settle;
    private LinearLayout ll_view_page_shopping_car_settle_root, layout_shopping_cart_empty;
    ;
    private TextView tv_view_page_shopping_car_delete;
    private int SHOPPING_CAR_FBHSC = 0;
    private int SHOPPING_CAR_YINJIFEN = 4;
    private int SHOPPING_CAR_JIAMEN = 5;
    private boolean isFirstPageShow = true;
    private List<ShoppingCarStoreInfo> mData;
    private int currentFlag = 0;//0-正品   3-金积分    4-银积分
    private String deleteGoodUrl; // 删除正品商城购物车
    private ShoppingCarAdapter shoppingCarAdapter;

    private int currentState = 0;
    private int totalCount = 0;// 购买的商品总数量
    private double totalPrice = 0.00;// 购买的商品总价

    private boolean isSilver;
    private int cartList_num = 0;
    private int selectIndex = -1;

    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_page_shopping_car, container, false);
        init(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState = outState == null ? new Bundle() : outState;
        outState.putBoolean("isSilver", isSilver);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null)
            return;
        isSilver = savedInstanceState.getBoolean("isSilver", false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 111 && data != null)
            return;
        if (shoppingCarAdapter == null)
            return;
        String store_data = data.getStringExtra("store_data");
        store_data = StringUtils.defaultStr(store_data, "{}");
        String store_id = data.getStringExtra("IntentSliverDetailCommodityID");
        boolean isSilver = data.getBooleanExtra("isSilver", false);
        shoppingCarAdapter.setSilver(isSilver);
        for (ShoppingCarStoreInfo group : shoppingCarAdapter.groups) {
            String store_id1 = group.getStore_id();
            if (com.blankj.utilcode.util.StringUtils.equals(store_id, store_id1))
                group.couponBean = MGson.from(store_data, PersonalCouponBean.class);
        }
        shoppingCarAdapter.notifyDataSetChanged();
    }

    private void init(View rootView) {
        mData = new ArrayList<>();
        checkShoppingCardList();
        Log.i("qlb", "gcurrentFlag-->" + currentFlag);
        initView(rootView);
        switch (WoAiSiJiApp.APPLICATION_SHOP_TYPE) {
            case 0:
                currentFlag = 0;
                cartList_num = 0;
                break;
            case 4:
                currentFlag = 4;
                cartList_num = 4;
                break;
            case 5:
                currentFlag = 3;
                cartList_num = 5;
                break;
        }
        switch (currentFlag) {
            case 0: // 正品商城
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART; // 删除正品商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 4: // 银积分商城
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;  // 删除银币商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 3://金积分商城
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }

    private void initView(View rootView) {
        //福百惠商城购物车
        btn_view_page_shopping_car_zhengping_mall = (Button) rootView.findViewById(R.id.btn_view_page_shopping_car_zhengping_mall);
        //银积分商城购物车
        btn_view_page_shopping_car_sliver_mall = (Button) rootView.findViewById(R.id.btn_view_page_shopping_car_sliver_mall);
        //加盟商家购物车
        btn_view_page_shopping_joina_the_business = (Button) rootView.findViewById(R.id.btn_view_page_shopping_joina_the_business);

//        ck_view_page_shopping_car_all_choose = (CheckBox) rootView.findViewById(R.id.ck_view_page_shopping_car_all_choose);
        tv_view_page_shopping_car_count_money = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_count_money);
        tv_view_page_shopping_car_settle = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_settle);

        expandList_view_page_shopping_car_list_data = (ExpandableListView) rootView.findViewById(R.id.expandList_view_page_shopping_car_list_data);
        layout_shopping_cart_empty = (LinearLayout) rootView.findViewById(R.id.layout_shopping_cart_empty);
        shoppingCarAdapter = new ShoppingCarAdapter(mData, getActivity(), isSilver);
        for (int i = 0; i < mData.size(); i++) {
            Log.i("qlb", "shoppingCarAdapter-getStore_id->" + mData.get(i).getStore_id());
            Log.i("qlb", "shoppingCarAdapter-getStore_name->" + mData.get(i).getStore_name());
            Log.i("qlb", "shoppingCarAdapter-getGoods->" + mData.get(i).getGoods());
        }
        Log.i("qlb", "shoppingisSilver-->" + isSilver);
        shoppingCarAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
        shoppingCarAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        expandList_view_page_shopping_car_list_data.setAdapter(shoppingCarAdapter);//listview的setadapter

        tv_view_page_shopping_car_editor = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_editor);//右上角的编辑和完成
        ll_view_page_shopping_car_settle_root = (LinearLayout) rootView.findViewById(R.id.ll_view_page_shopping_car_settle_root);//
        tv_view_page_shopping_car_delete = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_delete);//

        initListener();
    }

    private void initListener() {
        //编辑
        tv_view_page_shopping_car_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentState == 0) {
                    ll_view_page_shopping_car_settle_root.setVisibility(View.GONE);
                    tv_view_page_shopping_car_delete.setVisibility(View.VISIBLE);
                    tv_view_page_shopping_car_editor.setText("完成");
                } else if (currentState == 1) {
                    ll_view_page_shopping_car_settle_root.setVisibility(View.VISIBLE);
                    tv_view_page_shopping_car_delete.setVisibility(View.GONE);
                    tv_view_page_shopping_car_editor.setText("编辑");
                }
                currentState = (currentState + 1) % 2;//其余得到循环执行上面2个不同的功能
            }
        });
       /* ck_view_page_shopping_car_all_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheckAll();
            }
        });*/
        //结算按钮
        tv_view_page_shopping_car_settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ShoppingCarGoodsInfo> selectStoreInfo = new ArrayList<>();
                selectStoreInfo = statisticsChoosedProduct();
                if (selectStoreInfo.size() == 0)
                    if (mData == null || mData.size() == 0) {
                        Toast.makeText(getActivity(), "购物车暂无商品", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "请选择商品", Toast.LENGTH_SHORT).show();
                    }
                else {
                    Intent intent = new Intent(getActivity(), OrderPreviewActivity.class);//跳转到订单详情
                    String[] cardId = new String[selectStoreInfo.size()];
                    for (int i = 0; i < selectStoreInfo.size(); i++) {
                        cardId[i] = selectStoreInfo.get(i).getId();
                    }
                    ArrayList<String> strings = new ArrayList<>();
                    for (ShoppingCarStoreInfo group : shoppingCarAdapter.groups) {
                        if (group.couponBean == null)
                            continue;
                        String id = group.couponBean.getId();
                        strings.add(id);
                    }
                    intent.putExtra("cardIdList", cardId);
                    intent.putExtra("coupon_id", MGson.toJson(strings));
                    intent.putExtra("type", currentFlag);
                    intent.putExtra("isSilver", isSilver);
                    startActivity(intent);
                    //重新刷新数据
                    checkShoppingCardList();
                }
            }
        });
        tv_view_page_shopping_car_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalCount == 0) {
                    Toast.makeText(getActivity(), "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                alert.show();
            }
        });
        //福百惠商城
        btn_view_page_shopping_car_zhengping_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = true;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 0;
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART;
                mData.clear();
                isSilver = false;
                cartList_num = SHOPPING_CAR_FBHSC;
                checkShoppingCardList();
            }
        });
        //银积分商城
        btn_view_page_shopping_car_sliver_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 4;
                mData.clear();
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;
                isSilver = true;
                cartList_num = SHOPPING_CAR_YINJIFEN;
                checkShoppingCardList();
            }
        });
        //加盟商家
        btn_view_page_shopping_joina_the_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_joina_the_business.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 3;
                cartList_num = SHOPPING_CAR_JIAMEN;
                mData.clear();
//                isSilver = true;
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                checkShoppingCardList();
            }
        });
    }

    /**
     * 全选与反选
     */
    /*private void doCheckAll() {
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
            ShoppingCarStoreInfo group = mData.get(i);
            for (int j = 0; j < group.getGoods().size(); j++) {
                group.getGoods().get(j).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
            }
        }
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }*/

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        List<ShoppingCarStoreInfo> toBeDeleteGroups = new ArrayList<>();// 待删除的组元素列表
        for (int i = 0; i < mData.size(); i++) {
            ShoppingCarStoreInfo group = mData.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<ShoppingCarGoodsInfo> toBeDeleteProducts = new ArrayList<>();// 待删除的子元素列表
            List<ShoppingCarGoodsInfo> childs = mData.get(i).getGoods();
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    //网络请求
                    deleteGoodsRequest(childs.get(j).getId());
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);
        }
        mData.removeAll(toBeDeleteGroups);
        //记得重新设置购物车
        setCartNum();
        shoppingCarAdapter.notifyDataSetChanged();
    }

    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        if (mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
//                mData.get(i).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
                ShoppingCarStoreInfo group = mData.get(i);
                List<ShoppingCarGoodsInfo> childs = group.getGoods();
                for (ShoppingCarGoodsInfo goodsInfo : childs) {
                    count += 1;
                }
            }
        }
        //购物车已清空
        if (count == 0) {
            expandList_view_page_shopping_car_list_data.setVisibility(View.GONE);
            layout_shopping_cart_empty.setVisibility(View.VISIBLE);
        } else {
            expandList_view_page_shopping_car_list_data.setVisibility(View.VISIBLE);
            layout_shopping_cart_empty.setVisibility(View.GONE);
        }
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        statisticsChoosedProduct();
        tv_view_page_shopping_car_count_money.setText("￥" + totalPrice);
    }

    /**
     * 统计选上的产品
     */
    private List<ShoppingCarGoodsInfo> statisticsChoosedProduct() {
        List<ShoppingCarGoodsInfo> selectStoreInfo = new ArrayList<>();
        //将集合转成数组，传给订单详情页面
        for (int i = 0; i < mData.size(); i++) {
            ShoppingCarStoreInfo storeInfo = mData.get(i);
            for (int j = 0; j < storeInfo.getGoods().size(); j++) {
                if (storeInfo.getGoods().get(j).isChoosed()) {
                    totalCount++;
                    totalPrice += storeInfo.getGoods().get(j).getPay_price() * storeInfo.getGoods().get(j).getNum();
                    selectStoreInfo.add(storeInfo.getGoods().get(j));
                }
            }
        }
        return selectStoreInfo;
    }

    @Override
    public void onResume() {
        super.onResume();
        calculate();
        checkShoppingCardList();
    }

    private void checkShoppingCardList() {
        StringRequest checkShoppingCardList = new StringRequest(Request.Method.POST, ServerAddress.URL_COMMODITY_SHOPPING_CAR_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response)) return;
                        Gson gson = new Gson();
                        RespShoppingCarList respShoppingCarList = gson.fromJson(response, RespShoppingCarList.class);
                        if (respShoppingCarList.getCode() == 200) {
                            calculate();
                            mData.clear();
                            mData.addAll(respShoppingCarList.getData());//对象的集合
                            setCartNum();

                            for (int i = 0; i < mData.size(); i++) {
                                expandList_view_page_shopping_car_list_data.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                            }
                            shoppingCarAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("购物车请求失败", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("uid", WoAiSiJiApp.getUid());
                map.put("type", cartList_num + "");
                map.put("token", WoAiSiJiApp.token);
                Log.i("qlb", "getParamsisSilver-->" + (isSilver ? 4 : 3) + "");
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(checkShoppingCardList);
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        ShoppingCarStoreInfo group = mData.get(groupPosition);
        List<ShoppingCarGoodsInfo> childs = group.getGoods();
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        /*if (isAllCheck())
            ck_view_page_shopping_car_all_choose.setChecked(true);
        else
            ck_view_page_shopping_car_all_choose.setChecked(false);*/
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        List<ShoppingCarGoodsInfo> childs = mData.get(groupPosition).getGoods();
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            mData.get(groupPosition).setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            mData.get(groupPosition).setChoosed(false);// 否则，组元素一律设置为未选中状态
        }
        /*if (isAllCheck()) {
            ck_view_page_shopping_car_all_choose.setChecked(true);// 全选
        } else {
            ck_view_page_shopping_car_all_choose.setChecked(false);// 反选
        }*/
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck() {
        for (ShoppingCarStoreInfo group : mData) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ShoppingCarGoodsInfo product = (ShoppingCarGoodsInfo)
                shoppingCarAdapter.getChild(groupPosition, childPosition);
        int currentCount = product.getNum();
        currentCount++;
        //改变数量
        changeGoodsAmountRequest(product.getG_id(), String.valueOf(currentCount));
        product.setNum(currentCount);
//        ((EditText) showCountView).setText(currentCount + "");
//        ((EditText) showCountView).setSelection(String.valueOf(currentCount).length());
        ((EditText) showCountView).setSelection(String.valueOf(((TextView) showCountView).getText()).length());
        shoppingCarAdapter.notifyDataSetChanged();

        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ShoppingCarGoodsInfo product = (ShoppingCarGoodsInfo)
                shoppingCarAdapter.getChild(groupPosition, childPosition);
        int currentCount = product.getNum();
        if (currentCount == 1)
            return;
        currentCount--;
        //改变数量请求
        changeGoodsAmountRequest(product.getG_id(), String.valueOf(currentCount));
        product.setNum(currentCount);
//        ((EditText) showCountView).setText(currentCount + "");
        ((EditText) showCountView).setSelection(String.valueOf(((TextView) showCountView).getText()).length());
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 删除商品
     *
     * @param id
     */
    public void deleteGoodsRequest(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_SHOPPING_CAR_DELETE_COMMODITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id", id);
                map.put("uid", WoAiSiJiApp.getUid());
                map.put("token", WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /**
     * 改变数量
     */
    public void changeGoodsAmountRequest(final String goods_id, final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_SHOPPING_CAR_CHANGE_NUMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(ShoppingCarFragment.this.getActivity(),""+respShoppingCarList.getMsg(),Toast.LENGTH_LONG).show();;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("uid", WoAiSiJiApp.getUid());
                map.put("goods_id", goods_id);
                map.put("number", number);
                map.put("token", WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }
}