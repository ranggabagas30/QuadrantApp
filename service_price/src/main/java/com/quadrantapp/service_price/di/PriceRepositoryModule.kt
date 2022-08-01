package com.quadrantapp.service_price.di

import com.quadrantapp.core.util.LocationHelper
import com.quadrantapp.service_price.data.db.dao.PriceHistoryDao
import com.quadrantapp.service_price.data.db.repository.PriceHistoryDbRepositoryImpl
import com.quadrantapp.service_price.data.location.repository.LocationRepositoryImpl
import com.quadrantapp.service_price.data.webservice.CurrentPriceResultDtoMapper
import com.quadrantapp.service_price.data.webservice.mapper.BpiDtoMapper
import com.quadrantapp.service_price.data.webservice.mapper.TimeDtoMapper
import com.quadrantapp.service_price.data.webservice.repository.CurrentPriceRepositoryImpl
import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository
import com.quadrantapp.service_price.domain.repository.LocationRepository
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PriceRepositoryModule {
    @Provides
    @Singleton
    fun provideCurrentPriceRepository(coinDeskApi: CoinDeskApi): CurrentPriceRepository {
        return CurrentPriceRepositoryImpl(
            coinDeskApi,
            CurrentPriceResultDtoMapper(
                TimeDtoMapper(),
                BpiDtoMapper()
            )
        )
    }

    @Provides
    @Singleton
    fun providePriceHistoryDbRepository(priceHistoryDao: PriceHistoryDao): PriceHistoryDbRepository {
        return PriceHistoryDbRepositoryImpl(priceHistoryDao)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationHelper: LocationHelper): LocationRepository {
        return LocationRepositoryImpl(locationHelper)
    }
}