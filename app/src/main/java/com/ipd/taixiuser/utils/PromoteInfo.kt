package com.ipd.taixiuser.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.taixiuser.R

object PromoteInfo {
    const val RETAIL = 0
    const val VIP = 1
    const val PROXY = 2
    const val LEADER_PROXY = 3
    const val COMPANY = 4
    const val AREA_CEO = 5
    const val SHAREHOLDER = 6


    fun getLevelByResId(resId: Int): Int {
        return when (resId) {
            R.id.ll_shareholder -> SHAREHOLDER
            R.id.ll_area_ceo -> AREA_CEO
            R.id.ll_company -> COMPANY
            R.id.ll_leader_proxy -> LEADER_PROXY
            R.id.ll_proxy -> PROXY
            R.id.ll_vip -> VIP
            else -> RETAIL
        }
    }

    fun setStyleByLevel(context: Context, view: ViewGroup, level: Int) {
        when (view.id) {
            R.id.ll_shareholder -> {
                val textView = view.getChildAt(1) as TextView
                setStyleByLevel(context, textView, level, SHAREHOLDER)
            }
            R.id.ll_area_ceo -> {
                val textView = view.getChildAt(1) as TextView
                setStyleByLevel(context, textView, level, AREA_CEO)
            }
            R.id.ll_company -> {
                val textView = view.getChildAt(0) as TextView
                setStyleByLevel(context, textView, level, COMPANY)
            }
            R.id.ll_leader_proxy -> {
                val textView = view.getChildAt(0) as TextView
                setStyleByLevel(context, textView, level, LEADER_PROXY)
            }
            R.id.ll_proxy -> {
                val textView = view.getChildAt(0) as TextView
                setStyleByLevel(context, textView, level, PROXY)
            }
            R.id.ll_vip -> {
                val textView = view.getChildAt(0) as TextView
                setStyleByLevel(context, textView, level, VIP)
            }
            else -> {
                val textView = view.getChildAt(0) as TextView
                setStyleByLevel(context, textView, level, RETAIL)
            }
        }
    }

    private fun setStyleByLevel(context: Context, textView: TextView, curLevel: Int, resLevel: Int) {
        when {
            curLevel > resLevel -> {
                textView.setBackgroundResource(R.drawable.shape_promote_light)
                textView.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
            }
            curLevel == resLevel -> {
                textView.setBackgroundResource(R.drawable.shape_promote_current)
                textView.setTextColor(context.resources.getColor(R.color.white))
            }
            else -> {
                textView.setBackgroundResource(R.drawable.shape_promote_dark)
                textView.setTextColor(context.resources.getColor(R.color.black))
            }
        }
    }

}