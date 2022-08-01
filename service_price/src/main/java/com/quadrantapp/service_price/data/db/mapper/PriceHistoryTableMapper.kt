package com.quadrantapp.service_price.data.db.mapper

import com.quadrantapp.service_price.data.db.entity.PriceHistory
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity

object PriceHistoryTableMapper {
    operator fun invoke(from: PriceHistory?): PriceHistoryEntity? {
        return from?.let {
            PriceHistoryEntity(
                it.usd,
                it.gbp,
                it.eur,
                it.latitude,
                it.longitude,
                it.updatedISO,
                it.updatedTimestamp
            )
        }
    }
}