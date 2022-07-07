package com.quadrantapp.service_price.data.dto

import com.google.gson.annotations.SerializedName
import com.quadrantapp.core.model.ResultDto

data class CurrentPriceResultDto (
    @SerializedName("time") val time: TimeDto,
    @SerializedName("disclaimer") val disclaimer: String,
    @SerializedName("chartName") val chartName: String,
    @SerializedName("bpi") val bpiList: HashMap<String, BpiDto>
): ResultDto() {
}