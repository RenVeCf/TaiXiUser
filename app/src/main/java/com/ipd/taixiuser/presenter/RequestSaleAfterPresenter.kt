package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.SaleAfterBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class RequestSaleAfterPresenter : BasePresenter<RequestSaleAfterPresenter.IRequestSaleAfterView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadInfo(orderCode: String) {
        mModel?.getNormalRequestData(ApiManager.getService().salesAfter(GlobalParam.getUserIdOrJump(), orderCode),
                object : Response<BaseResult<List<SaleAfterBean>>>() {
                    override fun _onNext(result: BaseResult<List<SaleAfterBean>>) {
                        if (result.code == 200 && result.data != null && result.data.isNotEmpty()) {
                            mView?.loadInfoSuccess(result.data[0])
                        } else {
                            mView?.loadInfoFail(result.msg ?: "暂无数据")
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }
                })
    }


    fun requestSaleAfter(orderCode: String, returnType: Int, returnNum: String, returnReason: String, picStr: String) {
        mModel?.getNormalRequestData(ApiManager.getService().requestSalesAfter(GlobalParam.getUserIdOrJump(), orderCode, returnType.toString(), returnNum.toString(), returnReason, picStr),
                object : Response<BaseResult<SaleAfterBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<SaleAfterBean>) {
                        if (result.code == 200) {
                            mView?.requestSuccess()
                        } else {
                            mView?.requestFail(result.msg ?: "暂无数据")
                        }
                    }

                })
    }


    interface IRequestSaleAfterView {
        fun loadInfoSuccess(info: SaleAfterBean)
        fun loadInfoFail(errMsg: String)
        fun requestSuccess()
        fun requestFail(errMsg: String)
    }


}