package com.example.gitsearch.data.remote.model

import com.squareup.moshi.Json

data class ItemsResponse (@Json(name= "items") val items : List<Item> )

data class Item (
	@Json(name= "id") val id : Int,
	@Json(name= "name") val name : String,
	@Json(name= "owner") val owner : Owner,
	@Json(name= "description") val description : String,
	@Json(name= "url") val url : String,
	@Json(name= "updated_at") val updated_at : String,
	@Json(name= "stargazers_count") val stargazers_count : Int,
	@Json(name= "watchers_count") val watchers_count : Int,
	@Json(name= "language") val language : String,
	@Json(name= "topics") val topics : List<String>,
	@Json(name= "watchers") val watchers : Int
)