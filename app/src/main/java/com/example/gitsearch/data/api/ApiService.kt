package com.example.gitsearch.data.api

import com.example.gitsearch.data.model.ItemsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories?")
    suspend fun getRepositories(@Query("q") query: String = ""): ItemsResponse
}