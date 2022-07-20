package com.example.gitsearch.di

import android.content.Context
import com.example.gitsearch.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @OfflineCacheInterceptor
    @Provides
    fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                return@Interceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                val offlineRequest: Request = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .removeHeader("Pragma")
                    .build()
                return@Interceptor chain.proceed(offlineRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context,
        @OfflineCacheInterceptor offlineCacheInterceptor: Interceptor
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val cache = Cache(httpCacheDirectory,  1024 * 1024) // TODO get maxSze from device

        return OkHttpClient
            .Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(okHttpClient: OkHttpClient): ApiService {
        val BASE_URL = "https://api.github.com/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CacheInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OfflineCacheInterceptor