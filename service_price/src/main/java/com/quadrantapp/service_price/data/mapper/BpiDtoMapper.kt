package com.quadrantapp.service_price.data.mapper

import com.quadrantapp.service_price.data.dto.BpiDto
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