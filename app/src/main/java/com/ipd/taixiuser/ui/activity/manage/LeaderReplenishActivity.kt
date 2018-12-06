package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ReplenishAdapter
import com.ipd.taixiuser.bean.OfTheBankBean
import com.ipd.taixiuser.bean.ReplenishBean
import com.ipd.taixiuser.bean.WechatBean
import com.ipd.taixiuser.event.PayResultEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.presenter.ReplenishPayPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.AlipayUtils
import com.ipd.taixiuser.utils.WeChatUtils
import com.ipd.taixiuser.widget.ChoosePayTypeLayout
import com.ipd.taixiuser.widget.ProductOperationView
import kotlinx.android.synthetic.main.activity_leader_replenish_pay.*
import kotlinx.android.synthetic.main.item_replenish_product.view.*
import kotlinx.android.synthetic.main.layout_pay_type.*
import org.greenrobot.eventbus.Subscribe

class LeaderReplenishActivity : BaseUIActivity(), ReplenishPayPresenter.IReplenishView {
    override fun ofThePublicPaySuccess(result: OfTheBankBean) {

    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LeaderReplenishActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "补货"

    override fun getContentLayout(): Int = R.layout.activity_leader_replenish_pay

    private var mPresenter: ReplenishPayPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = ReplenishPayPresenter()
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
        mPresenter?.replenishProductList()
    }


    private fun setContent(data: ReplenishBean) {
        ImageLoader.loadAvatar(mActivity, data.userinfo.avatar, customer_avatar)
        tv_customer_name.text = data.userinfo.username
        tv_customer_level.text = "${data.userinfo.proxyname}(${data.userinfo.nickname})"
        tv_customer_phone.text = data.userinfo.phone

        product_recycler_view.adapter = ReplenishAdapter(mActivity, data.purchasegoods, object : ProductOperationView.OnCartNumChangeListener {
            override fun onNumChange(lastNum: Int, num: Int) {
                //计算总价
                tv_total_price.text = "￥ ${data.purchasegoods[0].price.toFloat() * num}"
            }

        }) {

        }

        tv_pay.setOnClickListener {
            if (data.purchasegoods == null || data.purchasegoods.isEmpty()) {
                toastShow("暂无可补货的商品")
                return@setOnClickListener
            }
            val num = product_recycler_view.layoutManager.findViewByPosition(0).fox_operation_view.getNum()
            if (num == 0) {
                toastShow("补货数量不能为0")
                return@setOnClickListener
            }
            when (pay_type_layout.getPayType()) {
                ChoosePayTypeLayout.PayType.BALANCE -> {
                    mPresenter?.balancePay("3", data.purchasegoods[0].id, num, GlobalParam.getUserId(), "", "", "", "")
                }
                ChoosePayTypeLayout.PayType.ALIPAY -> {
                    mPresenter?.alipay("3", data.purchasegoods[0].id, num, GlobalParam.getUserId(), "", "", "", "")
                }
                ChoosePayTypeLayout.PayType.WECHAT -> {
                    mPresenter?.wechatPay("3", data.purchasegoods[0].id, num, GlobalParam.getUserId(), "", "", "", "")
                }
            }

//            ApiManager.getService().replenish(GlobalParam.getUserIdOrJump(), data.purchasegoods[0].id, num)
//                    .compose(RxScheduler.applyScheduler())
//                    .subscribe(object : Response<BaseResult<ProductDetailBean>>(mActivity, true) {
//                        override fun _onNext(result: BaseResult<ProductDetailBean>) {
//                            if (result.code == 200) {
//                                toastShow(true, result.msg)
//                                finish()
//                            } else {
//                                toastShow(result.msg)
//                            }
//                        }
//                    })

        }

    }

    override fun initListener() {

    }

    override fun loadReplenishInfoSuccess(info: ReplenishBean) {
        setContent(info)
        showContent()
    }

    override fun loadReplenishInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun paySuccess(orderNo: String) {
        toastShow(true, "补货成功")
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