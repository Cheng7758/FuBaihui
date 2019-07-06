package com.example.zhanghao.woaisiji.dynamic;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.dynamic.adapter.AlbumGridViewAdapter;
import com.example.zhanghao.woaisiji.dynamic.bean.DynamicImageBean;
import com.example.zhanghao.woaisiji.dynamic.bean.ImageBucket;
import com.example.zhanghao.woaisiji.dynamic.util.AlbumHelper;
import com.example.zhanghao.woaisiji.dynamic.util.Bimp;
import com.example.zhanghao.woaisiji.dynamic.util.PublicWay;
import com.example.zhanghao.woaisiji.dynamic.util.Res;


/**
 * 这个是进入相册显示所有图片的界面
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:47:15
 */
public class AlbumActivity extends Activity implements OnClickListener{
    //显示手机里的所有图片的列表控件
    private GridView gridView;
    //当手机里没有图片时，提示用户没有图片的控件
    private TextView tv;
    //gridView的adapter
    private AlbumGridViewAdapter gridImageAdapter;
    //完成按钮
    private Button okButton;
    // 返回按钮
    private Button back;
    // 取消按钮
    private Button cancel;
    private Intent intent;

    // 预览按钮
    private Button preview;
    private Context mContext;
    private ArrayList<DynamicImageBean> dataList;
    private AlbumHelper helper;
    public static List<ImageBucket> contentList;
    public static Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_album"));
        PublicWay.activityList.add(this);
        mContext = this;
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        bitmap = BitmapFactory.decodeResource(getResources(), Res.getDrawableID("plugin_camera_no_pictures"));
        init();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
//        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mContext.unregisterReceiver(this);
            // TODO Auto-generated method stub  
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.i.back:
                finish();
                break;*/
            case R.id.cancel:
                Bimp.tempSelectBitmap.clear();
                finish();
                break;
            case R.id.ok_button:
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                finish();
                break;
        }
    }



    // 初始化，给一些对象赋值
    private void init() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
//
        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<DynamicImageBean>();
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);

        }

        cancel = (Button) findViewById(Res.getWidgetID("cancel"));
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        gridView = (GridView) findViewById(Res.getWidgetID("myGrid"));
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(Res.getWidgetID("myText"));
        gridView.setEmptyView(tv);
        okButton = (Button) findViewById(Res.getWidgetID("ok_button"));
        okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size()
                + "/" + PublicWay.num + ")");
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, boolean isChecked, Button chooseBt) {
                        if (Bimp.tempSelectBitmap.size() >= 9) {
                            toggleButton.setChecked(false);
                            chooseBt.setVisibility(View.GONE);
                            if (!removeOneData(dataList.get(position))) {
                                Toast.makeText(AlbumActivity.this, Res.getString("only_choose_num"),
                                        Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        if (isChecked) {
                            chooseBt.setVisibility(View.VISIBLE);
                            Bimp.tempSelectBitmap.add(dataList.get(position));


                            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size()
                                    + "/" + PublicWay.num + ")");
                        } else {
                            Bimp.tempSelectBitmap.remove(dataList.get(position));
                            chooseBt.setVisibility(View.GONE);
                            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                        }
//                        isShowOkBt();
                    }
                });

        cancel.setOnClickListener(this);
//        back.setOnClickListener(this);
        okButton.setOnClickListener(this);

    }


    private boolean removeOneData(DynamicImageBean imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            return true;
        }
        return false;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            intent.setClass(AlbumActivity.this, ImageFileActivity.class);
//            startActivity(intent);
            Bimp.tempSelectBitmap.clear();
            finish();
        }
        return false;

    }

    @Override
    protected void onRestart() {
//        isShowOkBt();
        super.onRestart();
    }


    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {

        // 注册广播接收器
        IntentFilter filter = new IntentFilter("data.broadcast.action");
//        filter.addAction("finish");
       registerReceiver(broadcastReceiver, filter);
        super.onResume();
    }
}
