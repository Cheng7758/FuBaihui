package com.example.zhanghao.woaisiji.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.main.MainActivity;
import com.example.zhanghao.woaisiji.bean.my.PersonalInfoBean;
import com.example.zhanghao.woaisiji.friends.LogoutEm;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.tools.CircleTransform;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.KeyPool;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.Util;
import com.example.zhanghao.woaisiji.view.DialogChooseImage;
import com.google.gson.Gson;
import com.example.network.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
public class PersonalSettingActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CUT_PHOTO = 3;
    private static final int REQUEST_TAKE_PHOTO = 2;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.png";

    private String imageName;
    private Uri imageUri;

    private ImageView iv_title_bar_view_left_left;
    private TextView tv_title_bar_view_centre_title;

    private ImageView iv_personal_setting_hv;
    private RelativeLayout rl_personal_setting_hv_root, rl_personal_setting_nick_name_root, rl_personal_setting_phone_root,
            rl_personal_setting_pwd_manager_root, rl_personal_setting_clean_cache_root, rl_personal_setting_feedback_root,
            rl_personal_setting_about_us_root;
    private TextView tv_personal_setting_nick_name, tv_personal_setting_phone, tv_personal_setting_clear_cache,
            tv_personal_setting_version_code;
    private Button btn_personal_setting_logout;

    private BroadcastReceiver modifyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        initView();
        setViewValue();

        modifyBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!TextUtils.isEmpty(intent.getAction()) && KeyPool.ACTION_MODIFY_PERSONAL_INFO.equals(intent.getAction())) {
                    setViewValue();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KeyPool.ACTION_MODIFY_PERSONAL_INFO);
        LocalBroadcastManager.getInstance(PersonalSettingActivity.this).registerReceiver(modifyBroadcastReceiver, intentFilter);
    }

    private void initView() {
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("个人资料");
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(this);
        btn_personal_setting_logout = (Button) findViewById(R.id.btn_personal_setting_logout);
        btn_personal_setting_logout.setOnClickListener(this);

        rl_personal_setting_hv_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_hv_root);
        rl_personal_setting_hv_root.setOnClickListener(this);
        rl_personal_setting_nick_name_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_nick_name_root);
        rl_personal_setting_nick_name_root.setOnClickListener(this);
        rl_personal_setting_phone_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_phone_root);
        rl_personal_setting_phone_root.setOnClickListener(this);
        rl_personal_setting_pwd_manager_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_pwd_manager_root);
        rl_personal_setting_pwd_manager_root.setOnClickListener(this);
        rl_personal_setting_clean_cache_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_clean_cache_root);
        rl_personal_setting_clean_cache_root.setOnClickListener(this);
        rl_personal_setting_feedback_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_feedback_root);
        rl_personal_setting_feedback_root.setOnClickListener(this);
        rl_personal_setting_about_us_root = (RelativeLayout) findViewById(R.id.rl_personal_setting_about_us_root);
        rl_personal_setting_about_us_root.setOnClickListener(this);

        iv_personal_setting_hv = (ImageView) findViewById(R.id.iv_personal_setting_hv);
        tv_personal_setting_nick_name = (TextView) findViewById(R.id.tv_personal_setting_nick_name);
        tv_personal_setting_phone = (TextView) findViewById(R.id.tv_personal_setting_phone);
        tv_personal_setting_clear_cache = (TextView) findViewById(R.id.tv_personal_setting_clear_cache);
        tv_personal_setting_version_code = (TextView) findViewById(R.id.tv_personal_setting_version_code);
    }

    private void setViewValue() {
        if (!TextUtils.isEmpty(WoAiSiJiApp.getCurrentUserInfo().getPic()))
            Picasso.with(this).load(ServerAddress.SERVER_ROOT + WoAiSiJiApp.getCurrentUserInfo().getPic())
                    .error(R.drawable.ic_fubaihui)
                    .transform(new CircleTransform(this))
                    .into(iv_personal_setting_hv);

        if (!TextUtils.isEmpty(WoAiSiJiApp.getCurrentUserInfo().getNickname()))
            tv_personal_setting_nick_name.setText(WoAiSiJiApp.getCurrentUserInfo().getNickname());
        if (!TextUtils.isEmpty(WoAiSiJiApp.getCurrentUserInfo().getUsername()))
            tv_personal_setting_phone.setText(WoAiSiJiApp.getCurrentUserInfo().getUsername());

        tv_personal_setting_version_code.setText("V " + Util.getVersionCode());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_view_left_left:
                finish();
                break;
            case R.id.rl_personal_setting_hv_root://头像
                openChooseDialog();
                break;
            case R.id.rl_personal_setting_nick_name_root://修改用户昵称
                startActivity(new Intent(PersonalSettingActivity.this, PersonalModifyNickActivity.class));
                break;
            case R.id.rl_personal_setting_phone_root://修改手机号
                startActivity(new Intent(PersonalSettingActivity.this, PersonalModifyPhoneActivity.class));
                break;
            case R.id.rl_personal_setting_pwd_manager_root://修改密码
                startActivity(new Intent(PersonalSettingActivity.this, PersonalModifyPwdActivity.class));
                break;
            case R.id.rl_personal_setting_clean_cache_root://清除缓存
                Toast.makeText(PersonalSettingActivity.this, "缓存清理完毕!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_personal_setting_about_us_root://关于我们
                startActivity(new Intent(PersonalSettingActivity.this, PersonalAboutUsActivity.class));
                break;
            case R.id.rl_personal_setting_feedback_root://意见反馈
                startActivity(new Intent(PersonalSettingActivity.this, PersonalOpinionFeedbackActivity.class));
                break;
            case R.id.btn_personal_setting_logout://退出
                // 退出账号，回到首页
                WoAiSiJiApp.setUid(null);
                WoAiSiJiApp.setCurrentUserInfo(null);
                WoAiSiJiApp.memberShipInfos = null;
                PrefUtils.setString(PersonalSettingActivity.this, "uid", "");
                PrefUtils.setString(PersonalSettingActivity.this, "personal_info", "");
                // 环信账号退出登录
                LogoutEm.logout();
                Intent intent = new Intent(PersonalSettingActivity.this, MainActivity.class);
                intent.putExtra(KeyPool.ACTION_LOGINOUT, KeyPool.ACTION_LOGINOUT);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 修改头像弹框
     */
    private void openChooseDialog() {
        final DialogChooseImage dialog = new DialogChooseImage(this);
        dialog.show();
        dialog.setClickGalleryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageName = getPhotoFileName();
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                dialog.dismiss();
            }
        });
        dialog.setClickCameraListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageName = getPhotoFileName();
                choseHeadImageFromCameraCapture();
                dialog.dismiss();
            }
        });
    }

    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (Util.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture, REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY: //选取图片
                    imageUri = data.getData();
                    if (imageUri != null)
                        startPhotoZoom(imageUri);
                    break;
                case REQUEST_TAKE_PHOTO: //拍照
                    if (Util.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                IMAGE_FILE_NAME);
                        imageUri = Uri.fromFile(tempFile);
                        startPhotoZoom(imageUri);
                    } else {
                        Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case REQUEST_CUT_PHOTO: //剪切图片
                    // 获取到裁剪后的图像
                    if (imageUri != null) {
                        Bitmap bitmap = getBitmapFromUri(imageUri);
                        saveBitmap(bitmap);
//                        fileUpLoad(new File(Environment.getExternalStorageDirectory(), imageName));
                        updateHeadPicServer();
                    }
                    break;
                default:

                    break;
            }
        }
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
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传头像
     */
    private void updateHeadPicServer() {
        showProgressDialog();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("uid", WoAiSiJiApp.getUid());
        paramMap.put("token", WoAiSiJiApp.token);
        File file = new File(Environment.getExternalStorageDirectory(), imageName);
        if (!file.exists()) {
            Toast.makeText(PersonalSettingActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        FunctionUtils.updateHeadPicServer(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();
                    RespData respData = gson.fromJson(response, RespData.class);
                    if (respData.getCode() == 200 && !TextUtils.isEmpty(respData.getData())) {
                        PersonalInfoBean currentUserInfo = gson.fromJson(PrefUtils.getString(
                                PersonalSettingActivity.this, "personal_info", ""),
                                PersonalInfoBean.class);
                        currentUserInfo.setPic(respData.getData());
                        String personalInforGsonString = gson.toJson(currentUserInfo);
                        PrefUtils.setString(PersonalSettingActivity.this, "personal_info", personalInforGsonString);
                        WoAiSiJiApp.setCurrentUserInfo(currentUserInfo);

                        Intent intent = new Intent();
                        intent.setAction(KeyPool.ACTION_MODIFY_PERSONAL_INFO);
                        LocalBroadcastManager.getInstance(PersonalSettingActivity.this).sendBroadcast(intent);
                        Toast.makeText(PersonalSettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                        finish();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        }, file, paramMap);
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
        startActivityForResult(intent, REQUEST_CUT_PHOTO);
    }

    /**
     * 保存Bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }
        imageName = StringUtils.defaultStr(imageName,System.currentTimeMillis() + "");
        File f = new File(Environment.getExternalStorageDirectory(), imageName);
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

}
