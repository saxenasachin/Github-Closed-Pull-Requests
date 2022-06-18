package com.sachinsaxena.common.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateTimeUtils {

    private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"
    private const val NO_DATE_FOUND = "No Date Found..."

    @SuppressLint("SimpleDateFormat")
    fun getDayWithMonthName(input: String): String {
        if (!input.isValid()) return NO_DATE_FOUND
        val dateFormatParse = SimpleDateFormat(DATE_FORMAT)
        val date = dateFormatParse.parse(input)
        val spf = SimpleDateFormat("dd MMM yyyy");
        date?.let {
            return spf.format(date).toString();
        }
        return NO_DATE_FOUND
    }
}