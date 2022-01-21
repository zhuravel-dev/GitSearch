package com.example.gitsearch.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.gitsearch.data.remote.model.Item
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface MainRepository {

    suspend fun getDataFromNetwork(q: String) : Flow<PagingData<Item>>
    fun getDataFromDB() : Flow<PagingData<Item>>
    fun getDataFromMediator (): Flow<PagingData<Item>>
    fun setSelectedId(id : Int)
    fun getDetailInfo() : Item?
}