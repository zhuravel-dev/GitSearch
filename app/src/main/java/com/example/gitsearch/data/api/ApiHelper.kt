package com.example.gitsearch.data.api

import com.example.gitsearch.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>

}