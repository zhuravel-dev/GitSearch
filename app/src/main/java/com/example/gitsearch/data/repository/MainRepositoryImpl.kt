package com.example.gitsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitsearch.data.GithubPagingSource
import com.example.gitsearch.data.NETWORK_PAGE_SIZE
import com.example.gitsearch.data.api.ApiService
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

data class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MainRepository {

    private val cachedItems = ArrayList<Item>()
    private var selectedModel: Item? = null

    /*override suspend fun getRepo(q: String) = apiService.getRepositories(q).items.also {
        cachedItems.clear()
        cachedItems.addAll(it)
    }*/

    //suspend?
    override fun getRepo(q: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(apiService, q) }
        ).flow
    }

    override fun setSelectedId(id : Int) {
        selectedModel = cachedItems.find { it.id == id }
    }

    override fun getDetailInfo(): Item? = selectedModel

}