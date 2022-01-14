package com.example.gitsearch.domain.repository

import androidx.paging.PagingData
import com.example.gitsearch.data.remote.model.Item
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getRepo(q: String) : Flow<PagingData<Item>>
    //suspend fun getRepo(q: String) : List<Item>
    suspend fun getRepoForDetailScreen() : Item
    fun setSelectedId(id : Int)
    fun getDetailInfo() : Item?
}