package com.ipd.taixiuser.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat

object StringUtils {
    val levels = arrayListOf(
            "零售",
            "VIP",
            "代理",
            "总代理",
            "分公司"
    )

    fun getLevelIdByLevel(str: String): String {
        return when (str) {
            "零售" -> "0"
            "VIP" -> "1"
            "代理" -> "2"
            "总代理" -> "3"
            "分公司" -> "4"
            else -> ""
        }
    }

    fun getLevelById(id: String): String {
        return when (id) {
            "0" -> "零售"
            "1" -> "VIP"
            "2" -> "代理"
            "3" -> "总代理"
            "4" -> "分公司"
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
}