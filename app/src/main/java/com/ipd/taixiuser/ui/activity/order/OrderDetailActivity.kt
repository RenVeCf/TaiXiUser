package com.ipd.taixiuser.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProductAdapter
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.OrderUtils
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : BaseUIActivity(), OrderUtils.OrderDetailBtnClickListener {

    companion object {
        fun launch(activity: Activity, actionType: Int) {
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra("actionType", actionType)
            activity.startActivity(intent)
        }
    }

    private val mActionType: Int by lazy { intent.getIntExtra("actionType", 0) }
    override fun getToolbarTitle(): String = "货单详情"

    override fun getContentLayout(): Int = R.layout.activity_order_detail


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        initOrderDetail()
        product_recycler_view.adapter = ProductAdapter(mActivity, listOf(ProductBean(), ProductBean(), ProductBean()), null)
    }

    private fun initOrderDetail() {
        when (mActionType) {
            OrderUtils.WAIT_SEND -> {
                iv_order_status.setImageResource(R.mipmap.wait_send)
                tv_order_status.text = "待发货"

                btn_operation1.text = "取消货单"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {

                }
                btn_operation2.visibility = View.GONE
                btn_operation2.setOnClickListener {

                }

            }
            OrderUtils.WAIT_RECEIVE -> {
                iv_order_status.setImageResource(R.mipmap.wait_receive)
                tv_order_status.text = "已发货"

                btn_operation1.text = "查看物流"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {

                }
                btn_operation1.text = "确认收货"
                btn_operation2.visibility = View.VISIBLE
                btn_operation2.setOnClickListener {

                }

            }
            OrderUtils.CANCELED -> {
                iv_order_status.setImageResource(R.mipmap.canceled)
                tv_order_status.text = "已取消"

                btn_operation1.text = "删除货单"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {

                }
                btn_operation2.visibility = View.GONE
                btn_operation2.setOnClickListener {

                }

            }
            OrderUtils.AFTER_SALE -> {
                iv_order_status.setImageResource(R.mipmap.after_sale)
                tv_order_status.text = "申请售后"

                btn_operation1.text = "删除货单"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {

                }
                btn_operation2.visibility = View.GONE
                btn_operation2.setOnClickListener {

                }

            }
        }
    }

    override fun initListener() {
    }

}