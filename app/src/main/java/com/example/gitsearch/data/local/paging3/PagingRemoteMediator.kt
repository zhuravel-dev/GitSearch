package com.example.gitsearch.data.local.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitsearch.data.local.db.DataDB
import com.example.gitsearch.data.local.db.RemoteKey
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel
import com.example.gitsearch.data.remote.api.ApiService
import okio.IOException
import retrofit2.HttpException

const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class PagingRemoteMediator(
    private val query: String,
    private val api: ApiService,
    private val dataBase: DataDB
) : RemoteMediator<Int, ItemLocalModel>() {

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
            else -> pageKeyData as Int
        }

        try {
            val response = api.getRepositories(query = query, page = page, pageSize = state.config.pageSize)
            val isEndOfList = response.items.isEmpty()
            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.getDataDao().deleteAll()
                    dataBase.getKeysDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.items.map {
                    RemoteKey(it.id ?: 0, prevKey = prevKey, nextKey = nextKey)
                }
                dataBase.getKeysDao().insertAll(keys)
                dataBase.getDataDao().let { dao ->
                    dao.insertData(response.items.map {
                        it.owner?.run {
                            dao.insertOwner(OwnerLocalModel(
                                id,
                                login,
                                avatar_url
                            ))
                        }
                        it.run {
                            ItemLocalModel(
                                id,
                                name,
                                ownerId = owner?.id ?: 0,
                                description,
                                url,
                                updated_at,
                                stargazers_count,
                                language,
                                topics,
                                watchers
                            )
                        }
                    })
                }
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, ItemLocalModel>
    ): Any {
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
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ItemLocalModel>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                dataBase.getKeysDao().getRemoteKeysUserId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ItemLocalModel>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { user -> dataBase.getKeysDao().getRemoteKeysUserId(user.id ?: 0) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, ItemLocalModel>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user -> dataBase.getKeysDao().getRemoteKeysUserId(user.id ?: 0) }
    }
}