package com.quadrantapp.core.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object SoftKeyboardUtil {
    fun showKeyboard(view: View) {
        view.requestFocus()
        view.isActivated
        view.isFocused
        view.isSelected
        view.isEnabled
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun hideKeyboard(view: View) {
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken, 0
        )
    }
}