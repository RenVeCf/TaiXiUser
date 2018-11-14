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
    String MINE_TEAM_LIST = "manage/myteam/info";
    String TRANSFER_CUSTOMER_AUTH = "manage/transfer";
    String ACCEPT_USER_INFO = "manage/user/info";
    String CONFIRM_TRANSFER = "manage/transfer/customer";
    String MY_AUTH = "manage/my/auth";

    String STORE_LIST = "manage/purchase";
    String EARNINGS_LIST = "manage/profit";
    String STOCK_RECORD_LIST = "manage/warehouselog";
    String MINE_TEAM = "manage/myteam";
    String RETAIL = "manage/retail";
    String PRODUCT_LIST = "goods/list";
    String PRODUCT_DETAIL = "manage/purchase/info";

    String REPLENISH_PRODUCT_LIST = "manage/replenishment/list";
    String REPLENISH = "manage/replenishment";


    String FACTORY_SHIP = "factory/generation";
    String EXPRESS_FEE = "factory/generation/freight";

    String BALANCE_PAY = "pay/balance";

    //order
    String ORDER_LIST = "factory/deliver/goods";
    String ORDER_DETAIL = "factory/deliver/goods/info";
    String CANCEL_OR_DELETE_ORDER = "factory/del/deliver/goods";
    String EXPRESS_INFO = "factory/look/logistics";



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
