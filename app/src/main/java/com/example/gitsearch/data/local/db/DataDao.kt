package com.example.gitsearch.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.ItemsResponse

@Dao
interface DataDao {

    @Query("SELECT * FROM users_info")
    fun getData(): PagingSource<Int, Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: ItemsResponse)

    @Query("DELETE FROM users_info")
    suspend fun deleteAll()
}