package com.quadrantapp.service_price.domain.usecase

import android.location.Location
import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.domain.repository.LocationRepository

class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
): BaseUseCase<Unit, Location?>() {
    override val default: Location?
        get() = null

    override suspend fun build(param: Unit): Result<Location?> {
        return locationRepository.getCurrentLocation()
    }
}