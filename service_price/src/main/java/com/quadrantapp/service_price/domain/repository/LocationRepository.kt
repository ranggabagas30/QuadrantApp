package com.quadrantapp.service_price.domain.repository

import android.location.Location
import com.quadrantapp.core.model.Result

interface LocationRepository {
    suspend fun getCurrentLocation(): Result<Location?>
}