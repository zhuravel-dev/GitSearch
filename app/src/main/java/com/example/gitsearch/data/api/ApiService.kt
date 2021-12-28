package com.example.gitsearch.data.api

import com.example.gitsearch.data.model.ItemsResponse
import com.example.gitsearch.data.model.RepositoryDetailModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") q: String): ItemsResponse

    @GET("search/users")
    suspend fun getDetailInfo(): RepositoryDetailModel

}