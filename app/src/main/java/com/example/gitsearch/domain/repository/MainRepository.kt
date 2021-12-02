package com.example.gitsearch.domain.repository

import com.example.gitsearch.data.model.User

interface MainRepository {

    suspend fun getUsers() : List<User>

}