package com.ipd.taixiuser.presenter

import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MatterDetailBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class MatterDetailPresenter : BasePresenter<MatterDetailPresenter.MatterDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadMatterDetail(matterId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().matterDetail(matterId),
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


    interface MatterDetailView {
        fun loadMatterDetailSuccess(info: MatterDetailBean)
        fun loadMatterDetailFail(errMsg: String)
    }


}