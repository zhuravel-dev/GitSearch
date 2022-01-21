package com.example.gitsearch.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_info")
data class DataEntity(
    @PrimaryKey val q: String,
    val page: Int,
    val pageSize: Int
)