package com.quadrantapp.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


object TelephonyUtil {
    fun dial(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        context.startActivity(intent)
    }
}