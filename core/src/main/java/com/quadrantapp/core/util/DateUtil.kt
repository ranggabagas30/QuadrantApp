package com.quadrantapp.core.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val TAG = this::class.java.simpleName

    fun getNow(
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        return Calendar.getInstance(timeZone).time
    }

    private fun millisToDate(millis: Long, format: String): String {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).format(millis)
        } catch (e: Exception) {
            ""
        }
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

    fun toTimestamp(
        date: String,
        formatDateSource: String = "yyyy-MM-dd",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Long {
        return toDate(date, formatDateSource, timeZone)?.time?: 0L
    }

    fun isMidnight(
        currentTimestamp: Long,
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Boolean {
        val formatter = SimpleDateFormat("HH:mm")
        formatter.timeZone = timeZone
        val timeString = formatter.format(Date(currentTimestamp))
        return "00:00" == timeString
    }

    const val DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ"
    const val DATE_FORMAT_SQLITE = "yyyy-MM-dd hh:mm:ss"
}