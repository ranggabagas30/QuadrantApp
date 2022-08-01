package com.quadrantapp.service_price.data.webservice.mapper

import com.quadrantapp.service_price.data.webservice.dto.BpiDto
import com.quadrantapp.service_price.domain.entity.BpiEntity

class BpiDtoMapper {
    operator fun invoke(from: BpiDto): BpiEntity {
        return BpiEntity(
            from.code,
            from.symbol,
            from.rate,
            from.description,
            from.rateFloat
        )
    }
}