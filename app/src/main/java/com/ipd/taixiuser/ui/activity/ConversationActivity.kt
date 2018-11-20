package com.ipd.taixiuser.ui.activity

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import io.rong.imkit.fragment.ConversationFragment


class ConversationActivity : BaseUIActivity() {
    override fun getToolbarTitle(): String = "在线客服"

    override fun getContentLayout(): Int = R.layout.conversation

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.conversation) as ConversationFragment
        if (!fragment.onBackPressed()) {
            finish()
        }
    }

}