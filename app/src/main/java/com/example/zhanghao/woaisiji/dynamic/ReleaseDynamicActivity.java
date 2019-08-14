package com.example.zhanghao.woaisiji.dynamic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.example.zhanghao.woaisiji.dynamic.bean.DynamicImageBean;
import com.example.zhanghao.woaisiji.dynamic.uploadimg.FileUploadImage;
import com.example.zhanghao.woaisiji.dynamic.util.Bimp;
import com.example.zhanghao.woaisiji.dynamic.util.FileUtils;
import com.example.zhanghao.woaisiji.dynamic.util.PictureUtil;
import com.example.zhanghao.woaisiji.dynamic.util.PublicWay;
import com.example.zhanghao.woaisiji.dynamic.util.Res;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/18.
 */
public class ReleaseDynamicActivity extends BaseActivity {
    private static final String TAG = "ReleaseDynamicActivity";

    /**
     * titleBar
     */
    private TextView tv_title_bar_view_centre_title, tv_title_bar_view_right_right_introduction;

    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CUT_PHOTO = 3;
    public static Bitmap bimap;
    private GridView gvSelectImage;
    private GridAdapter gridAdapter;
    private LinearLayout llPopup;
    private Button btnDialogGallery;
    private PopupWindow pop;
    private Uri imageUri;
    private View parentView;
    private String pictureId = "";
    private EditText editContent;
    private AlterResultBean resultBean;
    private Button btnDialogCancel;
    private Button btnDialogCamera;

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
        parentView = getLayoutInflater().inflate(R.layout.activity_release_dynamic, null);//写动态的界面

        // 针对6.0以上手机
        int permission = ActivityCompat.checkSelfPermission(ReleaseDynamicActivity.this,
                Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReleaseDynamicActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        setContentView(parentView);
        initView();
        initViewRelease();
    }

    private void initViewRelease() {
        editContent = (EditText) findViewById(R.id.edit_content);
        tv_title_bar_view_right_right_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editContent.getText().toString())) {
                    Toast.makeText(ReleaseDynamicActivity.this, "请输入要发布的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog();
                if (Bimp.tempSelectBitmap.size() > 0) {
                    final List<File> filesList = new ArrayList<>();
                    for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                        filesList.add(new File(Bimp.tempSelectBitmap.get(i).getImagePath()));
                    }

                    new Thread() {
                        @Override
                        public void run() {
                            final List<File> comList = new ArrayList<>();
                            for (File file : filesList) {
                                Bitmap compressBitmap = PictureUtil.compressImage(file.getAbsolutePath());
                                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                                    String saveFilePath = getExternalCacheDir().getPath() + file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/"));
                                    System.out.println("save:" + saveFilePath);
                                    boolean isSave = PictureUtil.saveBitmap(compressBitmap, saveFilePath);

                                    if (isSave) {
                                        comList.add(new File(saveFilePath));
                                    }
                                }
                                ;
                            }

                            if (!comList.isEmpty()) {
                                //上传图片
                                FunctionUtils.uploadServiceImgGroup(new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        dismissProgressDialog();
                                        if (TextUtils.isEmpty(response))
                                            return;
                                        Gson gson = new Gson();
                                        RespData respData = null;
                                        try {
                                            respData = gson.fromJson(response, RespData.class);
                                        }catch (Exception e){}
                                        if (respData != null && respData.getCode() == 200) {
                                            pictureId = respData.getData();
//                                getImageId();
                                            releaseDynamicToServer();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dismissProgressDialog();
                                    }
                                }, comList, "");
                            }
                        }
                    }.start();

                } else {
                    releaseDynamicToServer();
                }
            }
        });
    }

    private void getImageId() {
        int i = 0;
        FileUploadImage fileUploadImage = new FileUploadImage();
        for (i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            File file = fileUploadImage.getBitmapStoragePathFile(Bimp.tempSelectBitmap.get(i).getImagePath(), i);
            fileUploadImage.fileUpLoad(file);
            fileUploadImage.setSendDataListener(new FileUploadImage.SendDataListener() {
                @Override
                public void sendData(String data) {
                    pictureId = pictureId + data + ",";
                }
            });
        }
    }

    /**
     * 发布点评
     */
    private void releaseDynamicToServer() {
        final String content = editContent.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            StringRequest releaseRequest = new StringRequest(Request.Method.POST, ServerAddress.
                    URL_CIRCLE_RELEASE_DYNAMIC, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dismissProgressDialog();
                    if (TextUtils.isEmpty(response))
                        return;
                    Gson gson = new Gson();
                    RespNull respNull = gson.fromJson(response, RespNull.class);
                    if (respNull.getCode() == 200) {
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    Toast.makeText(ReleaseDynamicActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("uid", WoAiSiJiApp.getUid());
                    params.put("content", content);
                    if (!TextUtils.isEmpty(pictureId)) {
                        params.put("picture", pictureId);
                    }
                    return params;
                }
            };
            WoAiSiJiApp.mRequestQueue.add(releaseRequest);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gridAdapter.update();
//        pictureId = "";
//        getImageId();
    }

    private void initView() {
        tv_title_bar_view_centre_title = (TextView) findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_centre_title.setText("写动态");
        tv_title_bar_view_right_right_introduction = (TextView) findViewById(R.id.tv_title_bar_view_right_right_introduction);
        tv_title_bar_view_right_right_introduction.setVisibility(View.VISIBLE);
        tv_title_bar_view_right_right_introduction.setText("发布");

        pop = new PopupWindow(ReleaseDynamicActivity.this);
        View view = View.inflate(ReleaseDynamicActivity.this, R.layout.dialog_choose_image, null);
        llPopup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        view.findViewById(R.id.tv_dialog_title).setVisibility(View.GONE);
        //从相册获取
        btnDialogGallery = (Button) view.findViewById(R.id.dialog_btn_gallery);
        btnDialogGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bimp.tempSelectBitmap.clear();
                Intent intent = new Intent(ReleaseDynamicActivity.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                llPopup.clearAnimation();
//                finish();
            }
        });
        //拍照
        btnDialogCamera = (Button) view.findViewById(R.id.dialog_btn_camera);
        btnDialogCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo();
                pop.dismiss();
                llPopup.clearAnimation();
            }
        });
        btnDialogCancel = (Button) view.findViewById(R.id.dialog_btn_cancel);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                llPopup.clearAnimation();
            }
        });

        gvSelectImage = (GridView) findViewById(R.id.gv_select_image);  //添加图片按钮
        gvSelectImage.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridAdapter = new GridAdapter(this);
        gridAdapter.update();
        gvSelectImage.setAdapter(gridAdapter);
        gvSelectImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == Bimp.tempSelectBitmap.size()) {
                    llPopup.startAnimation(AnimationUtils.loadAnimation(ReleaseDynamicActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(llPopup, Gravity.BOTTOM, 0, 0);
                } else {

                }
            }
        });

    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
//        FileUtils.savePath(String.valueOf(System.currentTimeMillis()));
        FileUtils.savePath(getPhotoFileName());
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(FileUtils.picPath)));
        Log.d("ss", "拍照前路径" + FileUtils.picPath);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                    DynamicImageBean takePhoto = new DynamicImageBean();
                    takePhoto.setImagePath(FileUtils.picPath);
                    Log.d("-------ss", "相机的" + FileUtils.picPath);
                    if (Bimp.tempSelectBitmap != null) {
                        Bimp.tempSelectBitmap.clear();
                    }
                    Bimp.tempSelectBitmap.add(takePhoto);
                    new Thread() {

                    }.start();
                    Bitmap compressBitmap = PictureUtil.compressImage(takePhoto.getImagePath());
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        String saveFilePath = getExternalCacheDir().getPath() + FileUtils.picPath.substring(FileUtils.picPath.lastIndexOf("/"));
                        System.out.println("save:" + saveFilePath);
                        boolean isSave = PictureUtil.saveBitmap(compressBitmap, saveFilePath);
                        if (isSave) {
                            Bimp.tempSelectBitmap.clear();
                            takePhoto = new DynamicImageBean();
                            takePhoto.setImagePath(saveFilePath);
                            Bimp.tempSelectBitmap.add(takePhoto);

                            showProgressDialog();
                            if (Bimp.tempSelectBitmap.size() > 0) {
                                List<File> filesList = new ArrayList<>();
                                for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                                    filesList.add(new File(Bimp.tempSelectBitmap.get(i).getImagePath()));
                                }
                                //上传图片
                                FunctionUtils.uploadServiceImgGroup(new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        dismissProgressDialog();
                                        if (TextUtils.isEmpty(response))
                                            return;
                                        Gson gson = new Gson();
                                        RespData respData = gson.fromJson(response, RespData.class);
                                        if (respData.getCode() == 200) {
                                            pictureId = respData.getData();
//                                getImageId();
                                            releaseDynamicToServer();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dismissProgressDialog();
                                    }
                                }, filesList, "");
                            } else {
                                releaseDynamicToServer();
                            }
                        }
                    }
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }


        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.release_dynamic_image,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_focused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        gridAdapter.notifyDataSetChanged();
//                        getImageId();
                        break;

                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

}
