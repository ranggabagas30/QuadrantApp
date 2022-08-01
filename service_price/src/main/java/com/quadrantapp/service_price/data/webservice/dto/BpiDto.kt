package com.quadrantapp.service_price.data.webservice.dto

import com.google.gson.annotations.SerializedName

data class BpiDto(
    @SerializedName("code") val code: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("description") val description: String,
    @SerializedName("rate_float") val rateFloat: Float
)