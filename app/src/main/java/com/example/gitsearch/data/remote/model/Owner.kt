package com.example.gitsearch.data.remote.model

import com.squareup.moshi.Json

data class Owner (
	@Json(name = "login") val login : String,
	@Json(name = "id") val id : Int,
	@Json(name = "avatar_url") val avatar_url : String,
	@Json(name = "url") val url : String
)