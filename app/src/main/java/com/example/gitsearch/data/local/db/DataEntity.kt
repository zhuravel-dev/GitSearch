package com.example.gitsearch.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataFromGitHub")
class DataEntity(
    @PrimaryKey
    @ColumnInfo(name = "q") val q: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "pageSize") val pageSize: Int
)