package com.example.gitsearch.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.gitsearch.data.remote.model.Item
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface MainRepository {

    suspend fun getRepoFromNetwork(q: String) : Flow<PagingData<Item>>
    fun getRepoFromDB(q: String) : Flow<PagingData<Item>>
    fun getRepoFromMediator (q: String): Flow<PagingData<Item>>
    fun setSelectedId(id : Int)
    fun getDetailInfo() : Item?
}