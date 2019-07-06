package com.example.zhanghao.woaisiji.activity.uploadhead;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by horizon on 2016/10/12.
 */

public interface FileUploadService {
    @Multipart
    @POST("uploadPicture")
    Call<ImageBeen> upload(@Part("description") RequestBody description,
                           @Part MultipartBody.Part file);
}