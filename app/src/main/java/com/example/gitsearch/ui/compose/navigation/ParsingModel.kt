package com.example.gitsearch.ui.compose.navigation

import com.example.gitsearch.data.local.model.ItemLocalModel
import com.google.gson.Gson

val gson = Gson()

fun Any.toJsonString(): String = gson.toJson(this)

fun String.fromJsonToModel(): ItemLocalModel = gson.fromJson(this, ItemLocalModel::class.java)