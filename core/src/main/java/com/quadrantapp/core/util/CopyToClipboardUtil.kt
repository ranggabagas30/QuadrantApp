package com.quadrantapp.core.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object CopyToClipboardUtil {

    private const val DEFAULT_KEY = "COPIED_TEXT"

    fun copy(context: Context, key: String = DEFAULT_KEY, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(key, text)
        clipboard.setPrimaryClip(clip)
    }
}