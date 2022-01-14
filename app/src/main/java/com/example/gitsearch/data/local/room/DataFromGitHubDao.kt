package com.example.gitsearch.data.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.gitsearch.data.local.model.ItemLocalModel

@Dao
interface DataFromGitHubDao {

    @Query("SELECT * FROM dataFromGitHub")
    fun getData(): PagingSource<Int, ItemLocalModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(repositoryFromGitHub: DataFromGitHubEntity)

    @Update
    fun updateAll()

    @Query("DELETE FROM dataFromGitHub")
    fun deleteAll()
}