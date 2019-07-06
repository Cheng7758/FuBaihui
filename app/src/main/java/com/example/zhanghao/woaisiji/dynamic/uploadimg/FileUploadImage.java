package com.example.zhanghao.woaisiji.dynamic.uploadimg;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.zhanghao.woaisiji.bean.ImageBeen;
import com.example.zhanghao.woaisiji.dynamic.util.PictureUtil;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/10/21.
 */

public class FileUploadImage {
    public static final String TAG = "FileUploadImage";
    public String pictureId = "";
//    public static List<String> imgList = new ArrayList<>();
    public void fileUpLoad(File file) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.woaisiji.com/APP/Public/").build();
        //构建要上传的文件
//        String CameraDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "MaterialCamera";
//        File file = new File(CameraDir + File.separator + "PHOTO", "template.jpg");

        //先创建 service
        FileUploadService service = retrofit.create(FileUploadService.class);
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        //修改第一个参数将决定文件的名字以及返回的getList中的类名
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("img1", file.getName(), requestFile);

        String descriptionString = "http://www.woaisiji.com/";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        Call<ImageBeen> call = service.upload(description, body);
        call.enqueue(new Callback<ImageBeen>() {
            @Override
            public void onResponse(Call<ImageBeen> call,
                                   retrofit2.Response<ImageBeen> response) {
                Log.d(TAG, response.body().getMsg());
//                imgList.add(response.body().getList().getImg1().getId());
//                Log.d(TAG, String.valueOf(imgList.size()));
                if (listener!=null){
                    listener.sendData(response.body().getList().getImg1().getId());
                }

//                ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+response.body().getList().getImg1().getPath(),ivPersonalHead);
                // 修改服务器头像信息
//                updateHeadPicServer(response.body().getList().getImg1().getId());
            }

            @Override
            public void onFailure(Call<ImageBeen> call, Throwable t) {
//                Toast.makeText(PersonalDataActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
    private String imageName;
    private String[] temp = {"a","b","c","d","e","f","g","h","i","j","k"};
    /**
     * 内存卡/Horizon/template.png
     */
    public File getBitmapStoragePathFile(String imgUrl,int pos) {
        imageName = getPhotoFileName();
        saveBitmap(PictureUtil.compressImage(imgUrl),pos);
        return new File(Environment.getExternalStorageDirectory(), temp[pos]+imageName);
//        return new File(imgUrl);
    }

    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    /**
     * 保存Bitmap，这一步可省去了
     */
    public void saveBitmap(Bitmap bitmap,int pos) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }

        File f = new File(Environment.getExternalStorageDirectory(),temp[pos]+ imageName);
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



    public SendDataListener listener;
    public void setSendDataListener(SendDataListener listener){
        this.listener = listener;
    }

    public interface SendDataListener{
        public void sendData(String data);
    }

}
