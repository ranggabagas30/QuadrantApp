package com.quadrantapp.service_price.domain.entity

data class TimeEntity(
    val updated: String,
    val updatedIso: String,
    val updatedUk: String
) {
    companion object {
        val DEFAULT = TimeEntity("", "", "")
    }
}