package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_new_bank_card.*


class NewBankCardActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, NewBankCardActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "添加银行卡"

    override fun getContentLayout(): Int = R.layout.activity_new_bank_card

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_commit.setOnClickListener {
            finish()
        }


    }


}