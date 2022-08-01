package com.quadrantapp.feature_price.sub.chart.ui.workmanager

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.nhaarman.mockitokotlin2.given
import com.quadrantapp.core.model.Result
import com.quadrantapp.core_test.CoroutineTestRule
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse
import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository
import com.quadrantapp.service_price.domain.usecase.GetCurrentPriceUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetCurrencyWorkerTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var executor: Executor
    private lateinit var getCurrentPriceUseCase: GetCurrentPriceUseCase

    @Mock
    private lateinit var currentPriceRepository: CurrentPriceRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
        getCurrentPriceUseCase = GetCurrentPriceUseCase(currentPriceRepository)
    }

    @Test
    fun doWork() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val worker = TestListenableWorkerBuilder<GetCurrencyWorker>(context)
                .setWorkerFactory(GetCurrencyWorkerFactory(getCurrentPriceUseCase))
                .build()

            val result = Result(
                CurrentPriceResponse.DEFAULT
            )
            given(currentPriceRepository.getCurrentPrice()).willReturn(result)
            val workInfoResult = worker.doWork()
            assertEquals(workInfoResult, ListenableWorker.Result.success())
        }
    }
}