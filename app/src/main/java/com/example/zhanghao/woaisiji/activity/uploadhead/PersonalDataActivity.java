package com.example.zhanghao.woaisiji.activity.uploadhead;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.zhanghao.woaisiji.activity.PersonalHarvestAddressActivity;
import com.example.zhanghao.woaisiji.activity.PersonalInformationActivity;
import com.example.zhanghao.woaisiji.activity.PersonalModifyNickActivity;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.DialogChooseImage;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.qiansuo.zzz.yishucircle.MyApp;
//import com.qiansuo.zzz.yishucircle.R;
//import com.qiansuo.zzz.yishucircle.activity.PersonalHarvestAddressActivity;
//import com.qiansuo.zzz.yishucircle.activity.PersonalInformationActivity;
//import com.qiansuo.zzz.yishucircle.activity.PersonalModifyNickActivity;
//import com.qiansuo.zzz.yishucircle.bean.AlterResultBean;
//import com.qiansuo.zzz.yishucircle.bean.MemberShipInfosBean;
//import com.qiansuo.zzz.yishucircle.view.DialogChooseImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zzz on 2016/11/21.
 */
public class PersonalDataActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "PersonalInfoActivity";
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CUT_PHOTO = 3;
    private static final int REQUEST_NICK = 5;
    private static final int REQUEST_TAKE_PHOTO = 2;


    private Uri imageUri;
    private String imageName;

    private ImageView ivRegisterBack;
    private ImageButton ibUserNumber;
    private ImageButton ibUserNick;
    private ImageButton ibBasicInformation;
    private ImageButton ibUpdateHeader;
    private ImageButton ibHarvestAddress;
    private LinearLayout llReplaceHeader;
    private TextView tvDataUserNick;
    private TextView tvUserAccountNum;
    private LinearLayout llUserAccount;
    private LinearLayout llUserNick;
    private LinearLayout llBasicInformation;
    private LinearLayout llHarvestAddress;
    private RoundImageView ivPersonalHead;
    private MemberShipInfosBean memberShipInfo;
    private AlterResultBean resultBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        memberShipInfo = new MemberShipInfosBean();
        memberShipInfo = WoAiSiJiApp.memberShipInfos;
        initView();

        initClickListener();

        initDataInfos();
    }


    private void initView() {
        ivRegisterBack = (ImageView) findViewById(R.id.iv_register_back);
        ibUserNumber = (ImageButton) findViewById(R.id.ib_user_number);
        ibUserNick = (ImageButton) findViewById(R.id.ib_user_nick);
        ibBasicInformation = (ImageButton) findViewById(R.id.ib_basic_information);
        ibUpdateHeader = (ImageButton) findViewById(R.id.ib_update_header);
        ibHarvestAddress = (ImageButton) findViewById(R.id.ib_harvest_address);
        ivPersonalHead = (RoundImageView) findViewById(R.id.iv_personal_head);
        llReplaceHeader = (LinearLayout) findViewById(R.id.ll_replace_header);
        tvDataUserNick = (TextView) findViewById(R.id.tv_data_user_nick);
        tvUserAccountNum = (TextView) findViewById(R.id.tv_user_account_num);
        llUserAccount = (LinearLayout) findViewById(R.id.ll_user_account);
        llUserNick = (LinearLayout) findViewById(R.id.ll_user_nick);
        llBasicInformation = (LinearLayout) findViewById(R.id.ll_basic_information);
        llHarvestAddress = (LinearLayout) findViewById(R.id.ll_harvest_address);
    }

    private void initClickListener() {
        ibUserNumber.setOnClickListener(this);
        ibUserNick.setOnClickListener(this);
        ibBasicInformation.setOnClickListener(this);
        ibUpdateHeader.setOnClickListener(this);
        ibHarvestAddress.setOnClickListener(this);
        llReplaceHeader.setOnClickListener(this);
        llUserAccount.setOnClickListener(this);
        llUserNick.setOnClickListener(this);
        llBasicInformation.setOnClickListener(this);
        llHarvestAddress.setOnClickListener(this);
        ivRegisterBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.ib_user_number:
            case R.id.ll_user_account:
                Toast.makeText(PersonalDataActivity.this, "账号做为爱司机登录名不可以修改~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_nick:
            case R.id.ll_user_nick:
                Intent intentNick = new Intent(PersonalDataActivity.this, PersonalModifyNickActivity.class);
                intentNick.putExtra("nickname", memberShipInfo.info.nickname);
                startActivityForResult(intentNick, REQUEST_NICK);
//                startActivity());
                break;
            case R.id.ib_basic_information:
            case R.id.ll_basic_information:
                startActivity(new Intent(PersonalDataActivity.this, PersonalInformationActivity.class));
                break;
            case R.id.ib_update_header:
            case R.id.ll_replace_header:
                openChooseDialog();
//                imageName = getPhotoFileName();
//                getImageFromGallery();
                break;
            case R.id.ib_harvest_address:
            case R.id.ll_harvest_address:
                startActivity(new Intent(PersonalDataActivity.this, PersonalHarvestAddressActivity.class));
                break;
        }
    }

    private void openChooseDialog() {
        final DialogChooseImage dialog = new DialogChooseImage(PersonalDataActivity.this);
        dialog.show();

        dialog.setClickGalleryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageName = getPhotoFileName();
                getImageFromGallery();
                dialog.dismiss();
            }
        });
        dialog.setClickCameraListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageName = getPhotoFileName();
                choseHeadImageFromCameraCapture();
                dialog.dismiss();
//                Toast.makeText(PersonalDataActivity.this, "该功能尚未完善", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }


    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.png";
    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
//            Toast.makeText(PersonalDataActivity.this,"ddd",Toast.LENGTH_SHORT).show();
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, REQUEST_TAKE_PHOTO);
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // 修改昵称
                case REQUEST_NICK:
                    Bundle bundle = data.getExtras();
                    String nickName = bundle.getString("nickname");
                    memberShipInfo.info.nickname = nickName;
                    initDataInfos();
                    break;

                case REQUEST_CODE_GALLERY: //选取图片
                    imageUri = data.getData();
                    if (imageUri != null) {
                        startPhotoZoom(imageUri);
                    }
                    break;
            case REQUEST_TAKE_PHOTO: //拍照
//                if(resultCode == RESULT_OK) {
                    if (hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                IMAGE_FILE_NAME);
                        imageUri = Uri.fromFile(tempFile);
//                        Toast.makeText(PersonalDataActivity.this,"zhangyawei-bitmap:",Toast.LENGTH_SHORT).show();
                        startPhotoZoom(imageUri);
                    } else {
                        Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                                .show();
                    }


//                    if(mPicFile != null && mPicFile.exists()) {
//                        cutPhoto(Uri.fromFile(mPicFile), 200);
//                    }
//                }
                break;
                case REQUEST_CUT_PHOTO: //剪切图片
                    if (imageUri != null) {
//                        Log.d("zhangyawei", String.valueOf(imageUri));
                        Bitmap bitmap = getBitmapFromUri(imageUri);
/*
                        UploadImageToServer.uploadImage(bitmap);
*/
                        saveBitmap(bitmap);
                        fileUpLoad(getBitmapStoragePathFile());
                    }
                    break;
                default:

                    break;
            }
        }

    }

    private void initDataInfos() {
//        tvUserNick.setText("zhangyawei");
//        tvUserNick.setText(WoAiSiJiApp.uid);
        tvDataUserNick.setText(memberShipInfo.info.nickname);
        tvUserAccountNum.setText(memberShipInfo.info.username);
        String imgUrl = getIntent().getStringExtra("imgUrl");
        // 头像加载
//        Glide.with(PersonalDataActivity.this).load(imgUrl).into(rivInfoDetail);
        ImageLoader.getInstance().displayImage(imgUrl, ivPersonalHead);

    }

    private void fileUpLoad(File file) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.woaisiji.com/APP/Public/").build();/*"http://192.168.0.253:8080/WebTest/"*/
      //  构建要上传的文件
//        String CameraDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "MaterialCamera";
//         file = new File(CameraDir + File.separator + "PHOTO", "template.jpg");

        //先创建 service
        FileUploadService service = retrofit.create(FileUploadService.class);
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        //修改第一个参数将决定文件的名字以及返回的getList中的类名
//        Toast.makeText(PersonalInfoActivity.this, "file:" + file.getName(), Toast.LENGTH_SHORT).show();
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("img1", file.getName(), requestFile);

        String descriptionString = "http://192.168.0.253:8080/WebTest/";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        Call<ImageBeen> call = service.upload(description, body);
        call.enqueue(new Callback<ImageBeen>() {
            @Override
            public void onResponse(Call<ImageBeen> call,
                                   retrofit2.Response<ImageBeen> response) {
                Log.d(TAG, response.body().getMsg());
//                Toast.makeText(PersonalDataActivity.this, response.body().getList().img1.getId(), Toast.LENGTH_SHORT).show();
//                Glide.with(PersonalInfoActivity.this).load("http://www.woaisiji.com"+response.body().getList().getImg1().getPath()).into(rivInfoDetail);
                ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+response.body().getList().getImg1().getPath(),ivPersonalHead);
                // 修改服务器头像信息
                updateHeadPicServer(response.body().getList().getImg1().getId());
//                UpdateUserInfoUtils updateUserInfoUtils = new UpdateUserInfoUtils(PersonalInfoActivity.this);
//                updateUserInfoUtils.updateHeadPicServer(response.body().getList().getImg1().getId());
//                updateHeadPicServer();
            }

            @Override
            public void onFailure(Call<ImageBeen> call, Throwable t) {
//                Toast.makeText(PersonalDataActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }


    private void updateHeadPicServer(final String picId) {
        StringRequest updateHeadPicRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_UPDATE_INFO_TWO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                Toast.makeText(PersonalDataActivity.this, resultBean.msg, Toast.LENGTH_SHORT).show();
                WoAiSiJiApp.memberShipInfos.info.headpic = picId;
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
                params.put("headpic", picId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(updateHeadPicRequest);
    }
    private void transServerData(String response) {
        Gson gson = new Gson();
        resultBean = gson.fromJson(response, AlterResultBean.class);
    }

    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Toast.makeText(getApplicationContext(), "选择图片出错！", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //如果为true,则通过 Bitmap bmap = data.getParcelableExtra("data")取出数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        Toast.makeText(PersonalDataActivity.this,"zhangyawei-bitmap:",Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, REQUEST_CUT_PHOTO);
    }

    /**
     * 通过uri获取bitmap
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//            Log.e(TAG, "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存Bitmap，这一步可省去了
     */
    public void saveBitmap(Bitmap bitmap) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }

        File f = new File(Environment.getExternalStorageDirectory(), imageName);
//        File f = new File(Environment.getExternalStorageDirectory() + "/Horizon", imageName);
//        File f = new File(Environment.getExternalStorageDirectory() + "/Horizon", "templates.png");
       /* if (!f.getParentFile().mkdirs()) {
            return;
        }*/
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        if (f.exists()) {
            f.delete();
        }

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
//            Toast.makeText(PersonalInfoActivity.this,"zhangyawei"+f.getPath(),Toast.LENGTH_SHORT).show();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内存卡/Horizon/template.png
     */
    private File getBitmapStoragePathFile() {
        return new File(Environment.getExternalStorageDirectory(), imageName);
    }
}
