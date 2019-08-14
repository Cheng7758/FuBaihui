package com.example.zhanghao.woaisiji.httpurl;

import com.example.zhanghao.woaisiji.bean.BannerBean;
import com.example.zhanghao.woaisiji.bean.CancellationOrder;
import com.example.zhanghao.woaisiji.bean.MerchantsClassification;
import com.example.zhanghao.woaisiji.bean.billing.BillingDetailsBean;
import com.example.zhanghao.woaisiji.bean.friend.FriendsListBean;
import com.example.zhanghao.woaisiji.bean.homesearch.HomeSearch;
import com.example.zhanghao.woaisiji.bean.merchandise.MerchandiseDetails;
import com.example.zhanghao.woaisiji.bean.my.CancelCollectionBean;
import com.example.zhanghao.woaisiji.bean.my.CheckBogisticsBean;
import com.example.zhanghao.woaisiji.bean.my.MyCollectionBean;
import com.example.zhanghao.woaisiji.bean.my.MyRecommendedBean;
import com.example.zhanghao.woaisiji.bean.my.OrderBean;
import com.example.zhanghao.woaisiji.bean.pay.MerchantsPayBean;
import com.example.zhanghao.woaisiji.bean.pay.PaySignBean;
import com.example.zhanghao.woaisiji.bean.search.SearchVipBean;
import com.example.zhanghao.woaisiji.bean.zixun.NewsBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.my.ShopsDetailBean;
import com.example.zhanghao.woaisiji.my.ShopsDetails;
import com.example.zhanghao.woaisiji.my.ShopsRuzhuBean;
import com.example.zhanghao.woaisiji.resp.RespNull;

import java.util.Map;

import io.reactivex.Observable;
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
    //取消收藏
    @POST("/APP/Member/cancel_goods_collect")
    @FormUrlEncoded
    Observable<CancelCollectionBean> getCancelCollectionBean(@FieldMap Map<String, String> map);

    //订单
    @POST("/APP/Member/my_order")
    @FormUrlEncoded
    Observable<OrderBean> getMyOrderBean(@FieldMap Map<String, Object> map);

    //订单详情
    @POST("/APP/Member/order_detail")
    @FormUrlEncoded
    Observable<ShopsDetails> getMyOrderDetailBean(@FieldMap Map<String, String> map);

    //商品详情
    @GET("/APP/Shop/goods_detail/id/{id}")
    Observable<ShopsDetailBean> getShopsDetailBean(@Path("id") int id);

    //加盟商家入驻 - 页面数据
    @POST("/APP/Xinv/sjtojoininfo")
    @FormUrlEncoded
    Observable<ShopsRuzhuBean> getShopsRuzhuBean(@FieldMap Map<String, String> map);

    //加盟商家入驻 - 提交
    @POST("/APP/Xinv/sjtojoinApi")
    @FormUrlEncoded
    Observable<RespNull> getShopsRuzhuSubmit(@FieldMap Map<String, String> map);

    //加盟商家筛选列表
    @GET("/APP/Public/get_business")
    Observable<MerchantsClassification> getMerchantsClassification();

    //资讯列表
    @POST("/APP/Love/information")
    @FormUrlEncoded
    Observable<NewsBean> getNewsBean(@FieldMap Map<String, String> map);

    //查找会员
    @POST("/APP/Friend/search")
    @FormUrlEncoded
    Observable<SearchVipBean> getSearchVipBean(@FieldMap Map<String, String> map);

    //首页查找
    @POST("/APP/Xone/tsearch")
    @FormUrlEncoded
    Observable<HomeSearch> getHomeSearch(@FieldMap Map<String, String> map);

    //查看物流
    @POST("/APP/Xtojoin/Courier_api")
    @FormUrlEncoded
    Observable<CheckBogisticsBean> getCheckBogisticsBean(@FieldMap Map<String, String> map);

    //取消订单
    @POST("/APP/Member/cancel")
    @FormUrlEncoded
    Observable<CancellationOrder> getCancellationOrder(@FieldMap Map<String, String> map);

    //确认收货
    @POST("/APP/Member/receiving")
    @FormUrlEncoded
    Observable<CancellationOrder> getConfirmReceipt(@FieldMap Map<String, String> map);

    //申请退款
    @POST("/APP/Member/is_refund")
    @FormUrlEncoded
    Observable<CancellationOrder> getRefund(@FieldMap Map<String, String> map);

    //支付签名
    @POST("/APP/public/get_sign")
    @FormUrlEncoded
    Observable<PaySignBean> getPaySignBean(@FieldMap Map<String, String> map);

    //商家支付
    @POST("/APP/Member/do_pay_store")
    @FormUrlEncoded
    Observable<MerchantsPayBean> getMerchantsPayBean(@FieldMap Map<String, String> map);

    //账单明细
    @POST("/APP/Xtojoin/myzhangdan")
    @FormUrlEncoded
    Observable<BillingDetailsBean> getBillingDetailsBean(@FieldMap Map<String, String> map);

    //福百惠商城广告轮播图
    @GET("/APP/Public/bander/name/shop_top")
    Observable<BannerBean> getBannerBean();

    //宝贝详情列表数据
    @GET("/APP/Public/evaluate/id/{id}")
    Observable<MerchandiseDetails> getMerchandiseDetails(@Path("id") String id);

    //我的推荐
    @POST("/APP/member/one_member_list")
    @FormUrlEncoded
    Observable<MyRecommendedBean> getMyRecommendedBean(@FieldMap Map<String, String> map);
}
