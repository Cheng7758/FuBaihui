package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.OrderadvancepaymentBean;
import com.example.zhanghao.woaisiji.bean.OrdergenerationBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.example.zhanghao.woaisiji.view.UIAlertView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class OrdersubmissionActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private TextView number;
    private TextView price;
    private RelativeLayout jinbipayment;
    private ImageView jinbiSelected;
    private RelativeLayout xianjinpayment;
    private ImageView xianjinSelected;
    private TextView payment;
    private RelativeLayout gopayment;
    private int score;
    private int worthScore;
    private Double rmb;
    private Double finalPrice;
    private int option;
    private String dingdanhao;
    private int[] goods_num;
    private int[] goods_price;
    private int[] goods_f_sorts;
    private int[] goods_f_silver;
    private int[] goods_max;
    private String[] goods_name;
    private int[] goods_id;
    private int[] car_rmb;
    private String plcid;
    private String beuzhu;
    private int type;
    private String ordernum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersubmission);//提交订单界面

        initView();
        initData();

        int flag = getIntent().getFlags();
        if (flag == 0){
            getStringExtras();
            obtainDataFromServer();
        }else {
            ordernum = getIntent().getStringExtra("ordernum");
            Orderrequest(ordernum);
        }

    }

    private void getStringExtras() {
        Intent intent=getIntent();
        type = intent.getIntExtra("type",1);
        //商品名
        goods_num = intent.getIntArrayExtra("goods_num");
        //商品价格
        goods_price = intent.getIntArrayExtra("goods_price");
        //反金币
        goods_f_sorts = intent.getIntArrayExtra("goods_f_sorts");
        //反金币
         goods_f_silver= intent.getIntArrayExtra("goods_f_silver");
        //最大抵值
        goods_max = intent.getIntArrayExtra("goods_max");
        //商品名
        goods_name = intent.getStringArrayExtra("goods_name");
        //商品ID
        goods_id = intent.getIntArrayExtra("goods_id");
        //运费
        car_rmb = intent.getIntArrayExtra("car_rmb");
        //地址ID
        plcid = intent.getStringExtra("plcid");
        //备注
        beuzhu= intent.getStringExtra("beuzhu");


    }

    private void initView() {
        //后退按钮
        back = (ImageView) findViewById(R.id.iv_register_back);
        //订单编号
        number = (TextView) findViewById(R.id.number);
        //价格
        price = (TextView) findViewById(R.id.Price);
        //金币支付
        jinbipayment = (RelativeLayout) findViewById(R.id.jinbipayment);
        //选择金币支付
        jinbiSelected = (ImageView) findViewById(R.id.jinbiSelected);
        //现金支付
        xianjinpayment = (RelativeLayout) findViewById(R.id.xianjinpayment);
        //选择现金支付
        xianjinSelected = (ImageView) findViewById(R.id.xianjinSelected);
        //实际付款
        payment = (TextView) findViewById(R.id.payment);
        //去付款
        gopayment = (RelativeLayout) findViewById(R.id.Gopayment);
    }
    private void initData() {
        back.setOnClickListener(this);
        //付款点击事件
        gopayment.setOnClickListener(this);
        //金币支付点击事件
        jinbiSelected.setOnClickListener(this);
        //现金支付点击事件
        xianjinSelected.setOnClickListener(this);
    }


    private void obtainDataFromServer() {

        RequestParams requestParams=new RequestParams(ServerAddress.URL_ORDERGENERATION);
        if (goods_num != null){
            for(int i : goods_num){
                requestParams.addBodyParameter("goods_num[]",""+i);//1
            }}
        if (goods_price!= null){
            for(int i : goods_price){
                requestParams.addBodyParameter("goods_price[]",""+i);//1
            }
        }
        if (goods_f_sorts!= null){
            for(int i : goods_f_sorts){
                requestParams.addBodyParameter("goods_f_sorts[]",""+i);//1
            }
        }
        if (goods_f_silver!= null){
            for(int i : goods_f_silver){
                requestParams.addBodyParameter("goods_f_silver[]",""+i);//1
            }
        }
        if (goods_max!= null){
            for(int i : goods_max){
                requestParams.addBodyParameter("goods_max[]",""+i);//1
            }
        }
        if (goods_name!= null){
            for(String i : goods_name){
                requestParams.addBodyParameter("goods_name[]",""+i);//1
            }
        }
        if (goods_id!= null){
            for(int i : goods_id){
                requestParams.addBodyParameter("goods_id[]",""+i);//1
            }
        }



        requestParams.addBodyParameter("user_id", (WoAiSiJiApp.getUid()));//1
        if (car_rmb!= null){
            for(int i : car_rmb){
                requestParams.addBodyParameter("car_rmb[]",""+i);//1
            }
        }

        requestParams.addBodyParameter("plcid",plcid);//1
        requestParams.addBodyParameter("beuzhu",beuzhu);//1
//        requestParams.addBodyParameter("user_id",WoAiSiJiApp.uid);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                OrdergenerationBean ordergenerationBean=gson.fromJson(result,OrdergenerationBean.class);
                if(ordergenerationBean.getCode()==200){

                    if(type==1){
                        Orderrequest(ordergenerationBean.getOrdernum());
                    }else {
                        Orderrequest(String.valueOf(type));
                    }
                }else {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void Orderrequest(String ordernum) {
        RequestParams requestParams=new RequestParams(ServerAddress.URL_ORDERADVANCEPAYMENT);
        requestParams.addBodyParameter("orderid", String.valueOf(ordernum));
        requestParams.addBodyParameter("user_id", (WoAiSiJiApp.getUid()));
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
//                Log.d("订单预付款", "onSuccess: "+result);
                Gson gson=new Gson();
                OrderadvancepaymentBean orderadvancepaymentBean=gson.fromJson(result,OrderadvancepaymentBean.class);
                score=  Integer.parseInt(orderadvancepaymentBean.getJifen());
                worthScore = Integer.parseInt(orderadvancepaymentBean.getList().getScores());
                rmb = Double.parseDouble(orderadvancepaymentBean.getList().getRmb());
                price.setText("￥"+Double.valueOf(orderadvancepaymentBean.getList().getRmb()));
                number.setText(orderadvancepaymentBean.getList().getOrdernum());
                dingdanhao = orderadvancepaymentBean.getList().getOrdernum();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //去支付点击事件
            case R.id.Gopayment:
               Intent intent=new Intent(OrdersubmissionActivity.this,PaymentMethodActivity.class);
                if(option==1){
//                    Toast.makeText(OrdersubmissionActivity.this,"金币支付",Toast.LENGTH_LONG).show();
                    intent.putExtra("option",option);
                    intent.putExtra("ordernum",dingdanhao);
                    intent.putExtra("finalPrice",finalPrice);
                    intent.putExtra("price",rmb);
                    startActivity(intent);
                }else if(option==2){
//                    Toast.makeText(OrdersubmissionActivity.this,"现金支付",Toast.LENGTH_LONG).show();
                    intent.putExtra("option",option);
                    intent.putExtra("ordernum",dingdanhao);
                    intent.putExtra("finalPrice",finalPrice);
                    intent.putExtra("price",rmb);
                    startActivity(intent);
                }else {
                    Toast.makeText(OrdersubmissionActivity.this,"请选择支付方式",Toast.LENGTH_LONG).show();
                }
                break;
            //金币支付点击事件
            case R.id.jinbipayment:
                if(score>worthScore){
                    showDelDialog(worthScore);
                }else if(score<=worthScore){
                    showDelDialog(score);
                }
                option = 1;

                break;
            //现金支付点击事件
            case R.id.xianjinpayment:
                xianjinSelected.setImageResource(R.drawable.gongquan);
                jinbiSelected.setImageResource(R.drawable.baiquan);
                payment.setText("￥"+Double.valueOf(rmb));
                finalPrice=Double.valueOf(rmb);
                option = 2;
                break;

            case R.id.iv_register_back:
                for (Activity activity : PublicActivityList.activityList){
                    activity.finish();
                }
//                finish();
                break;
        }
    }

    private void showDelDialog( int jinbi) {
        final UIAlertView delDialog=new UIAlertView(OrdersubmissionActivity.this,"温习提示","您当前的剩余金币:"+score+",当前订单可用"+jinbi+"金A币抵值￥"+jinbi+"元","取消","确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                delDialog.dismiss();
            }

            @Override
            public void doRight() {
                if(score>worthScore){
                    finalPrice=Double.valueOf(rmb)-Double.valueOf(worthScore);
                    payment.setText("￥"+finalPrice);
                }else if(score<=worthScore){
                    finalPrice = Double.valueOf(rmb)-Double.valueOf(score);
                    payment.setText("￥"+ finalPrice);
                }
                jinbiSelected.setImageResource(R.drawable.gongquan);
                xianjinSelected.setImageResource(R.drawable.baiquan);
                delDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            for (Activity activity:PublicActivityList.activityList){
                activity.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
