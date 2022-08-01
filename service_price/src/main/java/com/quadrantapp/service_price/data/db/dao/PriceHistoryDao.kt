package com.quadrantapp.service_price.data.db.dao

import androidx.room.*
import com.quadrantapp.service_price.data.db.entity.PriceHistory

@Dao
interface PriceHistoryDao {
    @Query("SELECT * FROM price_history")
    suspend fun getAll(): List<PriceHistory>

    @Query("SELECT * FROM price_history order by updatedTimestamp desc limit :lastNumber")
    suspend fun getNLastHistoryData(lastNumber: Int): List<PriceHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(priceHistories: List<PriceHistory>)

    @Delete
    suspend fun delete(priceHistory: PriceHistory)

    @Query("DELETE FROM price_history WHERE updatedTimestamp < :deleteTimestamp")
    suspend fun deleteOldData(deleteTimestamp: Long)

    @Query("DELETE FROM price_history WHERE updatedTimestamp < CURRENT_TIMESTAMP * 1000")
    suspend fun deleteOldDataNow()

    @Query("DELETE FROM price_history WHERE updatedTimestamp <= strftime('%s', datetime('now', '-1 day'))")
    suspend fun deleteYesterdayData()
}