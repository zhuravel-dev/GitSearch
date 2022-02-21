package com.example.gitsearch.ui.pager

import androidx.paging.PagingData
import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class MainPagerState {
    object Idle : MainPagerState()
    object Loading : MainPagerState()
    data class DataLoaded(val data: PagingData<ItemLocalModel>) : MainPagerState()
    data class Error(val error: String?) : MainPagerState()
}