package com.quadrantapp.service_price.domain.usecase

import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository

class GetAllPriceHistoryUseCase(
    private val repository: PriceHistoryDbRepository
): BaseUseCase<Unit, List<PriceHistoryEntity>>() {

    override val default: List<PriceHistoryEntity>
        get() = PriceHistoryEntity.DEFAULT_LIST

    override suspend fun build(param: Unit): Result<List<PriceHistoryEntity>> {
        return repository.getAllPriceHistory()
    }
}