package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.FactoryShipAdapter
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_factory_ship_pay.*

class LeaderReplenishActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LeaderReplenishActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "补货"

    override fun getContentLayout(): Int = R.layout.activity_leader_replenish_pay

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = FactoryShipAdapter(mActivity, listOf(ProductBean(), ProductBean(), ProductBean(), ProductBean()), {

        })
    }

    override fun initListener() {
        tv_pay.setOnClickListener {
            toastShow(true,"支付成功")
            finish()
        }
    }


}