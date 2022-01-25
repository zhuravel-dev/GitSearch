package com.example.gitsearch.data.remote.model

import com.squareup.moshi.Json

data class ItemsResponse (@Json(name= "items") val items : List<Item> )

data class Item (
	@Json(name= "id") val id : Int? = null,
	@Json(name= "name") val name : String? = null,
	@Json(name= "owner") val owner : Owner? = null,
	@Json(name= "description") val description : String? = null,
	@Json(name= "url") val url : String? = null,
	@Json(name= "updated_at") val updated_at : String? = null,
	@Json(name= "stargazers_count") val stargazers_count : Int? = null,
	@Json(name= "watchers_count") val watchers_count : Int? = null,
	@Json(name= "language") val language : String? = null,
	@Json(name= "topics") val topics : List<String>? = null,
	@Json(name= "watchers") val watchers : Int? = null
)