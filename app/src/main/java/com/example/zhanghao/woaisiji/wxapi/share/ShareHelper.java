package com.example.zhanghao.woaisiji.wxapi.share;

import android.widget.Toast;


/**
 * Created by Administrator on 2018/3/24.
 */

public class ShareHelper {
    public ShereFactory jsAdapter;

    public ShareHelper(ShereFactory jsAdapter) {

       this.jsAdapter = jsAdapter;
    }

    public void Share() {

    }

    public static ShareHelper facotry(ShereFactory jsAdapter) {
        switch (jsAdapter.getAction()) {
            case ShereFactory.ShereType.WEIXIN:
            case ShereFactory.ShereType.WEIXINZONE:
                return new WeiXinShare(jsAdapter);
//            case ShereFactory.ShereType.QQ:
//            case ShereFactory.ShereType.QQZONE:
////                return new TententShare(jsAdapter);
//            case ShereFactory.ShereType.WEIBO:
//                return new WeiBoShare(jsAdapter);
            default:
                Toast.makeText(jsAdapter.getContext(), "没有分享目标", Toast.LENGTH_LONG).show();
               return null;
        }
    }
}
