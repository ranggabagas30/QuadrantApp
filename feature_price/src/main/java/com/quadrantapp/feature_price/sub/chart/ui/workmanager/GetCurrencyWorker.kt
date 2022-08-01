package com.quadrantapp.feature_price.sub.chart.ui.workmanager

import android.content.Context
import android.location.Location
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.quadrantapp.core.base.Constants
import com.quadrantapp.core.extension.StatefulResult
import com.quadrantapp.core.extension.either
import com.quadrantapp.core.extension.toResult
import com.quadrantapp.core.util.DateUtil
import com.quadrantapp.core.util.makeStatusNotification
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.usecase.GetCurrentLocationUseCase
import com.quadrantapp.service_price.domain.usecase.GetCurrentPriceUseCase
import com.quadrantapp.service_price.domain.usecase.SavePriceHistoryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@HiltWorker
class GetCurrencyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCurrentPriceUseCase: GetCurrentPriceUseCase,
    private val savePriceHistoryUseCase: SavePriceHistoryUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            Constants.NOTIFICATION_ID, makeStatusNotification("Syncing..", applicationContext)
        )
    }

    override suspend fun doWork(): Result {
        val finalResult = coroutineScope {
            val locationStatefulResult = async {
                either {
                    getCurrentLocationUseCase.build(Unit)
                }.toResult()
            }.await()

            val currentPriceStatefulResult = async {
                either {
                    getCurrentPriceUseCase.build(Unit)
                }.toResult()
            }.await()

            val priceHistoryEntity: PriceHistoryEntity? = when (currentPriceStatefulResult) {
                is StatefulResult.Success -> {
                    val location: Location? = if (locationStatefulResult.succeeded) {
                        val locationResult = locationStatefulResult as StatefulResult.Success
                        locationResult.data
                    } else
                        null

                    currentPriceStatefulResult.data.let {
                        PriceHistoryEntity(
                            it.bpiList["USD"]?.rateFloat?: 0.0f,
                            it.bpiList["GBP"]?.rateFloat?: 0.0f,
                            it.bpiList["EUR"]?.rateFloat?: 0.0f,
                            location?.latitude?.toString()?: "0.0",
                            location?.longitude?.toString()?: "0.0",
                            it.timeEntity.updatedIso,
                            DateUtil.toTimestamp(
                                it.timeEntity.updatedIso,
                                DateUtil.DATE_FORMAT_ISO_8601
                            )
                        )
                    }
                }
                else -> null
            }

            priceHistoryEntity?.let {
                either {
                    savePriceHistoryUseCase.build(listOf(it))
                }.toResult()
            }
        } ?: return Result.failure()

        return if (finalResult.succeeded)
            Result.success()
        else
            Result.failure()
    }
}