package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.zhanghao.woaisiji.bean.PersonalDetailBillBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zhanghao.woaisiji.bean.PersonalDetailBillBean.*;

/**
 * 账单明细
 */
public class PersonalDetailBillActivity extends Activity {

    private int[] imageIds = {R.drawable.bill_product,
            R.drawable.ic_fubaihui, R.drawable.bill_convert, R.drawable.bill_daily_sign,
            R.drawable.bill_return_goods, R.drawable.bill_driver_deduct, R.drawable.bill_question,
            R.drawable.bill_question_answer, R.drawable.ic_fubaihui,R.drawable.gold_coins_small,R.drawable.silver_coins_small
    };

    private ListView lvDetailBill;
    private List<DetailBillBean> detailBillBeanList;

    private PersonalDetailBillBean personalDetailBill;
    private String silverValue;
    private String scoreValue;
    private String textSource;
    private String textWeek = "";
    private String textDate = "";
    private ImageView ivRegisterBack;
    private TextView tvRegisterTitle;
    private Button btnRegisterMore;
    private int imageCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_bill);


        lvDetailBill = (ListView) findViewById(R.id.lv_detail_bill);
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ivRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRegisterTitle = (TextView) findViewById(R.id.tv_register_title);
        tvRegisterTitle.setText("账单");

        btnRegisterMore = (Button) findViewById(R.id.btn_register_more);
        btnRegisterMore.setVisibility(View.GONE);
        getDataFromServer();
    }

    public void getDataFromServer() {
        // 访问服务器数据库，获取数据
        detailBillBeanList = new ArrayList<>();
        StringRequest obtainBillRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DETAIL_BILL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (personalDetailBill.code == 200){
                    for (int i=0;i<personalDetailBill.list.size();i++){
                        if (!"0".equals(personalDetailBill.list.get(i).score) || (!"0".equals(personalDetailBill.list.get(i).silver))){
                            detailBillBeanList.add(personalDetailBill.list.get(i));
                        }

                    }
                    lvDetailBill.setAdapter(new DetailBillAdapter());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(obtainBillRequest);


    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        personalDetailBill = gson.fromJson(response,PersonalDetailBillBean.class);
    }

    // 对DetailBillBean中的数据，进行加工处理
    private void getResolveData(DetailBillBean item) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 解析时间戳
        String strTime = TimeUtils.timesOne(item.ctime);
//        Log.d("时间戳",strTime);
        Calendar calendar = Calendar.getInstance();
        // 得到当前时间的时间戳
        long currentStamp = calendar.getTimeInMillis();
        long yesterDayStamp = currentStamp - 24*3600*1000;

        String currentDate = sdf.format(currentStamp);
        String yesterDayDate = sdf.format(yesterDayStamp);
//        Log.d("当前时间",currentDate);
//        Log.d("昨天时间",yesterDayDate);
        if (strTime.contains(currentDate)){
            textWeek = "今天";
            textDate = strTime.split("-")[3]+ ":" + strTime.split("-")[4];
        }else if (strTime.contains(yesterDayDate)){
            textWeek = "昨天";
            textDate = strTime.split("-")[3]+ ":" + strTime.split("-")[4];
        }else {
            textWeek = TimeUtils.changeweek(item.ctime);
            textDate = strTime.split("-")[1]+ "-" + strTime.split("-")[2];
        }

//        Log.d("得到星期",textWeek);
        textSource  = strTime.substring(0,10) + item.back1;
//        Log.d("金银币来源",textSource);
        if ("司机商城".equals(item.back1)){
            imageCategory = imageIds[0];
//            Log.d("司机商城====",item.back1);
        }
        else if ("管理员发放".equals(item.back1)){
            imageCategory = imageIds[1];
//            Log.d("管理员发放====",item.back1);
        }else if ("兑换商城".equals(item.back1)||"司机商城".equals(item.back1)||"积分商城".equals(item.back1)||"分红商城".equals(item.back1)){
            imageCategory = imageIds[2];
        }else if ("签到奖励".equals(item.back1)){
            imageCategory = imageIds[3];
        }else if ("退货返还".equals(item.back1)){
            imageCategory = imageIds[4];
        }else if ("扣除赠送".equals(item.back1)){
            imageCategory = imageIds[5];
        }else if ("提问悬赏".equals(item.back1)){
            imageCategory = imageIds[6];
        }else if ("答题采纳".equals(item.back1)){
            imageCategory = imageIds[7];
        }else if ("金币充值".equals(item.back1)){
            imageCategory = imageIds[9];
        }else if ("金币转账".equals(item.back1)){
            imageCategory = imageIds[9];
            if ("0".equals(item.status)){
                textSource  = strTime.substring(0,10) + item.back1+" 支出给 "+item.back2;
            }else if ("1".equals(item.status)){
                textSource  = strTime.substring(0,10) + item.back1+" 收入于 "+item.back2;
            }

        }else if ("银币转账".equals(item.back1)){
            imageCategory = imageIds[10];
            if ("0".equals(item.status)){
                textSource  = strTime.substring(0,10) + item.back1+" 支出给 "+item.back2;
            }else if ("1".equals(item.status)){
                textSource  = strTime.substring(0,10) + item.back1+" 收入于 "+item.back2;
            }
        }else{
            imageCategory = imageIds[8];
        }
        silverValue = "";
        scoreValue = "";
        if ("0".equals(item.status)){
            if (!"0".equals(item.silver)){
                silverValue = "-" + item.silver+"银币";
            }
            if (!"0".equals(item.score)){
                scoreValue = "-" + item.score+"金币";
            }
        }else if ("1".equals(item.status)){
            if(!"0".equals(item.silver)){
                silverValue = "+" + item.silver+"银币";
            }
            if (!"0".equals(item.score)){
                scoreValue = "+" + item.score+"金币";
            }
        }
        

    }

    class DetailBillAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return detailBillBeanList.size();
        }

        @Override
        public DetailBillBean getItem(int position) {
            return detailBillBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                view = View.inflate(PersonalDetailBillActivity.this, R.layout.personal_detail_bill_item,null);
                viewHolder = new ViewHolder();
               viewHolder.tvDateName = (TextView) view.findViewById(R.id.tv_date_name);
                viewHolder.tvDateNumber = (TextView) view.findViewById(R.id.tv_date_number);
                viewHolder.ivName = (ImageView) view.findViewById(R.id.iv_name);
                viewHolder.tvSilverValue = (TextView) view.findViewById(R.id.tv_silver_value);
                viewHolder.tvScoreValue = (TextView) view.findViewById(R.id.tv_score_value);
                viewHolder.tvAddSource = (TextView) view.findViewById(R.id.tv_add_source);

                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            DetailBillBean item = getItem(position);
            // 对item数据进行加工处理
            getResolveData(item);
            viewHolder.tvDateName.setText(textWeek);
            viewHolder.tvDateNumber.setText(textDate);
            viewHolder.ivName.setImageResource(imageCategory);
            if (!TextUtils.isEmpty(silverValue)){
                viewHolder.tvSilverValue.setVisibility(View.VISIBLE);
                viewHolder.tvSilverValue.setText(silverValue);
            }else{
                viewHolder.tvSilverValue.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(scoreValue)){
                viewHolder.tvScoreValue.setVisibility(View.VISIBLE);
                viewHolder.tvScoreValue.setText(scoreValue);
            }else{
                viewHolder.tvScoreValue.setVisibility(View.GONE);
            }
            
            viewHolder.tvAddSource.setText(textSource);
            return view;
        }
    }



    static class ViewHolder{
        public TextView tvDateName;
        public TextView tvDateNumber;
        public ImageView ivName;
        public TextView tvSilverValue;
        public TextView tvScoreValue;
        public TextView tvAddSource;
    }
}
