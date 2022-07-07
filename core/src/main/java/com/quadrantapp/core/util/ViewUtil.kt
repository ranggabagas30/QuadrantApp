package com.quadrantapp.core.util

import android.app.Activity
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View

object ViewUtil {

    fun setSizeRatioToScreen(activity: Activity, imageView: View, imageWidth: Int, imageHeight: Int, heightModifier: Int = 0) {
        val imageRatio = imageWidth / imageHeight
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        var screenWidth = displayMetrics.widthPixels

        val layoutParams = imageView.layoutParams
        layoutParams.height = (screenWidth * imageRatio) + heightModifier
        imageView.layoutParams = layoutParams
    }

    fun toPX(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun toDP(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }
}