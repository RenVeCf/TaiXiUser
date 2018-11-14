package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.ProxyPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_proxy_info.*


class ProxyInfoActivity : BaseUIActivity(), ProxyPresenter.IProxyView {

    companion object {
        fun launch(activity: Activity, customerId: Int) {
            val intent = Intent(activity, ProxyInfoActivity::class.java)
            intent.putExtra("customerId", customerId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "代理资料"

    override fun getContentLayout(): Int = R.layout.activity_proxy_info


    private val mCustomerId by lazy { intent.getIntExtra("customerId", -1) }

    private var mPresenter: ProxyPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = ProxyPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }


    override fun loadData() {
        showProgress()
        mPresenter?.getCustomerInfo(mCustomerId)
    }


    override fun initListener() {


    }

    override fun getCustomerInfoSuccess(info: CustomerBean) {
        ImageLoader.loadAvatar(mActivity, info.avatar, customer_avatar)
        tv_customer_name.text = info.username
        tv_customer_level.text = "${info.proxyname} (${info.nickname})"
        tv_customer_phone.text = info.phone
        tv_wechat.text = info.weixin
        tv_phone.text = info.phone
        tv_leader.text = info.posname
        tv_address.text = info.area
        tv_detail_address.text = info.address
        tv_customer_remark.text = info.remark

        tv_transfer.setOnClickListener {
            //转移客户
            mPresenter?.transferAuth(info.phone)
        }
        showContent()
    }

    override fun getCustomerInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun authSuccess() {
        ProxyTransferActivity.launch(mActivity, mCustomerId)
    }

    override fun authFail(errMsg: String) {
        toastShow(errMsg)
    }


}