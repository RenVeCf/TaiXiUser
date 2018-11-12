package com.ipd.taixiuser.presenter

import android.text.TextUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.model.BasicModel
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.utils.StringUtils

class CustomerPresenter<T> : BasePresenter<T, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun getCustomerInfo(customerId: Int) {
        if (mView !is ICustomerView) return
        val view = mView as ICustomerView

        mModel?.getNormalRequestData(ApiManager.getService().customerInfo(customerId),
                object : Response<BaseResult<CustomerBean>>() {
                    override fun _onNext(result: BaseResult<CustomerBean>) {
                        if (result.code == 200) {
                            view.getCustomerInfoSuccess(result.data)
                        } else {
                            view.getCustomerInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        view.getCustomerInfoFail(mContext?.resources?.getString(R.string.loading_error)
                                ?: "连接服务器失败")
                    }

                })
    }

    fun addCustomer(phone: String, username: String, weixin: String, proxy: String, remark: String, area: String,address:String) {
        if (mView !is ICustomerOperationView) return
        val view = mView as ICustomerOperationView

        val level = StringUtils.getLevelIdByLevel(proxy)

        if (TextUtils.isEmpty(username)) {
            view.addCustomerFail("请输入姓名")
            return
        } else if (!CommonUtils.isMobileNO(phone)) {
            view.addCustomerFail("请输入正确的手机号")
            return
        } else if (TextUtils.isEmpty(area)) {
            view.addCustomerFail("请选择所在地区")
            return
        } else if (TextUtils.isEmpty(weixin)) {
            view.addCustomerFail("请输入微信号")
            return
        } else if (TextUtils.isEmpty(level)) {
            view.addCustomerFail("请选择代理级别")
            return
        }else if (TextUtils.isEmpty(address)) {
            view.addCustomerFail("请输入详细地址")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().newCustomer(phone, username, weixin, level, remark, "", area,address, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<CustomerBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<CustomerBean>) {
                        if (result.code == 200) {
                            view.addCustomerSuccess()
                        } else {
                            view.addCustomerFail(result.msg)
                        }

                    }

                })
    }


    interface ICustomerView {
        fun getCustomerInfoSuccess(data: CustomerBean)
        fun getCustomerInfoFail(errMsg: String)
    }

    interface ICustomerOperationView {
        fun addCustomerSuccess()
        fun addCustomerFail(errMsg: String)
    }


}