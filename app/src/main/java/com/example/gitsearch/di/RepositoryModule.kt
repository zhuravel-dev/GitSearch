package com.example.gitsearch.di

import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.repository.MainRepositoryImpl
import com.example.gitsearch.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @ExperimentalPagingApi
    @Binds
    @Singleton
    abstract fun bindNameRepository(nameRepositoryImpl: MainRepositoryImpl): MainRepository

}