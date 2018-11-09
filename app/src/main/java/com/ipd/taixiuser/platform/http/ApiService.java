package com.ipd.taixiuser.platform.http;


import com.ipd.taixiuser.bean.ActionMessageBean;
import com.ipd.taixiuser.bean.ApplyWithdrawBean;
import com.ipd.taixiuser.bean.BankCardBean;
import com.ipd.taixiuser.bean.BaseResult;
import com.ipd.taixiuser.bean.BusinessDetailBean;
import com.ipd.taixiuser.bean.BusinessSchoolBean;
import com.ipd.taixiuser.bean.CollectBusinessSchoolBean;
import com.ipd.taixiuser.bean.CustomerBean;
import com.ipd.taixiuser.bean.CustomerTransferRecordBean;
import com.ipd.taixiuser.bean.EarningParentBean;
import com.ipd.taixiuser.bean.ExplainHtmlBean;
import com.ipd.taixiuser.bean.ForgetPwdBean;
import com.ipd.taixiuser.bean.HomeBean;
import com.ipd.taixiuser.bean.ListResult;
import com.ipd.taixiuser.bean.LoginBean;
import com.ipd.taixiuser.bean.MatterBean;
import com.ipd.taixiuser.bean.MatterDetailBean;
import com.ipd.taixiuser.bean.ProductBean;
import com.ipd.taixiuser.bean.QuestionBean;
import com.ipd.taixiuser.bean.RegisterBean;
import com.ipd.taixiuser.bean.StockRecordParentBean;
import com.ipd.taixiuser.bean.SystemMessageBean;
import com.ipd.taixiuser.bean.UploadResultBean;
import com.ipd.taixiuser.bean.UserInfoBean;
import com.ipd.taixiuser.bean.WalletBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by jumpbox on 2017/7/24.
 */

public interface ApiService {

    /**
     * account
     */
    @FormUrlEncoded
    @POST(HttpUrl.LOGIN)
    Observable<BaseResult<LoginBean>> login(@Field("phone") String phone,
                                            @Field("password") String password);

    @FormUrlEncoded
    @POST(HttpUrl.REGISTER)
    Observable<BaseResult<RegisterBean>> register(@Field("phone") String phone,
                                                  @Field("password") String password,
                                                  @Field("mobile_code") String mobile_code);

    @FormUrlEncoded
    @POST(HttpUrl.SMS_CODE)
    Observable<BaseResult<String>> smsCode(@Field("phone") String phone,
                                           @Field("type") String type);

    @FormUrlEncoded
    @POST(HttpUrl.FORGET_PASSWORD)
    Observable<BaseResult<ForgetPwdBean>> forgetPassword(@Field("phone") String phone,
                                                         @Field("password") String password,
                                                         @Field("confirm") String confirm,
                                                         @Field("mobile_code") String mobile_code);


    /**
     * home
     */
    @FormUrlEncoded
    @POST(HttpUrl.HOME)
    Observable<BaseResult<HomeBean>> home(@Field("user_id") String user_id);

    @POST(HttpUrl.QUESTION)
    Observable<BaseResult<List<QuestionBean>>> question();

    @FormUrlEncoded
    @POST(HttpUrl.QUESTION_DETAIL)
    Observable<BaseResult<QuestionBean>> questionDetail(@Field("question_id") int question_id);

    @FormUrlEncoded
    @POST(HttpUrl.SYSTEM_MESSAGE)
    Observable<BaseResult<ListResult<SystemMessageBean>>> systemMessage(@Field("user_id") String user_id,
                                                                        @Field("page") int page,
                                                                        @Field("count") int count);

    @FormUrlEncoded
    @POST(HttpUrl.ACTION_MESSAGE)
    Observable<BaseResult<ListResult<ActionMessageBean>>> actionMessage(@Field("statue") int statue,
                                                                        @Field("user_id") String user_id,
                                                                        @Field("page") int page,
                                                                        @Field("count") int count);

    /**
     * matter
     */
    @FormUrlEncoded
    @POST(HttpUrl.MATTER_LIST)
    Observable<BaseResult<ListResult<MatterBean>>> matterList(@Field("page") int page,
                                                              @Field("count") int count);

    @FormUrlEncoded
    @POST(HttpUrl.MATTER_DETAIL)
    Observable<BaseResult<MatterDetailBean>> matterDetail(@Field("material_id") int material_id);

    /**
     * businessSchool
     */
    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_LIST)
    Observable<BaseResult<List<BusinessSchoolBean>>> businessSchoolList(@Field("page") int page,
                                                                        @Field("count") int count);


    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_DETAIL)
    Observable<BaseResult<BusinessDetailBean>> businessSchoolDetail(@Field("user_id") String user_id,
                                                                    @Field("business_id") int business_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_PRAISE_OR_COLLECT)
    Observable<BaseResult<BusinessDetailBean>> businessPraiseOrCollect(@Field("user_id") String user_id,
                                                                       @Field("business_id") int business_id,
                                                                       @Field("type") int type);


    /**
     * manage
     */
    @FormUrlEncoded
    @POST(HttpUrl.MINE_CUSTOMER)
    Observable<BaseResult<List<CustomerBean>>> mineCustomer(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.NEW_CUSTOMER)
    Observable<BaseResult<CustomerBean>> newCustomer(@Field("phone") String phone,
                                                     @Field("username") String username,
                                                     @Field("weixin") String weixin,
                                                     @Field("proxy") String proxy,
                                                     @Field("remark") String remark,
                                                     @Field("password") String password,
                                                     @Field("area") String area,
                                                     @Field("pos_id") String pos_id);


    @FormUrlEncoded
    @POST(HttpUrl.STORE_LIST)
    Observable<BaseResult<List<ProductBean>>> storeList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CUSTOMER_INFO)
    Observable<BaseResult<CustomerBean>> customerInfo(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST(HttpUrl.EARNINGS_LIST)
    Observable<BaseResult<EarningParentBean>> earningsList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(HttpUrl.EXPLAIN_HTML)
    Observable<BaseResult<ExplainHtmlBean>> explainHtml(@Field("statue") String statue);


    @FormUrlEncoded
    @POST(HttpUrl.STOCK_RECORD_LIST)
    Observable<BaseResult<StockRecordParentBean>> stockRecordList(@Field("user_id") String user_id);


    /**
     * mine
     */
    @FormUrlEncoded
    @POST(HttpUrl.MINE)
    Observable<BaseResult<UserInfoBean>> userInfo(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.UPDATE_USER_INFO)
    Observable<BaseResult<UserInfoBean>> updateUserInfo(@Field("user_id") String user_id,
                                                        @Field("avatar") String avatar,
                                                        @Field("username") String username,
                                                        @Field("area") String area,
                                                        @Field("address") String address,
                                                        @Field("weixin") String weixin);

    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_LIST)
    Observable<BaseResult<List<CollectBusinessSchoolBean>>> collectList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.MY_WALLET)
    Observable<BaseResult<WalletBean>> myWallet(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.APPLY_WITHDRAW)
    Observable<BaseResult<ApplyWithdrawBean>> applyWithdraw(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.BANK_LIST)
    Observable<BaseResult<List<BankCardBean>>> bankList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CUSTOMER_TRANSFER_RECORD)
    Observable<BaseResult<List<CustomerTransferRecordBean>>> customerTransferRecord(@Field("user_id") String user_id);


    //tools
    @Multipart
    @POST(HttpUrl.UPLOAD_PIC)
    Observable<UploadResultBean> uploadPicture(@PartMap Map<String, RequestBody> map);


}
