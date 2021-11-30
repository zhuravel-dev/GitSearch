package com.example.gitsearch.data.repository

import com.example.gitsearch.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}