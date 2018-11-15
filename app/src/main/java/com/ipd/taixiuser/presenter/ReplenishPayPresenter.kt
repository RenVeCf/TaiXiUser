package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ReplenishBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class ReplenishPayPresenter : PayPresenter<ReplenishPayPresenter.IReplenishView>() {
    override fun initModel() {
        mModel = BasicModel()
    }

//    fun loadExpressFee(fox: Int, area: String) {
//        mModel?.getNormalRequestData(ApiManager.getService().expressFee(GlobalParam.getUserIdOrJump(), fox, area),
//                object : Response<BaseResult<ExpressFeeBean>>(mContext, true) {
//                    override fun _onNext(result: BaseResult<ExpressFeeBean>) {
//                        if (result.code == 200) {
//                            mView?.loadReplenishInfoSuccess(result.data)
//                        } else {
//                            mView?.loadReplenishInfoFail(result.msg)
//                        }
//                    }
//                })
//    }
    fun replenishProductList() {
        mModel?.getNormalRequestData(ApiManager.getService().replenishProductList(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<ReplenishBean>>() {
                    override fun _onNext(result: BaseResult<ReplenishBean>) {
                        if (result.code == 200) {
                            mView?.loadReplenishInfoSuccess(result.data)
                        } else {
                            mView?.loadReplenishInfoFail(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadReplenishInfoFail("连接服务器失败")
                    }
                })
    }


    interface IReplenishView : IPayView {
        fun loadReplenishInfoSuccess(info: ReplenishBean)
        fun loadReplenishInfoFail(errMsg: String)
    }


}