package com.example.gitsearch.domain.repository

import com.example.gitsearch.data.model.Item

interface MainRepository {

    suspend fun getRepo(q: String) : List<Item>

}