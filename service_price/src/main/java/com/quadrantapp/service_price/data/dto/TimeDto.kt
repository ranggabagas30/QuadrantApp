package com.quadrantapp.service_price.data.dto

import com.google.gson.annotations.SerializedName

data class TimeDto(
    @SerializedName("updated") val updated: String,
    @SerializedName("updatedISO") val updatedIso: String,
    @SerializedName("updateduk") val updatedUk: String
) {
}