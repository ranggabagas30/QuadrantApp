package com.quadrantapp.service_price.data.webservice.repository

import com.nhaarman.mockitokotlin2.given
import com.quadrantapp.core.model.Result
import com.quadrantapp.core_test.CoroutineTestRule
import com.quadrantapp.service_price.data.webservice.CurrentPriceResultDtoMapper
import com.quadrantapp.service_price.data.webservice.dto.CurrentPriceResultDto
import com.quadrantapp.service_price.data.webservice.dto.TimeDto
import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse
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
class CurrentPriceRepositoryImplTest {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    // SUT
    private lateinit var currentPriceRepositoryImpl: CurrentPriceRepositoryImpl

    @Mock
    private lateinit var coinDeskApi: CoinDeskApi

    @Mock
    private lateinit var currentPriceResultDtoMapper: CurrentPriceResultDtoMapper

    @Before
    fun setup() {
        currentPriceRepositoryImpl = CurrentPriceRepositoryImpl(coinDeskApi, currentPriceResultDtoMapper)
    }

    @Test
    fun getCurrentPrice() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val resultDto = CurrentPriceResultDto(
                TimeDto("", "", ""),
                "",
                "",
                hashMapOf()
            )
            val expectedResult = Result(
                CurrentPriceResponse.DEFAULT
            )
            given(coinDeskApi.getCurrentPrice()).willReturn(resultDto)
            given(currentPriceResultDtoMapper.invoke(resultDto)).willReturn(expectedResult)
            val actualResult = currentPriceRepositoryImpl.getCurrentPrice()
            assertEquals(expectedResult.data, actualResult.data)
        }
    }
}