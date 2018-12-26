package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.event.UpdateCustomerEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.CustomerPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_customer_info.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class CustomerInfoActivity : BaseUIActivity(), CustomerPresenter.ICustomerView {

    companion object {
        fun launch(activity: Activity, customerId: Int) {
            val intent = Intent(activity, CustomerInfoActivity::class.java)
            intent.putExtra("customerId", customerId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "客户资料"

    override fun getContentLayout(): Int = R.layout.activity_customer_info

    private val mCustomerId by lazy { intent.getIntExtra("customerId", -1) }

    private var mPresenter: CustomerPresenter<CustomerPresenter.ICustomerView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = CustomerPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
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

    private var mInfo: CustomerBean? = null
    override fun getCustomerInfoSuccess(info: CustomerBean) {
        mInfo = info
        showContent()
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

    }

    override fun getCustomerInfoFail(errMsg: String) {
        showError(errMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_customer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.edit_customer) {
            //修改客户
            if (mInfo == null) return false
            NewCustomerActivity.launch(mActivity, mInfo)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    @Subscribe
    fun onMainEvent(event: UpdateCustomerEvent) {
        loadData()
    }


}