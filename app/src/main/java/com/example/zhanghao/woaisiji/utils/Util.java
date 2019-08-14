/*
 * Copyright (C) 2014 The Android Open Source Project.
 *
 *        yinglovezhuzhu@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.zhanghao.woaisiji.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


import com.example.zhanghao.woaisiji.BuildConfig;
import com.example.zhanghao.woaisiji.global.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Use:
 * Created by yinglovezhuzhu@gmail.com on 2014-07-24.
 */
public class Util {

    /**
     * 存储是否可用
     * @return
     */
    public static boolean hasStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 根据文件夹名称生成一个应用数据目录
     * @param folderName
     * @return
     */
    public static String getApplicationFolder(String folderName) {
        if(StringUtil.isEmpty(folderName)) {
            return null;
        }
        if(hasStorage()) {
            File storage = Environment.getExternalStorageDirectory();
            File file =  new File(storage, folderName);
            if(!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 根据系统时间产生一个在指定目录下的图片文件名
     *
     * @param folder
     * @return
     */
    public static String createImageFilename(String folder) {
        return createFilename(folder, Config.IMAGE_PREFIX, Config.IMAGE_SUFFIX);
    }

    /**
     * 根据系统时间产生一个在指定目录下的视频文件名
     *
     * @param folder
     * @return
     */
    public static String createVideoFilename(String folder) {
        return createFilename(folder, Config.VIDEO_PREFIX, Config.VIDEO_SUFFIX);
    }

    /**
     * 根据系统时间、前缀、后缀产生一个文件名
     *
     * @param folder
     * @param prefix
     * @param suffix
     * @return
     */
    private static String createFilename(String folder, String prefix, String suffix) {
//        Log.d("文件路径--folder",folder);
        File file = new File(folder);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
//        String filename = prefix + System.currentTimeMillis() + suffix;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.DATE_FORMAT_MILLISECOND);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename).getAbsolutePath();
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int getRealHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenHeight = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            screenHeight = dm.heightPixels;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                DisplayMetrics dm = new DisplayMetrics();
                display.getMetrics(dm);
                screenHeight = dm.heightPixels;
            }
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }
    public static int getRealWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenHeight = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            screenHeight = dm.widthPixels;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                DisplayMetrics dm = new DisplayMetrics();
                display.getMetrics(dm);
                screenHeight = dm.widthPixels;
            }
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            screenHeight = dm.widthPixels;
        }
        return screenHeight;
    }
    /**
     * 获取屏幕宽高
     */
    public static int getDisplayMetrics(Activity act) {
        WindowManager manager = act.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static String getVersionCode(){
        return BuildConfig.VERSION_NAME;
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

    /**
     * 判定输入汉字/字母/数字
     * @param charIndex
     * @return
     */
    public static boolean isMatchHanziOrNumberOrLetter(char charIndex) {
        if((19968 <= charIndex && charIndex <40869)||Character.isDigit(charIndex)
                || Character.isUpperCase(charIndex) || Character.isLowerCase(charIndex)) {
            return true;
        }
        return false;
    }

    /**
     * 上传用户头像
     */
    public static void uploadModeifyHv(Context context , String path){


    }

    /**
     * 获取的时间数据
     * @param longDateStr
     * @return
     */
    public static String getDateString(String longDateStr){
        if (TextUtils.isEmpty(longDateStr))
            return "";
        Date time = new Date( Long.valueOf(longDateStr));
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        String result = formatter.format(time);
        return result ;
    }
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 50;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /**
     * 通过uri获取bitmap
     */
    public static Bitmap getBitmapFromUri(Context context,Uri imageUri){
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存Bitmap
     */
    public static boolean saveTempBitmap(File path ,String imageName, Bitmap bitmap) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        try {
            File f = new File(path, imageName);
            if (!f.getParentFile().exists())f.getParentFile().mkdirs();
            if (f.exists())f.delete();
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    // 成新的颜色值
    public static int getNewColorByStartEndColor(Context context, float fraction, int startValue, int endValue) {
        return evaluate(fraction, context.getResources().getColor(startValue), context.getResources().getColor(endValue));
    }
    /**
     * 成新的颜色值
     * @param fraction 颜色取值的级别 (0.0f ~ 1.0f)
     * @param startValue 开始显示的颜色
     * @param endValue 结束显示的颜色
     * @return 返回生成新的颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }
}
