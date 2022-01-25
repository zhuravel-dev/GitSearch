package com.example.gitsearch.data.repository

import androidx.paging.*
import com.example.gitsearch.data.local.db.DataDB
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.paging3.GithubPagingSource
import com.example.gitsearch.data.local.paging3.PagingRemoteMediator
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

@ExperimentalPagingApi
data class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: DataDB
) : MainRepository {

    private val cachedItems = ArrayList<Item>()
    private var selectedModel: Item? = null

    override suspend fun getDataFromNetwork(q: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(apiService, q) }
        ).flow
    }

    override fun getDataFromMediator (q: String): Flow<PagingData<ItemLocalModel>> {
        val pagingSourceFactory = { database.getDataDao().getData() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = PagingRemoteMediator (
                q,
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map {
                it.apply {
                    owner = database.getDataDao().getOwner(ownerId).firstOrNull()
                }
            }
        }
    }

    override fun setSelectedId(id: Int) {
        selectedModel = cachedItems.find { it.id == id }
    }

    override fun getDetailInfo(detailData: ItemLocalModel): ItemLocalModel {
        return detailData
    }
}