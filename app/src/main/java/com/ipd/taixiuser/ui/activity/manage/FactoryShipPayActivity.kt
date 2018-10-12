package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProductAdapter
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_factory_ship_pay.*

class FactoryShipPayActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, FactoryShipPayActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "支付"

    override fun getContentLayout(): Int = R.layout.activity_factory_ship_pay

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = ProductAdapter(mActivity, listOf(ProductBean(), ProductBean(), ProductBean(), ProductBean()), {

        })
    }

    override fun initListener() {
        tv_pay.setOnClickListener {
            PayResultActivity.launch(mActivity)
            finish()
        }
    }


}