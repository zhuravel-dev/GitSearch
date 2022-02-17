package com.example.gitsearch.ui.activities

import androidx.paging.PagingData
import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class DataLoaded(val data: PagingData<ItemLocalModel>) : MainState()
    data class Error(val error: String?) : MainState()

}