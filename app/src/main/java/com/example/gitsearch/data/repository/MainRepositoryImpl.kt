package com.example.gitsearch.data.repository

import com.example.gitsearch.data.api.ApiService
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.domain.repository.MainRepository
import javax.inject.Inject

data class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MainRepository {

    private val cachedItems = ArrayList<Item>()
    private var selectedModel: Item? = null

    override suspend fun getRepo(q: String) = apiService.getRepositories(q).items.also {
        cachedItems.clear()
        cachedItems.addAll(it)
    }

    override fun setSelectedId(id : Int) {
        selectedModel = cachedItems.find { it.id == id }
    }

    override fun getDetailInfo(): Item? = selectedModel

}