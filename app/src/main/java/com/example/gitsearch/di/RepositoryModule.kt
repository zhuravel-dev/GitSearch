package com.example.gitsearch.di

import com.example.gitsearch.data.repository.MainRepositoryImpl
import com.example.gitsearch.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNameRepository(nameRepositoryImpl: MainRepositoryImpl): MainRepository

}