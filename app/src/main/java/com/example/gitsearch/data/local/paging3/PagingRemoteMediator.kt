package com.example.gitsearch.data.local.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.data.local.db.DataDB
import com.example.gitsearch.data.remote.model.Item
import retrofit2.HttpException
import okio.IOException

const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class PagingRemoteMediator(
    private val api: ApiService,
    private val dataBase: DataDB
) : RemoteMediator<Int, Item>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ItemLocalModel>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        try {
            val response = api.getRepositories(page = page, pageSize = state.config.pageSize)
            val isEndOfList = response.items.isEmpty()
            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.dataDao().deleteAll()
                   // dataBase.getKeysDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
               /* val keys = response.map {
                    RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }*/
              //  dataBase.getKeysDao().insertAll(keys)
                dataBase.dataDao().insertData(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

   /* private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Cat>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }*/
}