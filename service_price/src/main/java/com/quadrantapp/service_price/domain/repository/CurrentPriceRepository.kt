package com.quadrantapp.service_price.domain.repository

import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse

interface CurrentPriceRepository {
    suspend fun getCurrentPrice(): Result<CurrentPriceResponse>
}