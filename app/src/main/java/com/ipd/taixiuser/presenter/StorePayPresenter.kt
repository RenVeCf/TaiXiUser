package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ExpressFeeBean
import com.ipd.taixiuser.bean.ProductDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class StorePayPresenter : PayPresenter<StorePayPresenter.IStorePayView>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadProductDetail(productId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().productDetail(GlobalParam.getUserIdOrJump(), productId),
                object : Response<BaseResult<ProductDetailBean>>() {
                    override fun _onNext(result: BaseResult<ProductDetailBean>) {
                        if (result.code == 200) {
                            mView?.loadProductSuccess(result.data)
                        } else {
                            mView?.loadProductFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadProductFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })
    }


    fun loadExpressFee(fox: Int, area: String) {
        mModel?.getNormalRequestData(ApiManager.getService().expressFee(GlobalParam.getUserIdOrJump(), fox, area),
                object : Response<BaseResult<ExpressFeeBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ExpressFeeBean>) {
                        if (result.code == 200) {
                            mView?.loadExpressFeeSuccess(result.data)
                        } else {
                            mView?.loadExpressFeeFail(result.msg)
                        }
                    }
                })
    }


    interface IStorePayView : IPayView {
        fun loadProductSuccess(info: ProductDetailBean)
        fun loadProductFail(errMsg: String)
        fun loadExpressFeeSuccess(info: ExpressFeeBean)
        fun loadExpressFeeFail(errMsg: String)
    }


}