package com.quadrantapp.service_price.data.webservice.repository

import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.data.mapper.CurrentPriceResultDtoMapper
import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse
import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository

class CurrentPriceRepositoryImpl(
    private val coinDeskApi: CoinDeskApi,
    private val currentPriceResultDtoMapper: CurrentPriceResultDtoMapper
): CurrentPriceRepository {
    override suspend fun getCurrentPrice(): Result<CurrentPriceResponse> {
        return currentPriceResultDtoMapper(
            coinDeskApi.getCurrentPrice()
        )
    }
}