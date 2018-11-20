package com.ipd.taixiuser.utils;

import android.content.Context;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taixiuser.bean.WechatBean;
import com.ipd.taixiuser.platform.global.GlobalApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by jumpbox on 16/6/2.
 */
public class WeChatUtils {
    private static WeChatUtils weChatUtils;
    private static IWXAPI api;

    public static WeChatUtils getInstance(Context context) {
        if (weChatUtils == null) {
            weChatUtils = new WeChatUtils();
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, null);
        }
        return weChatUtils;
    }


    public void startPay(WechatBean wechatBean) {
        if (wechatBean == null) return;
        if (!api.isWXAppInstalled()) {
            ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), "您还没有安装微信");
            return;
        }
        PayReq req = genPayParam(wechatBean);
        api.sendReq(req);
    }

    private PayReq genPayParam(WechatBean wechatInfo) {
        PayReq req = new PayReq();
        req.appId = wechatInfo.appid;
        req.partnerId = wechatInfo.partnerid;
        req.prepayId = wechatInfo.prepayid;
        req.nonceStr = wechatInfo.noncestr;
        req.timeStamp = wechatInfo.timestamp;
        req.packageValue = wechatInfo.packageX;
        req.sign = wechatInfo.sign;
        return req;
    }


    public void release() {
        api = null;
        weChatUtils = null;
    }

}
