package com.quadrantapp.service_price.di

import com.quadrantapp.service_price.domain.repository.CurrentPriceRepository
import com.quadrantapp.service_price.domain.usecase.GetCurrentPriceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PriceUseCaseModule {
    @Provides
    fun provideGetCurrentPriceUseCase(repository: CurrentPriceRepository): GetCurrentPriceUseCase {
        return GetCurrentPriceUseCase(repository)
    }
}