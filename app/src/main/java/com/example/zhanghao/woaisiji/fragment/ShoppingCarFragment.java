package com.example.zhanghao.woaisiji.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespShoppingCarList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ShoppingCarFragment extends BaseFragment implements ShoppingCarAdapter.CheckInterface,
        ShoppingCarAdapter.ModifyCountInterface {

    private Button btn_view_page_shopping_car_zhengping_mall,btn_view_page_shopping_car_sliver_mall,btn_view_page_shopping_car_gold_mall;
    private TextView tv_view_page_shopping_car_editor;
    private ExpandableListView expandList_view_page_shopping_car_list_data;
    private CheckBox ck_view_page_shopping_car_all_choose;
    private TextView tv_view_page_shopping_car_count_money,tv_view_page_shopping_car_settle;
    private LinearLayout ll_view_page_shopping_car_settle_root,layout_shopping_cart_empty;;
    private TextView tv_view_page_shopping_car_delete;


    private boolean isFirstPageShow = true;
    private List<RespShoppingCarList.ShoppingCarStoreInfo> mData;
    private int currentFlag = 0;//0-正品   3-金积分    4-银积分
    private String deleteGoodUrl ; // 删除正品商城购物车
    private ShoppingCarAdapter shoppingCarAdapter;

    private int currentState = 0;
    private int totalCount = 0;// 购买的商品总数量
    private double totalPrice = 0.00;// 购买的商品总价


    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_page_shopping_car, container,false);
        init(view);
        return view;
    }
    private void init(View rootView) {
        mData = new ArrayList<>();
        checkShoppingCardList();

        initView(rootView);
        switch (currentFlag){
            case 0: // 正品商城
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART; // 删除正品商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 4: // 银积分商城
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;  // 删除银币商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 3://金积分商城
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }

    }

    private void initView(View rootView) {
        //正品商城购物车
        btn_view_page_shopping_car_zhengping_mall = (Button) rootView.findViewById(R.id.btn_view_page_shopping_car_zhengping_mall);
        //兑换商城购物车
        btn_view_page_shopping_car_sliver_mall = (Button) rootView.findViewById(R.id.btn_view_page_shopping_car_sliver_mall);
        btn_view_page_shopping_car_gold_mall = (Button) rootView.findViewById(R.id.btn_view_page_shopping_car_gold_mall);

        ck_view_page_shopping_car_all_choose = (CheckBox) rootView.findViewById(R.id.ck_view_page_shopping_car_all_choose);
        tv_view_page_shopping_car_count_money = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_count_money);
        tv_view_page_shopping_car_settle = (TextView) rootView.findViewById(R.id.tv_view_page_shopping_car_settle);

        expandList_view_page_shopping_car_list_data = (ExpandableListView) rootView.findViewById(R.id.expandList_view_page_shopping_car_list_data);
        layout_shopping_cart_empty = (LinearLayout) rootView.findViewById(R.id.layout_shopping_cart_empty);
        shoppingCarAdapter = new ShoppingCarAdapter(mData , getActivity());
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
        ck_view_page_shopping_car_all_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheckAll();
            }
        });
        //结算按钮
        tv_view_page_shopping_car_settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RespShoppingCarList.ShoppingCarGoodsInfo> selectStoreInfo = new ArrayList<>();
                selectStoreInfo = statisticsChoosedProduct();
                if (selectStoreInfo.size()==0)
                    Toast.makeText(getActivity(),"请选择商品", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getActivity(), OrderPreviewActivity.class);//跳转到订单详情
//                    intent.putExtra("id", goodsId);
//                    intent.putExtra("num", goodsNum);
                    intent.putExtra("type", currentFlag);
                    startActivity(intent);
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

        btn_view_page_shopping_car_zhengping_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = true;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 0;
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART;
                mData.clear();
                checkShoppingCardList();
            }
        });

        btn_view_page_shopping_car_sliver_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 4;
                mData.clear();
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;
                checkShoppingCardList();
            }
        });

        btn_view_page_shopping_car_gold_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                btn_view_page_shopping_car_zhengping_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_zhengping_mall.setTextColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn_view_page_shopping_car_gold_mall.setTextColor(Color.parseColor("#646464"));
                btn_view_page_shopping_car_sliver_mall.setBackgroundColor(Color.parseColor("#00000000"));
                btn_view_page_shopping_car_sliver_mall.setTextColor(Color.parseColor("#FFFFFF"));
                currentFlag = 3;
                mData.clear();
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                checkShoppingCardList();
            }
        });
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
            RespShoppingCarList.ShoppingCarStoreInfo group = mData.get(i);
            for (int j = 0; j < group.getGoods().size(); j++) {
                group.getGoods().get(j).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
            }
        }
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        List<RespShoppingCarList.ShoppingCarStoreInfo> toBeDeleteGroups = new ArrayList<>();// 待删除的组元素列表
        for (int i = 0; i < mData.size(); i++) {
            RespShoppingCarList.ShoppingCarStoreInfo group = mData.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<RespShoppingCarList.ShoppingCarGoodsInfo> toBeDeleteProducts = new ArrayList<>();// 待删除的子元素列表
            List<RespShoppingCarList.ShoppingCarGoodsInfo> childs = mData.get(i).getGoods();
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
        if (mData.size()>0) {
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setChoosed(ck_view_page_shopping_car_all_choose.isChecked());
                RespShoppingCarList.ShoppingCarStoreInfo group = mData.get(i);
                List<RespShoppingCarList.ShoppingCarGoodsInfo> childs = group.getGoods();
                for (RespShoppingCarList.ShoppingCarGoodsInfo goodsInfo : childs) {
                    count += 1;
                }
            }
        }
        //购物车已清空
        if(count==0){
            expandList_view_page_shopping_car_list_data.setVisibility(View.GONE);
            layout_shopping_cart_empty.setVisibility(View.VISIBLE);
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
    private List<RespShoppingCarList.ShoppingCarGoodsInfo> statisticsChoosedProduct(){
        List<RespShoppingCarList.ShoppingCarGoodsInfo> selectStoreInfo = new ArrayList<>();
        //将集合转成数组，传给订单详情页面
        for (int i=0;i<mData.size();i++){
            RespShoppingCarList.ShoppingCarStoreInfo storeInfo = mData.get(i);
            for (int j=0; j<storeInfo.getGoods().size();j++){
                if (storeInfo.getGoods().get(j).isChoosed()) {
                    totalCount++;
                    totalPrice += storeInfo.getGoods().get(j).getPay_price() * storeInfo.getGoods().get(j).getNum();
                    selectStoreInfo.add(storeInfo.getGoods().get(j));
                }
            }
        }
        return selectStoreInfo;
    }


    private void checkShoppingCardList() {
//        for (int i = 0; i < 3; i++) {
//            RespShoppingCarList.ShoppingCarStoreInfo goodInfo= new RespShoppingCarList.ShoppingCarStoreInfo();
//            goodInfo.setStore_name( "天猫店铺" + (i + 1) + "号店");
//            goodInfo.setStore_id( i + "");
//
//            List<RespShoppingCarList.ShoppingCarGoodsInfo> products = new ArrayList<RespShoppingCarList.ShoppingCarGoodsInfo>();
//            for (int j = 0; j <= i; j++) {
////                int[] img = {R.drawable.goods1, R.drawable.goods2, R.drawable.goods3, R.drawable.goods4, R.drawable.goods5, R.drawable.goods6};
//                products.add(new RespShoppingCarList.ShoppingCarGoodsInfo(
//                        "计把",
//                        "11",
//                        "22" ,
//                        222,
//                        2,
//                        "100",
//                        "1","1000"));
//            }
//            goodInfo.setGoods(products);
//            mData.add(goodInfo);
//        }

        StringRequest checkShoppingCardList = new StringRequest(Request.Method.POST, ServerAddress.URL_COMMODITY_SHOPPING_CAR_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response))
                            return;
                        Gson gson = new Gson();
                        RespShoppingCarList respShoppingCarList = gson.fromJson(response,RespShoppingCarList.class);
                        if(respShoppingCarList.getCode() == 200) {
                            mData.clear();
                            mData.addAll(respShoppingCarList.getData());//对象的集合
                            setCartNum();
                            shoppingCarAdapter.notifyDataSetChanged();

                            for (int i = 0; i < mData.size(); i++) {
                                expandList_view_page_shopping_car_list_data.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("购物车请求失败", "" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("uid", WoAiSiJiApp.getUid());
                map.put("type", String.valueOf(currentFlag));
                map.put("token", WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(checkShoppingCardList);
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        RespShoppingCarList.ShoppingCarStoreInfo group = mData.get(groupPosition);
        List<RespShoppingCarList.ShoppingCarGoodsInfo> childs = group.getGoods();
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            ck_view_page_shopping_car_all_choose.setChecked(true);
        else
            ck_view_page_shopping_car_all_choose.setChecked(false);
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        List<RespShoppingCarList.ShoppingCarGoodsInfo> childs = mData.get(groupPosition).getGoods();
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
        if (isAllCheck()) {
            ck_view_page_shopping_car_all_choose.setChecked(true);// 全选
        } else {
            ck_view_page_shopping_car_all_choose.setChecked(false);// 反选
        }
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }
    private boolean isAllCheck() {
        for (RespShoppingCarList.ShoppingCarStoreInfo group : mData) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        RespShoppingCarList.ShoppingCarGoodsInfo product = (RespShoppingCarList.ShoppingCarGoodsInfo)
                shoppingCarAdapter.getChild(groupPosition,childPosition);
        int currentCount = product.getNum();
        currentCount++;
        //改变数量
        changeGoodsAmountRequest(product.getG_id(),String.valueOf(currentCount));
        product.setNum(currentCount);
        ((EditText) showCountView).setText(currentCount + "");
        ((EditText) showCountView).setSelection(String.valueOf(currentCount).length());
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        RespShoppingCarList.ShoppingCarGoodsInfo product = (RespShoppingCarList.ShoppingCarGoodsInfo)
                shoppingCarAdapter.getChild(groupPosition,childPosition);
        int currentCount = product.getNum();
        if (currentCount == 1)
            return;
        currentCount--;
        //改变数量请求
        changeGoodsAmountRequest(product.getG_id(),String.valueOf(currentCount));
        product.setNum(currentCount);
        ((EditText) showCountView).setText(currentCount + "");
        ((EditText) showCountView).setSelection(String.valueOf(currentCount).length());
        shoppingCarAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 删除商品
     * @param id
     */
    public void deleteGoodsRequest(final String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ServerAddress.URL_SHOPPING_CAR_DELETE_COMMODITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("id",id);
                map.put("uid",WoAiSiJiApp.getUid());
                map.put("token",WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    /**
     * 改变数量
     */
    public void changeGoodsAmountRequest( final String goods_id, final String number){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ServerAddress.URL_SHOPPING_CAR_CHANGE_NUMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("uid",WoAiSiJiApp.getUid());
                map.put("goods_id",goods_id);
                map.put("number",number);
                map.put("token",WoAiSiJiApp.token);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }
}