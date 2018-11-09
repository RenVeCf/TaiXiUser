package com.ipd.taixiuser.presenter

import android.text.TextUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class UserPresenter : BasePresenter<UserPresenter.IUserView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadUserInfo() {
        mModel?.getNormalRequestData(ApiManager.getService().userInfo(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<UserInfoBean>>() {
                    override fun _onNext(result: BaseResult<UserInfoBean>) {
                        if (result.code == 200) {
                            mView?.loadUserInfoSuccess(result.data)
                        } else {
                            mView?.loadUserInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadUserInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })
    }

    fun updateUserInfo(avatar: String = "", username: String = "", area: String = "", address: String = "", weixin: String = "") {
        if (TextUtils.isEmpty(username)){
            mView?.updateUserInfoFail("请输入您的真实姓名")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().updateUserInfo(GlobalParam.getUserIdOrJump(), avatar, username, area, address, weixin),
                object : Response<BaseResult<UserInfoBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UserInfoBean>) {
                        if (result.code == 200) {
                            mView?.updateUserInfoSuccess()
                        } else {
                            mView?.updateUserInfoFail(result.msg)
                        }
                    }

                })
    }


    interface IUserView {
        fun loadUserInfoSuccess(info: UserInfoBean)
        fun loadUserInfoFail(errMsg: String)
        fun updateUserInfoSuccess()
        fun updateUserInfoFail(errMsg: String)
    }


}