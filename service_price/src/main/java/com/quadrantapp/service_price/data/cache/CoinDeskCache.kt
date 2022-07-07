package com.quadrantapp.service_price.data.cache

import android.content.Context
import com.quadrantapp.service_price.data.mapper.CurrentPriceResultDtoMapper

class CoinDeskCache(
    private val context: Context,
    private val currentPriceResultDtoMapper: CurrentPriceResultDtoMapper
) {
    /*fun getAllHistoryCurrency(): List<CurrentPriceResultDto> {
        val resultList = CacheUtil.getAll(context)?.values
        resultList?.forEach { item ->
            item?.let {
                val resultDto = Gson().fromJson<CurrentPriceResultDto>(it as String)
                val resultEntity = currentPriceResultDtoMapper(resultDto)

            }
        }
    }*/
}