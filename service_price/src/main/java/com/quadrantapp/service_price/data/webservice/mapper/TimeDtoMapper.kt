package com.quadrantapp.service_price.data.webservice.mapper

import com.quadrantapp.service_price.data.webservice.dto.TimeDto
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