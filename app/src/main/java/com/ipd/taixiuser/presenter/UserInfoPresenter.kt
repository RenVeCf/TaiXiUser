package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class UserInfoPresenter : BasePresenter<UserInfoPresenter.IUserInfoView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadUserInfo() {
        mModel?.getNormalRequestData(ApiManager.getService().userInfo(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<UserInfoBean>>() {
                    override fun _onNext(result: BaseResult<UserInfoBean>) {
                        if (result.code == 200) {
                            mView?.getUserInfoSuccess(result.data)
                        } else {
                            mView?.getUserInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.getUserInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }
                })
    }


    interface IUserInfoView {
        fun getUserInfoSuccess(userInfo: UserInfoBean)
        fun getUserInfoFail(errMsg: String)
    }


}