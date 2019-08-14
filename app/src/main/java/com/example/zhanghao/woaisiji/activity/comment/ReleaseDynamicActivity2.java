package com.example.zhanghao.woaisiji.activity.comment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.UploadImageAdapter;
import com.example.zhanghao.woaisiji.base.BaseObserver;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import razerdp.design.SlideFromBottomPopup;

public class ReleaseDynamicActivity2 extends BaseActivity implements SlideFromBottomPopup.BottomPopClick,
        InvokeListener, TakePhoto.TakeResultListener, UploadImageAdapter.OnItemClickListener {

    private EditText mEditContent;
    private TextView mPublish;
    private RecyclerView mRecyclerView;
    private SlideFromBottomPopup mPop;
    private InvokeParam invokeParam;
    private List<String> mList = new ArrayList<>();
    private List<File> mFileList = new ArrayList<>();
    private List<String> mIdList = new ArrayList<>();
    private UploadImageAdapter mAdapter;
    private TakePhotoImpl mTakePhoto;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList.clear();
        mFileList.clear();
        mIdList.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        mTakePhoto = new TakePhotoImpl(this, this);
//        mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(mTakePhoto1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_dynamic2);// R.drawable.icon_addpic_focused
        initPermmition();
        initView();
    }

    private void initPermmition() {
        int permission = ActivityCompat.checkSelfPermission(ReleaseDynamicActivity2.this,
                Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReleaseDynamicActivity2.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void initView() {
        mEditContent = (EditText) findViewById(R.id.edit_content);
        mPublish = (TextView) findViewById(R.id.publish);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new UploadImageAdapter(this, mList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mPop = new SlideFromBottomPopup(this);
        mPop.setLineText("相册", "拍照", "取消");
        mPop.setBottomClickListener(this);
        hideKeyboard(mEditContent);
    }

    //在这里上传图片和内容（总共是两个接口完成的）
    public void publishClick(View view) {
        if (TextUtils.isEmpty(mEditContent.getText().toString())) {
            Toast.makeText(this, "请输入要发布的内容", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog();
        if (mFileList.size() != 0) {
            for (int i = 0; i < mFileList.size(); i++) {
                netUpload(mFileList.get(i));
            }
        } else {
            commitComment();
        }
    }

    //调用上传图片接口，传单个图片
    private void netUpload(File pFile) {
        Rx2AndroidNetworking.upload(ServerAddress.URL_UPLOAD_PICTURES)//17744403932
                .setContentType("multipart/form-data")
                .addMultipartFile("img", pFile)
                .build()
                .getObjectObservable(RespData.class)//RespData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespData>() {
                    @Override
                    public void accept(RespData updataResult) throws Exception {
                        if (updataResult.getCode() == 200) {
                            if (!TextUtils.isEmpty(updataResult.getData())) {
                                mIdList.add(updataResult.getData());
                                if (mIdList.size() == mFileList.size()) {//所有的文件都已经上传成功
                                    commitComment();
                                }
                            } else {
                                Toast.makeText(ReleaseDynamicActivity2.this, updataResult.getMsg(), Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();
                            }
                        }
                    }
                });
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void commitComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", WoAiSiJiApp.getUid());
        params.put("content", mEditContent.getText().toString());
        if (mIdList.size() != 0) {
            String ids = "";
            for (int i = 0; i < mIdList.size(); i++) {
                if (i != mIdList.size() - 1) {
                    ids += mIdList.get(i) + ",";
                } else {
                    ids += mIdList.get(i);
                }
            }
            params.put("picture", ids);
            Log.e("评论：", "上传评论时添加了图片id数量：" + mIdList.size() + ",uid:" + WoAiSiJiApp.getUid());
        }
        //发布点评
        Rx2AndroidNetworking.post(ServerAddress.URL_CIRCLE_RELEASE_DYNAMIC)
                .addBodyParameter(params)
                .build()
                .getObjectObservable(RespNull.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onSuccess(Object value) {
                        RespNull respNull = (RespNull) value;
                        if (respNull.getCode() == 200) {
                            Log.e("评论：", "评论成功");
                            finish();
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        dismissProgressDialog();
                        Toast.makeText(ReleaseDynamicActivity2.this, "提交评论发生错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void takeSuccess(TResult result) {
        String originalPath = result.getImage().getCompressPath() != null ? result.getImage().getCompressPath() : result.getImage().getOriginalPath();//先设置到列表中
        mList.add(originalPath);
        mFileList.add(new File(originalPath));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void clickTop() {
        mTakePhoto = new TakePhotoImpl(this, this);
        mTakePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(1080).create(), true);
        mTakePhoto.onPickFromGalleryWithCrop(getUri(), getOption());
        mPop.dismiss();
    }

    @Override
    public void clickCenter() {
        mTakePhoto = new TakePhotoImpl(this, this);
        mTakePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(1080).create(), true);
        mTakePhoto.onPickFromCaptureWithCrop(getUri(), getOption());
        mPop.dismiss();
    }

    @Override
    public void clickBottom() {
        mPop.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTakePhoto.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode,
                permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private CropOptions getOption() {
        return new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
    }

    private Uri getUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/woaisiji/" + System.currentTimeMillis() + ".png");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    @Override
    public void onItemClick(int pos) {
        hideKeyboard(mEditContent);
        if (pos == mList.size())
            mPop.showPopupWindow();
    }

    @Override
    public void onBackPressed() {
        dismissProgressDialog();
        super.onBackPressed();
    }
}
