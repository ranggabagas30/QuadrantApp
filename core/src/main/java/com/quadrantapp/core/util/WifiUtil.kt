package com.quadrantapp.core.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object WifiUtil {

    fun getWifiManager(context: Context): WifiManager {
        return context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    fun isWifiConnected(wifiManager: WifiManager): Boolean {
        var isWifiConnected = true

        if (!wifiManager.isWifiEnabled) {
            isWifiConnected = false
        } else {
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.networkId == -1 && wifiInfo.supplicantState == SupplicantState.DISCONNECTED) {
                isWifiConnected = false
            }
        }

        return isWifiConnected
    }

    private fun isWifiPermission(fragment: Fragment) =
        ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    fun testPermission(fragment: Fragment, requestCode: Int): Boolean {
        if (!isWifiPermission(fragment)) {
            fragment.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), requestCode
            )
            return false
        }

        return true
    }

}