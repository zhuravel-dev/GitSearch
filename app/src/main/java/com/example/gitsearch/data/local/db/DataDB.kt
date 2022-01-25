package com.example.gitsearch.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel

@Database(entities = [DataEntity::class, ItemLocalModel::class, RemoteKey::class, OwnerLocalModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataDB : RoomDatabase() {
    abstract fun getDataDao() : DataDao
    abstract fun getKeysDao(): RemoteKeyDao
}