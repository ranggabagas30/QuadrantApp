package com.quadrantapp.service_price.domain.entity

data class CurrentPriceResponse(
    val timeEntity: TimeEntity,
    val disclaimer: String,
    val chartName: String,
    val bpiList: HashMap<String, BpiEntity>
) {
    companion object {
        val DEFAULT = CurrentPriceResponse(
            TimeEntity.DEFAULT,
            "",
            "",
            hashMapOf()
        )
    }
}