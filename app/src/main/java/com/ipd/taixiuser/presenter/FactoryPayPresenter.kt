package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ExpressFeeBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class FactoryPayPresenter : PayPresenter<FactoryPayPresenter.IFactoryPayView>() {
    override fun initModel() {
        mModel = BasicModel()
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


    interface IFactoryPayView : IPayView {
        fun loadExpressFeeSuccess(info: ExpressFeeBean)
        fun loadExpressFeeFail(errMsg: String)
    }


}