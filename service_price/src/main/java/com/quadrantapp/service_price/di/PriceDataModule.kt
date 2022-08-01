package com.quadrantapp.service_price.di

import android.content.Context
import androidx.room.Room
import com.quadrantapp.core.util.LocationHelper
import com.quadrantapp.service_price.data.db.PriceHistoryDatabase
import com.quadrantapp.service_price.data.db.dao.PriceHistoryDao
import com.quadrantapp.service_price.data.webservice.service.CoinDeskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PriceDataModule {
    @Provides
    @Singleton
    fun provideCoinDeskApi(retrofit: Retrofit): CoinDeskApi {
        return retrofit.create(CoinDeskApi::class.java)
    }

    @Provides
    @Singleton
    fun providePriceHistoryDatabase(@ApplicationContext context: Context): PriceHistoryDatabase {
        return Room.databaseBuilder(
            context,
            PriceHistoryDatabase::class.java,
            PriceHistoryDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePriceHistoryDao(priceHistoryDatabase: PriceHistoryDatabase): PriceHistoryDao {
        return priceHistoryDatabase.priceHistoryDao()
    }

    @Provides
    @Singleton
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper {
        return LocationHelper(context)
    }
}