package com.quadrantapp.service_price.di

import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository
import com.quadrantapp.service_price.domain.repository.LocationRepository
import com.quadrantapp.service_price.domain.repository.PriceHistoryDbRepository
import com.quadrantapp.service_price.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PriceUseCaseModule {
    @Provides
    @Singleton
    fun provideGetCurrentPriceUseCase(repository: CurrentPriceRepository): GetCurrentPriceUseCase {
        return GetCurrentPriceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNLastPriceHistoryUseCase(repository: PriceHistoryDbRepository): GetNLastPriceHistoryUseCase {
        return GetNLastPriceHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPriceHistoryUseCase(repository: PriceHistoryDbRepository): GetAllPriceHistoryUseCase {
        return GetAllPriceHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavePriceHistoryUseCase(repository: PriceHistoryDbRepository): SavePriceHistoryUseCase {
        return SavePriceHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(repository: LocationRepository): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteOldPriceHistoriesUseCase(repository: PriceHistoryDbRepository): DeleteOldPriceHistoriesUseCase {
        return DeleteOldPriceHistoriesUseCase(repository)
    }
}