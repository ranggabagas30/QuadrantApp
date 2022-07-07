package com.quadrantapp.di.network

import android.content.Context
import com.quadrantapp.core.BuildConfig
import com.quadrantapp.core.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return NetworkUtil.provideUnsafeOkHttpClient(
            context
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return NetworkUtil.provideRetrofit(okHttpClient, BuildConfig.BASE_API_URL)
    }
}