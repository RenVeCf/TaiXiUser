package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import kotlinx.android.synthetic.main.activity_store_pay.*

class StorePayActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StorePayActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "进货"

    override fun getContentLayout(): Int = R.layout.activity_store_pay

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {


    }

    override fun initListener() {
        ll_permission.setOnClickListener {
            WebActivity.launch(mActivity, WebActivity.URL, "http://www.baidu.com", "总代权限")
        }
        tv_pay.setOnClickListener {
            PayResultActivity.launch(mActivity,PayResultActivity.STORE)
            finish()
        }
    }


}