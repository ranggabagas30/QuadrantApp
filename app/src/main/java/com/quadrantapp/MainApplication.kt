package com.quadrantapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.gu.toolargetool.TooLargeTool
import com.quadrantapp.core.util.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        val builder = Configuration.Builder()
            .setWorkerFactory(workerFactory)
        return if (BuildConfig.DEBUG) {
            builder.setMinimumLoggingLevel(Log.DEBUG)
                .build()
        } else {
            builder.setMinimumLoggingLevel(Log.ERROR)
                .build()
        }
    }

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