package com.example.gitsearch.data.local.model

import androidx.room.PrimaryKey

data class OwnerLocalModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val node_id: String,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val following_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val type: String,
)