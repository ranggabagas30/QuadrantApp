package com.quadrantapp.core.util

import android.Manifest
import android.app.Activity
import pub.devrel.easypermissions.EasyPermissions

object SmsUtil {
    fun testPermission(
        activity: Activity,
        message: String,
        beforeRequestAsked: (() -> Unit)? = null
    ): Boolean {
        if (!EasyPermissions.hasPermissions(activity, Manifest.permission.RECEIVE_SMS)) {
            if (beforeRequestAsked != null) {
                beforeRequestAsked()
            }
            EasyPermissions.requestPermissions(activity, message, 1, Manifest.permission.RECEIVE_SMS)
            return false
        }
        return true
    }
}