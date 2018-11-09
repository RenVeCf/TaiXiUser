package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class BusinessSchoolDetailPresenter : BasePresenter<BusinessSchoolDetailPresenter.IBusinessSchoolDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadBusinessSchoolDetail(matterId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().businessSchoolDetail(GlobalParam.getUserId(), matterId),
                object : Response<BaseResult<BusinessDetailBean>>() {
                    override fun _onNext(result: BaseResult<BusinessDetailBean>) {
                        if (result.code == 200) {
                            mView?.loadBusinessDetailSuccess(result.data)
                        } else {
                            mView?.loadBusinessDetailFail(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadBusinessDetailFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })

    }

    fun praise(businessId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().businessPraiseOrCollect(GlobalParam.getUserIdOrJump(), businessId, 1),
                object : Response<BaseResult<BusinessDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<BusinessDetailBean>) {
                        if (result.code == 200) {
                            mView?.praiseSuccess()
                        } else {
                            mView?.praiseFail(result.msg)
                        }
                    }
                })

    }

    fun collect(businessId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().businessPraiseOrCollect(GlobalParam.getUserIdOrJump(), businessId, 0),
                object : Response<BaseResult<BusinessDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<BusinessDetailBean>) {
                        if (result.code == 200) {
                            mView?.collectSuccess()
                        } else {
                            mView?.praiseFail(result.msg)
                        }
                    }
                })

    }


    interface IBusinessSchoolDetailView {
        fun loadBusinessDetailSuccess(info: BusinessDetailBean)
        fun loadBusinessDetailFail(errMsg: String)
        fun praiseSuccess()
        fun praiseFail(errMsg: String)
        fun collectSuccess()
    }


}