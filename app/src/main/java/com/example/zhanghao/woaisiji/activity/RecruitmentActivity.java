package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.httpurl.Myserver;
import com.example.zhanghao.woaisiji.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.resp.RespNull;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.PrefUtils;
import com.example.zhanghao.woaisiji.utils.Util;
import com.example.zhanghao.woaisiji.utils.http.NetManager;
import com.example.zhanghao.woaisiji.view.DialogChooseImage;
import com.example.zhanghao.woaisiji.widget.PickerView.PickerScrollView;
import com.example.zhanghao.woaisiji.widget.PickerView.Pickers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.zhanghao.woaisiji.utils.Util.getBitmapFromUri;
import static com.example.zhanghao.woaisiji.utils.Util.saveTempBitmap;

public class RecruitmentActivity extends BaseActivity implements View.OnClickListener, PickerScrollView.onSelectListener {

    private LinearLayout ll_recruitment_province_city_country_root;
    //省的WheelView控件/市的WheelView控件//区的WheelView控件/
    private WheelView wheelview_recruitment_province, wheelview_recruitment_city, wheelview_recruitment_country;

    private List<String> provinceData = new ArrayList<>();  //省份
    private HashMap<String, List<String>> cityDataHash = new HashMap<String, List<String>>(); //省份下面的市数据
    private HashMap<String, List<String>> provinceDataHash = new HashMap<String, List<String>>(); //市添加区

    private EditText et_recruitment_input_shop_name, et_recruitment_input_people_name,
            et_recruitment_input_contact_way, et_recruitment_input_licence_number, detail_location;
    private TextView select_file, shop_classify, classify_label, location;
    private CheckBox checkbox_yuedu;
    private Button ruzhu_btn;
    private ImageView recruitment_back;
    private PickerScrollView pickerview;
    private TextView cancel, confirm;
    private RelativeLayout relative, relativeLayout;

    private List<ShopsRuzhuBean.DataBean.ShengBean> mShengBeans;
    private List<ShopsRuzhuBean.DataBean.DpflBean> mDpflBeanList;
    private List<ShopsRuzhuBean.DataBean.FlbqBean> mFlbqBeanList;
    private String licence_cert = "";//营业执照图片地址
    private int index;
    private boolean isFirst = true;
    private Pickers currentDpflPickers, currentFlbqPickers;
    private String imageName, currentXian;
    private Uri imageUri;
    private static final int REQUEST_CODE_GALLERY = 1978;
    private static final int REQUEST_TAKE_PHOTO = 1987;
    private static final String IMAGE_FILE_NAME = "temp_head_image.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);
        initData();
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirst) {
            isFirst = false;
            ll_recruitment_province_city_country_root.setVisibility(View.GONE);
        }
    }

    private void initData() {
        currentDpflPickers = new Pickers();
        currentFlbqPickers = new Pickers();

        String shengStr = PrefUtils.getString(this, "GeographicInfo", "");
        String dpfl = PrefUtils.getString(this, "RecruitDpfl", "");
        String flbq = PrefUtils.getString(this, "RecruitFlbq", "");
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(flbq))
            mFlbqBeanList = gson.fromJson(flbq, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean.FlbqBean>>() {
            }.getType());
        if (!TextUtils.isEmpty(dpfl))
            mDpflBeanList = gson.fromJson(dpfl, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean.DpflBean>>() {
            }.getType());
        if (!TextUtils.isEmpty(shengStr))
            mShengBeans = gson.fromJson(shengStr, new TypeToken<ArrayList<ShopsRuzhuBean.DataBean.ShengBean>>() {
            }.getType());
        if (mShengBeans != null && mShengBeans.size() > 0) {
            for (int i = 0; i < mShengBeans.size(); i++) {
                ShopsRuzhuBean.DataBean.ShengBean shengBean = mShengBeans.get(i);
                provinceData.add(shengBean.getRegion_name());
                List<String> cityDataList = new ArrayList<>();  //shi
                for (int j = 0; j < shengBean.getShi().size(); j++) {
                    ShopsRuzhuBean.DataBean.ShiBean shiBean = shengBean.getShi().get(j);
                    List<String> countyDataList = new ArrayList<>();  //县
                    cityDataList.add(shiBean.getRegion_name());
//                    Log.d("Log",mShengBeans.get(i).getRegion_name()+" "+shiBean.getRegion_name());
                    if (shiBean.getXian() != null && shiBean.getXian().size() > 0) {
                        for (int k = 0; k < shiBean.getXian().size(); k++) {
                            ShopsRuzhuBean.DataBean.XianBean xianBean = shiBean.getXian().get(k);
                            countyDataList.add(xianBean.getRegion_name());
                        }
                    } else {
                        countyDataList.add("");
                    }
                    cityDataHash.put(shiBean.getRegion_name(), countyDataList);
                }
                provinceDataHash.put(shengBean.getRegion_name(), cityDataList);
            }
        }
    }

    private void initWheelView() {
        wheelview_recruitment_province = (WheelView) findViewById(R.id.wheelview_recruitment_province);
        wheelview_recruitment_province.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelview_recruitment_province.setSkin(WheelView.Skin.Holo);
        wheelview_recruitment_province.setWheelData(provinceData);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        wheelview_recruitment_province.setStyle(style);

        wheelview_recruitment_city = (WheelView) findViewById(R.id.wheelview_recruitment_city);
        wheelview_recruitment_city.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelview_recruitment_city.setSkin(WheelView.Skin.Holo);
        wheelview_recruitment_city.setWheelData(provinceDataHash.get(provinceData.get(
                wheelview_recruitment_province.getSelection())));
        wheelview_recruitment_city.setStyle(style);
        wheelview_recruitment_province.join(wheelview_recruitment_city);
        wheelview_recruitment_province.joinDatas(provinceDataHash);

        wheelview_recruitment_country = (WheelView) findViewById(R.id.wheelview_recruitment_country);
        wheelview_recruitment_country.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelview_recruitment_country.setSkin(WheelView.Skin.Holo);
        wheelview_recruitment_country.setWheelData(cityDataHash.get(provinceDataHash.get
                (provinceData.get(wheelview_recruitment_province.getSelection()))
                .get(wheelview_recruitment_city.getSelection())));
        wheelview_recruitment_country.setStyle(style);
        wheelview_recruitment_city.join(wheelview_recruitment_country);
        wheelview_recruitment_city.joinDatas(cityDataHash);
    }

    private void initView() {
        select_file = (TextView) findViewById(R.id.select_file);
        shop_classify = (TextView) findViewById(R.id.shop_classify);
        classify_label = (TextView) findViewById(R.id.classify_label);
        location = (TextView) findViewById(R.id.location);
        checkbox_yuedu = (CheckBox) findViewById(R.id.checkbox_yuedu);
        ruzhu_btn = (Button) findViewById(R.id.ruzhu_btn);
        recruitment_back = (ImageView) findViewById(R.id.recruitment_back);
        pickerview = (PickerScrollView) findViewById(R.id.pickerview);
        relative = (RelativeLayout) findViewById(R.id.relative);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm = (TextView) findViewById(R.id.confirm);

        et_recruitment_input_shop_name = (EditText) findViewById(R.id.et_recruitment_input_shop_name);
        et_recruitment_input_people_name = (EditText) findViewById(R.id.et_recruitment_input_people_name);
        et_recruitment_input_contact_way = (EditText) findViewById(R.id.et_recruitment_input_contact_way);
        et_recruitment_input_licence_number = (EditText) findViewById(R.id.et_recruitment_input_licence_number);
        detail_location = (EditText) findViewById(R.id.detail_location);

        ll_recruitment_province_city_country_root = (LinearLayout) findViewById(R.id.
                ll_recruitment_province_city_country_root);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        initWheelView();

        relativeLayout.setOnClickListener(this);
        ruzhu_btn.setOnClickListener(this);
        recruitment_back.setOnClickListener(this);
        shop_classify.setOnClickListener(this);
        classify_label.setOnClickListener(this);
        location.setOnClickListener(this);
        pickerview.setOnSelectListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recruitment_back:
                finish();
                break;
            case R.id.relativeLayout://选择图片
                openChooseDialog();
                break;
            case R.id.shop_classify://店铺分类
                pickerviewShow(index = 1);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);
                break;
            case R.id.classify_label://分类标签
                pickerviewShow(index = 2);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);
                break;
            case R.id.location: //所在地点
                index = 3;
                ll_recruitment_province_city_country_root.setVisibility(View.VISIBLE);
                relative.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:   //取消
                pickerview.setVisibility(View.GONE);
                relative.setVisibility(View.GONE);
                ll_recruitment_province_city_country_root.setVisibility(View.GONE);
                break;
            case R.id.confirm:  //确定
                relative.setVisibility(View.GONE);
                if (index == 1) {
                    pickerview.setVisibility(View.GONE);
                    shop_classify.setText(currentDpflPickers.getShowConetnt());
                } else if (index == 2) {
                    pickerview.setVisibility(View.GONE);
                    classify_label.setText(currentFlbqPickers.getShowConetnt());
                } else if (index == 3) {
                    ll_recruitment_province_city_country_root.setVisibility(View.GONE);
                    ShopsRuzhuBean.DataBean.ShiBean shiBean = mShengBeans.get(wheelview_recruitment_province.getCurrentPosition()).getShi()
                            .get(wheelview_recruitment_city.getCurrentPosition());
                    if (shiBean.getXian() != null) {
                        currentXian = shiBean.getXian().get(wheelview_recruitment_country.getCurrentPosition()).getRegion_name();
                    } else {
                        currentXian = "";
                    }
                    location.setText(provinceData.get(wheelview_recruitment_province.getCurrentPosition()) + "\t"
                            + shiBean.getRegion_name() + "\t" + currentXian);
                }
                break;
            case R.id.ruzhu_btn:
                if (TextUtils.isEmpty(et_recruitment_input_licence_number.getText().toString())) {
                    Toast.makeText(RecruitmentActivity.this, "带 * 号的必填", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkbox_yuedu.isChecked()) {
                    Toast.makeText(RecruitmentActivity.this, "请勾选协议", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSubmit();
                break;
        }
    }

    private void pickerviewShow(int pI) {
        ArrayList<Pickers> list = new ArrayList<>();
        if (pI == 1) {
            for (int i = 0; i < mDpflBeanList.size(); i++) {
                list.add(new Pickers(mDpflBeanList.get(i).getName() +
                        "让利" + mDpflBeanList.get(i).getBusiness(), mDpflBeanList.get(i).getId()));
            }
        } else if (pI == 2) {
            for (int i = 0; i < mFlbqBeanList.size(); i++) {
                list.add(new Pickers(mFlbqBeanList.get(i).getName(), mDpflBeanList.get(i).getId()));
            }
        }
        // 设置数据，默认选择第一条
        pickerview.setData(list);
        pickerview.setSelected(0);
        pickerview.setVisibility(View.VISIBLE);
        relative.setVisibility(View.VISIBLE);
    }

    /**
     * 提交
     */
    private void clickSubmit() {
        showProgressDialog();
        final String uid = WoAiSiJiApp.getUid();
        final Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("name", et_recruitment_input_shop_name.getText().toString());
        params.put("contacts", et_recruitment_input_people_name.getText().toString());
        params.put("phone", et_recruitment_input_contact_way.getText().toString());
        params.put("licence_number", et_recruitment_input_licence_number.getText().toString());
        params.put("address_detail", detail_location.getText().toString());
        params.put("licence_cert", licence_cert);
        params.put("cid", currentDpflPickers.getPickersId());
        params.put("screen", currentFlbqPickers.getPickersId());
        params.put("province", mShengBeans.get(wheelview_recruitment_province.getCurrentPosition()).getRegion_id());
        params.put("city", mShengBeans.get(wheelview_recruitment_province.getCurrentPosition()).getShi().get(wheelview_recruitment_city.getCurrentPosition()).getRegion_id());
        params.put("district", !TextUtils.isEmpty(currentXian) ? mShengBeans.get(
                wheelview_recruitment_province.getCurrentPosition()).getShi().get(
                wheelview_recruitment_city.getCurrentPosition()).getXian().get(
                wheelview_recruitment_country.getCurrentPosition()).getRegion_id() : "");
        NetManager.getNetManager().getMyService(Myserver.url)
                .getShopsRuzhuSubmit(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RespNull>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RespNull value) {
                        dismissProgressDialog();
                        if (value.getCode() != 200) {
                            if (!TextUtils.isEmpty(value.getMsg()))
                                Toast.makeText(RecruitmentActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(RecruitmentActivity.this, "请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void openChooseDialog() {
        final DialogChooseImage dialog = new DialogChooseImage(this, "上传营业执照");
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
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可用，存储照片文件
                if (Util.hasSdcard()) {
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                }
                startActivityForResult(intentFromCapture, REQUEST_TAKE_PHOTO);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY: //选取图片
                    imageUri = data.getData();
                    if (imageUri != null) {
                        Bitmap bitmap = getBitmapFromUri(RecruitmentActivity.this, imageUri);
                        Log.i("qlb", "bitmap2222222Size" + bitmap.getByteCount());
                        Log.i("qlb", "bitmap2222222SizegetRowBytes" + bitmap.getRowBytes() * bitmap.getHeight());
                        Bitmap bitmap1 = Util.compressImage(bitmap);
                        Log.i("qlb", "bitmap3333333Size" + bitmap1.getByteCount());
                        Log.i("qlb", "bitmap3333333SizegetRowBytes" + bitmap1.getRowBytes() * bitmap.getHeight());
                        startUpload(bitmap1);
                    }
                    break;
                case REQUEST_TAKE_PHOTO: //拍照
                    if (Util.hasSdcard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                        imageUri = Uri.fromFile(tempFile);
                        if (imageUri != null) {
                            Bitmap bitmap = getBitmapFromUri(RecruitmentActivity.this, imageUri);
                            Log.i("qlb", "bitmap2222222Size" + bitmap.getByteCount());
                            Bitmap bitmap1 = Util.compressImage(bitmap);
                            Log.i("qlb", "bitmap3333333Size" + bitmap1.getByteCount());
                            startUpload(bitmap1);
                        }
                    } else {
                        Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    // 滚动选择器选中事件
    @Override
    public void onSelect(Pickers pickers) {
        if (index == 1) {
            currentDpflPickers = pickers;
        } else if (index == 2) {
            currentFlbqPickers = pickers;
        }
    }

    /**
     * 开始上传
     *
     * @param bitmap
     */
    private void startUpload(Bitmap bitmap) {
        showProgressDialog();
        if (saveTempBitmap(Environment.getExternalStorageDirectory(), imageName, bitmap)) {
            final File file = new File(Environment.getExternalStorageDirectory(), imageName);
            if (!file.exists()) {
                return;
            }
            FunctionUtils.uploadServiceImg(
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dismissProgressDialog();
                            if (TextUtils.isEmpty(response)) return;
                            Gson gson = new Gson();
                            RespData respData = gson.fromJson(response, RespData.class);
                            if (respData.getCode() == 200) {
                                licence_cert = respData.getData();
                                select_file.setText("上传成功！");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dismissProgressDialog();
                            Toast.makeText(RecruitmentActivity.this, error.toString(), Toast.LENGTH_SHORT);
                        }
                    }, file, "1");
        }
    }
}
