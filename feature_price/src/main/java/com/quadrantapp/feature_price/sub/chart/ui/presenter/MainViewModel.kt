package com.quadrantapp.feature_price.sub.chart.ui.presenter

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.quadrantapp.core.base.BaseViewModel
import com.quadrantapp.core.base.Constants
import com.quadrantapp.core.extension.*
import com.quadrantapp.core.model.Error
import com.quadrantapp.core.util.DateUtil
import com.quadrantapp.feature_price.sub.chart.ui.workmanager.GetCurrencyWorker
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.usecase.DeleteOldPriceHistoriesUseCase
import com.quadrantapp.service_price.domain.usecase.GetAllPriceHistoryUseCase
import com.quadrantapp.service_price.domain.usecase.GetNLastPriceHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    getAllPriceHistoryUseCase: GetAllPriceHistoryUseCase,
    getNLastPriceHistoryUseCase: GetNLastPriceHistoryUseCase,
    private val deleteOldPriceHistoriesUseCase: DeleteOldPriceHistoriesUseCase
): BaseViewModel(application) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val getPriceHistory: StatefulLiveData<Unit, List<PriceHistoryEntity>> =
        StatefulLiveData(getAllPriceHistoryUseCase, viewModelScope)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val getNLastPriceHistories: StatefulLiveData<Int, List<PriceHistoryEntity>> =
        StatefulLiveData(getNLastPriceHistoryUseCase, viewModelScope)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val deleteOldPriceHistories: StatefulLiveData<Long, Unit> =
        StatefulLiveData(deleteOldPriceHistoriesUseCase, viewModelScope)

    private val getCurrentPriceOutputWorkInfo: LiveData<List<WorkInfo>>
    private val workManager = WorkManager.getInstance(application)

    private val hasNotShowInitLastPriceHistories = NonNullMutableLiveData(true)

    init {
        getCurrentPriceOutputWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.TAG_GET_CURRENT_PRICE)
    }

    override fun getKillableStatefulLiveData(): List<StatefulLiveData<*, *>> {
        return listOf(getPriceHistory, getNLastPriceHistories, deleteOldPriceHistories)
    }

    fun getAllPriceHistories() {
        getPriceHistory.get(Unit)
    }

    fun getNLastPriceHistories(lastNumber: Int) {
        getNLastPriceHistories.get(lastNumber)
    }

    fun isHasNotShowInitLastPriceHistories() = hasNotShowInitLastPriceHistories.value

    private suspend fun runDeleteOldEntriesIfNecessary(): StatefulResult<Unit> {
        return either {
            deleteOldPriceHistoriesUseCase.build(DateUtil.getNow().time)
        }.toResult()
    }

    fun listenGetPriceHistories(
        lifecycleOwner: LifecycleOwner,
        showHistories: (List<PriceHistoryEntity>) -> Unit,
        showError: (Error) -> Unit
    ) {
        getPriceHistory.listen(
            lifecycleOwner,
            onSuccess = showHistories,
            onError = showError
        )
    }

    fun listenGetNLastPriceHistories(
        lifecycleOwner: LifecycleOwner,
        showNLastHistories: (List<PriceHistoryEntity>) -> Unit,
        showError: (Error) -> Unit
    ) {
        getNLastPriceHistories.listen(
            lifecycleOwner,
            onSuccess = showNLastHistories,
            onError = showError,
            onComplete = {
                hasNotShowInitLastPriceHistories.value = false
            }
        )
    }

    fun listenCurrentPriceWorker(lifecycleOwner: LifecycleOwner) {
        getCurrentPriceOutputWorkInfo.observe(lifecycleOwner) { listOfWorkInfos ->
            if (listOfWorkInfos.isNullOrEmpty())
                return@observe

            Timber.d("observe workinfos: $listOfWorkInfos")
            val workInfo = listOfWorkInfos[0]
            if (workInfo.state.isFinished) {
                repeat()
            }
        }
    }

    private fun repeat() {
        viewModelScope.launch {
            runDeleteOldEntriesIfNecessary()
            getAllPriceHistories()
            triggerCurrentPriceWorker()
        }
    }

    fun triggerCurrentPriceWorker() {
        val getCurrentPriceWorkerRequest =
            OneTimeWorkRequestBuilder<GetCurrencyWorker>()
                .addTag(Constants.TAG_GET_CURRENT_PRICE)
                .setInitialDelay(1, TimeUnit.HOURS)
                .build()

        workManager
            .enqueueUniqueWork(
                Constants.GET_CURRENT_PRICE_UNIQUE_NAME,
                ExistingWorkPolicy.KEEP,
                getCurrentPriceWorkerRequest
            )
    }
}