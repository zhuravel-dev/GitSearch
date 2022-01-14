package com.example.gitsearch.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataFromGitHubEntity::class], version = 1)
abstract class DataFromGitHubDB : RoomDatabase() {
    abstract fun dataDao() : DataFromGitHubDao

}