package com.quadrantapp.core.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationHelper(context: Context) {
    // FusedLocationProviderClient - Main class for receiving location updates.
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private var locationRequest: LocationRequest? = null

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private var locationCallback: LocationCallback? = null

    // This will store current location info
    var currentLocation: Location? = null

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        if (locationRequest != null && locationCallback != null && hasPermission(context))
            fusedLocationProviderClient.requestLocationUpdates(locationRequest!!, locationCallback!!, Looper.myLooper())
    }

    fun observeLocation(onSuccess: ((Location?) -> Unit)? = null) {
        locationRequest = LocationRequest().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = TimeUnit.SECONDS.toMillis(10)

            // Sets the fastest rate for active location updates.
            // This interval is exact, and your application will never
            // receive updates more frequently than this value
            fastestInterval = TimeUnit.SECONDS.toMillis(5)

            // Sets the maximum time when batched location
            // updates are delivered. Updates may be
            // delivered sooner than this interval
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.lastLocation?.let {
                    currentLocation = it
                    onSuccess?.invoke(currentLocation)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation2(context: Context) {
        if (hasPermission(context)) {
            fusedLocationProviderClient.lastLocation
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful) {
                    Timber.d("latest known location: ${it.result}")
                    currentLocation = it.result
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            try {
                observeLocation()
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    if (it.isSuccessful) {
                        stopObserveLocation()
                        cancellableContinuation.resume(it.result)
                    }
                    else
                        cancellableContinuation.resumeWithException(it.exception?.cause?: Throwable())
                }
            } catch (e: Exception) {
                cancellableContinuation.resumeWithException(e)
            }
        }
    }

    fun stopObserveLocation() {
        val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Location Callback removed.")
            } else {
                Timber.d("Failed to remove Location Callback.")
            }
        }
    }

    companion object {
        val requestCode = 101
        fun hasPermission(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode
            )
        }
    }
}