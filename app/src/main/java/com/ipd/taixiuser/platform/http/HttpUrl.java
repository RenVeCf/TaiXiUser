package com.ipd.taixiuser.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String BASE_URL = "http://www.txswshop.com/";
    String SERVER_URL = BASE_URL+"api/";
    String VIDEO_URL = BASE_URL;
    String IMAGE_URL = BASE_URL;


    String HTML_REG = SERVER_URL + "reg/index?invitation=";

    //account
    String SMS_CODE = "mobileCode";
    String REGISTER = "reg";
    String LOGIN = "login";
    String THIRD_LOGIN = "other/login";
    String FORGET_PASSWORD = "forget";
    String BINDING_PHONE = "other/bind/phone";


    //home
    String HOME = "home";
    String QUESTION = "home/question";
    String QUESTION_DETAIL = "home/questioninfo";
    String SYSTEM_MESSAGE = "home/sysnews";
    String ACTION_MESSAGE = "home/dynamic";

    //matter
    String MATTER_LIST = "material";
    String MATTER_SEARCH = "material/materialsearch";
    String MATTER_DETAIL = "material/materialinfo";

    //businessSchool
    String BUSINESS_CATEGORY = "business/list";
    String BUSINESS_TABS = "business/second";
    String BUSINESS_LIST = "business/info";
    String BUSINESS_DIRECTORY = "business/primary/info";
    String BUSINESS_TALK = "comment/list";
    String BUSINESS_NOTE = "notes/list";
    String BUSINESS_TALK_REPLY = "comment/reply";
    String BUSINESS_NOTE_ADD = "notes/add";
    String BUSINESS_NOTE_EDIT = "notes/update";
    String BUSINESS_NOTE_DELETE = "notes/del";
    String BUSINESS_DIRECTORY_DETAIL = "business/primary/info";
    String BUSINESS_PRAISE_OR_COLLECT = "business/collectpraise";
    String BUSINESS_MY_CERT = "business/second";


    //manage
    String MINE_CUSTOMER = "manage/mycustomer";
    String NEW_CUSTOMER = "manage/mycustomer/add";
    String EDIT_CUSTOMER = "manage/mycustomer/update";
    String CUSTOMER_INFO = "manage/mycustomer/info";
    String TEAM_STRUCT = "manage/myteam/structure";
    String MINE_TEAM_LIST = "manage/myteam/info";
    String TRANSFER_CUSTOMER_AUTH = "manage/transfer";
    String ACCEPT_USER_INFO = "manage/user/info";
    String CONFIRM_TRANSFER = "manage/transfer/customer";
    String MY_AUTH = "manage/my/auth";
    String PROMOTE = "manage/my/authorization";

    String STORE_LIST = "manage/purchase";
    String EARNINGS_LIST = "manage/profit";
    String STOCK_RECORD_LIST = "manage/warehouselog";
    String MINE_TEAM = "manage/myteam";
    String RETAIL = "manage/retail";
    String PRODUCT_LIST = "goods/list";
    String PRODUCT_DETAIL = "manage/purchase/info";

    String REPLENISH_PRODUCT_LIST = "manage/replenishment/list";
    String REPLENISH = "manage/replenishment";

    String MOVE_STOCK_PRODUCT = "manage/shifting/silo";
    String MOVE_STOCK_CUSTOMER_INFO = "manage/shift";
    String CONFIRM_MOVE_STOCK = "manage/shiftsilo";
    String MOVE_STOCK_HISTORY = "manage/shiftsilolog";


    String FACTORY_SHIP = "factory/generation";
    String EXPRESS_FEE = "factory/generation/freight";

    String BALANCE_PAY = "pay/balance";
    String ALIPAY = "pay/alipay";
    String WECHAT_PAY = "pay/wcpay";
    String OF_THE_PUBLIC_PAY = "pay/order";

    //order
    String ORDER_LIST = "factory/deliver/goods";
    String ORDER_DETAIL = "factory/deliver/goods/info";
    String CANCEL_OR_DELETE_ORDER = "factory/del/deliver/goods";
    String EXPRESS_INFO = "factory/look/logistics";
    String SALE_AFTER = "apply/sale";
    String REQUEST_SALE_AFTER = "factory/submission";


    //mine
    String MINE = "my";
    String UPDATE_USER_INFO = "my/userinfo";
    String COLLECT_LIST = "my/collect";
    String MY_WALLET = "my/wallet";
    String APPLY_WITHDRAW = "my/apply/bankshow";
    String BANK_LIST = "my/bank/card";
    String CUSTOMER_TRANSFER_RECORD = "my/customer/transfer";
    String ACCEPT_CUSTOMER_TRANSFER = "my/customer/accept/transfer";
    String DENY_CUSTOMER_TRANSFER = "my/customer/refuse/transfer";
    String NEW_BANK = "my/add/bank/card";
    String CONFIRM_WITHDRAW = "my/confirm";
    String WITHDRAW_PROGRESS = "my/bankshowlog";
    String WITHDRAW_DETAIL = "my/bankshow/info";


    String EXPLAIN_HTML = "manage/total/generation";
    String MINE_HTML = "about";


    //tools
    String UPLOAD_PIC = "my/upload";


}
