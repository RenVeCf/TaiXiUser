package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ExpressInfoBean
import com.ipd.taixiuser.bean.OrderDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.utils.OrderUtils

class OrderDetailPresenter : BasePresenter<OrderDetailPresenter.IOrderDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadOrderDetail(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderDetail(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<OrderDetailBean>>() {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.loadOrderDetailSuccess(result.data)
                        } else {
                            mView?.loadOrderDerailFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadOrderDerailFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }
                })
    }

    fun cancelOrder(mOrderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().cancelOrder(GlobalParam.getUserIdOrJump(), mOrderId, OrderUtils.CANCELED),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.cancelSuccess()
                        } else {
                            mView?.cancelFail(result.msg)
                        }
                    }
                })
    }

    fun confirmReceive(mOrderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().cancelOrder(GlobalParam.getUserIdOrJump(), mOrderId, OrderUtils.FINISH),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.confirmReceiveSuccess()
                        } else {
                            mView?.confirmReceiveFail(result.msg)
                        }
                    }
                })
    }

    fun deleteOrder(mOrderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().deleteOrder(GlobalParam.getUserIdOrJump(), mOrderId, 1),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.deleteSuccess()
                        } else {
                            mView?.deleteFail(result.msg)
                        }
                    }
                })
    }

    fun expressInfo(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().expressInfo(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 200) {
                            mView?.lookExpressSuccess(result.data)
                        } else {
                            mView?.lookExpressFail(result.msg)
                        }
                    }
                })
    }


    interface IOrderDetailView {
        fun loadOrderDetailSuccess(info: OrderDetailBean)
        fun loadOrderDerailFail(errMsg: String)
        fun cancelSuccess()
        fun cancelFail(errMsg: String)
        fun deleteSuccess()
        fun deleteFail(errMsg: String)
        fun lookExpressSuccess(url:String)
        fun lookExpressFail(errMsg: String)
        fun confirmReceiveSuccess()
        fun confirmReceiveFail(errMsg: String)
    }


}