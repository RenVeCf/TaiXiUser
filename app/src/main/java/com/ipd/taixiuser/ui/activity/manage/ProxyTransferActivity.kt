package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_proxy_transfer.*


class ProxyTransferActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, customerId: Int) {
            val intent = Intent(activity, ProxyTransferActivity::class.java)
            intent.putExtra("customerId", customerId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "转移客户"

    override fun getContentLayout(): Int = R.layout.activity_proxy_transfer


    private val mCustomerId: Int  by lazy { intent.getIntExtra("customerId", -1) }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_next.setOnClickListener {
            val acceptAccount = et_customer_account.text.toString().trim()
            if (!CommonUtils.isMobileNO(acceptAccount)) {
                toastShow("请输入正确的手机号")
                return@setOnClickListener
            }
            ApiManager.getService().acceptUserInfo(GlobalParam.getUserIdOrJump(), acceptAccount)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<CustomerBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<CustomerBean>) {
                            if (result.code == 200) {
                                ConfirmTransferActivity.launch(mActivity, result.data, mCustomerId)
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })


        }

    }


}