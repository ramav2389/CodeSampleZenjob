package com.zenjob.android.browsr.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        const val dd_MMM_YY: String = "dd MMM YYYY"

        fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        fun getCurrentDateTime(): Date {
            return Calendar.getInstance().time
        }
    }
}