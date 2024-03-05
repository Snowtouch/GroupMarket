package com.snowtouch.groupmarket.core.presentation.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timestampToDate(timestamp: Long, format: String = "yyyy-MM-dd"): String {
    val date = Date(timestamp)
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(date)
}