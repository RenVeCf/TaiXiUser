package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.presenter.RetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.CityUtils
import kotlinx.android.synthetic.main.activity_retail.*


class RetailActivity : BaseUIActivity(), RetailPresenter.IRetailView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, RetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "零售发货"

    override fun getContentLayout(): Int = R.layout.activity_retail


    private var mPresenter: RetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = RetailPresenter()
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
        mPresenter?.loadProductList()
    }

    override fun initListener() {
        ll_city.setOnClickListener {
            CityUtils.getInstance().showSelectDialog(mActivity) { province, city, area ->
                tv_receive_city.text = "${province.title}/${city.title}/${area.title}"
            }
        }

        iv_add_product.setOnClickListener {
            product_list_layout.addProductView()
        }

        tv_commit.setOnClickListener {
            val receiveName = et_receive_name.text.toString().trim()
            val receivePhone = et_receive_phone.text.toString().trim()
            val receiveCity = tv_receive_city.text.toString().trim()
            val receiveDetail = et_receive_detail_address.text.toString().trim()
            val checkedType = ship_type.checkedRadioButtonId
            val expressCompany = et_express_company.text.toString().trim()
            val expressNo = et_express_no.text.toString().trim()

            var shipType = "0"
            if (checkedType == R.id.rb_local) shipType = "1"

            product_list_layout.getShipProductList { msg, list ->
                if (!TextUtils.isEmpty(msg)) {
                    toastShow(msg)
                    return@getShipProductList
                }
                mPresenter?.startShip(receiveName, receivePhone, receiveCity, receiveDetail, shipType, expressCompany, expressNo, list!!)
            }

        }

    }

    override fun loadProductSuccess(productList: List<ProductBean>) {
        product_list_layout.init(productList)
        showContent()
    }

    override fun loadProductFail(errMsg: String) {
        showError()
    }


    override fun shipSuccess() {
        toastShow("发货成功")
        finish()
    }

    override fun shipFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_order, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.order) {
            //发货订单
            ShipOrderActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}