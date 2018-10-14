package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProxyReplenishProductAdapter
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_normal_replenish.*

class ProxyReplenishActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProxyReplenishActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "补货"

    override fun getContentLayout(): Int = R.layout.activity_normal_replenish

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = ProxyReplenishProductAdapter(mActivity, listOf(ProductBean(), ProductBean(), ProductBean(), ProductBean()), {

        })
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            toastShow(true, "补货成功")
            finish()
        }
    }


}