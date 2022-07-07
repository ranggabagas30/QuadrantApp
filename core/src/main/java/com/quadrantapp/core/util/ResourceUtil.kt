package com.quadrantapp.core.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat

object ResourceUtil {

    fun getDrawableFromAttr(activity: Activity, resId: Int): Drawable? {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(resId, typedValue, true)
        return ContextCompat.getDrawable(activity, typedValue.resourceId)
    }

    fun getResourceIdFromAttr(activity: Activity, resId: Int): Int? {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(resId, typedValue, true)
        return typedValue.resourceId
    }

    fun getColorFromAttr(activity: Activity, resId: Int): Int {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(resId, typedValue, true)
        return ContextCompat.getColor(activity, typedValue.resourceId)
    }

    fun getStringFromAttr(context: Context, resId: Int) : String {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(resId, typedValue, true)
        return context.getString(typedValue.resourceId)
    }
}