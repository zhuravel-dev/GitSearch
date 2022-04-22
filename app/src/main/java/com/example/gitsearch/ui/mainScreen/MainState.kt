package com.example.gitsearch.ui.mainScreen

import com.example.gitsearch.data.remote.model.ItemsResponse

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class DataLoaded(val data: ItemsResponse) : MainState()
    data class Error(val error: String?) : MainState()
}