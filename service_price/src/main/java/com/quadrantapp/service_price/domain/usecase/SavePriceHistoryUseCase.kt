package com.quadrantapp.service_price.domain.usecase

import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository

class SavePriceHistoryUseCase(
    private val priceHistoryDbRepository: PriceHistoryDbRepository
): BaseUseCase<List<PriceHistoryEntity>, Unit>() {

    override val default: Unit
        get() = Unit

    override suspend fun build(param: List<PriceHistoryEntity>): Result<Unit> {
        return priceHistoryDbRepository.savePriceHistory(param)
    }
}