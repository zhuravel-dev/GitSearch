package com.example.gitsearch.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.ItemsResponse
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface MainRepository {
    suspend fun getDataFromNetwork(q: String): Flow<PagingData<Item>>
    suspend fun getDataFromMediatorSortedByStars(q: String): Flow<PagingData<ItemLocalModel>>
    suspend fun getDataFromMediatorSortedByUpdate(q: String): Flow<PagingData<ItemLocalModel>>
    suspend fun getModelById(id: Int): ItemLocalModel
    suspend fun getOneOwnerById(id: Int): OwnerLocalModel
    suspend fun getMainModel(): ItemLocalModel
    suspend fun getResponse(q: String? = null): ItemsResponse
}