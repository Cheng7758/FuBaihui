package com.example.zhanghao.woaisiji.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespPersonalRecommendCode;
import com.example.zhanghao.woaisiji.view.ImageViewDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PersonalRecommendCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private ImageView iv_personal_recommendation_code_hv;
    private TextView tv_personal_recommendation_code_nick,tv_personal_recommendation_code_referrer;
    private TextView tv_personal_recommend_code_my_code;

    private LinearLayout ll_personal_recommendation_code_my_invitation_code,ll_personal_recommendation_code_merchant_recommend_code,
            ll_personal_recommendation_code_vip_recommend_code,ll_personal_recommendation_code_merchant_payment_code;

    private ImageViewDialog showRecommendCodeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recommend_code);

        initData();
        initView();
    }

    /**
     * 获取 我的邀请码
     */
    private void initData() {
        showProgressDialog();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_MY_PERSONAL_INFO_REFERRAL_CODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (TextUtils.isEmpty(response))
                    return;
                Gson gson = new Gson();
                RespPersonalRecommendCode respPersonalRecommendCode = gson.fromJson(response,RespPersonalRecommendCode.class);
                if (respPersonalRecommendCode.getCode()==200){
                    tv_personal_recommend_code_my_code.setText(respPersonalRecommendCode.getData().getRecommend_code());
                    tv_personal_recommendation_code_nick.setText(respPersonalRecommendCode.getData().getNickname());
                    tv_personal_recommendation_code_referrer.setText(respPersonalRecommendCode.getData().getPid());
                    if (WoAiSiJiApp.getCurrentUserInfo()!=null)
                        Picasso.with(PersonalRecommendCodeActivity.this).load(WoAiSiJiApp.getCurrentUserInfo().getPic())
                                .error(R.drawable.icon_loading).into(iv_personal_recommendation_code_hv);
                }else{
                    if (!TextUtils.isEmpty(respPersonalRecommendCode.getMsg()))
                        Toast.makeText(PersonalRecommendCodeActivity.this,respPersonalRecommendCode.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(PersonalRecommendCodeActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(registerRequest);
    }

    private void initView() {

        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(ImageView.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("推荐码");
        iv_personal_recommendation_code_hv = (ImageView) findViewById(R.id.iv_personal_recommendation_code_hv);
        tv_personal_recommendation_code_nick = (TextView) findViewById(R.id.tv_personal_recommendation_code_nick);
        tv_personal_recommendation_code_referrer = (TextView) findViewById(R.id.tv_personal_recommendation_code_referrer);
        tv_personal_recommend_code_my_code = (TextView) findViewById(R.id.tv_personal_recommend_code_my_code);

        ll_personal_recommendation_code_my_invitation_code = (LinearLayout) findViewById(R.id.ll_personal_recommendation_code_my_invitation_code);
        ll_personal_recommendation_code_merchant_recommend_code = (LinearLayout) findViewById(R.id.ll_personal_recommendation_code_merchant_recommend_code);
        ll_personal_recommendation_code_vip_recommend_code = (LinearLayout) findViewById(R.id.ll_personal_recommendation_code_vip_recommend_code);
        ll_personal_recommendation_code_merchant_payment_code = (LinearLayout) findViewById(R.id.ll_personal_recommendation_code_merchant_payment_code);
        ll_personal_recommendation_code_my_invitation_code.setOnClickListener(this);
        ll_personal_recommendation_code_merchant_recommend_code.setOnClickListener(this);
        ll_personal_recommendation_code_vip_recommend_code.setOnClickListener(this);
        ll_personal_recommendation_code_merchant_payment_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_personal_recommendation_code_merchant_recommend_code://商家推荐码
                getCode(1);
                break;
            case R.id.ll_personal_recommendation_code_vip_recommend_code://会员推荐码
                getCode(2);
                break;
            case R.id.ll_personal_recommendation_code_merchant_payment_code://商家支付码
                getCode(3);
                break;

        }
    }

    /**
     * 获取  商家推荐码  会员推荐吗  商家支付码
     * @param type
     */
    private void getCode(int type) {
        showProgressDialog();
        String url = "";
        switch (type) {
            case 1:
                url = ServerAddress.URL_MERCHANT_RECOMMEND_CODE;
                break;
            case 2:
                url = ServerAddress.URL_VIP_RECOMMEND_CODE;
                break;
            case 3:
                url = ServerAddress.URL_MERCHANT_PAYMENT_CODE;
                break;
        }
        Request<Bitmap> codeRequest = new Request<Bitmap>(Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", (WoAiSiJiApp.getUid()));
                return params;
            }

            @Override
            protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
                byte[] data = response.data;
                BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
                Bitmap bitmap = null;
                decodeOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
                return Response.success(bitmap, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected void deliverResponse(Bitmap response) {
                dismissProgressDialog();
                if (response!=null){
                    showRecommendCodeDialog = new ImageViewDialog(PersonalRecommendCodeActivity.this,response);
                    showRecommendCodeDialog.show();
                }
            }
        };
        WoAiSiJiApp.mRequestQueue.add(codeRequest);



//        InputSt codeRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                dismissProgressDialog();
//                if (response==null) {
//                    Toast.makeText(PersonalRecommendCodeActivity.this,"获取图片失败", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showRecommendCodeDialog = new ImageViewDialog(PersonalRecommendCodeActivity.this,response);
//                showRecommendCodeDialog.show();
//            }
//        }, 0,0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dismissProgressDialog();
//                Toast.makeText(PersonalRecommendCodeActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("id", WoAiSiJiApp.uid);
//                return params;
//            }
//        };

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (showRecommendCodeDialog!=null){
            showRecommendCodeDialog.dismiss();
        }
    }
}
