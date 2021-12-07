package com.example.gitsearch.data.repository

import com.example.gitsearch.data.api.ApiService
import com.example.gitsearch.domain.repository.MainRepository
import javax.inject.Inject

data class MainRepositoryImpl @Inject constructor(private val apiService: ApiService): MainRepository {

    override suspend fun getRepo() = apiService.getRepositories()

}