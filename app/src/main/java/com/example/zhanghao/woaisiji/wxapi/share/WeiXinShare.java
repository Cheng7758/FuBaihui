package com.example.zhanghao.woaisiji.wxapi.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;


import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/3/24.
 */

public class WeiXinShare extends ShareHelper {
    private String WEIXINAPPID = "wxc1184669ab904cdd";

    public WeiXinShare(ShereFactory jsAdapter) {
        super(jsAdapter);
    }

    @Override
    public void Share() {

        final IWXAPI iwxapi = WXAPIFactory.createWXAPI(jsAdapter.getContext(), WEIXINAPPID);
        if (iwxapi.registerApp(WEIXINAPPID)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    WXImageObject wximg = new WXImageObject();

                    wximg.imageData = bitmapBytes(jsAdapter.getImgBitmap(),true);

                    WXMediaMessage msg = new WXMediaMessage(wximg);
//                    WXWebpageObject webpage = new WXWebpageObject();
//                    webpage.webpageUrl = jsAdapter.getUrl();//.getString("url");
//                    WXMediaMessage msg = new WXMediaMessage(webpage);
//                    msg.title = jsAdapter.getTitle();//.getString("title");
//                    msg.description = jsAdapter.getDesc();//jsondict.getString("desc");
//                    msg.thumbData = (bitmapBytes(new HttpConnctionHelper(jsAdapter.getImgUrl(), new Handler()).getimage(),true));

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = Md5(Math.random() + "");
                    req.message = msg;


                    switch (jsAdapter.getAction()) {
                        case ShereFactory.ShereType.WEIXIN:
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            break;
                        case ShereFactory.ShereType.WEIXINZONE:
                            req.scene = SendMessageToWX.Req.WXSceneTimeline;
                            break;
                        default:
                            req.scene = SendMessageToWX.Req.WXSceneFavorite;
                            break;
                    }
                    iwxapi.sendReq(req);
                  //  jsAdapter.RssAppShareCallBack(new RssBundle().onComplete().getBundle());
                }
            }).start();
        }
    }
    public static String Md5(String pwd) {
        // 用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] bitmapBytes(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 10,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }
}
