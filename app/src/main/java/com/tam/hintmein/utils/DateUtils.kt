package com.tam.hintmein.utils

import java.util.*

enum class MonthName(val id: Int) {
    Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12)
}

const val MONTH_INDEX_IN_JAVA_DATE = 1
const val DAY_INDEX_IN_JAVA_DATE = 2
const val YEAR_INDEX_IN_JAVA_DATE = 5

fun getTodayAsYYYYMMDD(): Int {
    val today = Calendar.getInstance().time
    val todayDetails = today.toString().split(" ")

    var monthNumber: Int? = null
    for (monthName in MonthName.values()) {
        val javaMonth = todayDetails[MONTH_INDEX_IN_JAVA_DATE]
        if (monthName.toString().equals(javaMonth, ignoreCase = true)) {
            monthNumber = monthName.id
        }
    }
    monthNumber ?: return -1
    val monthNumberString = if (monthNumber < 10) "0$monthNumber" else "$monthNumber"
    val day = todayDetails[DAY_INDEX_IN_JAVA_DATE].toInt()
    val dayString = if (day < 10) "0$day" else "$day"
    val year = todayDetails[YEAR_INDEX_IN_JAVA_DATE].toInt()

    val yyyymmddString = "$year$monthNumberString$dayString"
    return Integer.parseInt(yyyymmddString)
}