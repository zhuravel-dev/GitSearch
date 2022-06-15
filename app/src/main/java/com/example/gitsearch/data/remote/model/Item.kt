package com.example.gitsearch.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "owner") val owner: Owner = Owner(
        "0", 0, "0", "0", "0", "0",
        "0", "0", "0", "0",
        "0", "0", "0", "0"),
    @Json(name = "description") val description: String = "",
    @Json(name = "url") val url: String = "",
    @Json(name = "updated_at") val updated_at: String = "",
    @Json(name = "stargazers_count") val stargazers_count: Int = 0,
    @Json(name = "watchers_count") val watchers_count: Int = 0,
    @Json(name = "language") val language: String = "",
    @Json(name = "topics") val topics: List<String> = listOf(),
    @Json(name = "watchers") val watchers: Int = 0
) : Parcelable