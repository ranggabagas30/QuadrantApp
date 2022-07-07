package com.quadrantapp.di.router

import com.quadrantapp.feature_price.sub.chart.ChartContract
import com.quadrantapp.router.ChartRouterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeaturePriceRouterModule {

    @Provides
    @Singleton
    fun provideChartRouter(): ChartContract.Router = ChartRouterImpl()
}