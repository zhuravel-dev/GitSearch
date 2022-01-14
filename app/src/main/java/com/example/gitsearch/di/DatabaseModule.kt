/*
package com.example.gitsearch.di

import android.content.Context
import androidx.room.Room
import com.example.gitsearch.data.local.room.DataFromGitHubDB
import com.example.gitsearch.data.local.room.DataFromGitHubDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context): DataFromGitHubDB {
        return Room
            .databaseBuilder(application, DataFromGitHubDB::class.java, "dataFromGitHub-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(dataFromGitHubDB: DataFromGitHubDB) : DataFromGitHubDao {
        return dataFromGitHubDB.dataDao()
    }

}*/
