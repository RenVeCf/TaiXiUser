package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.StockHistoryCategoryBean
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.manage.MoveStockHistoryFragment
import kotlinx.android.synthetic.main.activity_order.*

class MoveStockHistoryActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MoveStockHistoryActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "移仓记录"

    override fun getContentLayout(): Int = R.layout.activity_order

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        val categorys = listOf(StockHistoryCategoryBean("我发出的", 0), StockHistoryCategoryBean("我接收的", 1))
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = MoveStockHistoryFragment.newInstance(categorys[position].requestType)
            override fun getCount(): Int = categorys.size
            override fun getPageTitle(position: Int): CharSequence? = categorys[position].name
        }
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun initListener() {
    }

}