package com.example.gitsearch.data.remote.api

import androidx.annotation.IntRange
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.ItemsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): ItemsResponse

    @GET("search/repositories/{id}")
    suspend fun getRepository(
        @Path("id") id: Int
    ): Item

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
        const val MAX_PAGE_SIZE = 5
    }
}