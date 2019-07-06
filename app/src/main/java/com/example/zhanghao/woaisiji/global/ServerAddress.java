package com.example.zhanghao.woaisiji.global;


/**
 * Created by admin on 2016/9/1.
 */
public class ServerAddress {
    //    public final static String SERVER_ROOT = "http://www.woaisiji.com";
    public final static String SERVER_ROOT = "http://wasj.zhangtongdongli.com";//新的

    //    public final static String URL_SERVER = "http://www.woaisiji.com/APP/User/";
    public final static String URL_SERVER = "http://wasj.zhangtongdongli.com/";//新的

    //获取Token
    public final static String URL_GET_TOKEN = SERVER_ROOT + "/APP/Public/get_token";
    //发送验证码
    public final static String URL_SEND_SMS = SERVER_ROOT + "/APP/Public/sendsms";
    //注册
    public final static String URL_USER_NEW_REGISTER = SERVER_ROOT + "/APP/user/register";
    //登录
    public final static String URL_USER_LOGIN = SERVER_ROOT + "/APP/user/login";
    //找回密码
    public final static String URL_USER_FORGET_PASSWORD = SERVER_ROOT + "/APP/User/forget";
    //加入购物车
    public final static String URL_COMMODITY_ADD_SHOPPING_CAR = SERVER_ROOT + "/APP/Order/add_cart";
    //购物车列表
    public final static String URL_COMMODITY_SHOPPING_CAR_LIST = SERVER_ROOT + "/APP/Order/cart_list";
    //购物车数量改变
    public final static String URL_SHOPPING_CAR_CHANGE_NUMBER = SERVER_ROOT + "/APP/Order/cart_goods_setInc";
    //购物车删除
    public final static String URL_SHOPPING_CAR_DELETE_COMMODITY = SERVER_ROOT + "/APP/Order/del_good_car";
    //首页精选和公告
    public final static String URL_HOME_PAGE_JINGXUAN_GONGGAO = SERVER_ROOT + "/APP/Xone/goodslist";
    //加盟伙伴-搜索
    public final static String URL_JOIN_PARTNER_SEARCH_DATA = SERVER_ROOT + "/APP/Xone/tsearch";
    //加盟伙伴-请求列表
    public final static String URL_JOIN_PARTNER_REQUEST_JOIN_LIST = SERVER_ROOT + "/APP/Xtojoin/tojoin_list";
    //首页轮播图
    public final static String URL_GLOBAL_SLIDE_SHOW = SERVER_ROOT + "/APP/Public/bander/name/n_sybn";
    //FBH商城轮播
    public final static String URL_GLOBAL_FBH_SLIDE_SHOW = SERVER_ROOT + "/APP/Public/bander/name/shop_top";
    //商品分类
    public final static String URL_COMMODITY_CATEGORY = SERVER_ROOT + "/APP/public/category_list";
    //FBH商品列表
    public final static String URL_FBH_COMMODITY_DATA_LIST = SERVER_ROOT + "/APP/Shop/index/cid/61";
    //商家详情界面
    public final static String URL_COMMODITY_DETAIL = SERVER_ROOT + "/APP/Xtojoin/mer_details";
    //FBH商品详情
    public final static String URL_FBH_COMMODITY_DETAIL = SERVER_ROOT + "/APP/Shop/goods_detail/id/1561";
    //收藏产品
    public final static String URL_COLLECTION_PRODUCTION = SERVER_ROOT + "/APP/Member/goods_collect";
    //我的 信息
    public final static String URL_MY_PERSONAL_INFO = SERVER_ROOT + "/APP/Member/user_info";
    //修改昵称
    public final static String URL_MY_PERSONAL_INFO_MODIFY_NICK = SERVER_ROOT + "/APP/Member/user_info";
    //修改头像
    public final static String URL_MY_PERSONAL_INFO_MODIFY_HEAD_PORTRAIT = SERVER_ROOT + "/APP/Member/upload_headpic";
    //修改手机号
    public final static String URL_MY_PERSONAL_INFO_MODIFY_PHONE = SERVER_ROOT + "/APP/Member/upd_nickname";
    //优惠券 - 商家
    public final static String URL_MY_PERSONAL_INFO_COUPON = SERVER_ROOT + "/APP/Public/coupon_list";
    //优惠券 - 我的
    public final static String URL_MY_PERSONAL_INFO_MY_COUPON_LIST = SERVER_ROOT + "/APP/Public/coupon_list";
    //我的推荐
    public final static String URL_MY_PERSONAL_INFO_MY_RECOMMENDATION = SERVER_ROOT + "/APP/Xtojoin/mybill";
    //我的钱包
    public final static String URL_MY_PERSONAL_INFO_MY_WALLET = SERVER_ROOT + "/APP/Xtojoin/wallet";
    //金积分提现到余额
    public final static String URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT = SERVER_ROOT + "/APP/Xuser/refund";
    //提现到微信  支付宝
    public final static String URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_WX_ZFB = SERVER_ROOT + "/APP/Xuser/withdrawal";
    //提现是否成功
    public final static String URL_MY_PERSONAL_INFO_MY_WALLET_DEPOSIT_SUCCEED = SERVER_ROOT + "/APP/Xuser/txjudge";
    //商城点评 - 品论列表
    public final static String URL_DRIVER_REVIEW_COMMENT_LIST = SERVER_ROOT + "/APP/Circle/index";
    //推荐码
    public final static String URL_MY_PERSONAL_INFO_REFERRAL_CODE = SERVER_ROOT + "/APP/Xtojoin/recommended";
    //推荐码 - 会员推荐码
    public final static String URL_VIP_RECOMMEND_CODE = SERVER_ROOT + "/APP/Xinv/invcode";
    //推荐码 - 商家支付码
    public final static String URL_MERCHANT_PAYMENT_CODE = SERVER_ROOT + "/APP/Xtojoin/shopcode";
    //推荐码 - 商家推荐码
    public final static String URL_MERCHANT_RECOMMEND_CODE = SERVER_ROOT + "/APP/Xinv/tojoin";


    // 发送验证码
    public final static String URL_CODES = URL_SERVER + "sendsms";
    // 发送验证码
    public final static String URL_CODESS = SERVER_ROOT + "/APP/Public/revise";
    // 注册
    public final static String URL_REGISTER = URL_SERVER + "register";
    // 升级
    public final static String URL_UPDATE = SERVER_ROOT + "/APP/Version/get_version";

    // 金币兑换比例
    public final static String URL_GLOD_RATIO = "http://123.56.236.200/APP/Gold/index";
    // 金币兑换
    public final static String URL_GLOD_EXCHANGE = "http://123.56.236.200/APP/Gold/gold";
    // 金币兑换回调
    public final static String URL_GLOD_BACK = "http://123.56.236.200/APP/Gold/gold_status";

    // 金币转让
    public final static String URL_GLOD_SEND = "http://123.56.236.200/APP/golds/index";
    // 银币转让
    public final static String URL_GLOD_SLIVER = "http://123.56.236.200/APP/Silver/index";
    // 加盟汽修
    public final static String URL_GPS = "http://www.woaisiji.com/APP/Garage/index";
    // 加盟汽修列表
    public final static String URL_GPS_LIST = "http://www.woaisiji.com/APP/Garage/order";
    // 加盟汽修搜索列表
    public final static String URL_GPS_SEARCH = "http://www.woaisiji.com/APP/Garage/search";

    // 会员信息
    public final static String URL_MEMBER_INFO = "http://123.56.236.200/APP/Member/info";
    // 忘记密码
//    public final static String URL_FORGET_PASSWORD = "http://123.56.236.200/APP/User/forget";
    public final static String URL_FORGET_PASSWORD = "http://wasj.com/APP/User/forget";
    // 获取好友列表
    public final static String URL_FRIEND_LIST = "http://www.woaisiji.com/APP/Member/friendList";
    // 添加好友地址
    public final static String URL_ADD_FRIEND = "http://www.woaisiji.com/APP/Member/addha";
    // 获取图片地址
    public final static String URL_IMAGE = "http://www.woaisiji.com/APP/Public/get_img_path";
    // 上传图片地址
    public final static String URL_UPLOAD_IMAGE = "http://123.56.236.200/APP/Public/uploadPicture";
    // 修改会员信息
    public final static String URL_UPDATE_INFO = "http://123.56.236.200/APP/Member/edit";
    // 修改头像
    public final static String URL_UPDATE_INFO_TWO = "http://123.56.236.200/APP/Member/edit2";
    // 收获地址列表
    public final static String URL_GOODS_ADDRESS = "http://123.56.236.200/APP/Member/placeList";
    // 新增收获地址
    public final static String URL_ADD_HARVEST = "http://123.56.236.200/APP/Member/placeAdd";
    // 删除收获地址
    public final static String URL_DELETE_HARVEST = "http://123.56.236.200/APP/Member/placeDel";
    // 收藏关注列表
    public final static String URL_COLLECTION = "http://123.56.236.200/APP/Member/gzList";
    // 金银币明细账单
    public final static String URL_DETAIL_BILL = "http://123.56.236.200/APP/Member/billList";
    // 修改手机号码
    public final static String URL_REPLACE_PHONE = "http://123.56.236.200/APP/User/editphone";
    // 修改登录密码
    public final static String URL_REPLACE_PASSWORD = "http://123.56.236.200/APP/User/profile";
    // 司机爱情
    public final static String URL_DRIVER_SOCIAL = "http://123.56.236.200/APP/Love/index";
    // 查找好友
    public final static String URL_DRIVER_FRIEND = "http://www.woaisiji.com/APP/Member/addha";
    // 删除好友
    public final static String URL_DRIVER_DELETE_FRIEND = "http://www.woaisiji.com/App/Friend/del";

    // 司机爱情会员地址信息
    public final static String URL_DRIVER_MEMBER_ADDRESS = "http://123.56.236.200/APP/Public/get_city_address";
    // 司机健康列表
    public final static String URL_DRIVER_HEALTH = "http://123.56.236.200/APP/Love/health";
    // 所有的新闻内容详情前缀
    public final static String URL_HEALTH_DETAIL = "http://123.56.236.200/APP/Public/get_goods_detail";
    // 司机健康评论列表
    public final static String URL_HEALTH_COMMENT = "http://123.56.236.200/APP/Love/jkReply";
    // 司机健康添加评论
    public final static String URL_HEALTH_ADD_COMMENT = "http://123.56.236.200/APP/Love/jkReplyAdd";
    // 司机自驾游列表
    public final static String URL_DRIVE_TRAVEL = "http://123.56.236.200/APP/Love/travel";
    // 司机自驾游评论列表
    public final static String URL_TRAVEL_COMMENT = "http://123.56.236.200/APP/Love/travelDetail";
    // 司机自驾游添加评论
    public final static String URL_TRAVEL_ADD_COMMENT = "http://123.56.236.200/APP/Love/lyReplyAdd";
    // 司机自驾游添加赞
    public final static String URL_TRAVEL_ADD_PRAISE = "http://www.woaisiji.com/APP/Love/setyes";
    // 司机自驾游发布游记
    public final static String URL_TRAVEL_RELEASE = "http://123.56.236.200/APP/Love/travelAdd";
    // 司机资讯列表
    public final static String URL_DRIVE_INFORMATION = "http://123.56.236.200/APP/Love/information";
    // 司机资讯评论列表
    public final static String URL_INFORMATION_COMMENT = "http://123.56.236.200/APP/Love/informationDetail";
    // 商品订单列表
    public final static String URL_ORDER_FORM_ALL = "http://123.56.236.200/APP/Member/orderList";
    // 商品添加评论
    public final static String URL_ORDER_FORM_APPRAISE = "http://123.56.236.200/APP/Member/commentAdd";
    // 商品退货
    public final static String URL_ORDER_FORM_RETURN_GOOD = "http://123.56.236.200/APP/Order/ttt";
    // 取消商品订单
    public final static String URL_CANCEL_ORDER = "http://123.56.236.200/APP/Member/orderDel";
    // 物流信息
    public final static String URL_WULIU = "http://www.woaisiji.com/APP/Kdniao/index";


    // 日常签到--已签到
    public final static String URL_ALREADY_SIGN = "http://123.56.236.200/APP/Sign/index";
    // 日常签到--签到
    public final static String URL_SIGN = "http://123.56.236.200/APP/Sign/sign";
    // 司机圈个人详细信息
    public final static String URL_PERSONAL_DETAIL_INFO = "http://123.56.236.200/APP/Circle/info";
    // 添加购物车
    public final static String URL_ADD_GOOD_TO_CAR = "http://woaisiji.net/APP/Shop/add_goodcar";
    // 浏览历史列表
    public final static String URL_BROWS_HISTORY = "http://123.56.236.200/APP/Member/hisList";
    // 司机问答 问题列表
    public final static String URL_PROBLEM_INDEX = "http://123.56.236.200/APP/Problem/index";
    // 司机问答 我的回答
    public final static String URL_PROBLEM_MY_ANSWER = "http://123.56.236.200/APP/Problem/replySelf";
    // 司机问答 问题详情
    public final static String URL_PROBLEM_DETAIL = "http://123.56.236.200/APP/Problem/detail";
    // 司机问答 回答问题
    public final static String URL_PROBLEM_ADD_REPLY = "http://123.56.236.200/APP/Problem/replyAdd";
    // 司机问答 提问问题
    public final static String URL_PROBLEM_ADD_QUESTION = "http://123.56.236.200/APP/Problem/add";
    // 司机问答 问题采纳
    public final static String URL_PROBLEM_ADOPTION_ANSWER = "http://123.56.236.200/APP/Problem/replyAdopt";

    // 司机圈 说说列表
    public final static String URL_CIRCLE_INDEX = "http://123.56.236.200/APP/Circle/index";
    // 司机圈 点赞功能
    public final static String URL_CIRCLE_FAVORT = "http://123.56.236.200/APP/Circle/laud";
    // 司机圈 添加评论
    public final static String URL_CIRCLE_ADD_COMMENT = "http://123.56.236.200/APP/Circle/commentAdd";
    // 司机圈 添加收藏
    public final static String URL_CIRCLE_ADD_COLLECTION = "http://123.56.236.200/APP/Circle/collectAdd";
    // 司机圈 发说说
    public final static String URL_CIRCLE_RELEASE_DYNAMIC = "http://123.56.236.200/APP/Circle/add";
    // 司机圈 用户编辑个人资料
    public final static String URL_USER_EDIT_INFO = "http://123.56.236.200/APP/User/xuser";


    // 爱抢购
    public final static String URL_LOVE_BUY_MALL = "http://123.56.236.200/APP/Shopaqg/qianggou";
    // 正品商品搜索接口
    public final static String URL_PRODUCT_SEARCH = "http://123.56.236.200/APP/Shop/search";
    // 银币商城搜索接口
    public final static String URL_EXCHANGE_PRODUCT_SEARCH = "http://123.56.236.200/APP/Shopa/search";
    // 养护连锁搜索接口
    public final static String URL_CURING_PRODUCT_SEARCH = "http://123.56.236.200/APP/Shopyhls/search";


    // 正品商城分类
    public final static String URL_DRIVER_MALL = "http://123.56.236.200/APP/Shop/cates";
    // 推荐商品
    public final static String URL_RECOMMEND = "http://123.56.236.200/APP/Shop/index";
    // 司机商城
    public final static String URL_DEIVERSHOPPING = "http://123.56.236.200/APP/Shop/cates_goods";
    // 详情页面
    public final static String URL_DETAILS = "http://123.56.236.200/APP/shop/detail";
    // 详情图片页面
    public final static String URL_PRODUCTPICTURE = "http://123.56.236.200/APP/Public/get_cover_url";
    // 银币商城分类
    public final static String URL_EXCHANGE_CLASSIFICATION = "http://123.56.236.200/APP/Shopa/cates";
    // 银币推荐商品
    public final static String URL_EXCHANGE_RECOMMEND = "http://123.56.236.200/APP/Shopa/index";
    // 司机银币商城
    public final static String URL_EXCHANGE_DEIVERSHOPPING = "http://123.56.236.200/APP/Shopa/cates_goods";
    // 银币详情页面
    public final static String URL_EXCHANGE_DETAILS = "http://123.56.236.200/APP/shopa/detail";
    // 养护连锁商城分类
    public final static String URL_CURING_CLASSIFICATION = "http://123.56.236.200/APP/Shopyhls/cates";
    // 养护连锁推荐商品
    public final static String URL_CURING_RECOMMEND = "http://123.56.236.200/APP/Shopyhls/index";
    // 养护连锁商城
    public final static String URL_CURING_DEIVERSHOPPING = "http://123.56.236.200/APP/Shopyhls/cates_goods";
    // 养护连锁详情页面
    public final static String URL_CURING_DETAILS = "http://123.56.236.200/APP/Shopyhls/detail";
    // 添加购物车
    public final static String URL_ADDSHOPPINGCART = "http://123.56.236.200/APP/Shop/add_goodcar";
    // 添加正品商城商品收藏
    public final static String URL_ZHENGPINCOLLECTION = "http://123.56.236.200/APP/Shop/add_gz";
    // 添加购物车
    public final static String URL_DUIHUANADDSHOPPINGCART = "http://123.56.236.200/APP/Shopa/add_goodcar";
    // 添加银币商城商品收藏
    public final static String URL_DUIHUANCOLLECTION = "http://123.56.236.200/APP/Shopa/add_gz";
    // 添加购物车
    public final static String URL_YANGHUSHOPPINGCART = "http://123.56.236.200/APP/Shopyhls/add_goodcar";
    // 添加养护连锁商品收藏
    public final static String URL_YANGHUCOLLECTION = "http://123.56.236.200/APP/Shopyhls/add_gz";
    // 查看正品商城的购物车
    public final static String URL_GOOD_CAR = "http://123.56.236.200/APP/Shop/good_car";
    // 删除正品商城购物车
    public final static String URL_DELETEZHENGPINGSHOPPINGCART = "http://123.56.236.200/APP/Shop/del_good_car";
    // 查看银币商城的购物车
    public final static String URL_DUIHUANGOOD_CAR = "http://123.56.236.200/APP/Shopa/good_car";
    // 删除银币商城购物车
    public final static String URL_DELETEDUIHUANSHOPPINGCART = "http://123.56.236.200/APP/Shopa/del_good_car";
    // 查看金币商城的购物车
    public final static String URL_GOLDGOOD_CAR = "http://123.56.236.200/APP/Shopyhls/good_car";
    // 删除金币商城购物车
    public final static String URL_DELETEGOLDSHOPPINGCART = "http://123.56.236.200/APP/Shopyhls/del_good_car";

    // 首页广告
    public final static String URL_ADVERTISEMENT = "http://123.56.236.200/APP/Index/index";
    // 正品商城订单预览
    public final static String URL_ZHENGPINORDERPREVIEW = "http://123.56.236.200/APP/Shop/good_order";
    // 银币商城订单预览
    public final static String URL_DUIHUANORDERPREVIEW = "http://123.56.236.200/APP/Shopa/good_order";
    // 金币商城订单预览
    public final static String URL_GOLDORDERPREVIEW = "http://123.56.236.200/APP/Shopyhls/good_order";

    // 订单生成
    public final static String URL_ORDERGENERATION = "http://123.56.236.200/APP/Shop/add_order";
    // 银币商城 订单生成
    public final static String URL_ORDER_SLIVER_MALL = "http://123.56.236.200/APP/Shopa/add_order";
    // 金币商城 订单生成
    public final static String URL_ORDER_GOLD_MALL = "http://123.56.236.200/APP/Shopyhls/add_order";

    // 订单预付款Order advance payment
    public final static String URL_ORDERADVANCEPAYMENT = "http://123.56.236.200/APP/Shop/goods_success";
    // 金币订单预付款Order advance payment
    public final static String URL_GOLDADVANCEPAYMENT = "http://123.56.236.200/APP/Shopa/goods_success";
    // 银币预付款Order advance payment
    public final static String URL_SLIVERADVANCEPAYMENT = "http://123.56.236.200/APP/Shopyhls/goods_success";

    // 查看订单详情
    public final static String URL_ORDER_DETAIL = "http://123.56.236.200/APP/Order/order_detail";

    // 支付完成，回调服务器接口
    public final static String URL_PAY_END = "http://www.woaisiji.com/Member/Pay/jbpay";
    public final static String URL_CASH_PAY_END = "http://www.woaisiji.com/Member/Pay/xjpay";
    // 微信支付，回调服务器接口
    public final static String URL_WX_PAY_END = "http://www.woaisiji.com/member/wpay/wx";
    // 商品详情页
    public final static String COMMODITY_DETAILS = SERVER_ROOT + "/APP/Shop/goods_detail/id/1561";

}
