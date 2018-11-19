package com.ipd.taixiuser.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.matter_search.*

class SearchActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.matter_search

    override fun getToolbarTitle(): String = "搜索"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val searchFragment by lazy { SearchFragment() }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, searchFragment).commit()
    }

    override fun initListener() {
        tv_cancel.setOnClickListener { finish() }

        val inputMethodManager = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索
                val searchStr = et_search.text.toString().trim()
                if (TextUtils.isEmpty(searchStr)) {
                    toastShow("请输入搜索内容")
                    return@setOnEditorActionListener false
                }
                inputMethodManager.hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                searchFragment.search(searchStr)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }


}