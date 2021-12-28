package com.example.gitsearch.data.model

import com.squareup.moshi.Json

data class RepositoryDetailModel(
    @Json(name = "avatar_url") val avatar_url: String,
    @Json(name = "login") val login: String,
    @Json(name = "repository_name") val name: String,
    @Json(name = "repository_description") val description: String,
    @Json(name = "programing_languages") val language: String,
    @Json(name = "topics") val topics: List<String>
)