package com.example.gitsearch.ui.mainScreen

import androidx.paging.PagingData
import com.example.gitsearch.data.local.model.ItemLocalModel
import kotlinx.coroutines.flow.Flow

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class DataLoaded(val data: Flow<PagingData<ItemLocalModel>>) : MainState()
    data class Error(val error: String?) : MainState()
}