package com.quadrantapp.feature_price.sub.chart.ui.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.quadrantapp.core.base.BaseViewModel
import com.quadrantapp.core.extension.NonNullMutableLiveData
import com.quadrantapp.core.extension.StatefulLiveData
import com.quadrantapp.core.util.LiveDataUtil.add
import com.quadrantapp.feature_price.sub.chart.ui.mapper.BarChartModelMapper
import com.quadrantapp.feature_price.sub.chart.ui.model.BarChartModel
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse
import com.quadrantapp.service_price.domain.usecase.GetCurrentPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getCurrentPriceUseCase: GetCurrentPriceUseCase
): BaseViewModel() {

    val currentPrice: StatefulLiveData<Unit, CurrentPriceResponse> =
        StatefulLiveData(getCurrentPriceUseCase, viewModelScope)

    val trackedDataCurrency = NonNullMutableLiveData(mutableListOf<BarChartModel>())

    override fun getKillableStatefulLiveData(): List<StatefulLiveData<*, *>> {
        return listOf(currentPrice)
    }

    fun getCurrentPrice() {
        currentPrice.get(Unit)
    }

    fun listenCurrentPrice(lifecycleOwner: LifecycleOwner) {
        currentPrice.listen(
            lifecycleOwner,
            onSuccess = {
                Timber.d("onSuccess -> $it")
                trackedDataCurrency.add(
                    BarChartModelMapper(it)
                )
            },
            onError = {
                Timber.e("onError -> $it")
            }
        )
    }
}