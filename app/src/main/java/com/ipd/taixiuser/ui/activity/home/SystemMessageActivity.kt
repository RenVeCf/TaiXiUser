package com.ipd.taixiuser.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.home.SystemMessageFragment

class SystemMessageActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SystemMessageActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "系统消息"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, SystemMessageFragment()).commit()
    }

    override fun initListener() {
    }

}