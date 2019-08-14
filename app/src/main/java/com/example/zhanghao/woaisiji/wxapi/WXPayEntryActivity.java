package com.example.zhanghao.woaisiji.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.PaySuccessActivity;
import com.example.zhanghao.woaisiji.activity.PaymentMethodActivity;
import com.example.zhanghao.woaisiji.activity.RechargeGoldIntegralActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespData;
import com.example.zhanghao.woaisiji.utils.FunctionUtils;
import com.example.zhanghao.woaisiji.utils.PublicActivityList;
import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
    private IWXAPI api;
    
    /**
	 * 支付回调
	 */
	private static final int PAY_CALLBACK_REQUESTCODE = 101;
	/**
	 * 支付类型
	 */
	private String type;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 支付费用
	 */
	private String fee;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		PublicActivityList.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        String data = getIntent().getStringExtra("_wxapi_payresp_extdata");
        String[] str = data.split("#");
        type = str[0];
        orderId = str[1];
        fee = str[2];
//		Toast.makeText(WXPayEntryActivity.this,fee, Toast.LENGTH_SHORT).show();
//		type = "1";
//		orderId = "1481085333";
//		fee = "0.01";
    	api = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		// 支付结果回调...
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){//支付成功
				wxPayEnd();
			}else{
				Toast.makeText(WXPayEntryActivity.this,"支付失败", Toast.LENGTH_SHORT).show();
				WXPayEntryActivity.this.finish();
			}
		}else{
			WXPayEntryActivity.this.finish();
		}
	}

	private void wxPayEnd() {
		FunctionUtils.paymentEndCallBack(type , orderId,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (TextUtils.isEmpty(response))
					return;
				Gson gson = new Gson();
				RespData respConversion = gson.fromJson(response,RespData.class);
				if (respConversion.getCode()==200){
					Intent intent = new Intent(WXPayEntryActivity.this, PaySuccessActivity.class);
					intent.putExtra("order_num", orderId);
					intent.putExtra("good_price", fee);
					startActivity(intent);
					finish();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
	}


}