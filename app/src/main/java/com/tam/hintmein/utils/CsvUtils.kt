package com.tam.hintmein.utils

fun wrapTextContainingComma(text: String): String =
    if (text.contains(','))
        "\"$text\""
    else
        text

fun getHintsCsvName(): String =
    "hints${getTodayAsYYYYMMDD()}.csv"

const val HINT_HEADER = "domain,username,hint"