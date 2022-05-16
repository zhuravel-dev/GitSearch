package com.example.gitsearch.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner (
	@Json(name = "login") val login : String,
	@Json(name = "id") val id : Int,
	@Json(name = "avatar_url") val avatar_url : String,
	@Json(name = "url") val url : String
) : Parcelable