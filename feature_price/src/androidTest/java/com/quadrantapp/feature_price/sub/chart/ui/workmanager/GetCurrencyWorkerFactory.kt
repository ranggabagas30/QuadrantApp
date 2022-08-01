package com.quadrantapp.feature_price.sub.chart.ui.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.quadrantapp.service_price.domain.usecase.GetCurrentPriceUseCase

class GetCurrencyWorkerFactory(private val getCurrentPriceUseCase: GetCurrentPriceUseCase): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return GetCurrencyWorker(appContext, workerParameters, getCurrentPriceUseCase)
    }
}