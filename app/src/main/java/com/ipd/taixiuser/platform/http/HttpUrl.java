package com.ipd.taixiuser.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String SERVER_URL = "http://121.199.8.244:8555/api/";
    String IMAGE_URL = "http://121.199.8.244:8555/";

    //account
    String SMS_CODE = "mobileCode";
    String REGISTER = "reg";
    String LOGIN = "login";
    String FORGET_PASSWORD = "forget";


    //home
    String HOME = "home";
    String QUESTION = "home/question";
    String QUESTION_DETAIL = "home/questioninfo";
    String SYSTEM_MESSAGE = "home/sysnews";
    String ACTION_MESSAGE = "home/dynamic";

    //matter
    String MATTER_LIST = "material";
    String MATTER_DETAIL = "material/materialinfo";

    //businessSchool
    String BUSINESS_LIST = "business/list";
    String BUSINESS_DETAIL = "business/info";
    String BUSINESS_PRAISE_OR_COLLECT = "business/collectpraise";


    //manage
    String MINE_CUSTOMER = "manage/mycustomer";
    String NEW_CUSTOMER = "manage/mycustomer/add";
    String CUSTOMER_INFO = "manage/mycustomer/info";

    String STORE_LIST = "manage/purchase";
    String EARNINGS_LIST = "manage/profit";
    String STOCK_RECORD_LIST = "manage/warehouselog";


    //mine
    String MINE = "my";
    String UPDATE_USER_INFO = "my/userinfo";
    String COLLECT_LIST = "my/collect";
    String MY_WALLET = "my/wallet";
    String APPLY_WITHDRAW = "my/apply/bankshow";
    String BANK_LIST = "my/bank/card";
    String CUSTOMER_TRANSFER_RECORD = "my/customer/transfer";


    String EXPLAIN_HTML = "manage/total/generation";


    //tools
    String UPLOAD_PIC = "my/upload";


}
