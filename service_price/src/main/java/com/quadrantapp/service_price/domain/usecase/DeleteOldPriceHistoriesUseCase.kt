package com.quadrantapp.service_price.domain.usecase

import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.core.util.DateUtil
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository

class DeleteOldPriceHistoriesUseCase(
    private val repository: PriceHistoryDbRepository
): BaseUseCase<Long, Unit>() {
    override val default: Unit
        get() = Unit

    override suspend fun build(param: Long): Result<Unit> {
        return if (DateUtil.isMidnight(param))
            repository.deleteOldPriceHistories()
        else
            Result(Unit)
    }
}