package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzz on 2016/11/29.
 */
public class AppraiseOrderActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;
    private TextView tvLoveDriverTitle;
    private String goodId;
    private String goodPic;
    private String goodTitle;
    private int type;
    private ImageView ivAppraisePicture;
    private TextView tvAppraiseContent;
    private Button btnOrderFormAppraise;
    private EditText editOrderFormAppraise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraise_order);

        getData();

        initView();
        initClickListener();
    }

    private void getData() {
        Intent intent = getIntent();
        goodId = intent.getStringExtra("good_id");
        goodPic = intent.getStringExtra("good_pic");
        goodTitle = intent.getStringExtra("good_title");
        type = intent.getFlags();
    }


    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvLoveDriverTitle = (TextView) findViewById(R.id.love_driver_title);
        tvLoveDriverTitle.setText("商品评价");

        ivAppraisePicture = (ImageView) findViewById(R.id.iv_appraise_picture);
        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+goodPic,ivAppraisePicture);
        tvAppraiseContent = (TextView) findViewById(R.id.tv_appraise_content);
        tvAppraiseContent.setText(goodTitle);

        editOrderFormAppraise = (EditText) findViewById(R.id.edit_order_form_appraise);
        btnOrderFormAppraise = (Button) findViewById(R.id.btn_order_form_appraise);
    }

    private void initClickListener() {
        ivBack.setOnClickListener(this);
        btnOrderFormAppraise.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_order_form_appraise:
                String content = editOrderFormAppraise.getText().toString();
                if (content!=null){
                    // 发布评论
                    addCommentToServer(content);
                }
                break;
        }
    }

    private void addCommentToServer(final String content) {
        StringRequest addCommentRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_ORDER_FORM_APPRAISE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AlterResultBean alterResult = gson.fromJson(response,AlterResultBean.class);
                Toast.makeText(AppraiseOrderActivity.this,alterResult.msg,Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("type", String.valueOf(type));
                params.put("g_id", goodId);
                params.put("content",content);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addCommentRequest);
    }
}
