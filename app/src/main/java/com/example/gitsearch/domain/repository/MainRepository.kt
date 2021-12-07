package com.example.gitsearch.domain.repository

import com.example.gitsearch.data.model.Items

interface MainRepository {

    suspend fun getRepo() : List<Items>

}