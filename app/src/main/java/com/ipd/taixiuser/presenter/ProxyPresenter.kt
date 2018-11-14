package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class ProxyPresenter : BasePresenter<ProxyPresenter.IProxyView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun getCustomerInfo(customerId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().customerInfo(customerId),
                object : Response<BaseResult<CustomerBean>>() {
                    override fun _onNext(result: BaseResult<CustomerBean>) {
                        if (result.code == 200) {
                            mView?.getCustomerInfoSuccess(result.data)
                        } else {
                            mView?.getCustomerInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.getCustomerInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })
    }

    fun transferAuth(phone: String) {
        mModel?.getNormalRequestData(ApiManager.getService().transferCustomerAuth(GlobalParam.getUserIdOrJump(),phone),
                object : Response<BaseResult<CustomerBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<CustomerBean>) {
                        if (result.code == 200) {
                            mView?.authSuccess()
                        } else {
                            mView?.authFail(result.msg)
                        }
                    }

                })
    }


    interface IProxyView {
        fun getCustomerInfoSuccess(data: CustomerBean)
        fun getCustomerInfoFail(errMsg: String)
        fun authSuccess()
        fun authFail(errMsg: String)
    }


}