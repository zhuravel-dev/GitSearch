package com.example.gitsearch.di

import android.content.Context
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.ui.extensions.isNetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

   /* @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }*/

    @Singleton
    @OfflineCacheInterceptor
    @Provides
    fun provideOfflineCacheInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = if (isNetworkAvailable(context))
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60
                ).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        /*httpLoggingInterceptor: HttpLoggingInterceptor*/
        @ApplicationContext context: Context,
        @OfflineCacheInterceptor offlineCacheInterceptor: Interceptor
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cache = Cache(httpCacheDirectory, cacheSize)

        return OkHttpClient
            .Builder()
            .cache(cache)
            .addInterceptor(offlineCacheInterceptor)
           // .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CacheInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OfflineCacheInterceptor