package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.manage.ProxyListFragment

class ProxyListActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, proxy: Int, teamName: String) {
            val intent = Intent(activity, ProxyListActivity::class.java)
            intent.putExtra("proxy", proxy)
            intent.putExtra("teamName", teamName)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = intent.getStringExtra("teamName")

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mProxy: Int by lazy { intent.getIntExtra("proxy", -1) }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, ProxyListFragment.newInstance(mProxy)).commit()
    }

    override fun initListener() {
    }


}