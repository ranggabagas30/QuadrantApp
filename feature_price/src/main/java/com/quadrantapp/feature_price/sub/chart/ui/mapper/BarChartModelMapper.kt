package com.quadrantapp.feature_price.sub.chart.ui.mapper

import com.quadrantapp.core.util.DateUtil
import com.quadrantapp.feature_price.sub.chart.ui.model.BarChartModel
import com.quadrantapp.feature_price.sub.chart.ui.model.CurrencyModel
import com.quadrantapp.service_price.domain.entity.CurrentPriceResponse

object BarChartModelMapper {
    operator fun invoke(from: CurrentPriceResponse): BarChartModel {
        val date = DateUtil.toDate(from.timeEntity.updatedIso, DateUtil.DATE_FORMAT_ISO_8601)
        return BarChartModel(
            hashMapOf<String, CurrencyModel>().apply {
                from.bpiList.entries.forEach {
                    this[it.key] =
                        CurrencyModel(
                            currencyCode = it.value.code,
                            currentPrice = it.value.rateFloat
                        )
                }
            },
            "${date?.hours}:${date?.minutes}",
            "${date?.toString()}"
        )
    }
}