package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

public class WechatBean {

    /**
     * appid : wx5590956a456a3796
     * noncestr : fqnavmdnxejr48biblg83o7pb28citqz
     * package : Sign=WXPay
     * partnerid : 1510056091
     * prepayid : wx201110382252910de043bf881437107413
     * timestamp : 1542683437
     * sign : 2FA2C2ECB1204B4006F350A3EE0CF4D3
     * prepay_id : wx201110382252910de043bf881437107413
     */

    public String appid;
    public String noncestr;
    @SerializedName("package")
    public String packageX;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;
    public String prepay_id;

}
