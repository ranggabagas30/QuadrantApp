package com.quadrantapp.service_price.data.mapper

import com.quadrantapp.core.extension.toResult
import com.quadrantapp.core.model.Result
import com.quadrantapp.service_price.data.dto.CurrentPriceResultDto
import com.quadrantapp.service_price.domain.entity.BpiEntity
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse

class CurrentPriceResultDtoMapper(
    private val timeDtoMapper: TimeDtoMapper,
    private val bpiDtoMapper: BpiDtoMapper
) {
    operator fun invoke(from: CurrentPriceResultDto): Result<CurrentPriceResponse> {
        return from.toResult(from.let {
            CurrentPriceResponse(
                timeDtoMapper(it.time),
                it.disclaimer,
                it.chartName,
                hashMapOf<String, BpiEntity>().apply {
                    it.bpiList.entries.forEach{ entry ->
                        this[entry.key] = bpiDtoMapper(entry.value)
                    }
                }
            )
        })
    }
}