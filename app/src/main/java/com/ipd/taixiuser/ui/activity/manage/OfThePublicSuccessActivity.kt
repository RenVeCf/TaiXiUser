package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.OfTheBankBean
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_of_the_public.*

class OfThePublicSuccessActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, ofTheBankBean: OfTheBankBean) {
            val intent = Intent(activity, OfThePublicSuccessActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("info", ofTheBankBean)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "提交成功"

    override fun getContentLayout(): Int = R.layout.activity_of_the_public

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mInfo by lazy { intent.extras.getSerializable("info") as OfTheBankBean }
    override fun loadData() {
        tv_company_name.text = mInfo.company
        tv_bank_address.text = mInfo.name
        tv_bank_account.text = mInfo.code

    }

    override fun initListener() {
    }


}