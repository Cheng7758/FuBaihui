package com.example.zhanghao.woaisiji.activity.send;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.widget.DisplayUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinAutoDetailsActivity extends BaseActivity {
    public ImageView iv_join_us_detail_merchant_picture,iv_join_us_detail_merchant_phone_number;
    public TextView tv_join_us_detail_merchant_name;
    public TextView tv_join_us_detail_merchant_introduction;
    public TextView tv_join_us_detail_merchant_position;
    public TextView tv_join_us_detail_merchant_distance;

    public String call, img_url;
    private ImageView iv_title_bar_view_left_left , iv_join_us_detail_merchant_position;
    private TextView tv_title_bar_view_left_text;

    private String longitude,latitude ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_auto_details);

        tv_title_bar_view_left_text = (TextView) findViewById(R.id.v_title_bar_view_left_text);
        iv_title_bar_view_left_left = (ImageView) findViewById(R.id.iv_title_bar_view_left_left);
        iv_title_bar_view_left_left.setVisibility(View.VISIBLE);
        tv_title_bar_view_left_text.setVisibility(View.VISIBLE);
        iv_title_bar_view_left_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_join_us_detail_merchant_picture = (ImageView) findViewById(R.id.iv_join_us_detail_merchant_picture);
        tv_join_us_detail_merchant_name = (TextView) findViewById(R.id.tv_join_us_detail_merchant_name);
        tv_join_us_detail_merchant_position = (TextView) findViewById(R.id.tv_join_us_detail_merchant_position);
        iv_join_us_detail_merchant_position = (ImageView) findViewById(R.id.iv_join_us_detail_merchant_position);
        tv_join_us_detail_merchant_distance = (TextView) findViewById(R.id.tv_join_us_detail_merchant_distance);
        tv_join_us_detail_merchant_introduction = (TextView) findViewById(R.id.tv_join_us_detail_merchant_introduction);
        iv_join_us_detail_merchant_phone_number = (ImageView) findViewById(R.id.iv_join_us_detail_merchant_phone_number);


        img_url = getIntent().getStringExtra("img_car");
        String url = ServerAddress.SERVER_ROOT + img_url;
        Glide.with(this).load(url).error(R.drawable.icon_loading).into(iv_join_us_detail_merchant_picture);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("tx_name"))) {
            tv_join_us_detail_merchant_name.setText(getIntent().getStringExtra("tx_name"));
            tv_title_bar_view_left_text.setText(getIntent().getStringExtra("tx_name"));
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("tx_address_detail")))
            tv_join_us_detail_merchant_position.setText(getIntent().getStringExtra("tx_address_detail"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("tx_m")))
            tv_join_us_detail_merchant_distance.setText("(距离"+getIntent().getStringExtra("tx_m")+")");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("tx_content"))) {
            tv_join_us_detail_merchant_introduction.setText("商家简介："+getIntent().getStringExtra("tx_content"));
        }

        call = getIntent().getStringExtra("call");

        longitude= getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");

        iv_join_us_detail_merchant_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calls(call);
            }
        });
        iv_join_us_detail_merchant_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinAutoDetailsActivity.this,JoinCarLocationActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
            }
        });
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    @SuppressLint("MissingPermission")
    private void calls(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent2);
        }
    }


    /**
     * Getter
     */
    public class MyImageGetter implements Html.ImageGetter {

        WeakReference<TextView> mTextViewReference;
        Context mContext;

        public MyImageGetter(Context context, TextView textView, int with) {
            mContext = context.getApplicationContext();
            mTextViewReference = new WeakReference<TextView>(textView);
        }

        @Override
        public Drawable getDrawable(String url) {

            URLDrawable urlDrawable = new URLDrawable(mContext);

            // 异步获取图片，并刷新显示内容
            new ImageGetterAsyncTask(url, urlDrawable).execute();

            return urlDrawable;
        }

        public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
            WeakReference<URLDrawable> mURLDrawableReference;
            String mUrl;
            public ImageGetterAsyncTask(String url, URLDrawable drawable) {
                mURLDrawableReference = new WeakReference<URLDrawable>(drawable);
                mUrl = url;
            }
            @Override
            protected Drawable doInBackground(String... params) {
                // 下载图片，并且使用缓存
//                Bitmap bitmap = DownlaodUtils.getNetworkImageWithCache(mContext, mUrl);
                Bitmap bitmap = GetImageInputStream(mUrl);
                BitmapDrawable bitmapDrawable=null;
                if (bitmap!=null) {
                    bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    Rect bounds = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    if (mURLDrawableReference.get() != null) {
                        mURLDrawableReference.get().setBounds(bounds);
                    }
                    bitmapDrawable.setBounds(bounds);
                }
                return bitmapDrawable;
            }

            @Override
            protected void onPostExecute(Drawable result) {
                if (null != result) {
                    if (mURLDrawableReference.get() != null) {
                        mURLDrawableReference.get().drawable = result;
                    }
                    if (mTextViewReference.get() != null) {
                        // 加载完一张图片之后刷新显示内容
                        mTextViewReference.get().setText(mTextViewReference.get().getText());
                    }
                }
            }
        }


        public class URLDrawable extends BitmapDrawable {
            protected Drawable drawable;

            public URLDrawable(Context context) {
                // 设置默认大小和默认图片
                Rect bounds = new Rect(0, 0, 100, 100);
                setBounds(bounds);
                drawable = context.getResources().getDrawable(R.drawable.load_failed);
                drawable.setBounds(bounds);
            }

            @Override
            public void draw(Canvas canvas) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }
        }
    }

    /**
     * 得到图片
     * @param imageurl
     * @return
     */
    public Bitmap GetImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); // 超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); // 设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
