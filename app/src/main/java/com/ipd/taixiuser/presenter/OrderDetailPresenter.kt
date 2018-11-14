package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ExpressInfoBean
import com.ipd.taixiuser.bean.OrderDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

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

    fun cancelOrder(mOrderId: Int, statue: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().cancelOrder(GlobalParam.getUserIdOrJump(), mOrderId, statue),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.cancelOrDeleteSuccess()
                        } else {
                            mView?.cancelOrDeleteFail(result.msg)
                        }
                    }
                })
    }

    fun deleteOrder(mOrderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().deleteOrder(GlobalParam.getUserIdOrJump(), mOrderId, 1),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 200) {
                            mView?.cancelOrDeleteSuccess()
                        } else {
                            mView?.cancelOrDeleteFail(result.msg)
                        }
                    }
                })
    }

    fun expressInfo(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().expressInfo(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<ExpressInfoBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ExpressInfoBean>) {
                        if (result.code == 200) {
                            mView?.cancelOrDeleteSuccess()
                        } else {
                            mView?.cancelOrDeleteFail(result.msg)
                        }
                    }
                })
    }


    interface IOrderDetailView {
        fun loadOrderDetailSuccess(info: OrderDetailBean)
        fun loadOrderDerailFail(errMsg: String)
        fun cancelOrDeleteSuccess()
        fun cancelOrDeleteFail(errMsg: String)
    }


}