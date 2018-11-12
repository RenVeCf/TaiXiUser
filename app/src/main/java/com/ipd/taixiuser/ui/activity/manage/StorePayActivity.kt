package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ProductDetailBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.StorePayPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
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
        tv_pay.setOnClickListener {
            PayResultActivity.launch(mActivity, PayResultActivity.STORE)
            finish()
        }
    }

    override fun loadProductSuccess(info: ProductDetailBean) {
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

        showContent()
    }

    override fun loadProductFail(errMsg: String) {
        showError(errMsg)
    }


}