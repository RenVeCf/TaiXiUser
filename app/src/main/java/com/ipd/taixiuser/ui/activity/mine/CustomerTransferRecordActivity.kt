package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.mine.CustomerTransferRecordFragment
import kotlinx.android.synthetic.main.activity_customer_transfer_record.*

class CustomerTransferRecordActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, CustomerTransferRecordActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "客户转移记录"

    override fun getContentLayout(): Int = R.layout.activity_customer_transfer_record

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = CustomerTransferRecordFragment.newInstance(position)
            override fun getCount(): Int = 2
            override fun getPageTitle(position: Int): CharSequence? = if (position == 0) "我转移的" else "我接受的"
        }
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initListener() {
    }


}