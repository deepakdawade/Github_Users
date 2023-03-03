package com.dev2d.githubusers.di

import com.dev2d.githubusers.networking.NetworkService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/"
private const val NETWORK_CALL_TIMEOUT = 30L // IN MINUTE
private const val isDebugMode = true

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCookieManager(): CookieManager = CookieManager()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cookieManager: CookieManager
    ): OkHttpClient {
        val loggingEventListener = if (isDebugMode)
            LoggingEventListener.Factory()
        else null

        val httpLoggingInterceptor =
            if (isDebugMode) HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            } else null
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                httpLoggingInterceptor?.let { addInterceptor(it) }
                loggingEventListener?.let { eventListenerFactory(it) }
                connectTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            }
            .dispatcher(
                Dispatcher().apply {
                    // Allow for high number of concurrent image fetches on same host.
                    maxRequestsPerHost = 15
                }
            )
            .build()
        return okHttpClient.newBuilder()
            .readTimeout(
                NETWORK_CALL_TIMEOUT,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                NETWORK_CALL_TIMEOUT,
                TimeUnit.SECONDS
            )
            .cookieJar(okhttp3.JavaNetCookieJar(cookieManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        }.build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService = retrofit.create()
}