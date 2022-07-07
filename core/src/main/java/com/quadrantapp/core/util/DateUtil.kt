package com.quadrantapp.core.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val TAG = this::class.java.simpleName

    fun getNow(): Date {
        return Calendar.getInstance(Locale.getDefault()).time
    }

    private fun millisToDate(millis: Long, format: String): String {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).format(millis)
        } catch (e: Exception) {
            ""
        }
    }

    fun isDateSameDay(firstDateTime: Long, secondDateTime: Long, format: String): Boolean {
        val firstDateString = millisToDate(firstDateTime, format)
        val secondDateString = millisToDate(secondDateTime, format)
        return firstDateString == secondDateString
    }

    fun toDate(
        date: String,
        formatDateSource: String = "yyyy-MM-dd",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date? {
        return try {
            val formatter = SimpleDateFormat(formatDateSource)
            formatter.timeZone = timeZone
            formatter.parse(date) ?: Date()
        } catch (e: Exception) {
            null
        }
    }

    const val DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ"
}