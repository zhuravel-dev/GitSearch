package com.example.gitsearch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerLocalModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var login: String? = null,
    var avatar_url: String? = null,
    var url: String? = null,
)