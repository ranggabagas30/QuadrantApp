package com.quadrantapp.core.extension

import android.content.Context
import com.quadrantapp.core.R
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.MinutesToSecs(): Long {
    return TimeUnit.MINUTES.toSeconds(this)
}

fun Long.SecsToMinutes(): Long {
    return TimeUnit.SECONDS.toMinutes(this)
}

fun Long.HoursToDay(): Long {
    return TimeUnit.HOURS.toDays(this)
}

fun Long.toMonth(): Long {
    return this / 1000 / 60 / 60 / 24 / 30
}

fun Long.miliToSeconds(): Long {
    return TimeUnit.MILLISECONDS.toSeconds(this)
}

fun Long.miliMinutes(): Long {
    return TimeUnit.MILLISECONDS.toMinutes(this)
}

fun Long.miliHours(): Long {
    return TimeUnit.MILLISECONDS.toHours(this)
}

fun Long.miliTodays(): Long {
    return TimeUnit.MILLISECONDS.toDays(this)
}


