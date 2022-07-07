package com.quadrantapp.service_price.di

import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class PriceDataModule {
    @Provides
    fun provideCoinDeskApi(retrofit: Retrofit): CoinDeskApi {
        return retrofit.create(CoinDeskApi::class.java)
    }
}