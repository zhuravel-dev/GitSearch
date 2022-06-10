package com.example.gitsearch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String
)