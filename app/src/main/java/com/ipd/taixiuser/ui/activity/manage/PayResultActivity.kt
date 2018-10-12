package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.order.OrderActivity
import kotlinx.android.synthetic.main.activity_pay_result.*

class PayResultActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PayResultActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "支付状态"

    override fun getContentLayout(): Int = R.layout.activity_pay_result

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {

    }

    override fun initListener() {
        tv_ship_order.setOnClickListener {
            //发货记录
            OrderActivity.launch(mActivity)
            finish()
        }

    }


}