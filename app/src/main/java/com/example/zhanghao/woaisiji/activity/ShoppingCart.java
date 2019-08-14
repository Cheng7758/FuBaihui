package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.ProductPictureBean;
import com.example.zhanghao.woaisiji.bean.ShoppingcartBean;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.example.zhanghao.woaisiji.view.NoSlideViewPager;
import com.example.zhanghao.woaisiji.view.UIAlertView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ShoppingCart extends BaseActivity {
    private Button zhengping;
    private Button duihuan, jinbi;
    private LinearLayout llPersonalOrderForm;
    private NoSlideViewPager vp_gouwuche;
    private TextView tv_count_money;
    private boolean isFirstPageShow = true;
    private List<ShoppingcartBean.GidsBean> mData;
    private ImageView back;
    private int flags = 1;
    private String url;  // 查看正品商城的购物车
    private String deleteGoodUrl; // 删除正品商城购物车

    private ZhengPinAdapter adapter;
    private TextView bianji;
    private ListView listViewzhengpin;

    //订单总价
    private double orderCount = 0;
    private TextView tvCountMoney;
    private TextView settle;
    private TextView tvMallName;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);//结算

        Intent intent = getIntent();
        if (intent != null)
            flags = intent.getIntExtra("Getinto", 1);
        initView();

        switch (flags) {
            case 1: // 正品商城
                url = ServerAddress.URL_GOOD_CAR; // 查看正品商城的购物车
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART; // 删除正品商城购物车
                tvMallName.setText("正品商城");

                zhengping.setBackgroundColor(Color.parseColor("#FFFFFF"));
                zhengping.setTextColor(Color.parseColor("#646464"));
                duihuan.setBackgroundColor(Color.parseColor("#00000000"));
                duihuan.setTextColor(Color.parseColor("#FFFFFF"));
                jinbi.setBackgroundColor(Color.parseColor("#00000000"));
                jinbi.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 2: // 兑换商城
                url = ServerAddress.URL_DUIHUANGOOD_CAR;  // 查看银币商城的购物车
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;  // 删除银币商城购物车
                zhengping.setBackgroundColor(Color.parseColor("#00000000"));
                zhengping.setTextColor(Color.parseColor("#FFFFFF"));
                duihuan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                duihuan.setTextColor(Color.parseColor("#646464"));
                jinbi.setBackgroundColor(Color.parseColor("#00000000"));
                jinbi.setTextColor(Color.parseColor("#FFFFFF"));
                tvMallName.setText("银积分商城");
                break;
            case 3:
                url = ServerAddress.URL_GOLDGOOD_CAR;  // 查看银币商城的购物车
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                tvMallName.setText("金积分商城");
                zhengping.setBackgroundColor(Color.parseColor("#00000000"));
                zhengping.setTextColor(Color.parseColor("#FFFFFF"));
                jinbi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                jinbi.setTextColor(Color.parseColor("#646464"));
                duihuan.setBackgroundColor(Color.parseColor("#00000000"));
                duihuan.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
        mData = new ArrayList<>();

        obtainDataFromServer();
    }


    private void initView() {
        //正品商城购物车
        zhengping = (Button) findViewById(R.id.btn_zhengping);
        //兑换商城购物车
        duihuan = (Button) findViewById(R.id.btn_duihuan);
        jinbi = (Button) findViewById(R.id.btn_jinbi);

        llPersonalOrderForm = (LinearLayout) findViewById(R.id.ll_personal_order_form);
        tvMallName = (TextView) findViewById(R.id.tv_mall_name);

        listViewzhengpin = (ListView) findViewById(R.id.listView_zhengpin);
        settle = (TextView) findViewById(R.id.btnSettle);
        tvCountMoney = (TextView) findViewById(R.id.tv_count_money);//合计
        tvCountMoney.setText("合计：" + orderCount + "");
        LinearLayout llMallTypeName = (LinearLayout) findViewById(R.id.ll_mall_type_name);
        zhengping.setBackgroundColor(Color.parseColor("#FFFFFF"));
        bianji = (TextView) findViewById(R.id.tv_bianji);//右上角的编辑和完成
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.isBianJi()) {
                    bianji.setText("编辑");
                    adapter.setBianJi(false);
                    adapter.notifyDataSetChanged();
                } else {
                    bianji.setText("完成");
                    adapter.setBianJi(true);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //结算按钮
        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将集合转成数组，传给订单详情页面
                int[] goodsId = new int[mData.size()];
                int[] goodsNum = new int[mData.size()];
                for (int i = 0; i < mData.size(); i++) {
                    goodsId[i] = Integer.parseInt(mData.get(i).getId());
                    goodsNum[i] = Integer.parseInt(mData.get(i).getNum());
                }

                Intent intent = new Intent(ShoppingCart.this, OrderPreviewActivity.class);//跳转到订单详情
                intent.putExtra("id", goodsId);
                intent.putExtra("num", goodsNum);
                intent.putExtra("type", flags);
                startActivity(intent);
            }
        });


        back = (ImageView) findViewById(R.id.iv_register_back);

        duihuan.setTextColor(Color.parseColor("#FFFFFF"));
        zhengping.setTextColor(Color.parseColor("#DF8807"));
        initData();
    }

    private void obtainDataFromServer() {
        // 查看正品商城的购物车 // 删除正品商城购物车
        StringRequest shoppingCartRequerst = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        ShoppingcartBean shoppingcartBean = gson.fromJson(response, ShoppingcartBean.class);

                        if (shoppingcartBean == null) {
                            Log.d("购物车获取服务器废了", "" + response);
                        }
                        if (shoppingcartBean.getCode() == 200) {
                            Log.d("获取数据成功", "" + response);
                            mData = shoppingcartBean.getGids();//对象的集合

//                    Toast.makeText(ShoppingCart.this,  mData.get(0).getId(), Toast.LENGTH_SHORT).show();

                            adapter = new ZhengPinAdapter(mData);//适配器设置数据,mData是集合，里面装bean的对象
                            listViewzhengpin.setAdapter(adapter);//listview的setadapter

                            //对集合中的数据求和
                            orderCount = 0;
                            for (ShoppingcartBean.GidsBean bean : mData) {
                                orderCount += Integer.parseInt(bean.getNum()) * parseDouble(bean.getPrice());
                            }
                            tvCountMoney.setText("合计：" + orderCount + "");


                        } else if (shoppingcartBean.getCode() == 400) {
                            adapter = new ZhengPinAdapter(mData);
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
                map.put("user_id", (WoAiSiJiApp.getUid()));
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(shoppingCartRequerst);
    }

    private void initData() {

        zhengping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = true;
                tvMallName.setText("正品商城");
                zhengping.setBackgroundColor(Color.parseColor("#FFFFFF"));
                zhengping.setTextColor(Color.parseColor("#646464"));
                duihuan.setBackgroundColor(Color.parseColor("#00000000"));
                duihuan.setTextColor(Color.parseColor("#FFFFFF"));
                jinbi.setBackgroundColor(Color.parseColor("#00000000"));
                jinbi.setTextColor(Color.parseColor("#FFFFFF"));
                flags = 1;
                url = ServerAddress.URL_GOOD_CAR;
                deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART;
                mData.clear();
                obtainDataFromServer();
            }
        });

        duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                zhengping.setBackgroundColor(Color.parseColor("#00000000"));
                zhengping.setTextColor(Color.parseColor("#FFFFFF"));
                duihuan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                duihuan.setTextColor(Color.parseColor("#646464"));
                jinbi.setBackgroundColor(Color.parseColor("#00000000"));
                jinbi.setTextColor(Color.parseColor("#FFFFFF"));
                tvMallName.setText("银积分商城");
                flags = 2;
                mData.clear();
                url = ServerAddress.URL_DUIHUANGOOD_CAR;
                deleteGoodUrl = ServerAddress.URL_DELETEDUIHUANSHOPPINGCART;
                obtainDataFromServer();
            }
        });

        jinbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstPageShow = false;
                zhengping.setBackgroundColor(Color.parseColor("#00000000"));
                zhengping.setTextColor(Color.parseColor("#FFFFFF"));
                jinbi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                jinbi.setTextColor(Color.parseColor("#646464"));
                duihuan.setBackgroundColor(Color.parseColor("#00000000"));
                duihuan.setTextColor(Color.parseColor("#FFFFFF"));

                tvMallName.setText("金积分商城");
                flags = 3;
                mData.clear();
                url = ServerAddress.URL_GOLDGOOD_CAR;  // 查看银币商城的购物车
                deleteGoodUrl = ServerAddress.URL_DELETEGOLDSHOPPINGCART;  // 删除银币商城购物车
                obtainDataFromServer();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Activity activity : PublicActivityList.activityList) {
                    activity.finish();
                }
//                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isFirstPageShow = true;

        tvMallName.setText("司机商城");
        duihuan.setTextColor(Color.parseColor("#FFFFFF"));
        zhengping.setTextColor(Color.parseColor("#DF8807"));
        flags = 1;
        url = ServerAddress.URL_GOOD_CAR;
        deleteGoodUrl = ServerAddress.URL_DELETEZHENGPINGSHOPPINGCART;
        mData.clear();
//                orderCount = 0;
        obtainDataFromServer();
    }

    class ZhengPinAdapter extends BaseAdapter {
        DisplayImageOptions options;
        List<ShoppingcartBean.GidsBean> mData;//对象的集合
        Map<Integer, Integer> orderNum = new HashMap<>();//map集合装的什么

        private boolean isBianJi = false;

        public boolean isBianJi() {
            return isBianJi;
        }

        public void setBianJi(boolean bianJi) {
            isBianJi = bianJi;
        }

        public ZhengPinAdapter(List mData) {
            this.mData = mData;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.weixianshi)
                    .showImageForEmptyUri(R.drawable.weixianshi)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHoder hoder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShoppingCart.this).inflate(R.layout.item_shopping_cart, null);
                hoder = new ViewHoder();
                hoder.tv_Describe = (TextView) convertView.findViewById(R.id.tvdescribe);
                hoder.tv_Stock = (TextView) convertView.findViewById(R.id.tvStock);
                hoder.tv_PriceNew = (TextView) convertView.findViewById(R.id.tvPriceNew);
                hoder.tv_Price0ld = (TextView) convertView.findViewById(R.id.tvPriceOld);
                hoder.tv_Price0ld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字被划掉效果
                hoder.tv_yinbi = (TextView) convertView.findViewById(R.id.tv_yinbi);
                hoder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);//商品数量
                hoder.ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);//增加按钮
                hoder.tvNum2 = (EditText) convertView.findViewById(R.id.tvNum2);//商品数量（编辑状态）
                hoder.ivReduce = (ImageView) convertView.findViewById(R.id.ivReduce);//减少按钮
                hoder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
                hoder.iv_ImageView = (ImageView) convertView.findViewById(R.id.ivpicture);
                hoder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
                hoder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
                convertView.setTag(hoder);
            } else {
                hoder = (ViewHoder) convertView.getTag();
            }
            //商品名称和备注
            hoder.tv_Describe.setText(mData.get(position).getTitle());
            hoder.tv_Stock.setText("库存" + mData.get(position).getNumber());
            hoder.tv_PriceNew.setText("￥" + Double.valueOf(mData.get(position).getPrice()));
            hoder.tv_Price0ld.setText("￥" + Double.valueOf(mData.get(position).getPrice_sc()));
            hoder.tv_yinbi.setText("送银币" + mData.get(position).getF_silver());
            if (orderNum.get(position) == null) {
                hoder.tvNum.setText("ｘ" + mData.get(position).getNum());//商品数量
                hoder.tvNum2.setText("" + mData.get(position).getNum());//编辑状态下商品数量
            } else {
                hoder.tvNum.setText("ｘ" + orderNum.get(position));
                hoder.tvNum2.setText("" + orderNum.get(position));
            }

            //图片
            final String imgUrl = mData.get(position).getCover();
            // 详情图片页面
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    ServerAddress.URL_PRODUCTPICTURE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    ProductPictureBean productPicture = gson.fromJson(response, ProductPictureBean.class);
                    if (productPicture == null) {
                        Toast.makeText(ShoppingCart.this, "服务器维护", Toast.LENGTH_SHORT).show();
                    }
                    if (productPicture.getCode() == 200) {
                        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + productPicture.getList().get(0), hoder.iv_ImageView, options, null);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("cover", imgUrl);
                    return map;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(stringRequest);
            //编辑
            if (isBianJi) {
                //库存量
                final int goodCount = Integer.parseInt(mData.get(position).getNumber());
                //订单量
                final int buyCount = Integer.parseInt(mData.get(position).getNum());//商品数量

                hoder.ivReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//减少按钮
                        int count = Integer.parseInt(hoder.tvNum2.getText().toString());
                        if (!(count <= 1)) {
                            count -= 1;
                            orderCount -= parseDouble(mData.get(position).getPrice());//订单总价
                            hoder.tvNum.setText(count + "");//显示商品数量
                            tvCountMoney.setText("合计：" + orderCount + "");//显示合计
                            orderNum.put(position, count);//map装位置和数量

                            mData.get(position).setNum(String.valueOf(count));

                        }
                        hoder.tvNum2.setText(count + "");
                    }
                });
                hoder.ivAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//增加按钮
                        int count = Integer.parseInt(hoder.tvNum2.getText().toString());
                        if (count < goodCount) {
                            count += 1;
                            orderCount += parseDouble(mData.get(position).getPrice());
                            tvCountMoney.setText("合计：" + orderCount + "");//合计
                            orderNum.put(position, count);

                            mData.get(position).setNum(String.valueOf(count));
//                            mapData.put(mData.get(position).getId(),String.valueOf(count));
                        }
                        hoder.tvNum2.setText(count + "");
                    }
                });

                hoder.tvNum2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().isEmpty())
                            return;
                        int count = Integer.valueOf(s.toString());//编辑商品数量
                        mData.get(position).setNum(String.valueOf(count));

                        double orderCount = 0;
                        for (int i = 0; i < mData.size(); i++) {
                            orderCount += Double.parseDouble(mData.get(i).getPrice()) *
                                    Integer.valueOf(mData.get(i).getNum());
                        }

                        tvCountMoney.setText("合计：" + orderCount + "");//合计
                        orderNum.put(position, count);
                    }
//                    hoder.tvNum.setText(count+"");
                });
            }

            //状态切换
            if (isBianJi) {
                hoder.rlEditStatus.setVisibility(View.VISIBLE);
                hoder.llGoodInfo.setVisibility(View.INVISIBLE);
            } else {
                hoder.llGoodInfo.setVisibility(View.VISIBLE);
                hoder.rlEditStatus.setVisibility(View.INVISIBLE);
            }
            hoder.tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDelDialog(position);
                }
            });
            return convertView;
        }

        private void showDelDialog(final int Position) {
            final UIAlertView delDialog = new UIAlertView(ShoppingCart.this, "温馨提示", "确认删除该商品吗?",
                    "取消", "确定");
            delDialog.show();
            delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                @Override
                public void doLeft() {
                    delDialog.dismiss();
                }
                @Override
                public void doRight() {
                    String productID = mData.get(Position).getId();
                    DeletelGoods(productID);
                    orderCount -= Integer.parseInt(mData.get(Position).getNum()) *
                            parseDouble(mData.get(Position).getPrice());
                    tvCountMoney.setText("合计：" + orderCount + "");
                    mData.remove(Position);

                    notifyDataSetChanged();
                    delDialog.dismiss();
                }
            });
        }
    }

    public void DeletelGoods(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteGoodUrl, new Response.Listener<String>() {
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
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    class ViewHoder {
        //图片
        ImageView iv_ImageView;
        //商品描述
        TextView tv_Describe;
        //库存
        TextView tv_Stock;
        //新价格
        TextView tv_PriceNew;
        //旧价格
        TextView tv_Price0ld;
        //送银币
        TextView tv_yinbi;
        //数量
        TextView tvNum;
        //////////编辑状态下的view////////////////
        //增加数量
        ImageView ivAdd;
        //添加或者删除的时候的数量
        EditText tvNum2;
        //减少数量
        ImageView ivReduce;
        //删除商品
        TextView tvDel;
        //改变界面
        RelativeLayout rlEditStatus;
        LinearLayout llGoodInfo;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (Activity activity : PublicActivityList.activityList) {
                activity.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

