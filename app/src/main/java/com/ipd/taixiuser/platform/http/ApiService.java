package com.ipd.taixiuser.platform.http;


import com.ipd.taixiuser.bean.ActionMessageBean;
import com.ipd.taixiuser.bean.ApplyWithdrawBean;
import com.ipd.taixiuser.bean.BankCardBean;
import com.ipd.taixiuser.bean.BaseResult;
import com.ipd.taixiuser.bean.BusinessDetailBean;
import com.ipd.taixiuser.bean.BusinessDirectoryBean;
import com.ipd.taixiuser.bean.BusinessNoteBean;
import com.ipd.taixiuser.bean.BusinessSchoolBean;
import com.ipd.taixiuser.bean.BusinessSchoolCategoryBean;
import com.ipd.taixiuser.bean.BusinessSchoolTabBean;
import com.ipd.taixiuser.bean.BusinessTalkBean;
import com.ipd.taixiuser.bean.CertBean;
import com.ipd.taixiuser.bean.CollectMatterBean;
import com.ipd.taixiuser.bean.CustomerBean;
import com.ipd.taixiuser.bean.CustomerTransferRecordBean;
import com.ipd.taixiuser.bean.EarningParentBean;
import com.ipd.taixiuser.bean.ExplainHtmlBean;
import com.ipd.taixiuser.bean.ExpressFeeBean;
import com.ipd.taixiuser.bean.FactoryShipBean;
import com.ipd.taixiuser.bean.ForgetPwdBean;
import com.ipd.taixiuser.bean.HomeBean;
import com.ipd.taixiuser.bean.ListResult;
import com.ipd.taixiuser.bean.LoginBean;
import com.ipd.taixiuser.bean.MatterBean;
import com.ipd.taixiuser.bean.MatterDetailBean;
import com.ipd.taixiuser.bean.MatterResultBean;
import com.ipd.taixiuser.bean.MoveStockBean;
import com.ipd.taixiuser.bean.MoveStockHistoryBean;
import com.ipd.taixiuser.bean.MoveStockInfoBean;
import com.ipd.taixiuser.bean.OfTheBankBean;
import com.ipd.taixiuser.bean.OrderBean;
import com.ipd.taixiuser.bean.OrderDetailBean;
import com.ipd.taixiuser.bean.ProductBean;
import com.ipd.taixiuser.bean.ProductDetailBean;
import com.ipd.taixiuser.bean.QuestionBean;
import com.ipd.taixiuser.bean.RegisterBean;
import com.ipd.taixiuser.bean.ReplenishBean;
import com.ipd.taixiuser.bean.SaleAfterBean;
import com.ipd.taixiuser.bean.StockRecordParentBean;
import com.ipd.taixiuser.bean.SystemMessageBean;
import com.ipd.taixiuser.bean.TeamGroupBean;
import com.ipd.taixiuser.bean.TeamStructBean;
import com.ipd.taixiuser.bean.UploadResultBean;
import com.ipd.taixiuser.bean.UserInfoBean;
import com.ipd.taixiuser.bean.WalletBean;
import com.ipd.taixiuser.bean.WebBean;
import com.ipd.taixiuser.bean.WechatBean;
import com.ipd.taixiuser.bean.WithdrawDetailBean;
import com.ipd.taixiuser.bean.WithdrawProgressBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
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
    @POST(HttpUrl.THIRD_LOGIN)
    Observable<BaseResult<LoginBean>> thirdLogin(@Field("openid") String openid,
                                                 @Field("statue") String statue);


    @FormUrlEncoded
    @POST(HttpUrl.BINDING_PHONE)
    Observable<BaseResult<LoginBean>> bindingPhone(@Field("phone") String phone,
                                                   @Field("openid") String openid,
                                                   @Field("statue") int statue);


    @FormUrlEncoded
    @POST(HttpUrl.REGISTER)
    Observable<BaseResult<RegisterBean>> register(@Field("phone") String phone,
                                                  @Field("password") String password,
                                                  @Field("mobile_code") String mobile_code,
                                                  @Field("Invitationcode") String Invitationcode);

    @FormUrlEncoded
    @POST(HttpUrl.SMS_CODE)
    Observable<BaseResult<LoginBean>> smsCode(@Field("phone") String phone,
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
    Observable<BaseResult<MatterResultBean>> matterList(@Field("page") int page,
                                                        @Field("count") int count);

    @FormUrlEncoded
    @POST(HttpUrl.MATTER_LIST)
    Observable<BaseResult<MatterResultBean>> matterList(@Field("page") int page,
                                                        @Field("count") int count,
                                                        @Field("type_id") int type_id);

    @FormUrlEncoded
    @POST(HttpUrl.MATTER_SEARCH)
    Observable<BaseResult<ListResult<MatterBean>>> matterSearch(@Field("search") String search,
                                                                @Field("page") int page,
                                                                @Field("count") int count);

    @FormUrlEncoded
    @POST(HttpUrl.MATTER_DETAIL)
    Observable<BaseResult<MatterDetailBean>> matterDetail(@Field("user_id") String user_id,
                                                          @Field("material_id") int material_id);

    /**
     * businessSchool
     */
    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_CATEGORY)
    Observable<BaseResult<List<BusinessSchoolCategoryBean>>> businessSchoolCategory(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_TABS)
    Observable<BaseResult<List<BusinessSchoolTabBean>>> businessSchoolTabs(@Field("type_id") int type_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_LIST)
    Observable<BaseResult<ListResult<BusinessSchoolBean>>> businessSchoolList(@Field("page") int page,
                                                                              @Field("type_id") int type_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_MY_CERT)
    Observable<BaseResult<CertBean>> businessMyCert(@Field("user_id") String user_id,
                                                    @Field("type_id") int type_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_DIRECTORY)
    Observable<BaseResult<List<BusinessDirectoryBean>>> businessDirectory(@Field("primary_directory_id") int primary_directory_id);


    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_TALK)
    Observable<BaseResult<List<BusinessTalkBean>>> businessTalk(@Field("primary_directory_id") int primary_directory_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_NOTE)
    Observable<BaseResult<List<BusinessNoteBean>>> businessNote(@Field("user_id") String user_id,
                                                                @Field("primary_directory_id") int primary_directory_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_TALK_REPLY)
    Observable<BaseResult<BusinessTalkBean>> businessTalkReply(@Field("user_id") String user_id,
                                                               @Field("primary_directory_id") int primary_directory_id,
                                                               @Field("content") String content);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_TALK_REPLY)
    Observable<BaseResult<BusinessTalkBean>> businessTalkReplySecond(@Field("user_id") String user_id,
                                                                     @Field("pid") int pid,
                                                                     @Field("content") String content);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_NOTE_ADD)
    Observable<BaseResult<BusinessTalkBean>> businessNoteAdd(@Field("user_id") String user_id,
                                                             @Field("primary_directory_id") int primary_directory_id,
                                                             @Field("content") String content);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_NOTE_EDIT)
    Observable<BaseResult<BusinessTalkBean>> businessNoteEdit(@Field("notes_id") int notes_id,
                                                              @Field("content") String content);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_NOTE_DELETE)
    Observable<BaseResult<BusinessTalkBean>> businessNoteDelete(@Field("notes_id") int notes_id);


    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_DIRECTORY_DETAIL)
    Observable<BaseResult<BusinessDetailBean>> businessSchoolDirectoryDetail(@Field("user_id") String user_id,
                                                                             @Field("primary_directory_id") int primary_directory_id);

    @FormUrlEncoded
    @POST(HttpUrl.BUSINESS_PRAISE_OR_COLLECT)
    Observable<BaseResult<String>> businessPraiseOrCollect(@Field("user_id") String user_id,
                                                                     @Field("material_id") int material_id,
                                                                     @Field("type") int type);


    /**
     * manage
     */
    @FormUrlEncoded
    @POST(HttpUrl.MINE_CUSTOMER)
    Observable<BaseResult<List<CustomerBean>>> mineCustomer(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.TEAM_STRUCT)
    Observable<BaseResult<TeamStructBean>> teamStruct(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.NEW_CUSTOMER)
    Observable<BaseResult<CustomerBean>> newCustomer(@Field("phone") String phone,
                                                     @Field("username") String username,
                                                     @Field("weixin") String weixin,
                                                     @Field("proxy") String proxy,
                                                     @Field("remark") String remark,
                                                     @Field("password") String password,
                                                     @Field("area") String area,
                                                     @Field("address") String address,
                                                     @Field("pos_id") String pos_id);

    @FormUrlEncoded
    @POST(HttpUrl.EDIT_CUSTOMER)
    Observable<BaseResult<CustomerBean>> editCustomer(@Field("proxy") String proxy,
                                                      @Field("remark") String remark,
                                                      @Field("area") String area,
                                                      @Field("address") String address,
                                                      @Field("user_id") int user_id);


    @FormUrlEncoded
    @POST(HttpUrl.STORE_LIST)
    Observable<BaseResult<List<ProductBean>>> storeList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CUSTOMER_INFO)
    Observable<BaseResult<CustomerBean>> customerInfo(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST(HttpUrl.EARNINGS_LIST)
    Observable<BaseResult<EarningParentBean>> earningsList(@Field("user_id") String user_id,
                                                           @Field("page") int page);


    @FormUrlEncoded
    @POST(HttpUrl.EXPLAIN_HTML)
    Observable<BaseResult<ExplainHtmlBean>> explainHtml(@Field("statue") String statue);


    @FormUrlEncoded
    @POST(HttpUrl.STOCK_RECORD_LIST)
    Observable<BaseResult<StockRecordParentBean>> stockRecordList(@Field("user_id") String user_id,
                                                                  @Field("page") int page);

    @FormUrlEncoded
    @POST(HttpUrl.MINE_TEAM)
    Observable<BaseResult<List<TeamGroupBean>>> mineTeam(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.MINE_TEAM_LIST)
    Observable<BaseResult<List<CustomerBean>>> mineTeamList(@Field("user_id") String user_id,
                                                            @Field("proxy") int proxy);

    @FormUrlEncoded
    @POST(HttpUrl.TRANSFER_CUSTOMER_AUTH)
    Observable<BaseResult<CustomerBean>> transferCustomerAuth(@Field("user_id") String user_id,
                                                              @Field("phone") String phone);

    @FormUrlEncoded
    @POST(HttpUrl.ACCEPT_USER_INFO)
    Observable<BaseResult<CustomerBean>> acceptUserInfo(@Field("user_id") String user_id,
                                                        @Field("phone") String phone);

    @FormUrlEncoded
    @POST(HttpUrl.CONFIRM_TRANSFER)
    Observable<BaseResult<CustomerBean>> confirmTransfer(@Field("accept") int accept,
                                                         @Field("transfer") String transfer,
                                                         @Field("customer") int customer);

    @FormUrlEncoded
    @POST(HttpUrl.MY_AUTH)
    Observable<BaseResult<WebBean>> myAuth(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.PROMOTE)
    Observable<BaseResult<UserInfoBean>> promote(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.RETAIL)
    Observable<BaseResult<ProductBean>> retail(@Field("user_id") String user_id,
                                               @Field("consignee") String consignee,
                                               @Field("phone") String phone,
                                               @Field("city") String city,
                                               @Field("address") String address,
                                               @Field("style") String style,
                                               @Field("logistics") String logistics,
                                               @Field("trackingnumber") String trackingnumber,
                                               @Field("goods") String goods);

    @FormUrlEncoded
    @POST(HttpUrl.PRODUCT_LIST)
    Observable<BaseResult<List<ProductBean>>> productList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.REPLENISH_PRODUCT_LIST)
    Observable<BaseResult<ReplenishBean>> replenishProductList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.REPLENISH)
    Observable<BaseResult<ProductDetailBean>> replenish(@Field("user_id") String user_id,
                                                        @Field("goods_id") int goods_id,
                                                        @Field("fox") int fox);

    @FormUrlEncoded
    @POST(HttpUrl.PRODUCT_DETAIL)
    Observable<BaseResult<ProductDetailBean>> productDetail(@Field("user_id") String user_id,
                                                            @Field("goods_id") int goods_id);


    @FormUrlEncoded
    @POST(HttpUrl.FACTORY_SHIP)
    Observable<BaseResult<FactoryShipBean>> factoryShip(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.EXPRESS_FEE)
    Observable<BaseResult<ExpressFeeBean>> expressFee(@Field("user_id") String user_id,
                                                      @Field("fox") int fox,
                                                      @Field("area") String area);

    @FormUrlEncoded
    @POST(HttpUrl.MOVE_STOCK_PRODUCT)
    Observable<BaseResult<List<MoveStockBean>>> moveStockProduct(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.MOVE_STOCK_CUSTOMER_INFO)
    Observable<BaseResult<MoveStockInfoBean>> moveStockCustomerInfo(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CONFIRM_MOVE_STOCK)
    Observable<BaseResult<CustomerBean>> confirmMoveStock(@Field("user_id") String user_id,
                                                          @Field("goods_id") int goods_id,
                                                          @Field("receiver_id") int receiver_id,
                                                          @Field("fox") int fox);

    @FormUrlEncoded
    @POST(HttpUrl.MOVE_STOCK_HISTORY)
    Observable<BaseResult<List<MoveStockHistoryBean>>> moveStockHistory(@Field("user_id") String user_id,
                                                                        @Field("statue") int statue);

    @FormUrlEncoded
    @POST(HttpUrl.NEW_BANK)
    Observable<BaseResult<MoveStockHistoryBean>> addBank(@Field("user_id") String user_id,
                                                         @Field("name") String name,
                                                         @Field("code") String code,
                                                         @Field("newbank") String newbank);

    @FormUrlEncoded
    @POST(HttpUrl.CONFIRM_WITHDRAW)
    Observable<BaseResult<ApplyWithdrawBean>> confirmWithdraw(@Field("user_id") String user_id,
                                                              @Field("bank_id") String bank_id,
                                                              @Field("money") String money);

    @FormUrlEncoded
    @POST(HttpUrl.WITHDRAW_PROGRESS)
    Observable<BaseResult<List<WithdrawProgressBean>>> withdrawProgress(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.WITHDRAW_DETAIL)
    Observable<BaseResult<WithdrawDetailBean>> withdrawDetail(@Field("bankshow_id") int bankshow_id);

    /**
     * order
     */
    @FormUrlEncoded
    @POST(HttpUrl.ORDER_LIST)
    Observable<BaseResult<List<OrderBean>>> orderList(@Field("user_id") String user_id,
                                                      @Field("statue") int statue);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_DETAIL)
    Observable<BaseResult<OrderDetailBean>> orderDetail(@Field("user_id") String user_id,
                                                        @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST(HttpUrl.CANCEL_OR_DELETE_ORDER)
    Observable<BaseResult<OrderDetailBean>> cancelOrder(@Field("user_id") String user_id,
                                                        @Field("order_id") int order_id,
                                                        @Field("statue") int statue);

    @FormUrlEncoded
    @POST(HttpUrl.CANCEL_OR_DELETE_ORDER)
    Observable<BaseResult<OrderDetailBean>> deleteOrder(@Field("user_id") String user_id,
                                                        @Field("order_id") int order_id,
                                                        @Field("is_del") int is_del);

    @FormUrlEncoded
    @POST(HttpUrl.EXPRESS_INFO)
    Observable<BaseResult<String>> expressInfo(@Field("user_id") String user_id,
                                               @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST(HttpUrl.SALE_AFTER)
    Observable<BaseResult<List<SaleAfterBean>>> salesAfter(@Field("user_id") String user_id,
                                                           @Field("ordercode") String ordercode);

    @FormUrlEncoded
    @POST(HttpUrl.REQUEST_SALE_AFTER)
    Observable<BaseResult<SaleAfterBean>> requestSalesAfter(@Field("user_id") String user_id,
                                                            @Field("ordercode") String ordercode,
                                                            @Field("returntype") String returntype,
                                                            @Field("fox") String fox,
                                                            @Field("reason") String reason,
                                                            @Field("img") String img);

    /**
     * pay
     */
    @FormUrlEncoded
    @POST(HttpUrl.BALANCE_PAY)
    Observable<BaseResult<String>> balancePay(@Field("user_id") String user_id,
                                              @Field("statue") String statue,
                                              @Field("goods_id") int goods_id,
                                              @Field("fox") int fox,
                                              @Field("receiver_id") String receiver_id,
                                              @Field("receiver_name") String receiver_name,
                                              @Field("receiver_phone") String receiver_phone,
                                              @Field("receiver_area") String receiver_area,
                                              @Field("receiver_address") String receiver_address,
                                              @Field("freight") String freight);

    @FormUrlEncoded
    @POST(HttpUrl.ALIPAY)
    Observable<BaseResult<String>> aliPay(@Field("user_id") String user_id,
                                          @Field("statue") String statue,
                                          @Field("goods_id") int goods_id,
                                          @Field("fox") int fox,
                                          @Field("receiver_id") String receiver_id,
                                          @Field("receiver_name") String receiver_name,
                                          @Field("receiver_phone") String receiver_phone,
                                          @Field("receiver_area") String receiver_area,
                                          @Field("receiver_address") String receiver_address,
                                          @Field("freight") String freight);

    @FormUrlEncoded
    @POST(HttpUrl.WECHAT_PAY)
    Observable<BaseResult<WechatBean>> wechatPay(@Field("user_id") String user_id,
                                                 @Field("statue") String statue,
                                                 @Field("goods_id") int goods_id,
                                                 @Field("fox") int fox,
                                                 @Field("receiver_id") String receiver_id,
                                                 @Field("receiver_name") String receiver_name,
                                                 @Field("receiver_phone") String receiver_phone,
                                                 @Field("receiver_area") String receiver_area,
                                                 @Field("receiver_address") String receiver_address,
                                                 @Field("freight") String freight);


    @FormUrlEncoded
    @POST(HttpUrl.OF_THE_PUBLIC_PAY)
    Observable<BaseResult<OfTheBankBean>> ofThePublicPay(@Field("user_id") String user_id,
                                                         @Field("goods_id") int goods_id,
                                                         @Field("fox") int fox);


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
    Observable<BaseResult<List<CollectMatterBean>>> collectList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.MY_WALLET)
    Observable<BaseResult<WalletBean>> myWallet(@Field("user_id") String user_id,
                                                @Field("page") int page);

    @FormUrlEncoded
    @POST(HttpUrl.APPLY_WITHDRAW)
    Observable<BaseResult<ApplyWithdrawBean>> applyWithdraw(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.BANK_LIST)
    Observable<BaseResult<List<BankCardBean>>> bankList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CUSTOMER_TRANSFER_RECORD)
    Observable<BaseResult<List<CustomerTransferRecordBean>>> customerTransferRecord(@Field("user_id") String user_id,
                                                                                    @Field("statue") int statue);

    @FormUrlEncoded
    @POST(HttpUrl.ACCEPT_CUSTOMER_TRANSFER)
    Observable<BaseResult<CustomerTransferRecordBean>> acceptCustomerTransfer(@Field("user_id") String user_id,
                                                                              @Field("transfer_id") int transfer_id);

    @FormUrlEncoded
    @POST(HttpUrl.DENY_CUSTOMER_TRANSFER)
    Observable<BaseResult<CustomerTransferRecordBean>> denyCustomerTransfer(@Field("user_id") String user_id,
                                                                            @Field("transfer_id") int transfer_id);


    @GET(HttpUrl.MINE_HTML)
    Observable<BaseResult<WebBean>> mineHtml(@Query("statue") String statue);


    //tools
    @Multipart
    @POST(HttpUrl.UPLOAD_PIC)
    Observable<UploadResultBean> uploadPicture(@PartMap Map<String, RequestBody> map);


}
