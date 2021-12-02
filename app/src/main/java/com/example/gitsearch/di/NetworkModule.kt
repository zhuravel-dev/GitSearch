package com.example.gitsearch.di

import com.example.gitsearch.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    @Provides
    @Singleton
    fun provideAPI() : ApiService {
        val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"
         return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}