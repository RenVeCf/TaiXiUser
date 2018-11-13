package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ProductDetailBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.presenter.StorePayPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.CityUtils
import kotlinx.android.synthetic.main.activity_store_pay.*
import kotlinx.android.synthetic.main.item_store_product_pay.*

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
            WebActivity.launch(mActivity, WebActivity.URL, "http://www.baidu.com", "总代权限")
        }

        ll_city.setOnClickListener {
            //城市
            CityUtils.getInstance().showSelectDialog(mActivity) { province, city, area ->
                tv_receive_city.text = "${province.title}/${city.title}/${area.title}"
            }
        }
    }

    private var mInfo: ProductDetailBean? = null
    override fun loadProductSuccess(info: ProductDetailBean) {
        mInfo = info
        if (info.statue == 1) {
            //特殊商品
            product_operation_view.visibility = View.GONE
            ll_receive_info.visibility = View.GONE
        }

        product_operation_view.setMinNum(1)

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
            mPresenter?.balancePay("1", info.id, num, GlobalParam.getUserIdOrJump(), receiveName, receivePhone, receiveCity, receiveDetail)

        }

        showContent()
    }

    override fun loadProductFail(errMsg: String) {
        showError(errMsg)
    }

    override fun paySuccess() {
        if (mInfo?.statue == 1) {
            PayResultActivity.launch(mActivity, PayResultActivity.STORE)
            finish()
        } else {
            toastShow("支付成功")
            finish()
        }
    }

    override fun payFail(errMsg: String) {
        toastShow(errMsg)
    }


}