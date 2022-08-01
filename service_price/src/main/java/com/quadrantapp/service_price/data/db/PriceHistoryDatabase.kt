package com.quadrantapp.service_price.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quadrantapp.service_price.data.db.PriceHistoryDatabase.Companion.ROOM_VERSION
import com.quadrantapp.service_price.data.db.dao.PriceHistoryDao
import com.quadrantapp.service_price.data.db.entity.PriceHistory

@Database(entities = [PriceHistory::class], version = ROOM_VERSION, exportSchema = false)
abstract class PriceHistoryDatabase: RoomDatabase() {

    abstract fun priceHistoryDao(): PriceHistoryDao

    companion object {
        const val DATABASE_NAME = "quadrant_price_history"
        const val ROOM_VERSION = 1
    }
}