package com.ipd.taixiuser.bean;

import java.util.List;

public class WalletBean {

    /**
     * balance : 98704
     * consumption : [{"id":1,"user_id":3,"title":"产品购买","expense":-17680,"ctime":"2017-10-25 11:24:58"},{"id":2,"user_id":3,"title":"产品购买","expense":-2000,"ctime":"2018-02-18 05:11:38"},{"id":3,"user_id":3,"title":"产品购买","expense":-1000,"ctime":"2018-06-13 22:58:18"}]
     */

    public String balance;
    public List<ConsumeBean> consumption;

}
