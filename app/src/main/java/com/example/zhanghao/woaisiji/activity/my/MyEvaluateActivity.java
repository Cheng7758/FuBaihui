package com.example.zhanghao.woaisiji.activity.my;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.adapter.my.EvaImageAdapter;
import com.example.zhanghao.woaisiji.bean.my.ImageBean;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.http.VolleyMultipartRequest;
import com.google.gson.Gson;
import com.hedgehog.ratingbar.RatingBar;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO 评价界面
public class MyEvaluateActivity extends TakePhotoActivity {

    private String mId;
    private OrderBean.DataBean bean;

    private ImageView image, add_image;
    private TextView content, money, number;
    private EditText pingjia;
    private RecyclerView image_recy;
    private RatingBar ratingbar2, ratingbar3, ratingbar4;
    private Button submit;
    private TakePhoto takePhoto;
    private List<String> mList = new ArrayList<>();
    private EvaImageAdapter mAdapter;
    //评价
    public final static String URL_MY_PERSONAL_INFO_Evaluate = "http://wasj" +
            ".com/APP/Member/do_evaluate";

    private float mCount2;
    private float mCount3;
    private float mCount4;

    private String[] mStrings = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        bean = getIntent().getParcelableExtra("bean");
        initView();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        add_image = (ImageView) findViewById(R.id.add_image);
        content = (TextView) findViewById(R.id.content);
        money = (TextView) findViewById(R.id.money);
        number = (TextView) findViewById(R.id.number);
        pingjia = (EditText) findViewById(R.id.pingjia);
        image_recy = (RecyclerView) findViewById(R.id.image_recy);

        ratingbar2 = (RatingBar) findViewById(R.id.ratingbar2);
        ratingbar3 = (RatingBar) findViewById(R.id.ratingbar3);
        ratingbar4 = (RatingBar) findViewById(R.id.ratingbar4);
        submit = (Button) findViewById(R.id.submit);

        ratingbar2.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {

            @Override
            public void onRatingChange(float RatingCount) {
                mCount2 = RatingCount;
            }
        });

        ratingbar3.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {

            @Override
            public void onRatingChange(float RatingCount) {
                mCount3 = RatingCount;
            }
        });


        ratingbar4.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {


            @Override
            public void onRatingChange(float RatingCount) {
                mCount4 = RatingCount;
            }
        });

        content.setText(bean.getGoods_name());
        Glide.with(this).load(ServerAddress.SERVER_ROOT + bean.getGoods_img()).into(image);
        money.setText("￥" + bean.getPay_price());
        number.setText("x" + bean.getGoods_num());

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickk(getTakePhoto());
            }
        });

        mAdapter = new EvaImageAdapter(this, mList);
        image_recy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        image_recy.setAdapter(mAdapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                releaseDynamicToServer(bean.getId());

            }
        });


    }


    /**
     * 发布点评
     */
    private void releaseDynamicToServer(final String id) {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("uid", WoAiSiJiApp.getUid());
        paramMap.put("token", WoAiSiJiApp.token);
        paramMap.put("id", id);
        paramMap.put("goods_c", mCount2 + "");
        paramMap.put("wl", mCount3 + "");
        paramMap.put("service_c", mCount4 + "");
        paramMap.put("content", pingjia.getText().toString());
        List<File> list = new ArrayList<>();
        if (mList.size() > 0) {
            if (mList.size() == 1) {
                File file1 = new File(mList.get(0));
                list.add(file1);
            } else if (mList.size() == 2) {
                File file1 = new File(mList.get(0));
                list.add(file1);
                File file2 = new File(mList.get(1));
                list.add(file2);
            } else if (mList.size() == 3) {
                File file1 = new File(mList.get(0));
                list.add(file1);
                File file2 = new File(mList.get(1));
                list.add(file2);
                File file3 = new File(mList.get(2));
                list.add(file3);
            }


        }


        uploadServiceImgGroup(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();
                    ImageBean respData = gson.fromJson(response, ImageBean.class);
                    Toast.makeText(MyEvaluateActivity.this, respData.getMsg(), Toast
                            .LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyEvaluateActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }, list, paramMap);
    }


    public void back(View view) {
        finish();
    }


    public TakePhoto getTakePhoto() {

        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl
                    (this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(),
                true);
        return takePhoto;
    }

    public void onClickk(TakePhoto takePhoto) {
        configCompress(takePhoto);
        takePhoto.onPickMultiple(3);
    }


    private void configCompress(TakePhoto takePhoto) {//压缩配置
        int maxSize = Integer.parseInt("409600");//最大 压缩
        int width = Integer.parseInt("800");//宽
        int height = Integer.parseInt("800");//高
        CompressConfig config;
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(false)//拍照压缩后是否显示原图
                .create();
        takePhoto.onEnableCompress(config, false);//是否显示进度条
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        ArrayList<TImage> images = result.getImages();
        mList.clear();
        for (int i = 0; i < images.size(); i++) {
            mList.add(images.get(i).getCompressPath());
        }

        mAdapter.notifyDataSetChanged();
    }


    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    /**
     * 上传
     *
     * @param listener
     * @param errorListener
     * @param file
     * @param paramsMap
     */
    public static void updateHeadPicServer(Response.Listener<String> listener, Response
            .ErrorListener errorListener, File file, Map<String, String> paramsMap) {
        if (!paramsMap.isEmpty()) {
            VolleyMultipartRequest request = new VolleyMultipartRequest
                    (URL_MY_PERSONAL_INFO_Evaluate,
                            listener, errorListener, "file", file, paramsMap);
            //注意这个key必须是f_file[],后面的[]不能少
            WoAiSiJiApp.mRequestQueue.add(request);
        }
    }


    /**
     * 上传多张图片
     *
     * @param listener
     * @param errorListener
     */
    public static void uploadServiceImgGroup(Response.Listener<String> listener, Response
            .ErrorListener errorListener,
                                             List<File> files, Map<String, String> paramsMap) {
        VolleyMultipartRequest request = new VolleyMultipartRequest(ServerAddress
                .URL_UPLOAD_PICTURES,
                listener, errorListener, "img[]", files, paramsMap);
        WoAiSiJiApp.mRequestQueue.add(request);
    }


}
