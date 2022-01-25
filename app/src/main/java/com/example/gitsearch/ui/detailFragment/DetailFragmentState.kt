package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class DetailFragmentState {

    object Idle : DetailFragmentState()
    object Loading : DetailFragmentState()
    data class DataLoaded(val detailData: ItemLocalModel) : DetailFragmentState()
    data class Error(val error: String?) : DetailFragmentState()

}