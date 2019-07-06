package com.example.zhanghao.woaisiji.httpurl;

import com.example.zhanghao.woaisiji.bean.my.MyCollectionBean;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.bean.my.ShopsDetailBean;
import com.example.zhanghao.woaisiji.bean.my.ShopsDetails;
import com.example.zhanghao.woaisiji.bean.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Cheng on 2019/6/30.
 */

public interface Myserver {
    String url = ServerAddress.SERVER_ROOT;

    //收藏
    @POST("APP/Member/goods_collect_list")
    @FormUrlEncoded
    Observable<MyCollectionBean> getMyCollectionBean(@FieldMap Map<String, String> map);

    //订单
    @POST("/APP/Member/my_order")
    @FormUrlEncoded
    Observable<OrderBean> getMyOrderBean(@FieldMap Map<String, String> map);

    //订单详情
    @POST("/APP/Member/order_detail")
    @FormUrlEncoded
    Observable<ShopsDetails> getMyOrderDetailBean(@FieldMap Map<String, String> map);

    //商品详情
    @GET("/APP/Shop/goods_detail/id/{id}")
    Observable<ShopsDetailBean> getShopsDetailBean(@Path("id") int id);

    //加盟商家入驻
    @POST("/APP/Xinv/sjtojoininfo")
    @FormUrlEncoded
    Observable<ShopsRuzhuBean> getShopsRuzhuBean(@FieldMap Map<String, String> map);
}
