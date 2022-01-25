package com.example.gitsearch.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey var userId: Int = 0,
    var prevKey: Int? = null,
    var nextKey: Int? = null
)
