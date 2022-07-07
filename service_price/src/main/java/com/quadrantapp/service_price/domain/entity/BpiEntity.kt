package com.quadrantapp.service_price.domain.entity

data class BpiEntity(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    val rateFloat: Float
) {
    companion object {
        val DEFAULT = BpiEntity(
            "",
            "",
            "",
            "",
            0.0f
        )
    }
}