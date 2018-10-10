package com.ipd.taixiuser.utils

import java.text.SimpleDateFormat

object StringUtils {
    fun getDateBySecond(second: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(second * 1000)
    }
}