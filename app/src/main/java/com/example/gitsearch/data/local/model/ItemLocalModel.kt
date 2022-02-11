package com.example.gitsearch.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "items")
data class ItemLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ownerId: Int,
    val description: String? = null,
    val url: String,
    val updated_at: String,
    @ColumnInfo(name = "stars") val stargazers_count: Int,
    val language: String? = null,
    val topics: List<String>? = null,
    @ColumnInfo(name = "watchers") val watchers: Int
) : Parcelable {
    @Ignore var owner: OwnerLocalModel? = null
}