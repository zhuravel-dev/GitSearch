package com.example.gitsearch.ui.detailRepoScreen

import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class DetailState {
    object Idle : DetailState()
    object Loading : DetailState()
    data class DataLoadedAll(val model: ItemLocalModel) : DetailState()
    data class Error(val error: String?) : DetailState()
}