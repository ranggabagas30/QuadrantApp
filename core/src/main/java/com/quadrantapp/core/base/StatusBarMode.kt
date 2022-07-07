package com.quadrantapp.core.base

import android.graphics.Color
import android.view.View
import android.view.Window

enum class StatusBarMode (
    private val statusBarUi: Int
) {
    LIGHT(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR),
    DARK(0);

    fun setStatusBar(window: Window) {
        val newUi = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or statusBarUi

        if (newUi != window.decorView.systemUiVisibility) {
            window.apply {
                decorView.systemUiVisibility = newUi
                statusBarColor = Color.TRANSPARENT
            }
        }
    }
}