package com.quadrantapp.service_price.data.mapper

import com.quadrantapp.service_price.data.dto.TimeDto
import com.quadrantapp.service_price.domain.entity.TimeEntity

class TimeDtoMapper {
    operator fun invoke(from: TimeDto): TimeEntity {
        return TimeEntity(
            from.updated,
            from.updatedIso,
            from.updatedUk
        )
    }
}