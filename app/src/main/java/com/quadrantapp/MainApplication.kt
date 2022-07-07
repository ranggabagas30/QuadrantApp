package com.quadrantapp

import android.app.Application
import com.gu.toolargetool.TooLargeTool
import com.quadrantapp.core.util.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // tooLargeTool
        if (BuildConfig.DEBUG)
            TooLargeTool.startLogging(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}