package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_proxy_info.*


class ProxyInfoActivity : BaseUIActivity() {

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


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_transfer.setOnClickListener {
            //转移客户
            ProxyTransferActivity.launch(mActivity)
        }


    }


}