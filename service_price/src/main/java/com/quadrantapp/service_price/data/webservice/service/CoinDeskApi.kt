package com.quadrantapp.service_price.data.webservice.service

import com.quadrantapp.service_price.data.webservice.dto.CurrentPriceResultDto
import retrofit2.http.GET

interface CoinDeskApi {
    @GET("bpi/currentprice.json")
    suspend fun getCurrentPrice(): CurrentPriceResultDto
}