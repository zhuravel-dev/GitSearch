package com.example.gitsearch.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.remote.model.Item
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface MainRepository {

    suspend fun getDataFromNetwork(q: String) : Flow<PagingData<Item>>
    fun getDataFromMediator (q: String): Flow<PagingData<ItemLocalModel>>
    fun setSelectedId(id : Int)
    fun getDetailInfo(detailData: ItemLocalModel) : ItemLocalModel
}