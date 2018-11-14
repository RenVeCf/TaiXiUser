package com.ipd.taixiuser.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.OrderProductAdapter
import com.ipd.taixiuser.bean.OrderDetailBean
import com.ipd.taixiuser.bean.UpdateOrderEvent
import com.ipd.taixiuser.presenter.OrderDetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.OrderUtils
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.greenrobot.eventbus.EventBus

class OrderDetailActivity : BaseUIActivity(), OrderUtils.OrderDetailBtnClickListener, OrderDetailPresenter.IOrderDetailView {

    companion object {
        fun launch(activity: Activity, orderId: Int) {
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            activity.startActivity(intent)
        }
    }

    private val mOrderId: Int by lazy { intent.getIntExtra("orderId", 0) }
    override fun getToolbarTitle(): String = "货单详情"

    override fun getContentLayout(): Int = R.layout.activity_order_detail


    private var mPresenter: OrderDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = OrderDetailPresenter()
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
        mPresenter?.loadOrderDetail(mOrderId)
    }

    override fun loadOrderDetailSuccess(info: OrderDetailBean) {
        initOrderBtn(info)
        product_recycler_view.adapter = OrderProductAdapter(mActivity, info.fox, listOf(info.goods), null)

        tv_receive_name.text = info.address.username
        tv_receive_phone.text = info.address.phone
        tv_receive_detail_address.text = info.address.area + " " + info.address.address
        tv_order_price.text = "￥ ${info.goods.price.toFloat() * info.fox}"
        tv_express_fee.text = "￥ ${info.freight}"
        tv_total_price.text = "￥ ${info.expence}"
        tv_order_no.text = "订单编号：${info.ordercode}"
        tv_trade_no.text = "交易号：${info.ordercode}"
        tv_order_create_time.text = "创建时间：${info.ctime}"
        tv_order_deal_time.text = "成交时间：${info.ctime}"


        showContent()
    }

    override fun loadOrderDerailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun cancelOrDeleteSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(OrderUtils.WAIT_SEND, OrderUtils.FINISH, OrderUtils.AFTER_SALE)))
        toastShow("操作成功")
        finish()
    }

    override fun cancelOrDeleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    private fun initOrderBtn(info: OrderDetailBean) {
        when (info.statue) {
            OrderUtils.WAIT_SEND -> {
                iv_order_status.setImageResource(R.mipmap.wait_send)
                tv_order_status.text = "待发货"

                btn_operation1.text = "取消货单"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {
                    mPresenter?.cancelOrder(mOrderId, info.statue)
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
                    mPresenter?.expressInfo(mOrderId)
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
                    mPresenter?.deleteOrder(mOrderId)
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
                    mPresenter?.deleteOrder(mOrderId)
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