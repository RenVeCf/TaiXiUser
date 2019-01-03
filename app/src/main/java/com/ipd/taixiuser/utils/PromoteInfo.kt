package com.ipd.taixiuser.utils

import android.view.View
import com.ipd.taixiuser.R

object PromoteInfo {
    const val RETAIL = 0
    const val GIFT_BOX = 1
    const val VIP = 2
    const val PROXY = 3
    const val LEADER_PROXY = 4
    const val COMPANY = 5
    const val AREA_CEO = 6
    const val SHAREHOLDER = 7


    fun getLevelByResId(resId: Int): Int {
        return when (resId) {
            R.id.ll_shareholder -> SHAREHOLDER
            R.id.ll_area_ceo -> AREA_CEO
            R.id.ll_company -> COMPANY
            R.id.ll_leader_proxy -> LEADER_PROXY
            R.id.ll_proxy -> PROXY
            R.id.ll_vip -> VIP
            R.id.ll_gift_box -> GIFT_BOX
            else -> RETAIL
        }
    }

    fun getIconByResId(view: View, status: Int): Int {
        return when (view.id) {
            R.id.ll_shareholder -> {
                when (status) {
                    0 -> R.mipmap.vip8_dark
                    else -> R.mipmap.vip8_light
                }
            }
            R.id.ll_area_ceo -> {
                when (status) {
                    0 -> R.mipmap.vip7_dark
                    else -> R.mipmap.vip7_light
                }
            }
            R.id.ll_company -> {
                when (status) {
                    0 -> R.mipmap.vip6_dark
                    else -> R.mipmap.vip6_light
                }
            }
            R.id.ll_leader_proxy -> {
                when (status) {
                    0 -> R.mipmap.vip5_dark
                    else -> R.mipmap.vip5_light
                }
            }
            R.id.ll_proxy -> {
                when (status) {
                    0 -> R.mipmap.vip4_dark
                    else -> R.mipmap.vip4_light
                }
            }
            R.id.ll_vip -> {
                when (status) {
                    0 -> R.mipmap.vip3_dark
                    else -> R.mipmap.vip3_light
                }
            }
            R.id.ll_gift_box -> {
                when (status) {
                    0 -> R.mipmap.vip2_dark
                    else -> R.mipmap.vip2_light
                }
            }
            else -> {
                when (status) {
                    0 -> R.mipmap.vip1_dark
                    else -> R.mipmap.vip1_light
                }
            }
        }
    }
//    fun setStyleByLevel(context: Context, view: ViewGroup, level: Int) {
//        when (view.id) {
//            R.id.ll_shareholder -> {
//                val textView = view.getChildAt(1) as TextView
//                setStyleByLevel(context, textView, level, SHAREHOLDER)
//            }
//            R.id.ll_area_ceo -> {
//                val textView = view.getChildAt(1) as TextView
//                setStyleByLevel(context, textView, level, AREA_CEO)
//            }
//            R.id.ll_company -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, COMPANY)
//            }
//            R.id.ll_leader_proxy -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, LEADER_PROXY)
//            }
//            R.id.ll_proxy -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, PROXY)
//            }
//            R.id.ll_vip -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, VIP)
//            }
//            R.id.ll_gift_box -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, GIFT_BOX)
//            }
//            else -> {
//                val textView = view.getChildAt(0) as TextView
//                setStyleByLevel(context, textView, level, RETAIL)
//            }
//        }
//    }
//
//    private fun setStyleByLevel(context: Context, textView: TextView, curLevel: Int, resLevel: Int) {
//        when {
//            curLevel > resLevel -> {
//                textView.setBackgroundResource(R.drawable.shape_promote_light)
//                textView.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
//            }
//            curLevel == resLevel -> {
//                textView.setBackgroundResource(R.drawable.shape_promote_current)
//                textView.setTextColor(context.resources.getColor(R.color.white))
//            }
//            else -> {
//                textView.setBackgroundResource(R.drawable.shape_promote_dark)
//                textView.setTextColor(context.resources.getColor(R.color.black))
//            }
//        }
//    }

}