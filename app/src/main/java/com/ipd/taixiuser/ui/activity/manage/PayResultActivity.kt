package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.order.OrderActivity
import kotlinx.android.synthetic.main.activity_pay_result.*

class PayResultActivity : BaseUIActivity() {
    companion object {
        val STORE = 0
        val FACTORY = 1

        fun launch(activity: Activity, type: Int = FACTORY, orderNo: String) {
            val intent = Intent(activity, PayResultActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("orderNo", orderNo)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "支付状态"

    override fun getContentLayout(): Int = R.layout.activity_pay_result

    private val mType: Int by lazy { intent.getIntExtra("type", FACTORY) }
    private val mOrderNo: String by lazy { intent.getStringExtra("orderNo") }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        tv_order_no.text = "订单编号：$mOrderNo"

        if (mType == STORE) {
            tv_order_no.text = "恭喜您！您已经成为总代，将拥有更多的权限！"
            fl_bottom_menu.visibility = View.GONE
        }

    }

    override fun initListener() {
        tv_ship_order.setOnClickListener {
            //发货记录
            OrderActivity.launch(mActivity)
            finish()
        }

    }


}