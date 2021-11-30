package com.example.gitsearch.data.api

import com.example.gitsearch.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

}