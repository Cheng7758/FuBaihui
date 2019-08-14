package com.example.zhanghao.woaisiji.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    public static long MAX_SIZE = 1024 * 1024 * 3 / 2;

    /**
     * 将图片路劲转成bitmap
     *
     * @param url 图片路劲
     * @return
     */
    public static Bitmap getBitmap(String url) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap img = BitmapFactory.decodeFile(url, options);
        return img;
    }

    /**
     * 是否需要压缩
     *
     * @param image   压缩的图片
     * @param maxSize 允许图片的最大值
     * @return
     */
    public static boolean isNeedCompress(Bitmap image, long maxSize) {
        if (maxSize == 0) maxSize = MAX_SIZE;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int length = baos.toByteArray().length;
        Log.e("----图片大小----",length/1024+"");
        return length > maxSize;
    }

    /**
     * 压缩图片
     *
     * @param image   要压缩的图片
     * @param maxSize 压缩到多大为止
     * @return
     */
    public static Bitmap compressImage(Bitmap image, long maxSize) {
        if (maxSize == 0) maxSize = MAX_SIZE;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        Log.e("压缩前图片大小为 : ", baos.toByteArray().length / 1024 + "KB");
        while (baos.toByteArray().length > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options < 0) {
                // options = 90;
                break;
            }
        }
        Log.e("压缩后图片大小为 : ", baos.toByteArray().length / 1024 + "KB");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    public static File saveBitmapAsFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int length = baos.toByteArray().length;
        Log.e("----转成文件前图片大小----",length/1024+"");
        File file = new File(Environment.getExternalStorageDirectory(), "/woaisiji_compress/" + System.currentTimeMillis() + ".png");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public static File saveCahceBitmapToFile(Bitmap bitmap) throws IOException {
        BufferedOutputStream os = null;
        File file;
        try {
            File filePath = new File(Environment.getExternalStorageDirectory() + "/compress");

            if (!filePath.exists()) {
                filePath.mkdir();
            }
            file = new File(filePath + File.separator + System.currentTimeMillis());
            if (!file.exists()) {
                file.createNewFile();
                os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e("Error", e.getMessage(), e);
                }
            }
        }
        return file;
    }
}
