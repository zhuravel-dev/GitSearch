package com.example.gitsearch.data.local.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.ItemsResponse

@Dao
interface DataDao {

    @Query("SELECT * FROM dataFromGitHub")
    fun getData(): PagingSource<Int, Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: ItemsResponse)

    @Update
    fun updateAll()

    @Query("DELETE FROM dataFromGitHub")
    fun deleteAll()
}