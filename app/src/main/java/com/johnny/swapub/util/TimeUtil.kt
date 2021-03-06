package com.johnny.swapub.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    @JvmStatic
    fun StampToDate(time: Long): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun StampToDatex(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun DateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        return simpleDateFormat.parse(date).time
    }
}