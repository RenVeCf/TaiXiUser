package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProductAdapter
import com.ipd.taixiuser.bean.*
import com.ipd.taixiuser.event.ChooseCustomerEvent
import com.ipd.taixiuser.event.PayResultEvent
import com.ipd.taixiuser.presenter.FactoryPayPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.AlipayUtils
import com.ipd.taixiuser.utils.WeChatUtils
import com.ipd.taixiuser.widget.ChoosePayTypeLayout
import kotlinx.android.synthetic.main.activity_factory_ship_pay.*
import kotlinx.android.synthetic.main.layout_pay_type.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FactoryShipPayActivity : BaseUIActivity(), FactoryPayPresenter.IFactoryPayView {
    override fun ofThePublicPaySuccess(result: OfTheBankBean) {

    }

    companion object {
        fun launch(activity: Activity, list: ArrayList<ProductBean>) {
            val intent = Intent(activity, FactoryShipPayActivity::class.java)
            intent.putParcelableArrayListExtra("product", list)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "支付"

    override fun getContentLayout(): Int = R.layout.activity_factory_ship_pay

    private var mPresenter: FactoryPayPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = FactoryPayPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }

    private val mProductList: ArrayList<ProductBean> by lazy { intent.getParcelableArrayListExtra<ProductBean>("product") }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = ProductAdapter(mActivity, mProductList) {

        }
        setTotalPrice(0f)
    }

    override fun initListener() {
        tv_pay.setOnClickListener {
            if (mCustomerInfo == null) {
                toastShow("请选择收货人")
                return@setOnClickListener
            }

            when (pay_type_layout.getPayType()) {
                ChoosePayTypeLayout.PayType.ALIPAY -> {
                    val productInfo = mProductList[0]
                    mPresenter?.alipay("2", productInfo.id, productInfo.chooseNum, mCustomerInfo!!.id.toString(), mCustomerInfo!!.username, mCustomerInfo!!.phone, mCustomerInfo!!.area, mCustomerInfo!!.address)

                }
                ChoosePayTypeLayout.PayType.WECHAT -> {
                    val productInfo = mProductList[0]
                    mPresenter?.wechatPay("2", productInfo.id, productInfo.chooseNum, mCustomerInfo!!.id.toString(), mCustomerInfo!!.username, mCustomerInfo!!.phone, mCustomerInfo!!.area, mCustomerInfo!!.address)
                }
                ChoosePayTypeLayout.PayType.BALANCE -> {
                    val productInfo = mProductList[0]
                    mPresenter?.balancePay("2", productInfo.id, productInfo.chooseNum, mCustomerInfo!!.id.toString(), mCustomerInfo!!.username, mCustomerInfo!!.phone, mCustomerInfo!!.area, mCustomerInfo!!.address)
                }
            }
        }

        cl_empty_receive.setOnClickListener {
            MineCustomerActivity.launch(mActivity, MineCustomerActivity.CHOOSE)
        }
        cl_receive_info.setOnClickListener {
            MineCustomerActivity.launch(mActivity, MineCustomerActivity.CHOOSE)
        }
    }

    private var mCustomerInfo: CustomerBean? = null
    @Subscribe
    fun onMainEvent(event: ChooseCustomerEvent) {
        val customerInfo = event.customerInfo
        mCustomerInfo = customerInfo

        var totalNum = 0
        mProductList?.forEach {
            totalNum += it.chooseNum
        }
        mPresenter?.loadExpressFee(totalNum, customerInfo.area)

        tv_receive_name.text = customerInfo.username
        tv_receive_phone.text = customerInfo.phone
        tv_receive_detail_address.text = customerInfo.area + " " + customerInfo.address

        cl_empty_receive.visibility = View.GONE
        cl_receive_info.visibility = View.VISIBLE
    }

    override fun loadExpressFeeSuccess(info: ExpressFeeBean) {
        tv_express_fee.text = "￥${info.freight}"
        tv_express_fee_menu.text = "含运费(${info.freight}元)"
        setTotalPrice(info.freight.toFloat())
    }

    private fun setTotalPrice(expressFee: Float) {
        var totalPrice = expressFee
//        mProductList.forEach {
//            totalPrice += it.price.toFloat() * it.chooseNum
//        }
        tv_total_price.text = "￥ $totalPrice"
        if (totalPrice == 0f) {
            tv_pay.text = "确认"
            ll_alipay.visibility = View.GONE
            ll_wechat.visibility = View.GONE
            pay_type_layout.switchPayType(ChoosePayTypeLayout.PayType.BALANCE)

        } else {
            tv_pay.text = "支付"
            ll_alipay.visibility = View.VISIBLE
            ll_wechat.visibility = View.VISIBLE
        }

    }

    override fun loadExpressFeeFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun paySuccess(orderNo: String) {
        PayResultActivity.launch(mActivity, orderNo = orderNo)
        finish()
    }

    override fun payFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun alipaySuccess(result: String) {
        AlipayUtils.getInstance().alipayByData(mActivity, result, object : AlipayUtils.OnPayListener {
            override fun onPaySuccess() {
                toastShow(true, "支付成功")
                finish()
            }

            override fun onPayWait() {
            }

            override fun onPayFail() {
                payFail("支付失败")
            }
        })
    }

    override fun wechatPaySuccess(result: WechatBean) {
        WeChatUtils.getInstance(mActivity).startPay(result)
    }


    @Subscribe
    fun onMainEvent(event: PayResultEvent) {
        when (event.status) {
            0 -> {
                toastShow(true, "支付成功")
                finish()
            }
            -1 -> toastShow("支付失败")
            -2 -> toastShow("取消支付")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }


}