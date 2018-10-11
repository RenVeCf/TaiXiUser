package com.ipd.taixiuser.utils

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
}