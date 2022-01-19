package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.model.Item

sealed class DetailFragmentState {

    object Idle : DetailFragmentState()
    object Loading : DetailFragmentState()
    data class DataLoaded(val detailData: Item?) : DetailFragmentState()
    data class Error(val error: String?) : DetailFragmentState()

}