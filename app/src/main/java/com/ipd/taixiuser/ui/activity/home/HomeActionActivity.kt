package com.ipd.taixiuser.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.home.HomeActionFragment

class HomeActionActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, title: String, actionType: Int) {
            val intent = Intent(activity, HomeActionActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("actionType", actionType)
            activity.startActivity(intent)
        }
    }

    private val mActionType: Int by lazy { intent.getIntExtra("actionType", 0) }
    override fun getToolbarTitle(): String = intent.getStringExtra("title")

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeActionFragment.newInstance(mActionType)).commit()
    }

    override fun initListener() {
    }

}