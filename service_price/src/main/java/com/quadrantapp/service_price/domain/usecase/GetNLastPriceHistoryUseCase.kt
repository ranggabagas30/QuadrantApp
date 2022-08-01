package com.quadrantapp.service_price.domain.usecase

import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository

class GetNLastPriceHistoryUseCase(
    private val priceHistoryDbRepository: PriceHistoryDbRepository
): BaseUseCase<Int, List<PriceHistoryEntity>>() {

    override val default: List<PriceHistoryEntity>
        get() = PriceHistoryEntity.DEFAULT_LIST

    override suspend fun build(param: Int): Result<List<PriceHistoryEntity>> {
        return priceHistoryDbRepository.getNLastPriceHistory(param)
    }
}