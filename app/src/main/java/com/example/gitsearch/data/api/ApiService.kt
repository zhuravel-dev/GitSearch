package com.example.gitsearch.data.api

import com.example.gitsearch.data.model.Items
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getRepositories(): List<Items>
}