package com.quadrantapp.core.util

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.quadrantapp.core.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkUtil {

    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideRetrofitScalar(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun provideOkHttpClient(
        context: Context,
        vararg interceptors: Interceptor
    ): OkHttpClient {
        return provideOkHttpClient(context, null, *interceptors)
    }

    fun provideOkHttpClient(
        context: Context,
        certificatePinner: CertificatePinner?,
        vararg interceptors: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        if (certificatePinner != null) builder.certificatePinner(certificatePinner)
        return builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context))
            .addLoggingInterceptor()
            .build()
    }


    fun provideUnsafeOkHttpClient(
        context: Context,
        vararg interceptors: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context))
            .addLoggingInterceptor()
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            p0: Array<out X509Certificate>?,
                            p1: String?
                        ) {
                            //"Not yet implemented"
                        }

                        override fun checkServerTrusted(
                            p0: Array<out X509Certificate>?,
                            p1: String?
                        ) {
                            //"Not yet implemented"
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

            // Install the al  l-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { p0, p1 -> true })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor { message ->
                Timber.v(
                    "MyXLUltimate",
                    message
                )
            }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(interceptor)
        }
        return this
    }
}