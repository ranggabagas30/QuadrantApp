package com.quadrantapp.service_price.data.location.repository

import android.location.Location
import com.quadrantapp.core.model.Result
import com.quadrantapp.core.util.LocationHelper
import com.quadrantapp.service_price.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationHelper: LocationHelper
): LocationRepository {
    override suspend fun getCurrentLocation(): Result<Location?> {
        return Result(locationHelper.getCurrentLocation())
    }
}