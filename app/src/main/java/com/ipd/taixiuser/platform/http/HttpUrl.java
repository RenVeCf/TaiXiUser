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

    //manage
    String MINE_CUSTOMER = "manage/mycustomer";
    String NEW_CUSTOMER = "manage/mycustomer/add";
    String CUSTOMER_INFO = "manage/mycustomer/info";


    //mine
    String MINE = "my";


}
