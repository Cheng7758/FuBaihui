package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.zhanghao.woaisiji.bean.DriveTravelBean;
import com.example.zhanghao.woaisiji.dynamic.AlbumActivity;
import com.example.zhanghao.woaisiji.dynamic.ReleaseDynamicActivity;
import com.example.zhanghao.woaisiji.dynamic.uploadimg.FileUploadImage;
import com.example.zhanghao.woaisiji.dynamic.util.Bimp;
import com.example.zhanghao.woaisiji.dynamic.util.PublicWay;
import com.example.zhanghao.woaisiji.dynamic.util.Res;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.MyEditText;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/21.
 */
public class SelfDriveReleaseActivity extends Activity implements View.OnClickListener{
    private static final int PHOTO_REQUEST_GALLERY = 1;
    private EditText etReleaseTitle;
    private TextView tv_ReleaseTitleNumLimit;

    private int mNum = 20;
    private ImageButton ibSelfDriveShare;
    private ImageButton ibSelfDriveImage;
    private EditText myEditSelfDriveContent;

    private Button btnBack;
    private Button btnTravelRelease;
    private AlterResultBean resultBean;

    public static Bitmap bimap;
    private ImageView ivSelectedPic;
    private String picture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_focused);
        PublicWay.activityList.add(this);
        Bimp.max = 0;
        Bimp.tempSelectBitmap.clear();
        setContentView(R.layout.activity_selfdrive_release);
        btnBack = (Button) findViewById(R.id.btn_back1);
        btnTravelRelease = (Button) findViewById(R.id.btn_travel_release);

        etReleaseTitle = (EditText) findViewById(R.id.et_release_title);
        tv_ReleaseTitleNumLimit = (TextView) findViewById(R.id.tv_release_title_num_limit);
        ibSelfDriveShare = (ImageButton) findViewById(R.id.ib_self_drive_share);
        ibSelfDriveImage = (ImageButton) findViewById(R.id.ib_self_drive_image);
        myEditSelfDriveContent = (EditText) findViewById(R.id.my_edit_self_drive_content);
        ivSelectedPic = (ImageView) findViewById(R.id.iv_selected_pic);
        ivSelectedPic.setVisibility(View.GONE);
        editListener();

        ibSelfDriveShare.setOnClickListener(this);
        ibSelfDriveImage.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnTravelRelease.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Bimp.tempSelectBitmap.size()>0){
            ivSelectedPic.setVisibility(View.VISIBLE);
            ivSelectedPic.setImageBitmap(Bimp.tempSelectBitmap.get(0).getBitmap());
        }else {
            ivSelectedPic.setVisibility(View.GONE);
        }
    }

    private void editListener() {
        etReleaseTitle.addTextChangedListener(new TextWatcher() {
            private CharSequence mTemp;
            private int mSelectionStart;
            private int mSelectionEnd;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTemp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = mNum - editable.length();
                tv_ReleaseTitleNumLimit.setText(String.valueOf(number)+"/20");
                mSelectionStart = etReleaseTitle.getSelectionStart();
                mSelectionEnd = etReleaseTitle.getSelectionEnd();
                if(mTemp.length() > mNum){
                    editable.delete(mSelectionStart-1,mSelectionEnd);
                    int tempSelection = mSelectionEnd;
                    etReleaseTitle.setText(editable);
                    etReleaseTitle.setSelection(tempSelection);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_self_drive_share:
                showShareDialog();
                break;
            case R.id.ib_self_drive_image:
//                Toast.makeText(SelfDriveReleaseActivity.this,"上传图片功能尚未完善,暂不支持！",Toast.LENGTH_SHORT).show();
                // 从相册中获取图片
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image*//**//*");
                startActivityForResult(intent,PHOTO_REQUEST_GALLERY);*/
                Intent intent = new Intent(SelfDriveReleaseActivity.this,
                        AlbumActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_back1:
                finish();
                break;
            case R.id.btn_travel_release:
                FileUploadImage fileUploadImage = new FileUploadImage();
                File file = fileUploadImage.getBitmapStoragePathFile(Bimp.tempSelectBitmap.get(0).getImagePath(),0);
                fileUploadImage.fileUpLoad(file);
                fileUploadImage.setSendDataListener(new FileUploadImage.SendDataListener() {
                    @Override
                    public void sendData(String data) {
                        picture = data;
                        releaseTravelToServer();
                    }
                });

                finish();
                break;
        }
    }

    private void releaseTravelToServer() {
        StringRequest releaseTravelRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_TRAVEL_RELEASE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (resultBean.code == 200){

                }
                Toast.makeText(SelfDriveReleaseActivity.this,resultBean.msg,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("title",etReleaseTitle.getText().toString().trim());
                params.put("content",myEditSelfDriveContent.getText().toString().trim());
                if (picture != null){
                    params.put("picture",picture);
                }

                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(releaseTravelRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        resultBean = gson.fromJson(response,AlterResultBean.class);
    }

    private void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(SelfDriveReleaseActivity.this,R.layout.custom_share_dialog,null);
        builder.setTitle("转发");
        builder.setView(view);
        builder.create().show();
    }
}
