package com.ipd.taixiuser.utils

import android.text.TextUtils
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat

object StringUtils {
    val levels = arrayListOf(
            "零售",
            "礼盒装",
            "VIP",
            "代理",
            "总代理",
            "分公司"
    )
    val addCustomerLevels = arrayListOf(
            "零售",
            "礼盒装",
            "VIP",
            "代理"
    )

    fun getLevelIdByLevel(str: String): String {
        return when (str) {
            "零售" -> "0"
            "礼盒装" -> "1"
            "VIP" -> "2"
            "代理" -> "3"
            "总代理" -> "4"
            "分公司" -> "5"
            else -> ""
        }
    }

    fun getLevelById(id: String): String {
        return when (id) {
            "0" -> "零售"
            "1" -> "礼盒装"
            "2" -> "VIP"
            "3" -> "代理"
            "4" -> "总代理"
            "5" -> "分公司"
            else -> ""
        }
    }

    fun getDateBySecond(second: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(second * 1000)
    }

    /**
     * 最多2位小数   120.0  120.01 120.12
     * 如果上千(>= 1000)并且是整数就显示整数部分 3224 3224.2
     *
     * @param d
     * @return
     */
    fun beautifulDouble(d: Double): String {
        val str = d.toString()
        try {
            if (str.contains(".")) {
                val s = str.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val length = s.length
                if (length > 2) {
                    val big = BigDecimal(d)
                    val res = big.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
                    val df = DecimalFormat("######0.000")
                    return df.format(res)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return d.toString() + ""
    }


    fun fixedPicStr(picStr: String): String {
        if (TextUtils.isEmpty(picStr)) {
            return picStr
        }
        var fixPicStr = picStr
        if (fixPicStr.last() == '，') {
            fixPicStr = fixPicStr.substring(0, fixPicStr.length - 1)
        }
        return fixPicStr
    }


    fun splitImages(picStr: String): List<String> {
        if (TextUtils.isEmpty(picStr)) {
            return arrayListOf()
        }
        var fixPicStr = picStr
        if (fixPicStr.last() == '，') {
            fixPicStr = fixPicStr.substring(0, picStr.length - 1)
        }

        if (fixPicStr.contains("，")) {
            return fixPicStr.split("，")
        }
        return arrayListOf(fixPicStr)
    }

    fun splitImagesV2(picStr: String): List<String> {
        if (TextUtils.isEmpty(picStr)) {
            return arrayListOf()
        }
        var fixPicStr = picStr
        if (fixPicStr.last() == ',') {
            fixPicStr = fixPicStr.substring(0, picStr.length - 1)
        }

        if (fixPicStr.contains(",")) {
            return fixPicStr.split(",")
        }
        return arrayListOf(fixPicStr)
    }
}