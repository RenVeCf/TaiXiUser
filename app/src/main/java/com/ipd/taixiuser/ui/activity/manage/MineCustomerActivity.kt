package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.manage.MineCustomerFragment

class MineCustomerActivity : BaseUIActivity() {
    companion object {
        val NORMAL = 0
        val CHOOSE = 1
        fun launch(activity: Activity, type: Int = NORMAL) {
            val intent = Intent(activity, MineCustomerActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我的客户"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mType by lazy { intent.getIntExtra("type", NORMAL) }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, MineCustomerFragment.newInstance(mType)).commit()
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_customer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.new_customer) {
            //添加客户
            NewCustomerActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}