package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.StockRecordParentBean
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.manage.StockRecordFragment
import kotlinx.android.synthetic.main.activity_stock_record.*

class StockRecordActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StockRecordActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "库存记录"

    override fun getContentLayout(): Int = R.layout.activity_stock_record

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, StockRecordFragment()).commit()
    }

    override fun initListener() {
    }

    fun setStockInfo(result: BaseResult<StockRecordParentBean>) {
        tv_cur_stock.text = result.data.num
        tv_total_stock.text = result.data.num1
    }


}