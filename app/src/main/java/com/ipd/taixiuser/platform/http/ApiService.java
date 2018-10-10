package com.ipd.taixiuser.platform.http;


import com.ipd.taixiuser.bean.BaseResult;
import com.ipd.taixiuser.bean.ForgetPwdBean;
import com.ipd.taixiuser.bean.LoginBean;
import com.ipd.taixiuser.bean.RegisterBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jumpbox on 2017/7/24.
 */

public interface ApiService {

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

}
