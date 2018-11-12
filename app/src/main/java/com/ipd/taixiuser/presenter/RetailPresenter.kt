package com.ipd.taixiuser.presenter

import android.text.TextUtils
import com.google.gson.Gson
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.bean.StartShipProductBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response

class RetailPresenter : BasePresenter<RetailPresenter.IRetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun startShip(receiveName: String, receivePhone: String, city: String, address: String, style: String, expressCompany: String, expressNo: String, productList: List<StartShipProductBean>) {
        if (TextUtils.isEmpty(receiveName)) {
            mView?.shipFail("请输入收货人姓名")
            return
        } else if (!CommonUtils.isMobileNO(receivePhone)) {
            mView?.shipFail("请输入正确的联系电话")
            return
        } else if (TextUtils.isEmpty(city)) {
            mView?.shipFail("请选择收货人所在地区")
            return
        } else if (TextUtils.isEmpty(address)) {
            mView?.shipFail("请输入收货人详细地址")
            return
        } else if (TextUtils.isEmpty(expressCompany)) {
            mView?.shipFail("请输入物流公司名称")
            return
        } else if (TextUtils.isEmpty(expressNo)) {
            mView?.shipFail("请输入快递单号")
            return
        } else if (productList == null || productList.isEmpty()) {
            mView?.shipFail("请选择商品")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().retail(GlobalParam.getUserIdOrJump(), receiveName, receivePhone, city, address, style, expressCompany, expressNo, Gson().toJson(productList)),
                object : Response<BaseResult<ProductBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ProductBean>) {
                        if (result.code == 200) {
                            mView?.shipSuccess()
                        } else {
                            mView?.shipFail(result.msg)
                        }
                    }

                })
    }

    fun loadProductList() {
        mModel?.getNormalRequestData(ApiManager.getService().productList(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<List<ProductBean>>>() {
                    override fun _onNext(result: BaseResult<List<ProductBean>>) {
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


    interface IRetailView {
        fun shipSuccess()
        fun shipFail(errMsg: String)
        fun loadProductSuccess(productList: List<ProductBean>)
        fun loadProductFail(errMsg: String)
    }


}