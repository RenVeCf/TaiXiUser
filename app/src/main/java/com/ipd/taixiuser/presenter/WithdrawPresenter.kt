package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ApplyWithdrawBean
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class WithdrawPresenter : BasePresenter<WithdrawPresenter.IWithdrawView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadWithdrawInfo() {
        mModel?.getNormalRequestData(ApiManager.getService().applyWithdraw(GlobalParam.getUserId()),
                object : Response<BaseResult<ApplyWithdrawBean>>() {
                    override fun _onNext(result: BaseResult<ApplyWithdrawBean>) {
                        if (result.code == 200) {
                            mView?.loadWithdrawInfoSuccess(result.data)
                        } else {
                            mView?.loadWithdrawInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadWithdrawInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })
    }


    interface IWithdrawView {
        fun loadWithdrawInfoSuccess(info: ApplyWithdrawBean)
        fun loadWithdrawInfoFail(errMsg: String)
    }


}