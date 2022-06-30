package com.example.gitsearch.ui.detailRepoScreen

import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel

sealed class DetailFragmentState {
    object Idle : DetailFragmentState()
    object Loading : DetailFragmentState()
    data class DataLoadedAll(val model: ItemLocalModel) : DetailFragmentState()
    data class Error(val error: String?) : DetailFragmentState()
}