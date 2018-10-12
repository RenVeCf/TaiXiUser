package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProductAdapter
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_move_stock_confirm.*

class MoveStockConfirmActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MoveStockConfirmActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "移仓确认"

    override fun getContentLayout(): Int = R.layout.activity_move_stock_confirm

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = ProductAdapter(mActivity, listOf(ProductBean(), ProductBean(), ProductBean(), ProductBean()), {

        })
    }

    override fun initListener() {
        tv_commit.setOnClickListener {
            toastShow(true, "移仓成功")
            finish()
        }
    }


}