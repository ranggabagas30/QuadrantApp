package com.quadrantapp.service_price.domain.usecase

import com.nhaarman.mockitokotlin2.given
import com.quadrantapp.core.model.Result
import com.quadrantapp.core_test.CoroutineTestRule
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAllPriceHistoryUseCaseTest {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    // SUT
    private lateinit var getAllPriceHistoryUseCase: GetAllPriceHistoryUseCase

    @Mock
    private lateinit var repository: PriceHistoryDbRepository

    @Before
    fun setup() {
        getAllPriceHistoryUseCase = GetAllPriceHistoryUseCase(repository)
    }

    @Test
    fun default() {
        val actualResult = getAllPriceHistoryUseCase.default
        assertEquals(PriceHistoryEntity.DEFAULT_LIST, actualResult)
    }

    @Test
    fun build() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val expectedResult = Result(
                PriceHistoryEntity.DEFAULT_LIST
            )
            given(repository.getAllPriceHistory()).willReturn(expectedResult)
            val actualResult = getAllPriceHistoryUseCase.build(Unit)
            assertEquals(expectedResult, actualResult)
        }
    }
}