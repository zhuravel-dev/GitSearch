package com.example.gitsearch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String,
    val avatar_url: String,
    val url: String,
    val type: String
)