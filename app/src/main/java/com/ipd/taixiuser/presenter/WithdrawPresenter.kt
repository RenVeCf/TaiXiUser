package com.ipd.taixiuser.presenter

import android.text.TextUtils
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

    fun confirmWithdraw(bankId: Int, money: String) {
        if (TextUtils.isEmpty(money)) {
            mView?.withdrawFail("请输入提现金额")
            return
        } else if (money.toInt() < 100) {
            mView?.withdrawFail("提现金额不能少于100")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().confirmWithdraw(GlobalParam.getUserId(), bankId.toString(), money),
                object : Response<BaseResult<ApplyWithdrawBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ApplyWithdrawBean>) {
                        if (result.code == 200) {
                            mView?.withdrawSuccess()
                        } else {
                            mView?.withdrawFail(result.msg)
                        }
                    }
                })
    }


    interface IWithdrawView {
        fun loadWithdrawInfoSuccess(info: ApplyWithdrawBean)
        fun loadWithdrawInfoFail(errMsg: String)
        fun withdrawSuccess()
        fun withdrawFail(errMsg: String)
    }


}