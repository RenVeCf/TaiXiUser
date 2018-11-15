package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

open class PayPresenter<T : PayPresenter.IPayView> : BasePresenter<T, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun balancePay(status: String, productId: Int, fox: Int, receiveId: String, receiveName: String, receivePhone: String, receiveArea: String, receiveAddress: String, expressFee: String = "0") {
        mModel?.getNormalRequestData(ApiManager.getService().balancePay(GlobalParam.getUserIdOrJump(), status, productId, fox, receiveId, receiveName, receivePhone, receiveArea, receiveAddress, expressFee),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 200) {
                            mView?.paySuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }

                })
    }

    fun alipay(status: String, productId: Int, fox: Int, receiveId: String, receiveName: String, receivePhone: String, receiveArea: String, receiveAddress: String, expressFee: String = "0") {
        mModel?.getNormalRequestData(ApiManager.getService().aliPay(GlobalParam.getUserIdOrJump(), status, productId, fox, receiveId, receiveName, receivePhone, receiveArea, receiveAddress, expressFee),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 200) {
                            mView?.alipaySuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }

                })
    }

    fun wechatPay(status: String, productId: Int, fox: Int, receiveId: String, receiveName: String, receivePhone: String, receiveArea: String, receiveAddress: String, expressFee: String = "0") {
        mModel?.getNormalRequestData(ApiManager.getService().wechatPay(GlobalParam.getUserIdOrJump(), status, productId, fox, receiveId, receiveName, receivePhone, receiveArea, receiveAddress, expressFee),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 200) {
                            mView?.paySuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }

                })
    }


    interface IPayView {
        fun paySuccess(orderNo: String)
        fun payFail(errMsg: String)
        fun alipaySuccess(result:String)
    }


}