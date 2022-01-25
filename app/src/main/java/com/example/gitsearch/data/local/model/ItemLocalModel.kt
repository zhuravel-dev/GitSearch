package com.example.gitsearch.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "items")
data class ItemLocalModel(
    @PrimaryKey var id: Int? = 0,
    var name: String? = null,
    var ownerId: Int? = null,
    var description: String? = null,
    var url: String? = null,
    var updated_at: String? = null,
    var stargazers_count: Int? = null,
    var language: String? = null,
    var topics: List<String>? = null,
    var watchers: Int? = null
) : Parcelable {
    @Ignore var owner: OwnerLocalModel? = null
}