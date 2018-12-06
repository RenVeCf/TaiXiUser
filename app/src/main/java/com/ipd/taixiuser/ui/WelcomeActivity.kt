package com.ipd.taixiuser.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.MainActivity
import com.ipd.taixiuser.R
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.ui.activity.account.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.layout_welcome.view.*


/**
 * Created by jumpbox on 2018/5/9.
 */
class WelcomeActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, WelcomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getBaseLayout(): Int = R.layout.activity_welcome

    override fun initView(bundle: Bundle?) {
        if (Build.VERSION.SDK_INT in 12..18) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions

        }
    }

    override fun loadData() {
        view_pager.adapter = object : PagerAdapter() {

            private val images = intArrayOf(R.mipmap.welcome_1, R.mipmap.welcome_2, R.mipmap.welcome_3)
            override fun getCount(): Int {
                return images.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_welcome, container, false)
                contentView.iv_bg.setImageResource(images[position])
                contentView.enter.setOnClickListener {
                    GlobalParam.setFirstEnter(false)
                    if (GlobalParam.isLogin()) {
                        MainActivity.launch(mActivity)
                    } else {
                        LoginActivity.launch(mActivity)
                    }
                    finish()
                }
                container.addView(contentView)
                return contentView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
    }

    override fun initListener() {
    }


}