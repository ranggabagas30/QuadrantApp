package com.quadrantapp.service_price.domain.usecase

import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse
import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository

class GetCurrentPriceUseCase(
    private val repository: CurrentPriceRepository
): BaseUseCase<Unit, CurrentPriceResponse>() {
    override val default: CurrentPriceResponse
        get() = CurrentPriceResponse.DEFAULT

    override suspend fun build(param: Unit): Result<CurrentPriceResponse> {
        return repository.getCurrentPrice()
    }
}