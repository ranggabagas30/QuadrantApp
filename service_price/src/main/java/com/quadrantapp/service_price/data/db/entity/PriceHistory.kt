package com.quadrantapp.service_price.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_history")
data class PriceHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "USD") val usd: Float,
    @ColumnInfo(name = "GBP") val gbp: Float,
    @ColumnInfo(name = "EUR") val eur: Float,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "updatedISO") val updatedISO: String,
    @ColumnInfo(name = "updatedTimestamp") val updatedTimestamp: Long
) {
}