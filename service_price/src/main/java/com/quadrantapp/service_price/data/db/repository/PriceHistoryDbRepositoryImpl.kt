package com.quadrantapp.service_price.data.db.repository

import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.data.db.dao.PriceHistoryDao
import com.quadrantapp.service_price.data.db.mapper.PriceHistoryEntityMapper
import com.quadrantapp.service_price.data.db.mapper.PriceHistoryTableMapper
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository
import javax.inject.Inject

class PriceHistoryDbRepositoryImpl @Inject constructor(
    private val priceHistoryDao: PriceHistoryDao
): PriceHistoryDbRepository {

    override suspend fun getAllPriceHistory(): Result<List<PriceHistoryEntity>> {
        return Result(priceHistoryDao.getAll().mapNotNull { PriceHistoryTableMapper(it) })
    }

    override suspend fun getNLastPriceHistory(lastNumber: Int): Result<List<PriceHistoryEntity>> {
        return Result(priceHistoryDao.getNLastHistoryData(lastNumber).mapNotNull { PriceHistoryTableMapper(it) })
    }

    override suspend fun savePriceHistory(priceHistoryEntities: List<PriceHistoryEntity>): Result<Unit> {
        return Result(priceHistoryDao.insertAll(
            priceHistoryEntities.map {
                PriceHistoryEntityMapper(it)
            }
        ))
    }

    override suspend fun deleteOldPriceHistories(): Result<Unit> {
        return Result(priceHistoryDao.deleteYesterdayData())
    }
}