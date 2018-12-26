package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MatterDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class MatterDetailPresenter : BasePresenter<MatterDetailPresenter.MatterDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadMatterDetail(matterId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().matterDetail(GlobalParam.getUserId(), matterId),
                object : Response<BaseResult<MatterDetailBean>>() {
                    override fun _onNext(result: BaseResult<MatterDetailBean>) {
                        if (result.code == 200) {
                            mView?.loadMatterDetailSuccess(result.data)
                        } else {
                            mView?.loadMatterDetailFail(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadMatterDetailFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })

    }


    fun praise(matterId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().businessPraiseOrCollect(GlobalParam.getUserIdOrJump(), matterId, 1),
                object : Response<BaseResult<MatterDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<MatterDetailBean>) {
                        if (result.code == 200) {
                            mView?.praiseSuccess()
                        } else {
                            mView?.praiseFail(result.msg)
                        }
                    }
                })

    }

    fun collect(matterId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().businessPraiseOrCollect(GlobalParam.getUserIdOrJump(), matterId, 0),
                object : Response<BaseResult<MatterDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<MatterDetailBean>) {
                        if (result.code == 200) {
                            mView?.collectSuccess()
                        } else {
                            mView?.praiseFail(result.msg)
                        }
                    }
                })

    }

    interface MatterDetailView {
        fun loadMatterDetailSuccess(info: MatterDetailBean)
        fun loadMatterDetailFail(errMsg: String)
        fun praiseSuccess()
        fun praiseFail(errMsg: String)
        fun collectSuccess()
    }


}