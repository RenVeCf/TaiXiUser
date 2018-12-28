package com.ipd.taixiuser.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.OrderProductAdapter
import com.ipd.taixiuser.adapter.ReturnPictureAdapter
import com.ipd.taixiuser.bean.OrderDetailBean
import com.ipd.taixiuser.bean.UpdateOrderEvent
import com.ipd.taixiuser.event.UpdateOrderDetailEvent
import com.ipd.taixiuser.presenter.OrderDetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.OrderUtils
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
        EventBus.getDefault().register(this)
        mPresenter = OrderDetailPresenter()
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
        tv_order_create_time.text = "创建时间：${StringUtils.getDateTimeBySecond(info.ctime)}"
        tv_order_deal_time.text = "成交时间：${StringUtils.getDateTimeBySecond(info.ctime)}"


        if (info.statue == OrderUtils.AFTER_SALE) {
            ll_order_info.visibility = View.GONE
            ll_return_info.visibility = View.VISIBLE
            tv_return_type.text = "退货方式：${if (info.applysale.returntype == 0) "退货" else "换货"}"
            tv_return_num.text = "退回数量：${info.applysale.fox}箱"
            tv_return_reason.text = "退回原因：${info.applysale.resultsreason}"
            tv_return_reason.text = "退回原因：${info.applysale.reason}"
            tv_return_status.text = "退货状态：${when (info.applysale.statue) {
                0 -> "正在处理"
                1 -> "已接受"
                else -> "已拒绝"
            }}"

            if (info.applysale.statue == 2) {
                tv_reject_reason.visibility = View.VISIBLE
                tv_reject_reason.text = "拒绝原因：${info.applysale.resultsreason}"
            } else {
                tv_reject_reason.visibility = View.GONE
            }


            if (TextUtils.isEmpty(info.applysale.img)) {
                ll_return_pic.visibility = View.GONE
            } else {
                ll_return_pic.visibility = View.VISIBLE
                return_pic_recycler_view.adapter = ReturnPictureAdapter(mActivity, StringUtils.splitImages(info.applysale.img))
            }


        } else {
            ll_order_info.visibility = View.VISIBLE
            ll_return_info.visibility = View.GONE
            ll_return_pic.visibility = View.GONE
        }


        showContent()
    }

    override fun loadOrderDerailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun cancelSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(OrderUtils.WAIT_SEND, OrderUtils.FINISH, OrderUtils.AFTER_SALE)))
        toastShow(true, "操作成功")
        finish()
    }

    override fun cancelFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(OrderUtils.CANCELED)))
        toastShow(true, "删除成功")
        finish()
    }

    override fun deleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun lookExpressSuccess(url: String) {
        WebActivity.launch(mActivity, WebActivity.URL, url,"物流查询")
    }

    override fun lookExpressFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun confirmReceiveSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(OrderUtils.WAIT_RECEIVE, OrderUtils.FINISH)))
        loadData()
    }

    override fun confirmReceiveFail(errMsg: String) {
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
                    mPresenter?.cancelOrder(mOrderId)
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

                btn_operation2.text = "确认收货"
                btn_operation2.visibility = View.VISIBLE
                btn_operation2.setOnClickListener {
                    mPresenter?.confirmReceive(mOrderId)
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
            OrderUtils.FINISH -> {
                iv_order_status.setImageResource(R.mipmap.wait_receive)
                tv_order_status.text = "已完成"

                btn_operation1.text = "查看物流"
                btn_operation1.visibility = View.VISIBLE
                btn_operation1.setOnClickListener {
                    mPresenter?.expressInfo(mOrderId)
                }
                btn_operation2.text = "申请售后"
                btn_operation2.visibility = View.VISIBLE
                btn_operation2.setOnClickListener {
                    RequestSaleAfterActivity.launch(mActivity, info.ordercode)
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

                if (info.applysale.statue == 2) {
                    btn_operation2.text = "再次申请"
                    btn_operation2.visibility = View.VISIBLE
                    btn_operation2.setOnClickListener {
                        RequestSaleAfterActivity.launch(mActivity, info.ordercode)
                    }
                } else {
                    btn_operation2.text = "再次申请"
                    btn_operation2.visibility = View.GONE
                    btn_operation2.setOnClickListener {
                    }
                }


            }
        }
    }

    override fun initListener() {
    }

    @Subscribe
    fun onMainEvent(event: UpdateOrderDetailEvent) {
        loadData()
    }

}