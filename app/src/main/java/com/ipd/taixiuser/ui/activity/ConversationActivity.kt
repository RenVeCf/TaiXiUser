package com.ipd.taixiuser.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.KeyEvent
import com.ipd.taixiuser.R
import io.rong.imkit.fragment.ConversationFragment


class ConversationActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.conversation) as ConversationFragment
        if (!fragment.onBackPressed()) {
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return super.onKeyDown(keyCode, event)
    }
}