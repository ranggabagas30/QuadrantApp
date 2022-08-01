package com.quadrantapp.service_price.domain.entity

data class PriceHistoryEntity(
    val USD: Float,
    val GBP: Float,
    val EUR: Float,
    val latitude: String,
    val longitude: String,
    val updatedISO: String,
    val updatedTimestamp: Long
) {
    companion object {
        val DEFAULT = PriceHistoryEntity(
            0.0f,
            0.0f,
            0.0f,
            "",
            "",
            "",
            0
        )
        val DEFAULT_LIST = listOf<PriceHistoryEntity>()
    }
}