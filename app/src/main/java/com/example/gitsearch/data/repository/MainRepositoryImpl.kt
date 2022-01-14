package com.example.gitsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitsearch.data.local.paging3.GithubPagingSource
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

data class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MainRepository {

    private val cachedItems = ArrayList<Item>()
    private var selectedModel: Item? = null

    override suspend fun getRepo(q: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(apiService, q) }
        ).flow
    }

    override suspend fun getRepoForDetailScreen() = apiService.getDetailRepositories().also {
        cachedItems.clear()
        cachedItems.addAll(listOf(it))
    }

    override fun setSelectedId(id: Int) {
        selectedModel = cachedItems.find { it.id == id }
    }

    override fun getDetailInfo(): Item? = selectedModel
}