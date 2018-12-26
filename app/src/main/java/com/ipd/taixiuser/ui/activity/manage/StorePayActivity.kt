package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.*
import com.ipd.taixiuser.event.PayResultEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.presenter.StorePayPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.AlipayUtils
import com.ipd.taixiuser.utils.CityUtils
import com.ipd.taixiuser.utils.WeChatUtils
import com.ipd.taixiuser.widget.ChoosePayTypeLayout
import com.ipd.taixiuser.widget.ProductOperationView
import kotlinx.android.synthetic.main.activity_store_pay.*
import kotlinx.android.synthetic.main.item_store_product_pay.*
import kotlinx.android.synthetic.main.layout_pay_type.*
import org.greenrobot.eventbus.Subscribe

class StorePayActivity : BaseUIActivity(), StorePayPresenter.IStorePayView {

    companion object {
        fun launch(activity: Activity, productId: Int) {
            val intent = Intent(activity, StorePayActivity::class.java)
            intent.putExtra("productId", productId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "商城"

    override fun getContentLayout(): Int = R.layout.activity_store_pay

    private var mPresenter: StorePayPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = StorePayPresenter()
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

    private val mProductId: Int by lazy { intent.getIntExtra("productId", -1) }
    override fun loadData() {
        showProgress()
        mPresenter?.loadProductDetail(mProductId)
    }

    override fun initListener() {
        ll_permission.setOnClickListener {
            ApiManager.getService().explainHtml("4")
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<ExplainHtmlBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<ExplainHtmlBean>) {
                            if (result.code == 200) {
                                WebActivity.launch(mActivity, WebActivity.HTML, result.data.content, "总代权限")
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        }

        ll_city.setOnClickListener {
            //城市
            CityUtils.getInstance().showSelectDialog(mActivity) { province, city, area ->
                tv_receive_city.text = "${province.title}/${city.title}/${area.title}"
                mPresenter?.loadExpressFee(product_operation_view.getNum(), tv_receive_city.text.toString().trim())
            }
        }
    }

    private var mExpressFee = 0f
    private var mInfo: ProductDetailBean? = null
    override fun loadProductSuccess(info: ProductDetailBean) {
        mInfo = info
        if (info.statue == 1) {
            //特殊商品
            product_operation_view.visibility = View.GONE
            ll_receive_info.visibility = View.GONE
        } else if (info.statue == 2) {
            //特殊商品
            product_operation_view.visibility = View.GONE
            ll_receive_info.visibility = View.GONE
            pay_type_layout.visibility = View.GONE
            ll_of_the_public.visibility = View.VISIBLE
        }

        product_operation_view.setMinNum(1)
        product_operation_view.setOnCartNumChangeListener(object : ProductOperationView.OnCartNumChangeListener {
            override fun onNumChange(lastNum: Int, num: Int) {
                tv_total_price.text = "￥ ${info.price.toFloat() * num + mExpressFee}"
                val city = tv_receive_city.text.toString().trim()
                if (!TextUtils.isEmpty(city)) {
                    mPresenter?.loadExpressFee(num, city)
                }

            }
        })

        ImageLoader.loadNoPlaceHolderImg(mActivity, info.img, iv_product)
        tv_product_name.text = info.name
        tv_product_desc.text = info.content
        tv_product_spec.text = "${info.fox}${info.unit}"
        tv_product_price.text = "￥ ${info.price}"
        tv_total_price.text = "￥ ${info.price}"

        tv_pay.setOnClickListener {
            val num = product_operation_view.getNum()
            val receiveName = et_receive_name.text.toString().trim()
            val receivePhone = et_receive_phone.text.toString().trim()
            val receiveCity = tv_receive_city.text.toString().trim()
            val receiveDetail = et_receive_detail.text.toString().trim()

            if (info.statue == 2) {
                //对公支付
                mPresenter?.ofThePublicPay(info.id, num)
                return@setOnClickListener
            }

            if (info.statue != 1) {
                if (TextUtils.isEmpty(receiveName)) {
                    toastShow("请输入收货人姓名")
                    return@setOnClickListener
                } else if (!CommonUtils.isMobileNO(receivePhone)) {
                    toastShow("请输入正确的手机号")
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(receiveCity)) {
                    toastShow("请选择所在地区")
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(receiveDetail)) {
                    toastShow("请输入详细地址")
                    return@setOnClickListener
                }
            }
            when (pay_type_layout.getPayType()) {
                ChoosePayTypeLayout.PayType.ALIPAY -> {
                    mPresenter?.alipay("1", info.id, num, GlobalParam.getUserIdOrJump(), receiveName, receivePhone, receiveCity, receiveDetail)
                }
                ChoosePayTypeLayout.PayType.WECHAT -> {
                    mPresenter?.wechatPay("1", info.id, num, GlobalParam.getUserIdOrJump(), receiveName, receivePhone, receiveCity, receiveDetail)
                }
                ChoosePayTypeLayout.PayType.BALANCE -> {
                    mPresenter?.balancePay("1", info.id, num, GlobalParam.getUserIdOrJump(), receiveName, receivePhone, receiveCity, receiveDetail)
                }
            }

        }

        showContent()
    }

    override fun loadProductFail(errMsg: String) {
        showError(errMsg)
    }

    override fun loadExpressFeeSuccess(info: ExpressFeeBean) {
        mExpressFee = info.freight.toFloat()
        tv_express_fee.text = "(含运费${info.freight}元)"
        tv_total_price.text = "￥ ${(mInfo?.price
                ?: "0").toFloat() * product_operation_view.getNum() + mExpressFee}"
    }

    override fun loadExpressFeeFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun ofThePublicPaySuccess(result: OfTheBankBean) {
        OfThePublicSuccessActivity.launch(mActivity, result)
        finish()
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

    override fun paySuccess(orderNo: String) {
        if (mInfo?.statue == 1) {
            PayResultActivity.launch(mActivity, PayResultActivity.STORE, orderNo)
            finish()
        } else {
            toastShow(true, "支付成功")
            finish()
        }
    }

    override fun payFail(errMsg: String) {
        toastShow(errMsg)
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