package com.jp.ruutchallenge.extensions

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun String.getTimeFromDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return try {
        val date = formatter.parse(this) ?: Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val minutes = cal.get(Calendar.MINUTE)
        "${cal.get(Calendar.HOUR_OF_DAY)}:" +
            (if (minutes < 10) "0" else "") +
            "$minutes"
    } catch (e: Exception) {
        "0:00"
    }
}

fun String.toTwoDecimalDouble(): Double {
    return this.toDouble().toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
}