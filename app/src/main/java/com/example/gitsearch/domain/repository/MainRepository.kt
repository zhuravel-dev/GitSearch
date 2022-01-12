package com.example.gitsearch.domain.repository

import androidx.paging.PagingData
import com.example.gitsearch.data.model.Item
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getRepo(q: String) : Flow<PagingData<Item>>
    fun setSelectedId(id : Int)
    fun getDetailInfo() : Item?
}