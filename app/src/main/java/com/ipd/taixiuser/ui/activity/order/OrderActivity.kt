package com.ipd.taixiuser.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.order.OrderFragment
import com.ipd.taixiuser.utils.OrderUtils
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, OrderActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "发货记录"

    override fun getContentLayout(): Int = R.layout.activity_order

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        val categorys = OrderUtils.buildOrderCategory()
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = OrderFragment.newInstance(categorys[position].requestType)
            override fun getCount(): Int = categorys.size
            override fun getPageTitle(position: Int): CharSequence? = categorys[position].name
        }
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun initListener() {
    }


}