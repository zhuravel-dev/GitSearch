package com.example.gitsearch.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1)
abstract class DataDB : RoomDatabase() {
    abstract fun dataDao() : DataDao

}