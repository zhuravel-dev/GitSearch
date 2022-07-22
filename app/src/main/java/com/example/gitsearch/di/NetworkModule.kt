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
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

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
    @Provides
    fun onlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }
    }

    @Singleton
    @OfflineCacheInterceptor
    @Provides
    fun offlineInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            if (!isNetworkAvailable(context)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .addInterceptor(offlineInterceptor(context))
            .addNetworkInterceptor(onlineInterceptor())
            .cache(cache)
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
annotation class OfflineCacheInterceptor