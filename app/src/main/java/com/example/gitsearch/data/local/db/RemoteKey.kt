package com.example.gitsearch.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
