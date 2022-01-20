package com.example.gitsearch.data.local.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gitsearch.data.remote.api.ApiService
import com.example.gitsearch.data.remote.model.Item
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class GithubPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        return try {
            val pageNumber: Int = params.key ?: 1
            val pageSize: Int = params.loadSize
            val response = apiService.getRepositories(query, pageNumber, pageSize)

            LoadResult.Page(
                data = response.items,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (response.items.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}