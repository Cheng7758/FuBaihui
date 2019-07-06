package com.example.zhanghao.woaisiji.wxapi;

/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午10:12:59<br>
 * 版本 :	[v1.0]<br>
 * 类描述 : 微信支付所需参数及配置<br>
 */
public class WeChatConstans {
	
	/** 应用从官方网站申请到的合法appId */
    public static final String APP_ID = "wxc1184669ab904cdd";
	/** 微信开放平台和商户约定的密钥 */
    public static final String APP_SECRET = "52094c067431f98d44b9db2b9bb806ed";
    /** 商家向财付通申请的商家id */
    public static final String MCH_ID = "1412811402";

    /** 微信公众平台商户模块和商户约定的密钥 */
    public static final String PARTNER_ID = "woaisiji20160128woaisiji20160128";

    /** 微信统一下单接口 */
	public static final String WECHAT_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /** 回调接口 */
    public static final String NOTIFY_URL = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php";


}
