package com.quadrantapp.core.util

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toDateString(format: String = "d MMM yyyy"): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this * 1000L)
}

fun Long.toDate(): Date {
    return Date(this * 1000L)
}

fun Date.getRemainingDays(): Long {
    val diff = this.time - Calendar.getInstance().timeInMillis
    return TimeUnit.MILLISECONDS.toDays(diff)
}

fun Fragment.getDrawableCompat(@DrawableRes drawable: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), drawable)
}

fun Fragment.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(requireContext(), color)
}