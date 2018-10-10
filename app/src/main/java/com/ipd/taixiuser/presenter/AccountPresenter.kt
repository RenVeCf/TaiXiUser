package com.ipd.taixiuser.presenter

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ForgetPwdBean
import com.ipd.taixiuser.bean.LoginBean
import com.ipd.taixiuser.bean.RegisterBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class AccountPresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun login(phone: String, password: String) {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        if (!CommonUtils.isMobileNO(phone)) {
            view.loginFail("请输入正确的手机号")
            return
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.loginFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().login(phone, password),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 200) {
                            loginSuccess(result.data)
                            view.loginSuccess()
                        } else {
                            view.loginFail(result.msg)
                        }
                    }
                })

    }


    fun getSmsCode(phone: String, type: String) {
        if (mView !is BaseSmsCodeView) return
        val view = mView as BaseSmsCodeView

        if (!CommonUtils.isMobileNO(phone)) {
            view.getSmsCodeFail("请输入正确的手机号")
            return
        }

        mModel?.getNormalRequestData(
                ApiManager.getService().smsCode(phone, type),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 200) {
                            view.getSmsCodeSuccess()
                        } else {
                            view.getSmsCodeFail(result.msg)
                        }
                    }
                })
    }

    fun register(phone: String, password: String, code: String) {
        if (mView !is IRegisterView) return
        val view = mView as IRegisterView

        if (!CommonUtils.isMobileNO(phone)) {
            view.registerFail("请输入正确的手机号")
            return
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.registerFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.registerFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().register(phone, password, code),
                object : Response<BaseResult<RegisterBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<RegisterBean>) {
                        if (result.code == 200) {
                            view.registerSuccess(result.data)
                        } else {
                            view.registerFail(result.msg)
                        }
                    }
                })
    }

    fun findPassword(phone: String, password: String, code: String) {
        if (mView !is IForgetPasswordView) return
        val view = mView as IForgetPasswordView

        if (!CommonUtils.isMobileNO(phone)) {
            view.findFail("请输入正确的手机号")
            return
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.findFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.findFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().forgetPassword(phone, password, password, code),
                object : Response<BaseResult<ForgetPwdBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ForgetPwdBean>) {
                        if (result.code == 200) {
                            view.findSuccess()
                        } else {
                            view.findFail(result.msg)
                        }
                    }
                })
    }


    private fun loginSuccess(loginResult: LoginBean) {
        GlobalParam.saveUserId(loginResult.user_id.toString())
    }


    interface BaseSmsCodeView {
        fun getSmsCodeSuccess()
        fun getSmsCodeFail(errMsg: String)
    }


    interface ILoginView {
        fun loginSuccess()
        fun loginFail(errMsg: String)
    }

    interface IRegisterView : BaseSmsCodeView {
        fun registerSuccess(registerInfo: RegisterBean)
        fun registerFail(errMsg: String)
    }

    interface IForgetPasswordView : BaseSmsCodeView {
        fun findSuccess()
        fun findFail(errMsg: String)
    }
}