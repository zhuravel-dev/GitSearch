package com.example.gitsearch.data.local.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.data.local.room.DataFromGitHubDB

@ExperimentalPagingApi
class PagingRemoteMediator(
    private val api: ApiService,
    private val dataBase: DataFromGitHubDB
) : RemoteMediator<Int, ItemLocalModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ItemLocalModel>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}