package com.quadrantapp.service_price.data.db.mapper

import com.quadrantapp.service_price.data.db.entity.PriceHistory
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity

object PriceHistoryEntityMapper {
    operator fun invoke(from: PriceHistoryEntity): PriceHistory {
        return PriceHistory(
            usd = from.USD,
            gbp = from.GBP,
            eur = from.EUR,
            longitude = from.longitude,
            latitude = from.latitude,
            updatedISO = from.updatedISO,
            updatedTimestamp = from.updatedTimestamp
        )
    }
}