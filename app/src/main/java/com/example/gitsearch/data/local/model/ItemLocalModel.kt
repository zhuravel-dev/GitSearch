package com.example.gitsearch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Items")
data class ItemLocalModel(
    @PrimaryKey val id: Int,
    val node_id: String,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val html_url: String,
    val description: String,
    val fork: Boolean,
    val url: String,
    val forks_url: String,
    val keys_urls_url: String,
    val collaborators_url: String,
    val teams_url: String,
    val hooks_url: String,
    val issue_events_url: String,
    val events_url: String,
    val assignees_url: String,
    val branches_url: String,
    val tags_url: String,
    val watchers_count: Int,
    val language: String,
    val topics: List<String>,
    val watchers: Int,
)