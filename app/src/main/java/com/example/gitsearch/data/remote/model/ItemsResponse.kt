package com.example.gitsearch.data.remote.model

import com.squareup.moshi.Json

data class ItemsResponse (@Json(name= "items") val items : List<Item> )