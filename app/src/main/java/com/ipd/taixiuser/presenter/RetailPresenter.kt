package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.model.BasicModel

class RetailPresenter : BasePresenter<RetailPresenter.IRetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun startShip() {
//        mModel?.getNormalRequestData(ApiManager.getService().customerInfo(customerId),
//                object : Response<BaseResult<CustomerBean>>() {
//                    override fun _onNext(result: BaseResult<CustomerBean>) {
//                        if (result.code == 200) {
//                            view.getCustomerInfoSuccess(result.data)
//                        } else {
//                            view.getCustomerInfoFail(result.msg)
//                        }
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        view.getCustomerInfoFail(mContext?.resources?.getString(R.string.loading_error)
//                                ?: "连接服务器失败")
//                    }
//
//                })
    }


    interface IRetailView {
        fun shipSuccess()
        fun shipFail(errMsg: String)
    }


}