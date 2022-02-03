package com.example.gitsearch.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel

@Dao
interface DataDao {

    @Query("SELECT * FROM items")
    fun getData(): PagingSource<Int, ItemLocalModel>

    @Query("SELECT * FROM owner WHERE id=:id")
    suspend fun getOwnerById(id: Int?): List<OwnerLocalModel>

    @Query("SELECT * FROM owner WHERE id=:id")
    suspend fun getOneOwnerById(id: Int?): OwnerLocalModel

    @Query("SELECT * FROM items WHERE id=:id")
    suspend fun getItemById(id: Int?): ItemLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<ItemLocalModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(data: OwnerLocalModel)

    @Query("DELETE FROM items")
    suspend fun deleteAll()
}