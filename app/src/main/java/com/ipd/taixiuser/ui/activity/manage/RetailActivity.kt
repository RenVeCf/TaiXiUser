package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
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
    }

    override fun initListener() {
        ll_city.setOnClickListener {
            CityUtils.getInstance().showSelectDialog(mActivity, { province, city, area ->
                tv_receive_city.text = "${province.title}/${city.title}/${area.title}"
            })
        }

        iv_add_product.setOnClickListener {
            product_list_layout.addProductView()
        }
    }


    override fun shipSuccess() {

    }

    override fun shipFail(errMsg: String) {
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