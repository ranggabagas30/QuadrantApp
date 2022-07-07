package com.quadrantapp.service_price.di

import com.quadrantapp.service_price.data.mapper.BpiDtoMapper
import com.quadrantapp.service_price.data.mapper.CurrentPriceResultDtoMapper
import com.quadrantapp.service_price.data.mapper.TimeDtoMapper
import com.quadrantapp.service_price.data.webservice.repository.CurrentPriceRepositoryImpl
import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PriceRepositoryModule {
    @Provides
    fun provideCurrentPriceRepository(coinDeskApi: CoinDeskApi): CurrentPriceRepository {
        return CurrentPriceRepositoryImpl(
            coinDeskApi,
            CurrentPriceResultDtoMapper(
                TimeDtoMapper(),
                BpiDtoMapper()
            )
        )
    }
}