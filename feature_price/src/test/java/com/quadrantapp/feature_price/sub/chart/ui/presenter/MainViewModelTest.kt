package com.quadrantapp.feature_price.sub.chart.ui.presenter

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockitokotlin2.given
import com.quadrantapp.core_test.CoroutineTestRule
import com.quadrantapp.service_price.domain.usecase.DeleteOldPriceHistoriesUseCase
import com.quadrantapp.service_price.domain.usecase.GetAllPriceHistoryUseCase
import com.quadrantapp.service_price.domain.usecase.GetNLastPriceHistoryUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    // SUT
    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var getAllPriceHistoryUseCase: GetAllPriceHistoryUseCase

    @Mock
    private lateinit var getNLastPriceHistoryUseCase: GetNLastPriceHistoryUseCase

    @Mock
    private lateinit var deleteOldPriceHistoriesUseCase: DeleteOldPriceHistoriesUseCase

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    private lateinit var lifecycle: Lifecycle

    @Before
    fun setup() {
        given(lifecycleOwner.lifecycle).willReturn(lifecycle)
        given(lifecycle.currentState).willReturn(Lifecycle.State.STARTED)
        mainViewModel = MainViewModel(
            application,
            getAllPriceHistoryUseCase,
            getNLastPriceHistoryUseCase,
            deleteOldPriceHistoriesUseCase
        )
        mainViewModel.getPriceHistory.listen(lifecycleOwner, {}, {})
        mainViewModel.getNLastPriceHistories.listen(lifecycleOwner, {}, {})
        mainViewModel.deleteOldPriceHistories.listen(lifecycleOwner, {}, {})
    }

    @Test
    fun getKillableStatefulLiveData() {
        val list = mainViewModel.getKillableStatefulLiveData()
        assertEquals(mainViewModel.getPriceHistory, list[0])
        assertEquals(mainViewModel.getNLastPriceHistories, list[1])
        assertEquals(mainViewModel.deleteOldPriceHistories, list[2])
    }
}