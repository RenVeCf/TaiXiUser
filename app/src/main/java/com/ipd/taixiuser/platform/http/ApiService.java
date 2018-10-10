package com.ipd.taixiuser.platform.http;


import com.ipd.taixiuser.bean.ActionMessageBean;
import com.ipd.taixiuser.bean.BaseResult;
import com.ipd.taixiuser.bean.ForgetPwdBean;
import com.ipd.taixiuser.bean.HomeBean;
import com.ipd.taixiuser.bean.ListResult;
import com.ipd.taixiuser.bean.LoginBean;
import com.ipd.taixiuser.bean.MatterBean;
import com.ipd.taixiuser.bean.MatterDetailBean;
import com.ipd.taixiuser.bean.QuestionBean;
import com.ipd.taixiuser.bean.RegisterBean;
import com.ipd.taixiuser.bean.SystemMessageBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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


}
