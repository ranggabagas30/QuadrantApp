package com.quadrantapp.service_price.domain.repository

import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity

interface PriceHistoryDbRepository {
    suspend fun getAllPriceHistory(): Result<List<PriceHistoryEntity>>
    suspend fun getNLastPriceHistory(lastNumber: Int): Result<List<PriceHistoryEntity>>
    suspend fun savePriceHistory(priceHistoryEntities: List<PriceHistoryEntity>): Result<Unit>
    suspend fun deleteOldPriceHistories(): Result<Unit>
}